/**
  * 팝빌 홈택스 현금영수증 API Java SDK SpringMVC Example
  *
  * SpringMVC 연동 튜토리얼 안내 : https://developers.popbill.com/guide/htcashbill/java/getting-started/tutorial?fwn=springmvc
  * 연동 기술지원 연락처 : 1600-9854
  * 연동 기술지원 이메일 : code@linkhubcorp.com
  *
  * <테스트 연동개발 준비사항>
  * 1) 홈택스 로그인 인증정보를 등록합니다. (부서사용자등록 / 공동인증서 등록)
  *    - 팝빌로그인 > [홈택스수집] > [환경설정] > [인증 관리] 메뉴
  *    - 홈택스수집 인증 관리 팝업 URL(GetCertificatePopUpURL API) 반환된 URL을 이용하여
  *      홈택스 인증 처리를 합니다.
  */
package com.popbill.example;

import java.util.Date;
import java.util.Locale;
import com.popbill.api.ChargeInfo;
import com.popbill.api.FlatRateState;
import com.popbill.api.HTCashbillService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.hometax.HTCashbillJobState;
import com.popbill.api.hometax.HTCashbillSearchResult;
import com.popbill.api.hometax.HTCashbillSummary;
import com.popbill.api.hometax.QueryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 팝빌 홈택스연계 현금영수증 API 예제.
 */
@Controller
@RequestMapping("HTCashbillService")
public class HTCashbillServiceExample {

    @Autowired
    private HTCashbillService htCashbillService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String CorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String UserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "HTCashbill/index";
    }

    @RequestMapping(value = "requestJob", method = RequestMethod.GET)
    public String requestJob(Model m) {
        /**
         * 홈택스에 신고된 현금영수증 매입/매출 내역 수집을 팝빌에 요청합니다.
         * - 최대 3개월 단위로 수집 요청이 가능하며, 수집기한의 제한은 없습니다.
         * - API를 호출하고 반환 받은 작업아이디(JobID)는 수집을 요청한 시점으로부터 1시간 동안만 유효합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/job#RequestJob
         */

        // 현금영수증 유형, SELL-매출, BUY-매입
        QueryType queryType = QueryType.SELL;

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20230102";

        // 종료일자, 날짜형식(yyyyMMdd)
        String EDate = "20230131";

        try {
            String jobID = htCashbillService.requestJob(CorpNum, queryType, SDate, EDate, UserID);
            m.addAttribute("Result", jobID);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getJobState", method = RequestMethod.GET)
    public String getJobState(Model m) {
        /**
         * [RequestJob – 수집 요청] API를 호출하고 반환 받은 작업아이디(JobID)를 이용하여 수집 상태를 확인합니다.
         * - 수집상태(jobState) = 3(완료) 이면서, 수집 결과코드(errorCode) = 1(수집성공)인 경우 [Search - 수집 내역 확인] 이 가능합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/job#GetJobState
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "";

        try {
            HTCashbillJobState jobState = htCashbillService.getJobState(CorpNum, jobID, UserID);
            m.addAttribute("JobState", jobState);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTCashbill/GetJobState";
    }

    @RequestMapping(value = "listActiveJob", method = RequestMethod.GET)
    public String listActiveJob(Model m) {
        /**
         * [RequestJob – 수집 요청] API를 호출하고 반환 받은 작업아이디(JobID) 목록의 수집 상태를 확인합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/job#ListActiveJob
         */

        try {
            HTCashbillJobState[] jobStates = htCashbillService.listActiveJob(CorpNum, UserID);
            m.addAttribute("JobStates", jobStates);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTCashbill/ListActiveJob";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /**
         * 홈택스에서 수집된 현금영수증 매입/매출 내역을 확인합니다.
         * - 18개 항목으로 구성된 내역 확인이 가능합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/search#Search
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "";

        // 거래구분 배열 ("P" 와 "C" 중 선택, 다중 선택 가능)
        // └ P = 소득공제용 , C = 지출증빙용
        // - 미입력 시 전체조회
        String[] TradeUsage = {"P", "C"};

        // 문서형태 배열 ("N" 와 "C" 중 선택, 다중 선택 가능)
        // └ N = 일반 현금영수증 , C = 취소현금영수증
        // - 미입력 시 전체조회
        String[] TradeType = {"N", "C"};

        // 페이지번호 (기본값 = 1)
        int Page = 1;

        // 페이지당 목록개수 (기본값 = 500 , 최대 = 1000)
        int PerPage = 10;

        // 정렬방향, "D" / "A" 중 택 1
        // └ D = 내림차순(기본값) , A = 오름차순
        String Order = "D";

        try {
            HTCashbillSearchResult searchInfo = htCashbillService.search(CorpNum, jobID, TradeUsage, TradeType, Page,
                    PerPage, Order, UserID);
            m.addAttribute("SearchResult", searchInfo);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTCashbill/SearchResult";
    }

    @RequestMapping(value = "summary", method = RequestMethod.GET)
    public String summary(Model m) {
        /**
         * 홈택스에서 수집된 현금영수증 매입/매출 내역의 합계정보를 제공합니다.
         * - 합계정보 - 수집 건수, 공급가액 합계, 세액 합계, 총계 (공급가액 합계+세액 합계)
         * - https://developers.popbill.com/reference/htcashbill/java/api/search#Summary
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "";

        // 거래구분 배열 ("P" 와 "C" 중 선택, 다중 선택 가능)
        // └ P = 소득공제용 , C = 지출증빙용
        // - 미입력 시 전체조회
        String[] TradeUsage = {"P", "C"};

        // 문서형태 배열 ("N" 와 "C" 중 선택, 다중 선택 가능)
        // └ N = 일반 현금영수증 , C = 취소현금영수증
        // - 미입력 시 전체조회
        String[] TradeType = {"N", "C"};

        try {
            HTCashbillSummary summaryInfo = htCashbillService.summary(CorpNum, jobID, TradeUsage, TradeType, UserID);
            m.addAttribute("SummaryResult", summaryInfo);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTCashbill/Summary";
    }

    @RequestMapping(value = "getCertificatePopUpURL", method = RequestMethod.GET)
    public String getCertificatePopUpURL(Model m) {
        /**
         * 홈택스 인증정보를 등록하는 팝업 URL을 반환합니다.
         * - 권장 사이즈 : width = 800px / height = 660px
         * - 반환되는 URL은 30초 동안만 사용이 가능합니다.
         * - 반환되는 URL에서만 유효한 세션을 포함하고 있습니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/cert#GetCertificatePopUpURL
         */

        try {
            String url = htCashbillService.getCertificatePopUpURL(CorpNum, UserID);
            m.addAttribute("Result", url);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getCertificateExpireDate", method = RequestMethod.GET)
    public String getCertificateExpireDate(Model m) {
        /**
         * 팝빌에 등록된 인증서의 만료일자를 확인합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/cert#GetCertificateExpireDate
         */

        try {
            Date expireDate = htCashbillService.getCertificateExpireDate(CorpNum, UserID);
            m.addAttribute("Result", expireDate);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "checkCertValidation", method = RequestMethod.GET)
    public String checkCertValidation(Model m) {
        /**
         * 팝빌에 등록된 인증서로 홈택스 로그인 가능 여부를 확인합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/cert#CheckCertValidation
         */

        try {
            Response response = htCashbillService.checkCertValidation(CorpNum, UserID);
            m.addAttribute("Response", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "registDeptUser", method = RequestMethod.GET)
    public String registDeptUser(Model m) {
        /**
         * 팝빌에 부서사용자를 등록합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/cert#RegistDeptUser
         */

        // 홈택스에서 생성한 현금영수증 부서사용자 아이디
        String deptUserID = "userid";

        // 홈택스에서 생성한 현금영수증 부서사용자 비밀번호
        String deptUserPWD = "passwd";

        try {
            Response response = htCashbillService.registDeptUser(CorpNum, deptUserID, deptUserPWD, UserID);
            m.addAttribute("Response", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "checkDeptUser", method = RequestMethod.GET)
    public String checkDeptUser(Model m) {
        /**
         * 팝빌에 부서사용자 등록 여부를 확인합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/cert#CheckDeptUser
         */

        try {
            Response response = htCashbillService.checkDeptUser(CorpNum, UserID);
            m.addAttribute("Response", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "checkLoginDeptUser", method = RequestMethod.GET)
    public String checkLoginDeptUser(Model m) {
        /**
         * 팝빌에 등록된 부서사용자로 홈택스 로그인 가능 여부를 확인합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/cert#CheckLoginDeptUser
         */

        try {
            Response response = htCashbillService.checkLoginDeptUser(CorpNum, UserID);
            m.addAttribute("Response", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "deleteDeptUser", method = RequestMethod.GET)
    public String deleteDeptUser(Model m) {
        /**
         * 팝빌에 등록된 부서사용자 계정을 삭제합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/api/cert#DeleteDeptUser
         */

        try {
            Response response = htCashbillService.deleteDeptUser(CorpNum, UserID);
            m.addAttribute("Response", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
    public String chargeInfo(Model m) {
        /**
         * 팝빌 홈택스수집 API 서비스 과금정보를 확인합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/common-api/point#GetChargeInfo
         */

        try {
            ChargeInfo chrgInfo = htCashbillService.getChargeInfo(CorpNum, UserID);
            m.addAttribute("ChargeInfo", chrgInfo);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

    @RequestMapping(value = "getFlatRatePopUpURL", method = RequestMethod.GET)
    public String getFlatRatePopUpURL(Model m) {
        /**
         * 정액제를 신청하는 팝업 URL을 반환합니다.
         * - 권장 사이즈 : width = 800px / height = 700px
         * - 반환되는 URL은 30초 동안만 사용이 가능합니다.
         * - 반환되는 URL에서만 유효한 세션을 포함하고 있습니다.
         * - https://developers.popbill.com/reference/htcashbill/java/common-api/point#GetFlatRatePopUpURL
         */

        try {
            String url = htCashbillService.getFlatRatePopUpURL(CorpNum, UserID);
            m.addAttribute("Result", url);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getFlatRateState", method = RequestMethod.GET)
    public String getFlatRateState(Model m) {
        /**
         * 홈택스수집 정액제 서비스 상태를 확인합니다.
         * - https://developers.popbill.com/reference/htcashbill/java/common-api/point#GetFlatRateState
         */

        try {
            FlatRateState flatRateInfo = htCashbillService.getFlatRateState(CorpNum, UserID);
            m.addAttribute("State", flatRateInfo);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTCashbill/GetFlatRateState";
    }

}