/*
 * 팝빌 홈택스 전자세금계산서 연계 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/httaxinvoice/tutorial/java
 * - 업데이트 일자 : 2022-10-06
 * - 연동 기술지원 연락처 : 1600-9854
 * - 연동 기술지원 이메일 : code@linkhubcorp.com
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 *    util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 연동신청 시 메일로
 *    발급받은 인증정보를 참조하여 변경합니다.
 * 2) 홈택스 로그인 인증정보를 등록합니다. (부서사용자등록 / 공동인증서 등록)
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

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "HTTaxinvoice/index";
    }

    @RequestMapping(value = "requestJob", method = RequestMethod.GET)
    public String requestJob(Model m) {
        /*
         * 홈택스에 신고된 전자세금계산서 매입/매출 내역 수집을 팝빌에 요청합니다. (조회기간 단위 : 최대 3개월)
         * 주기적으로 자체 DB에 세금계산서 정보를 INSERT 하는 경우, 조회할 일자 유형(DType) 값을 "S"로 하는 것을 권장합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#RequestJob
         */

        // 전자세금계산서 유형, SELL-매출, BUY-매입, TRUSTEE-수탁
        QueryType TIType = QueryType.SELL;

        // 조회할 일자 유형, W-작성일자, I-발행일자, S-전송일자
        String DType = "S";

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20220201";

        // 종료일자, 닐짜형식(yyyyMMdd)
        String EDate = "20220228";

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
         * 수집 요청(RequestJob API) 함수를 통해 반환 받은 작업 아이디의 상태를 확인합니다.
         * - 수집 결과 조회(Search API) 함수 또는 수집 결과 요약 정보 조회(Summary API) 함수를 사용하기 전에
         *   수집 작업의 진행 상태, 수집 작업의 성공 여부를 확인해야 합니다.
         * - 작업 상태(jobState) = 3(완료)이고 수집 결과 코드(errorCode) = 1(수집성공)이면
         *   수집 결과 내역 조회(Search) 또는 수집 결과 요약 정보 조회(Summary)를 해야합니다.
         * - 작업 상태(jobState)가 3(완료)이지만 수집 결과 코드(errorCode)가 1(수집성공)이 아닌 경우에는
         *   오류메시지(errorReason)로 수집 실패에 대한 원인을 파악할 수 있습니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetJobState
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "";

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
         * 전자세금계산서 매입/매출 내역 수집요청에 대한 상태 목록을 확인합니다.
         * - 수집 요청 후 1시간이 경과한 수집 요청건은 상태정보가 반환되지 않습니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#ListActiveJob
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
         * 수집 상태 확인(GetJobState API) 함수를 통해 상태 정보가 확인된 작업아이디를 활용하여 수집된 전자세금계산서 매입/매출 내역을 조회합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#Search
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "";

        // 문서형태 배열 ("N" 와 "M" 중 선택, 다중 선택 가능)
        // └ N = 일반 , M = 수정
        // - 미입력 시 전체조회
        String[] Type = {"N", "M"};

        // 과세형태 배열 ("T" , "N" , "Z" 중 선택, 다중 선택 가능)
        // └ T = 과세, N = 면세, Z = 영세
        // - 미입력 시 전체조회
        String[] TaxType = {"T", "Z", "N"};

        // 발행목적 배열 ("R" , "C", "N" 중 선택, 다중 선택 가능)
        // └ R = 영수, C = 청구, N = 없음
        // - 미입력 시 전체조회
        String[] PurposeType = {"R", "C", "N"};

        // 종사업장번호 유무 (null , "0" , "1" 중 택 1)
        // - null = 전체조회 , 0 = 없음, 1 = 있음
        String TaxRegIDYN = null;

        // 종사업장번호의 주체 ("S" , "B" , "T" 중 택 1)
        // - S = 공급자 , B = 공급받는자 , T = 수탁자
        String TaxRegIDType = "S";

        // 종사업장번호
        // - 다수기재 시 콤마(",")로 구분. ex) "0001,0002"
        // - 미입력 시 전체조회
        String TaxRegID = "";

        // 페이지번호 (기본값 = 1)
        int Page = 1;

        // 페이지당 목록개수 (기본값 = 500 , 최대 = 1000)
        int PerPage = 10;

        // 정렬 방향
        // - 수집 요청(requestJob API) 함수 사용시 사용한 DType 값을 기준.
        // - D = 내림차순(기본값) , A = 오름차순
        String Order = "D";

        // 거래처 상호 / 사업자번호 (사업자) / 주민등록번호 (개인) / "9999999999999" (외국인) 중 검색하고자 하는 정보 입력
        // - 사업자번호 / 주민등록번호는 하이픈('-')을 제외한 숫자만 입력
        // - 미입력시 전체조회
        String searchString = "";

        try {
            HTTaxinvoiceSearchResult searchInfo = htTaxinvoiceService.search(testCorpNum,
                    jobID, Type, TaxType, PurposeType, TaxRegIDYN, TaxRegIDType,
                    TaxRegID, Page, PerPage, Order, testUserID, searchString);
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
         * 수집 상태 확인(GetJobState API) 함수를 통해 상태 정보가 확인된 작업아이디를 활용하여 수집된 전자세금계산서 매입/매출 내역의 요약 정보를 조회합니다.
         * - 요약 정보 : 전자세금계산서 수집 건수, 공급가액 합계, 세액 합계, 합계 금액
         * - https://docs.popbill.com/httaxinvoice/java/api#Summary
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "";

        // 문서형태 배열 ("N" 와 "M" 중 선택, 다중 선택 가능)
        // └ N = 일반 , M = 수정
        // - 미입력 시 전체조회
        String[] Type = {"N", "M"};

        // 과세형태 배열 ("T" , "N" , "Z" 중 선택, 다중 선택 가능)
        // └ T = 과세, N = 면세, Z = 영세
        // - 미입력 시 전체조회
        String[] TaxType = {"T", "Z", "N"};

        // 발행목적 배열 ("R" , "C", "N" 중 선택, 다중 선택 가능)
        // └ R = 영수, C = 청구, N = 없음
        // - 미입력 시 전체조회
        String[] PurposeType = {"R", "C", "N"};

        // 종사업장번호 유무 (null , "0" , "1" 중 택 1)
        // - null = 전체조회 , 0 = 없음, 1 = 있음
        String TaxRegIDYN = null;

        // 종사업장번호의 주체 ("S" , "B" , "T" 중 택 1)
        // - S = 공급자 , B = 공급받는자 , T = 수탁자
        String TaxRegIDType = "S";

        // 종사업장번호
        // - 다수기재 시 콤마(",")로 구분. ex) "0001,0002"
        // - 미입력 시 전체조회
        String TaxRegID = "";

        // 거래처 상호 / 사업자번호 (사업자) / 주민등록번호 (개인) / "9999999999999" (외국인) 중 검색하고자 하는 정보 입력
        // - 사업자번호 / 주민등록번호는 하이픈('-')을 제외한 숫자만 입력
        // - 미입력시 전체조회
        String searchString = "";

        try {
            HTTaxinvoiceSummary summaryInfo = htTaxinvoiceService.summary(testCorpNum,
                    jobID, Type, TaxType, PurposeType, TaxRegIDYN, TaxRegIDType, TaxRegID,
                    testUserID, searchString);
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
         * 국세청 승인번호를 통해 수집한 전자세금계산서 1건의 상세정보를 반환합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetTaxinvoice
         */

        // 전자세금계산서 국세청 승인번호
        String ntsconfirmNum = "202112044100020300000c0a";

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
         * 국세청 승인번호를 통해 수집한 전자세금계산서 1건의 상세정보를 XML 형태의 문자열로 반환합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetXML
         */

        // 전자세금계산서 국세청 승인번호
        String ntsconfirmNum = "20211202410002030000196d";

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
         * 수집된 전자세금계산서 1건의 상세내역을 확인하는 페이지의 팝업 URL을 반환합니다.
         * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetPopUpURL
         */

        // 조회할 전자세금계산서 국세청 승인번호
        String NTSConfirmNum = "20211202410002030000196d";

        try {

            String url = htTaxinvoiceService.getPopUpURL(testCorpNum, NTSConfirmNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getPrintURL", method = RequestMethod.GET)
    public String getPrintURL(Model m) {
        /*
         * 수집된 전자세금계산서 1건의 상세내역을 인쇄하는 페이지의 URL을 반환합니다.
         * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetPrintURL
         */

        // 조회할 전자세금계산서 국세청 승인번호
        String NTSConfirmNum = "20161202410002030000196d";

        try {

            String url = htTaxinvoiceService.getPrintURL(testCorpNum, NTSConfirmNum, testUserID);

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
         * 홈택스연동 인증정보를 관리하는 페이지의 팝업 URL을 반환합니다.
         * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetCertificatePopUpURL
         */

        try {

            String url = htTaxinvoiceService.getCertificatePopUpURL(testCorpNum, testUserID);

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
         * 팝빌에 등록된 인증서 만료일자를 확인합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetCertificateExpireDate
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
         * 팝빌에 등록된 인증서로 홈택스 로그인 가능 여부를 확인합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#CheckCertValidation
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
         * 홈택스연동 인증을 위해 팝빌에 전자세금계산서용 부서사용자 계정을 등록합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#RegistDeptUser
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
         * 홈택스연동 인증을 위해 팝빌에 등록된 전자세금계산서용 부서사용자 계정을 확인합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#CheckDeptUser
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
         * 팝빌에 등록된 전자세금계산서용 부서사용자 계정 정보로 홈택스 로그인 가능 여부를 확인합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#CheckLoginDeptUser
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
         * 팝빌에 등록된 홈택스 전자세금계산서용 부서사용자 계정을 삭제합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#DeleteDeptUser
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
         * 팝빌 홈택스연동(세금) API 서비스 과금정보를 확인합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetChargeInfo
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
         * 홈택스연동 정액제 서비스 신청 페이지의 팝업 URL을 반환합니다.
         * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetFlatRatePopUpURL
         */

        try {

            String url = htTaxinvoiceService.getFlatRatePopUpURL(testCorpNum, testUserID);

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
         * 홈택스연동 정액제 서비스 상태를 확인합니다.
         * - https://docs.popbill.com/httaxinvoice/java/api#GetFlatRateState
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
