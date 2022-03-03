/*
 * 팝빌 계좌조회 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/easyfinbank/tutorial/java
 * - 업데이트 일자 : 2022-02-28
 * - 연동 기술지원 연락처 : 1600-9854
 * - 연동 기술지원 이메일 : code@linkhubcorp.com
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 *    util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 연동신청 시 메일로
 *    발급받은 인증정보를 참조하여 변경합니다.
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

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.ChargeInfo;
import com.popbill.api.EasyFinBankService;
import com.popbill.api.FlatRateState;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.easyfin.EasyFinBankAccount;
import com.popbill.api.easyfin.EasyFinBankAccountForm;
import com.popbill.api.easyfin.EasyFinBankJobState;
import com.popbill.api.easyfin.EasyFinBankSearchResult;
import com.popbill.api.easyfin.EasyFinBankSummary;

/*
 * 팝빌 계좌조회 API 예제.
 */
@Controller
@RequestMapping("EasyFinBankService")
public class EasyFinBankServiceExample {

    @Autowired
    private EasyFinBankService easyFinBankService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "EasyFinBank/index";
    }

    @RequestMapping(value = "registBankAccount", method = RequestMethod.GET)
    public String registBankAccount(Model m) {
        /*
         * 계좌조회 서비스를 이용할 계좌를 팝빌에 등록합니다.
         * - https://docs.popbill.com/easyfinbank/java/api#RegistBankAccount
         */

        // 계좌정보 클래스 인스턴스 생성
        EasyFinBankAccountForm bankInfo = new EasyFinBankAccountForm();

        // 기관코드
        // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
        // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
        // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
        bankInfo.setBankCode("");

        // 계좌번호 하이픈('-') 제외
        bankInfo.setAccountNumber("");

        // 계좌비밀번호
        bankInfo.setAccountPWD("");

        // 계좌유형, "법인" / "개인" 중 택 1
        bankInfo.setAccountType("");

        // 예금주 식별정보 ('-' 제외)
        // 계좌유형이 "법인"인 경우 : 사업자번호(10자리)
        // 계좌유형이 "개인"인 경우 : 예금주 생년월일 (6자리-YYMMDD)
        bankInfo.setIdentityNumber("");

        // 계좌 별칭
        bankInfo.setAccountName("");

        // 인터넷뱅킹 아이디 (국민은행 필수)
        bankInfo.setBankID("");

        // 조회전용 계정 아이디 (대구은행, 신협, 신한은행 필수)
        bankInfo.setFastID("");

        // 조회전용 계정 비밀번호 (대구은행, 신협, 신한은행 필수)
        bankInfo.setFastPWD("");

        // 정액제 이용할 개월수, 1~12 입력가능
        // - 미입력시 기본값 1개월 처리
        // - 파트너 과금방식의 경우 입력값에 관계없이 1개월 처리
        bankInfo.setUsePeriod(1);

        // 메모
        bankInfo.setMemo("");

        try {

            Response response = easyFinBankService.registBankAccount(testCorpNum, bankInfo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "updateBankAccount", method = RequestMethod.GET)
    public String updateBankAccount(Model m) {
        /*
         * 팝빌에 등록된 계좌정보를 수정합니다.
         * - https://docs.popbill.com/easyfinbank/java/api#UpdateBankAccount
         */

        // 계좌정보 클래스 인스턴스 생성
        EasyFinBankAccountForm bankInfo = new EasyFinBankAccountForm();

        // 기관코드
        // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
        // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
        // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
        bankInfo.setBankCode("");

        // 계좌번호 하이픈('-') 제외
        bankInfo.setAccountNumber("");

        // 계좌비밀번호
        bankInfo.setAccountPWD("");

        // 계좌 별칭
        bankInfo.setAccountName("");

        // 인터넷뱅킹 아이디 (국민은행 필수)
        bankInfo.setBankID("");

        // 조회전용 계정 아이디 (대구은행, 신협, 신한은행 필수)
        bankInfo.setFastID("");

        // 조회전용 계정 비밀번호 (대구은행, 신협, 신한은행 필수)
        bankInfo.setFastPWD("");

        // 메모
        bankInfo.setMemo("");

        try {

            Response response = easyFinBankService.updateBankAccount(testCorpNum, bankInfo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getBankAccountInfo", method = RequestMethod.GET)
    public String getBankAccountInfo(Model m) {
        /*
        * 팝빌에 등록된 계좌 정보를 확인합니다.
        * - https://docs.popbill.com/easyfinbank/java/api#GetBankAccountInfo
        */

        // 기관코드
        // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
        // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
        // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
        String BankCode = "";

        // 계좌번호 하이픈('-') 제외
        String AccountNumber = "";

        try {

            EasyFinBankAccount bankAccountInfo = easyFinBankService.getBankAccountInfo(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("Account", bankAccountInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/GetBankAccount";
    }

    @RequestMapping(value = "listBankAccount", method = RequestMethod.GET)
    public String listBankAccount(Model m) {
        /*
        * 팝빌에 등록된 계좌정보 목록을 반환합니다.
        * - https://docs.popbill.com/easyfinbank/java/api#ListBankAccount
        */
        try {

            EasyFinBankAccount[] bankList = easyFinBankService.listBankAccount(testCorpNum);
            m.addAttribute("BankAccountList", bankList);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/ListBankAccount";
    }

    @RequestMapping(value = "getBankAccountMgtURL", method = RequestMethod.GET)
    public String getBankAccountMgtURL(Model m) {
        /*
        * 계좌 등록, 수정 및 삭제할 수 있는 계좌 관리 팝업 URL을 반환합니다.
        * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
        * - https://docs.popbill.com/easyfinbank/java/api#GetBankAccountMgtURL
        */

        try {
            String url = easyFinBankService.getBankAccountMgtURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "closeBankAccount", method = RequestMethod.GET)
    public String closeBankAccount(Model m) {
        /*
         * 계좌의 정액제 해지를 요청합니다.
         * - https://docs.popbill.com/easyfinbank/java/api#CloseBankAccount
         */

        // 기관코드
        // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
        // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
        // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
        String BankCode = "";

        // 계좌번호 하이픈('-') 제외
        String AccountNumber = "";

        // 해지유형, "일반", "중도" 중 택 1
        // 일반(일반해지) – 이용중인 정액제 기간 만료 후 해지
        // 중도(중도해지) – 해지 요청일 기준으로 정지되고 팝빌 담당자가 승인시 해지
        // └ 중도일 경우, 정액제 잔여기간은 일할로 계산되어 포인트 환불 (무료 이용기간 중 해지하면 전액 환불)
        String CloseType = "중도";

        try {

            Response response = easyFinBankService.closeBankAccount(testCorpNum, BankCode, AccountNumber, CloseType);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "revokeCloseBankAccount", method = RequestMethod.GET)
    public String revokeCloseBankAccount(Model m) {
        /*
         * 신청한 정액제 해지요청을 취소합니다.
         * - https://docs.popbill.com/easyfinbank/java/api#RevokeCloseBankAccount
         */

        // 기관코드
        // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
        // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
        // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
        String BankCode = "";

        // 계좌번호 하이픈('-') 제외
        String AccountNumber = "";

        try {

            Response response = easyFinBankService.revokeCloseBankAccount(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "deleteBankAccount", method = RequestMethod.GET)
    public String deleteBankAccount(Model m) {
        /*
        * 등록된 계좌를 삭제합니다.
        * - 정액제가 아닌 종량제 이용 시에만 등록된 계좌를 삭제할 수 있습니다.
        * - 정액제 이용 시 정액제 해지요청(CloseBankAccount API) 함수를 사용하여 정액제를 해제할 수 있습니다.
        * - https://docs.popbill.com/easyfinbank/java/api#DeleteBankAccount
        */

        // 기관코드
        // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
        // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
        // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
        String BankCode = "";

        // 계좌번호 하이픈('-') 제외
        String AccountNumber = "";

        try {
            Response response = easyFinBankService.deleteBankAccount(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);

            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "requestJob", method = RequestMethod.GET)
    public String requestJob(Model m) {
        /*
        * 계좌 거래내역을 확인하기 위해 팝빌에 수집요청을 합니다. (조회기간 단위 : 최대 1개월)
        * - 조회일로부터 최대 3개월 이전 내역까지 조회할 수 있습니다.
        * - 반환 받은 작업아이디는 함수 호출 시점부터 1시간 동안 유효합니다.
        * - https://docs.popbill.com/easyfinbank/java/api#RequestJob
        */

        // 기관코드
        String BankCode = "";

        // 계좌번호
        String AccountNumber = "";

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20220201";

        // 종료일자, 닐짜형식(yyyyMMdd)
        String EDate = "20220228";

        try {

            String jobID = easyFinBankService.requestJob(testCorpNum, BankCode, AccountNumber, SDate, EDate);
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
         * - 거래 내역 조회(Search API) 함수 또는 거래 요약 정보 조회(Summary API) 함수를 사용하기 전에
         *   수집 작업의 진행 상태, 수집 작업의 성공 여부를 확인해야 합니다.
         * - 작업 상태(jobState) = 3(완료)이고 수집 결과 코드(errorCode) = 1(수집성공)이면
         *   거래 내역 조회(Search) 또는 거래 요약 정보 조회(Summary) 를 해야합니다.
         * - 작업 상태(jobState)가 3(완료)이지만 수집 결과 코드(errorCode)가 1(수집성공)이 아닌 경우에는
         *   오류메시지(errorReason)로 수집 실패에 대한 원인을 파악할 수 있습니다.
         * - https://docs.popbill.com/easyfinbank/java/api#GetJobState
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "022021815000000001";

        try {
            EasyFinBankJobState jobState = easyFinBankService.getJobState(testCorpNum, jobID);
            m.addAttribute("JobState", jobState);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/GetJobState";
    }

    @RequestMapping(value = "listActiveJob", method = RequestMethod.GET)
    public String listActiveJob(Model m) {
        /*
         * 수집 요청(RequestJob API) 함수를 통해 반환 받은 작업아이디의 목록을 확인합니다.
         * - 수집 요청 후 1시간이 경과한 수집 요청건은 상태정보가 반환되지 않습니다.
         * - https://docs.popbill.com/easyfinbank/java/api#ListActiveJob
         */

        try {
            EasyFinBankJobState[] jobState = easyFinBankService.listActiveJob(testCorpNum);
            m.addAttribute("JobStates", jobState);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/ListActiveJob";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /*
         * 수집 상태 확인(GetJobState API) 함수를 통해 상태 정보가 확인된 작업아이디를 활용하여 계좌 거래 내역을 조회합니다.
         * - https://docs.popbill.com/easyfinbank/java/api#Search
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "022021815000000001";

        // 거래유형 배열 ("I" 와 "O" 중 선택, 다중 선택 가능)
        // └ I = 입금 , O = 출금
        // - 미입력 시 전체조회
        String[] TradeType = { "I", "O" };

        // 페이지번호
        int Page = 1;

        // 페이지당 목록개수
        int PerPage = 10;

        // 정렬방향 D-내림차순, A-오름차순
        String Order = "D";

        // "입·출금액" / "메모" / "비고" 중 검색하고자 하는 값 입력
        // - 메모 = 거래내역 메모저장(SaveMemo API) 함수를 사용하여 저장한 값
        // - 비고 = EasyFinBankSearchDetail의 remark1, remark2, remark3 값
        // - 미입력시 전체조회
        String SearchString = "";

        try {
            EasyFinBankSearchResult searchInfo = easyFinBankService.search(testCorpNum, jobID, TradeType, SearchString, Page, PerPage, Order,
                    testUserID);
            m.addAttribute("SearchResult", searchInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/SearchResult";
    }

    @RequestMapping(value = "summary", method = RequestMethod.GET)
    public String summary(Model m) {
        /*
         * 수집 상태 확인(GetJobState API) 함수를 통해 상태 정보가 확인된 작업아이디를 활용하여 계좌 거래내역의 요약 정보를 조회합니다.
         * - 요약 정보 : 입·출 금액 합계, 입·출 거래 건수
         * - https://docs.popbill.com/easyfinbank/java/api#Summary
         */

        // 수집요청(requestJob API) 함수 호출 시 반환받은 작업아이디
        String jobID = "022021815000000001";

        // 거래유형 배열 ("I" 와 "O" 중 선택, 다중 선택 가능)
        // └ I = 입금 , O = 출금
        // - 미입력 시 전체조회
        String[] TradeType = { "I", "O" };

        // "입·출금액" / "메모" / "비고" 중 검색하고자 하는 값 입력
        // - 메모 = 거래내역 메모저장(SaveMemo API) 함수를 사용하여 저장한 값
        // - 비고 = EasyFinBankSearchDetail의 remark1, remark2, remark3 값
        // - 미입력시 전체조회
        String SearchString = "";

        try {

            EasyFinBankSummary summaryInfo = easyFinBankService.summary(testCorpNum, jobID, TradeType, SearchString, testUserID);
            m.addAttribute("SummaryResult", summaryInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/Summary";
    }

    @RequestMapping(value = "saveMemo", method = RequestMethod.GET)
    public String saveMemo(Model m) {
        /*
        * 한 건의 거래 내역에 메모를 저장합니다.
        * - https://docs.popbill.com/easyfinbank/java/api#SaveMemo
        */

        // 메모를 저장할 거래내역 아이디
        // └ 거래내역 조회(Seach API) 함수의 반환값인 EasyFinBankSearchDetail 의 tid를 통해 확인 가능
        String TID = "";

        // 거래 내역에 저장할 메모
        String Memo = "Memo테스트";

        try {

            Response response = easyFinBankService.saveMemo(testCorpNum, TID, Memo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getFlatRatePopUpURL", method = RequestMethod.GET)
    public String getFlatRatePopUpURL(Model m) {
        /*
        * 계좌조회 정액제 서비스 신청 페이지의 팝업 URL을 반환합니다.
        * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
        * - https://docs.popbill.com/easyfinbank/java/api#GetFlatRatePopUpURL
        */
        try {
            String url = easyFinBankService.getFlatRatePopUpURL(testCorpNum, testUserID);

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
        * 계좌조회 정액제 서비스 상태를 확인합니다.
        * - https://docs.popbill.com/easyfinbank/java/api#GetFlatRateState
        */

        // 기관코드
        String BankCode = "";

        // 계좌번호
        String AccountNumber = "";

        try {

            FlatRateState flatRateInfo = easyFinBankService.getFlatRateState(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("State", flatRateInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/GetFlatRateState";
    }

    @RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
    public String chargeInfo(Model m) {
        /*
         * 팝빌 계좌조회 API 서비스 과금정보를 확인합니다.
         * - https://docs.popbill.com/easyfinbank/java/api#GetChargeInfo
         */
        try {

            ChargeInfo chrgInfo = easyFinBankService.getChargeInfo(testCorpNum);
            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }
}
