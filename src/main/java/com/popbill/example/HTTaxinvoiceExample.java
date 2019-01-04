/*
 * 팝빌 홈택스 전자세금계산서 연계 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : http://blog.linkhub.co.kr/591/
 * - 업데이트 일자 : 2019-01-04
 * - 연동 기술지원 연락처 : 1600-9854 / 070-4304-2991~2
 * - 연동 기술지원 이메일 : code@linkhub.co.kr
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 * 	  util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 링크허브 가입시 메일로
 *    발급받은 인증정보를 참조하여 변경합니다.
 * 2) 팝빌 개발용 사이트(test.popbill.com)에 연동회원으로 가입합니다.
 * 3) 홈택스에서 이용가능한 공인인증서를 등록합니다.
 *    - 팝빌로그인 > [홈택스연계] > [환경설정] > [공인인증서 관리] 메뉴
 *    - 공인인증서 등록(GetCertificatePopUpURL API) 반환된 URL을 이용하여
 *      팝업 페이지에서 공인인증서 등록
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

import com.popbill.api.ChargeInfo;
import com.popbill.api.FlatRateState;
import com.popbill.api.HTTaxinvoiceService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.hometax.HTTaxinvoice;
import com.popbill.api.hometax.HTTaxinvoiceJobState;
import com.popbill.api.hometax.HTTaxinvoiceSearchResult;
import com.popbill.api.hometax.HTTaxinvoiceSummary;
import com.popbill.api.hometax.HTTaxinvoiceXMLResponse;
import com.popbill.api.hometax.QueryType;


/*
 * 팝빌 홈택스연계 전자세금계산서 API 예제.
 */
@Controller
@RequestMapping("HTTaxinvoiceService")
public class HTTaxinvoiceExample {

    @Autowired
    private HTTaxinvoiceService htTaxinvoiceService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "HTTaxinvoice/index";
    }

    @RequestMapping(value = "requestJob", method = RequestMethod.GET)
    public String requestJob(Model m) {
        /*
         * 전자세금계산서 매출/매입 내역 수집을 요청합니다
         * - 홈택스연동 프로세스는 "[홈택스연동(전자세금계산서계산서) API 연동매뉴얼] >
         *   1.1. 홈택스연동(전자세금계산서) API 구성" 을 참고하시기 바랍니다.
         * - 수집 요청후 반환받은 작업아이디(JobID)의 유효시간은 1시간 입니다.
         */

        // 전자세금계산서 유형, SELL-매출, BUY-매입, TRUSTEE-수탁
        QueryType TIType = QueryType.SELL;

        // 일자유형, W-작성일자, I-발행일자, S-전송일자
        String DType = "W";

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20181201";

        // 종료일자, 닐짜형식(yyyyMMdd)
        String EDate = "20190104";

        try {
            String jobID = htTaxinvoiceService.requestJob(testCorpNum, TIType,
                    DType, SDate, EDate);
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
         * - 응답항목 관한 정보는 "[홈택스연동 (전자세금계산서계산서) API 연동매뉴얼] >
         *   3.1.2. GetJobState(수집 상태 확인)" 을 참고하시기 바랍니다.
         */

        // 수집요청(requestJob)시 반환받은 작업아이디
        String jobID = "019010415000000002";

        try {
            HTTaxinvoiceJobState jobState = htTaxinvoiceService.getJobState(testCorpNum, jobID);
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
         * - 응답항목에 관한 정보는 "[홈택스연동 (전자세금계산서계산서) API 연동매뉴얼] >
         *   3.1.3. ListActiveJob(수집 상태 목록 확인)" 을 참고하시기 바랍니다.
         */

        try {
            HTTaxinvoiceJobState[] jobStates = htTaxinvoiceService.listActiveJob(testCorpNum);
            m.addAttribute("JobStates", jobStates);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTTaxinvoice/ListActiveJob";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /*
         * 전자세금계산서 매입/매출 내역의 수집 결과를 조회합니다.
         * - 응답항목에 관한 정보는 "[홈택스연동 (전자세금계산서계산서) API 연동매뉴얼] >
         *   3.2.1. Search(수집 결과 조회)" 을 참고하시기 바랍니다.
         */

        // 수집 요청시 발급받은 작업아이디
        String jobID = "019010415000000002";

        // 문서형태, N-일반, M-수정
        String[] Type = {"N", "M"};

        // 과세형태, T-과세, N-면세, Z-영세
        String[] TaxType = {"T", "Z", "N"};

        // 영수/청구 R-영수, C-청구, N-없음
        String[] PurposeType = {"R", "C", "N"};

        // 종사업장 유무, 공백-전체조회, 0-종사업장번호 없음, 1-종사업장번호 있음
        String TaxRegIDYN = "";

        // 종사업장 유형, S-공급자, B-공급받는자, T-수탁자
        String TaxRegIDType = "S";

        // 종사업장번호, 다수기재시 콤마(",")로 구분하여 구성 ex) "0001,0002"
        String TaxRegID = "";

        // 페이지번호
        int Page = 1;

        // 페이지당 목록개수
        int PerPage = 10;

        // 정렬방향 D-내림차순, A-오름차순
        String Order = "D";

        try {
            HTTaxinvoiceSearchResult searchInfo = htTaxinvoiceService.search(testCorpNum,
                    jobID, Type, TaxType, PurposeType, TaxRegIDYN, TaxRegIDType,
                    TaxRegID, Page, PerPage, Order);
            m.addAttribute("SearchResult", searchInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTTaxinvoice/SearchResult";
    }

    @RequestMapping(value = "summary", method = RequestMethod.GET)
    public String summary(Model m) {
        /*
         * 전자세금계산서 매입/매출 내역의 수집 결과 요약정보를 조회합니다.
         * - 응답항목에 관한 정보는 "[홈택스연동 (전자세금계산서계산서) API 연동매뉴얼] >
         *   3.2.2. Summary(수집 결과 요약정보 조회)" 을 참고하시기 바랍니다.
         */

        // 수집 요청시 발급받은 작업아이디
        String jobID = "019010415000000002";

        // 문서형태, N-일반, M-수정
        String[] Type = {"N", "M"};

        // 과세형태, T-과세, N-면세, Z-영세
        String[] TaxType = {"T", "Z", "N"};

        // 영수/청구 R-영수, C-청구, N-없음
        String[] PurposeType = {"R", "C", "N"};

        // 종사업장 유무, 공백-전체조회, 0-종사업장번호 없음, 1-종사업장번호 있음
        String TaxRegIDYN = "";

        // 종사업장 유형, S-공급자, B-공급받는자, T-수탁자
        String TaxRegIDType = "S";

        // 종사업장번호, 다수기재시 콤마(",")로 구분하여 구성 ex) "0001,0002"
        String TaxRegID = "";

        try {
            HTTaxinvoiceSummary summaryInfo = htTaxinvoiceService.summary(testCorpNum,
                    jobID, Type, TaxType, PurposeType, TaxRegIDYN, TaxRegIDType, TaxRegID);
            m.addAttribute("SummaryResult", summaryInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "HTTaxinvoice/Summary";
    }
    
    @RequestMapping(value = "getTaxinvoice", method = RequestMethod.GET)
    public String getTaxinvoice(Model m) {
        /*
         * 전자세금계산서 1건의 상세정보를 확인합니다.
         * - 응답항목에 관한 정보는 "[홈택스연동 (전자세금계산서계산서) API 연동매뉴얼] >
         *   4.1.2. GetTaxinvoice 응답전문 구성" 을 참고하시기 바랍니다.
         */

        // 전자세금계산서 국세청승인번호
        String ntsconfirmNum = "201812044100020300000c0a";

        try {
            HTTaxinvoice taxinvoiceInfo = htTaxinvoiceService.getTaxinvoice(testCorpNum,
                    ntsconfirmNum);

            m.addAttribute("Taxinvoice", taxinvoiceInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTTaxinvoice/Taxinvoice";
    }

    @RequestMapping(value = "getXML", method = RequestMethod.GET)
    public String getXML(Model m) {
        /*
         * XML 형식의 전자세금계산서 상세정보를 확인합니다.
         * - 응답항목에 관한 정보는 "[홈택스연동 (전자세금계산서계산서) API 연동매뉴얼] >
         *   3.2.4. GetXML(상세정보 확인 - XML)" 을 참고하시기 바랍니다.
         */

        // 전자세금계산서 국세청승인번호
        String ntsconfirmNum = "20161202410002030000196d";

        try {
            HTTaxinvoiceXMLResponse xmlResponse = htTaxinvoiceService.getXML(testCorpNum,
                    ntsconfirmNum);

            m.addAttribute("TaxinvoiceXML", xmlResponse);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTTaxinvoice/TaxinvoiceXML";
    }

    @RequestMapping(value = "getPopUpURL", method = RequestMethod.GET)
    public String getPopUpURL(Model m) {
        /*
         * 홈택스 전자세금계산서 보기 팝업 URL을 반환합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */

        // 조회할 전자세금계산서 국세청승인번호
        String NTSConfirmNum = "20161202410002030000196d";

        try {

            String url = htTaxinvoiceService.getPopUpURL(testCorpNum, NTSConfirmNum);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }
    
    @RequestMapping(value = "getCertificatePopUpURL", method = RequestMethod.GET)
    public String getCertificatePopUpURL(Model m) {
        /*
         * 홈택스연동 인증관리를 위한 URL을 반환합니다.
         * 인증방식에는 부서사용자/공인인증서 인증 방식이 있습니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */

        try {

            String url = htTaxinvoiceService.getCertificatePopUpURL(testCorpNum);

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

            Date expireDate = htTaxinvoiceService.getCertificateExpireDate(testCorpNum);

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

            Response response = htTaxinvoiceService.checkCertValidation(testCorpNum);

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
         * 홈택스 전자세금계산서 부서사용자 계정을 팝빌에 등록합니다.
         */

        // 홈택스에서 생성한 전자세금계산서 부서사용자 아이디
        String deptUserID = "userid";

        // 홈택스에서 생성한 전자세금계산서 부서사용자 비밀번호
        String deptUserPWD = "passwd";

        try {

            Response response = htTaxinvoiceService.registDeptUser(testCorpNum, deptUserID, deptUserPWD);

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
         * 팝빌에 등록된 전자세금계산서 부서사용자 아이디를 확인합니다.
         */

        try {

            Response response = htTaxinvoiceService.checkDeptUser(testCorpNum);

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
         * 팝빌에 등록된 전자세금계산서 부서사용자 계정정보를 이용하여 홈택스 로그인을 테스트합니다.
         */

        try {

            Response response = htTaxinvoiceService.checkLoginDeptUser(testCorpNum);

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
         * 팝빌에 등록된 전자세금계산서 부서사용자 계정정보를 삭제합니다.
         */

        try {

            Response response = htTaxinvoiceService.deleteDeptUser(testCorpNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }
    
    @RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
    public String chargeInfo(Model m) {
        /*
         * 홈택스연동 API 서비스 과금정보를 확인합니다.
         */

        try {

            ChargeInfo chrgInfo = htTaxinvoiceService.getChargeInfo(testCorpNum);
            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

    @RequestMapping(value = "getFlatRatePopUpURL", method = RequestMethod.GET)
    public String getFlatRatePopUpURL(Model m) {
        /*
         * 정액제 서비스 신청 URL을 반환합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */

        try {

            String url = htTaxinvoiceService.getFlatRatePopUpURL(testCorpNum);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getFlatRateState", method = RequestMethod.GET)
    public String getFlatRateState(Model m) {
        /*
         * 정액제 서비스 상태를 확인합니다.
         */

        try {

            FlatRateState flatRateInfo = htTaxinvoiceService.getFlatRateState(testCorpNum);

            m.addAttribute("State", flatRateInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "HTTaxinvoice/GetFlatRateState";
    }



}

