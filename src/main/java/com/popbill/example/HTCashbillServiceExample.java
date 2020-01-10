/*
 *  * 팝빌 홈택스 현금영수증 연계 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/htcashbill/tutorial/java
 * - 업데이트 일자 : 2019-01-04
 * - 연동 기술지원 연락처 : 1600-9854 / 070-4304-2991~2
 * - 연동 기술지원 이메일 : code@linkhub.co.kr
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 * 	  util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 링크허브 가입시 메일로
 *    발급받은 인증정보를 참조하여 변경합니다.
 * 2) 팝빌 개발용 사이트(test.popbill.com)에 연동회원으로 가입합니다.
 * 3) 홈택스 인증 처리를 합니다. (부서사용자등록 / 공인인증서 등록)
 *    - 팝빌로그인 > [홈택스연동] > [환경설정] > [인증 관리] 메뉴
 *    - 홈택스연동 인증 관리 팝업 URL(GetCertificatePopUpURL API) 반환된 URL을 이용하여
 *      홈택스 인증 처리를 합니다.
 *
 * Copyright 2006-2014 linkhub.co.kr, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.popbill.example;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.HTCashbillService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.hometax.HTCashbillJobState;
import com.popbill.api.hometax.HTCashbillSearchResult;
import com.popbill.api.hometax.HTCashbillSummary;
import com.popbill.api.hometax.QueryType;


/*
 * 팝빌 홈택스연계 현금영수증 API 예제.
 */
@Controller
@RequestMapping("HTCashbillService")
public class HTCashbillServiceExample {

    @Autowired
    private HTCashbillService htCashbillService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "HTCashbill/index";
    }

    @RequestMapping(value = "requestJob", method = RequestMethod.GET)
    public String requestJob(Model m) {
        /*
         * 현금영수증 매출/매입 내역 수집을 요청합니다
         * - 홈택스연동 프로세스는 "[홈택스연동(현금영수증) API 연동매뉴얼] >
         *   1.1. 홈택스연동(현금영수증) API 구성" 을 참고하시기 바랍니다.
         * - 수집 요청후 반환받은 작업아이디(JobID)의 유효시간은 1시간 입니다.
         */

        // 현금영수증 유형, SELL-매출, BUY-매입
        QueryType TIType = QueryType.SELL;

        // 시작일자, 표시형식(yyyyMMdd)
        String SDate = "20181201";

        // 종료일자, 표시형식(yyyyMMdd)
        String EDate = "20190104";

        try {
            String jobID = htCashbillService.requestJob(testCorpNum, TIType, SDate, EDate);
            m.addAttribute("Result", jobID);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getJobState", method = RequestMethod.GET)
    public String getJobState(Model m) {
        /*
         * 수집 요청 상태를 확인합니다.
         * - 응답항목 관한 정보는 "[홈택스연동 (현금영수증) API 연동매뉴얼] >
         *   3.1.2. GetJobState(수집 상태 확인)" 을 참고하시기 바랍니다.
         */

        // 수집요청(requestJob)시 반환받은 작업아이디
        String jobID = "019010415000000005";

        try {
            HTCashbillJobState jobState = htCashbillService.getJobState(testCorpNum, jobID);
            m.addAttribute("JobState", jobState);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTTaxinvoice/GetJobState";
    }

    @RequestMapping(value = "listActiveJob", method = RequestMethod.GET)
    public String listActiveJob(Model m) {
        /*
         * 수집 요청건들에 대한 상태 목록을 확인합니다.
         * - 수집 요청 작업아이디(JobID)의 유효시간은 1시간 입니다.
         * - 응답항목에 관한 정보는 "[홈택스연동 (현금영수증) API 연동매뉴얼] >
         *   3.1.3. ListActiveJob(수집 상태 목록 확인)" 을 참고하시기 바랍니다.
         */

        try {
            HTCashbillJobState[] jobStates = htCashbillService.listActiveJob(testCorpNum);
            m.addAttribute("JobStates", jobStates);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTCashbill/ListActiveJob";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /*
         * 현금영수증 매입/매출 내역의 수집 결과를 조회합니다.
         * - 응답항목에 관한 정보는 "[홈택스연동 (현금영수증) API 연동매뉴얼] >
         *   3.2.1. Search(수집 결과 조회)" 을 참고하시기 바랍니다.
         */

        // 수집 요청시 발급받은 작업아이디
        String jobID = "019010415000000005";

        // 거래용도, P-소득공제용, C-지출증빙용
        String[] TradeUsage = {"P", "C"};

        // 거래유형, N-일반 현금영수증, C-취소현금영수증
        String[] TradeType = {"N", "C"};

        // 페이지번호
        int Page = 1;

        // 페이지당 목록개수
        int PerPage = 10;

        // 정렬방향 D-내림차순, A-오름차순
        String Order = "D";

        try {
            HTCashbillSearchResult searchInfo = htCashbillService.search(testCorpNum,
                    jobID, TradeUsage, TradeType, Page, PerPage, Order);
            m.addAttribute("SearchResult", searchInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTCashbill/SearchResult";
    }

    @RequestMapping(value = "summary", method = RequestMethod.GET)
    public String summary(Model m) {
        /*
         * 현금영수증 매입/매출 내역의 수집 결과 요약정보를 조회합니다.
         * - 응답항목에 관한 정보는 "[홈택스연동 (현금영수증) API 연동매뉴얼] >
         *   3.2.2. Summary(수집 결과 요약정보 조회)" 을 참고하시기 바랍니다.
         */

        // 수집 요청시 발급받은 작업아이디
        String jobID = "019010415000000005";

        // 거래용도, P-소득공제용, C-지출증빙용
        String[] TradeUsage = {"P", "C"};

        // 거래유형, N-일반 현금영수증, C-취소현금영수증
        String[] TradeType = {"N", "C"};

        try {
            HTCashbillSummary summaryInfo = htCashbillService.summary(testCorpNum,
                    jobID, TradeUsage, TradeType);
            m.addAttribute("SummaryResult", summaryInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "HTCashbill/Summary";
    }

    @RequestMapping(value = "getCertificatePopUpURL", method = RequestMethod.GET)
    public String getCertificatePopUpURL(Model m) {
        /*
         * 홈택스연동 인증관리를 위한 URL을 반환합니다.
         * 인증방식에는 부서사용자/공인인증서 인증 방식이 있습니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */

        try {

            String url = htCashbillService.getCertificatePopUpURL(testCorpNum);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getCertificateExpireDate", method = RequestMethod.GET)
    public String getCertificateExpireDate(Model m) {
        /*
         * 팝빌에 등록된 홈택스 공인인증서의 만료일자를 반환합니다.
         */
        try {

            Date expireDate = htCashbillService.getCertificateExpireDate(testCorpNum);

            m.addAttribute("Result", expireDate);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "checkCertValidation", method = RequestMethod.GET)
    public String checkCertValidation(Model m) {
        /*
         * 팝빌에 등록된 공인인증서의 홈택스 로그인을 테스트합니다.
         */
        try {

            Response response = htCashbillService.checkCertValidation(testCorpNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "registDeptUser", method = RequestMethod.GET)
    public String registDeptUser(Model m) {
        /*
         * 홈택스 현금영수증 부서사용자 계정을 팝빌에 등록합니다.
         */

        // 홈택스에서 생성한 현금영수증 부서사용자 아이디
        String deptUserID = "userid";

        // 홈택스에서 생성한 현금영수증 부서사용자 비밀번호
        String deptUserPWD = "passwd";

        try {

            Response response = htCashbillService.registDeptUser(testCorpNum, deptUserID, deptUserPWD);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "checkDeptUser", method = RequestMethod.GET)
    public String checkDeptUser(Model m) {
        /*
         * 팝빌에 등록된 현금영수증 부서사용자 아이디를 확인합니다.
         */
        try {

            Response response = htCashbillService.checkDeptUser(testCorpNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "checkLoginDeptUser", method = RequestMethod.GET)
    public String checkLoginDeptUser(Model m) {
        /*
         * 팝빌에 등록된 현금영수증 부서사용자 계정정보를 이용하여 홈택스 로그인을 테스트합니다.
         */
        try {

            Response response = htCashbillService.checkLoginDeptUser(testCorpNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "deleteDeptUser", method = RequestMethod.GET)
    public String deleteDeptUser(Model m) {
        /*
         * 팝빌에 등록된 현금영수증 부서사용자 계정정보를 삭제합니다.
         */
        try {

            Response response = htCashbillService.deleteDeptUser(testCorpNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

}

