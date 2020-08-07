/*
 * 팝빌 현금영수증 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/cashbill/tutorial/java
 * - 업데이트 일자 : 2020-01-20
 * - 연동 기술지원 연락처 : 1600-9854 / 070-4304-2991~2
 * - 연동 기술지원 이메일 : code@linkhub.co.kr
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 * 	  util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 링크허브 가입시 메일로
 *    발급받은 인증정보를 참조하여 변경합니다.
 * 2) 팝빌 개발용 사이트(test.popbill.com)에 연동회원으로 가입합니다.
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

import com.popbill.api.CashbillService;
import com.popbill.api.ChargeInfo;
import com.popbill.api.EmailSendConfig;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.cashbill.CBSearchResult;
import com.popbill.api.cashbill.Cashbill;
import com.popbill.api.cashbill.CashbillInfo;
import com.popbill.api.cashbill.CashbillLog;

/*
 * 팝빌 현금영수증 API 예제.
 */

@Controller
@RequestMapping("CashbillService")
public class CashbillServiceExample {

    @Autowired
    private CashbillService cashbillService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "Cashbill/index";
    }

    @RequestMapping(value = "checkMgtKeyInUse", method = RequestMethod.GET)
    public String checkMgtKeyInUse(Model m) {
        /*
         * 현금영수증 관리번호 중복여부를 확인합니다.
         * - 관리번호는 1~24자리로 숫자, 영문 '-', '_' 조합으로 구성할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#CheckMgtKeyInUse
         */

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20190104-001";

        String isUseStr;

        try {
            boolean IsUse = cashbillService.checkMgtKeyInUse(testCorpNum, mgtKey);

            isUseStr = (IsUse) ? "사용중" : "미사용중";

            m.addAttribute("Result", isUseStr);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "registIssue", method = RequestMethod.GET)
    public String registIssue(Model m) {
        /*
         * 1건의 현금영수증을 [즉시발행]합니다.
         * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청 전송결과를 확인할 수 있습니다.
         * - 현금영수증 국세청 전송 정책 : https://docs.popbill.com/cashbill/ntsSendPolicy?lang=java
         * - https://docs.popbill.com/cashbill/java/api#RegistIssue
         */

        // 메모
        String Memo = "현금영수증 즉시발행 메모";

        // 현금영수증 정보 객체
        Cashbill cashbill = new Cashbill();
        
        // 문서번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
        cashbill.setMgtKey("20191022-001");

        // 문서형태, {승인거래, 취소거래} 중 기재
        cashbill.setTradeType("승인거래");

        // 취소거래시 기재, 원본 현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
        cashbill.setOrgConfirmNum("");

        // 취소거래시 기재, 원본 현금영수증 거래일자 - getInfo API를 통해 tradeDate 값 기재
        cashbill.setOrgTradeDate("");

        // 과세형태, {과세, 비과세} 중 기재
        cashbill.setTaxationType("과세");

        // 거래처 식별번호, 거래유형에 따라 작성
        // 소득공제용 - 주민등록/휴대폰/카드번호 기재가능
        // 지출증빙용 - 사업자번호/주민등록/휴대폰/카드번호 기재가능
        cashbill.setIdentityNum("0101112222");

        // 거래구분, {소득공제용, 지출증빙용} 중 기재
        cashbill.setTradeUsage("소득공제용");

        // 거래유형, {읿반, 도서공연, 대중교통} 중 기재
        cashbill.setTradeOpt("대중교통");

        // 공급가액, 숫자만 가능
        cashbill.setSupplyCost("10000");

        // 부가세, 숫자만 가능
        cashbill.setTax("1000");

        // 봉사료, 숫자만 가능
        cashbill.setServiceFee("0");

        // 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 부가세
        cashbill.setTotalAmount("11000");


        // 발행자 사업자번호, '-'제외 10자리
        cashbill.setFranchiseCorpNum("1234567890");

        // 발행자 상호
        cashbill.setFranchiseCorpName("발행자 상호");

        // 발행자 대표자명
        cashbill.setFranchiseCEOName("발행자 대표자");

        // 발행자 주소
        cashbill.setFranchiseAddr("발행자 주소");

        // 발행자 연락처
        cashbill.setFranchiseTEL("07043042991");

        // 발행안내 문자 전송여부
        cashbill.setSmssendYN(false);


        // 거래처 고객명
        cashbill.setCustomerName("고객명");

        // 거래처 주문상품명
        cashbill.setItemName("상품명");

        // 거래처 주문번호
        cashbill.setOrderNumber("주문번호");

        // 거래처 이메일
        // 팝빌 개발환경에서 테스트하는 경우에도 안내 메일이 전송되므로,
        // 실제 거래처의 메일주소가 기재되지 않도록 주의
        cashbill.setEmail("code@linkhub.co.kr");

        // 거래처 휴대폰
        cashbill.setHp("010111222");

        
        // 발행안내 메일제목, 미기재시 기본양식으로 메일 전송 
        String emailSubject = "";
        
        try {

            Response response = cashbillService.registIssue(testCorpNum, cashbill, Memo, testUserID, emailSubject);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Model m) {
        /*
         * 1건의 현금영수증을 [임시저장]합니다.
         * - [임시저장] 상태의 현금영수증은 발행(Issue API)을 호출해야만 국세청에 전송됩니다.
         * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#Register
         */

        // 현금영수증 정보 객체
        Cashbill cashbill = new Cashbill();

        // 문서번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
        cashbill.setMgtKey("20190104-001");

        // 문서형태, {승인거래, 취소거래} 중 기재
        cashbill.setTradeType("승인거래");

        // 취소거래시 기재, 원본 현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
        cashbill.setOrgConfirmNum("");

        // 취소거래시 기재, 원본 현금영수증 거래일자 - getInfo API를 통해 tradeDate 값 기재
        cashbill.setOrgTradeDate("");

        // 과세형태, {과세, 비과세} 중 기재
        cashbill.setTaxationType("과세");

        // 거래처 식별번호, 거래유형에 따라 작성
        // 소득공제용 - 주민등록/휴대폰/카드번호 기재가능
        // 지출증빙용 - 사업자번호/주민등록/휴대폰/카드번호 기재가능
        cashbill.setIdentityNum("01011112222");

        // 거래구분, {소득공제용, 지출증빙용} 중 기재
        cashbill.setTradeUsage("소득공제용");

        // 거래유형, {일반, 도서공연, 대중교통} 중 기재
        cashbill.setTradeOpt("일반");

        // 공급가액, 숫자만 가능
        cashbill.setSupplyCost("10000");

        // 부가세, 숫자만 가능
        cashbill.setTax("1000");

        // 봉사료, 숫자만 가능
        cashbill.setServiceFee("0");

        // 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 부가세
        cashbill.setTotalAmount("11000");


        // 발행자 사업자번호, '-'제외 10자리
        cashbill.setFranchiseCorpNum("1234567890");

        // 발행자 상호
        cashbill.setFranchiseCorpName("발행자 상호");

        // 발행자 대표자명
        cashbill.setFranchiseCEOName("발행자 대표자");

        // 발행자 주소
        cashbill.setFranchiseAddr("발행자 주소");

        // 발행자 연락처
        cashbill.setFranchiseTEL("07043042991");

        // 발행안내 문자 전송여부
        cashbill.setSmssendYN(false);


        // 거래처 고객명
        cashbill.setCustomerName("고객명");

        // 거래처 주문상품명
        cashbill.setItemName("상품명");

        // 거래처 주문번호
        cashbill.setOrderNumber("주문번호");

        // 거래처 이메일
        cashbill.setEmail("test@test.com");

        // 거래처 휴대폰
        cashbill.setHp("010111222");


        try {

            Response response = cashbillService.register(testCorpNum, cashbill);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(Model m) {
        /*
         * 1건의 현금영수증을 [수정]합니다.
         * - [임시저장] 상태의 현금영수증만 수정할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#Update
         */
        
        // 수정할 현금영수증 문서번호
        String mgtKey = "20190104-001";

        // 현금영수증 정보 객체
        Cashbill cashbill = new Cashbill();

        // 문서번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
        cashbill.setMgtKey(mgtKey);

        // 문서형태, {승인거래, 취소거래} 중 기재
        cashbill.setTradeType("승인거래");

        // 취소거래시 기재, 원본현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
        //cashbill.setOrgConfirmNum("");


        // 과세형태, {과세, 비과세} 중 기재
        cashbill.setTaxationType("과세");

        // 거래처 식별번호, 거래유형에 따라 작성
        // 소득공제용 - 주민등록/휴대폰/카드번호 기재가능
        // 지출증빙용 - 사업자번호/주민등록/휴대폰/카드번호 기재가능
        cashbill.setIdentityNum("01011112222");

        // 거래유형, {소득공제용, 지출증빙용} 중 기재
        cashbill.setTradeUsage("소득공제용");

        // 공급가액, 숫자만 가능
        cashbill.setSupplyCost("10000");

        // 부가세, 숫자만 가능
        cashbill.setTax("1000");

        // 봉사료, 숫자만 가능
        cashbill.setServiceFee("0");

        // 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 부가세
        cashbill.setTotalAmount("11000");

        // 발행자 사업자번호, '-'제외 10자리
        cashbill.setFranchiseCorpNum("1234567890");

        // 발행자 상호
        cashbill.setFranchiseCorpName("발행자 상호_수정");

        // 발행자 대표자명
        cashbill.setFranchiseCEOName("발행자 대표자_수정");

        // 발행자 주소
        cashbill.setFranchiseAddr("발행자 주소");

        // 발행자 연락처
        cashbill.setFranchiseTEL("07043042991");

        // 발행안내 문자 전송여부
        cashbill.setSmssendYN(false);

        // 거래처 고객명
        cashbill.setCustomerName("고객명");

        // 거래처 주문상품명
        cashbill.setItemName("상품명");

        // 거래처 주문번호
        cashbill.setOrderNumber("주문번호");

        // 거래처 이메일
        cashbill.setEmail("test@test.com");

        // 거래처 휴대폰
        cashbill.setHp("010111222");


        try {

            Response response = cashbillService.update(testCorpNum, mgtKey,
                    cashbill);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "issue", method = RequestMethod.GET)
    public String issue(Model m) {
        /*
         * 1건의 [임시저장] 현금영수증을 [발행]합니다.
         * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#CBIssue
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        // 메모
        String memo = "발행메모";

        try {

            Response response = cashbillService.issue(testCorpNum, mgtKey, memo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "cancelIssue", method = RequestMethod.GET)
    public String cancelIssue(Model m) {
        /*
         * [발행완료] 상태의 현금영수증을 [발행취소]합니다.
         * - 발행취소는 국세청 전송전에만 가능합니다.
         * - 발행취소된 형금영수증은 국세청에 전송되지 않습니다.
         * - https://docs.popbill.com/cashbill/java/api#CancelIssue
         */

        // 문서번호
        String mgtKey = "20190104-001";

        // 메모
        String memo = "발행취소 메모";

        try {
            Response response = cashbillService.cancelIssue(testCorpNum, mgtKey, memo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(Model m) {
        /*
         * 1건의 현금영수증을 [삭제]합니다.
         * - 현금영수증을 삭제하면 사용된 문서번호(mgtKey)를 재사용할 수 있습니다.
         * - 삭제가능한 문서 상태 : [임시저장], [발행취소]
         * - https://docs.popbill.com/cashbill/java/api#Delete
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            Response response = cashbillService.delete(testCorpNum, mgtKey);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "revokeRegister", method = RequestMethod.GET)
    public String revokeRegister(Model m) {
        /*
         * 1건의 취소현금영수증을 [임시저장]합니다.
         * - [임시저장] 상태의 현금영수증은 [발행(Issue API)]을 해야만 국세청에 전송됩니다.
         * - https://docs.popbill.com/cashbill/java/api#RevokeRegister
         */

        // 문서번호, 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 24자리 문자열로 사업자별로
        // 중복되지 않도록 구성
        String mgtKey = "20190104-001";

        // 원본현금영수증 승인번호
        String orgConfirmNum = "820116333";

        // 원본현금영수증 거래일자
        String orgTradeDate = "20190102";


        try {

            Response response = cashbillService.revokeRegister(testCorpNum, mgtKey, orgConfirmNum, orgTradeDate);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "revokeRegister_part", method = RequestMethod.GET)
    public String revokeRegister_part(Model m) {
        /*
         * 1건의 (부분)취소현금영수증을 [임시저장]합니다.
         * - [임시저장] 상태의 현금영수증은 [발행(Issue API)]을 해야만 국세청에 전송됩니다.
         * - https://docs.popbill.com/cashbill/java/api#RevokeRegister
         */

        // 문서번호, 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 24자리 문자열로 사업자별로
        // 중복되지 않도록 구성
        String mgtKey = "20190104-001";

        // 원본현금영수증 승인번호
        String orgConfirmNum = "820116333";

        // 원본현금영수증 거래일자
        String orgTradeDate = "20190103";

        // 안내문자 전송여부
        Boolean smssendYN = false;

        // 부분취소 여부, false 기재시
        Boolean isPartCancel = true;

        // 취소사유 (integer 타입) / 1-거래취소, 2-오류발급취소 3-기타
        Integer cancelType = 1;

        // [취소] 공급가액
        String supplyCost = "3000";

        // [취소] 부가세
        String tax = "300";

        // [취소] 봉사료
        String serviceFee = "0";

        // [취소] 합계금액
        String totalAmount = "3300";

        try {

            Response response = cashbillService.revokeRegister(testCorpNum, mgtKey,
                    orgConfirmNum, orgTradeDate, smssendYN, isPartCancel, cancelType,
                    supplyCost, tax, serviceFee, totalAmount);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "revokeRegistIssue", method = RequestMethod.GET)
    public String revokeRegistIssue(Model m) {
        /*
         * 1건의 취소현금영수증을 [즉시발행]합니다.
         * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#RevokeRegistIssue
         */

        // 문서번호, 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 24자리 문자열로 사업자별로
        // 중복되지 않도록 구성
        String mgtKey = "20190104-001";

        // 원본현금영수증 승인번호
        String orgConfirmNum = "820116333";

        // 원본현금영수증 거래일자
        String orgTradeDate = "201901003";

        try {

            Response response = cashbillService.revokeRegistIssue(testCorpNum, mgtKey, orgConfirmNum, orgTradeDate);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "revokeRegistIssue_part", method = RequestMethod.GET)
    public String revokeRegistIssue_part(Model m) {
        /*
         * 1건의 (부분)취소현금영수증을 [즉시발행]합니다.
         * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#RevokeRegistIssue
         */

        // 문서번호, 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 24자리 문자열로 사업자별로
        // 중복되지 않도록 구성
        String mgtKey = "20190104-001";

        // 원본현금영수증 승인번호
        String orgConfirmNum = "820116333";

        // 원본현금영수증 거래일자
        String orgTradeDate = "20190103";

        // 안내문자 전송여부
        Boolean smssendYN = false;

        // 발행 메모
        String memo = "취소 현금영수증 발행 메모";

        // 부분취소 여부
        Boolean isPartCancel = true;

        // 취소사유 (integer 타입) / 1-거래취소, 2-오류발급취소 3-기타
        Integer cancelType = 1;

        // [취소] 공급가액
        String supplyCost = "3000";

        // [취소] 부가세
        String tax = "300";

        // [취소] 봉사료
        String serviceFee = "0";

        // [취소] 합계금액
        String totalAmount = "3300";

        try {

            Response response = cashbillService.revokeRegistIssue(testCorpNum, mgtKey,
                    orgConfirmNum, orgTradeDate, smssendYN, memo, isPartCancel, cancelType,
                    supplyCost, tax, serviceFee, totalAmount);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getInfo", method = RequestMethod.GET)
    public String getInfo(Model m) {
        /*
         * 1건의 현금영수증 상태/요약 정보를 확인합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetInfo
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            CashbillInfo cashbillInfo = cashbillService.getInfo(testCorpNum, mgtKey);

            m.addAttribute("CashbillInfo", cashbillInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Cashbill/CashbillInfo";
    }

    @RequestMapping(value = "getInfos", method = RequestMethod.GET)
    public String getInfos(Model m) {
        /*
         * 대량의 현금영수증 상태/요약 정보를 확인합니다. (최대 1000건)
         * - https://docs.popbill.com/cashbill/java/api#GetInfos
         */

        // 현금영수증 문서번호 배열 최대(1000건)
        String[] mgtKeyList = new String[]{"20190104-001", "20190104-002", "20190104-003"};

        try {

            CashbillInfo[] cashbillInfos = cashbillService.getInfos(testCorpNum, mgtKeyList);

            m.addAttribute("CashbillInfos", cashbillInfos);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Cashbill/CashbillInfo";
    }

    @RequestMapping(value = "getDetailInfo", method = RequestMethod.GET)
    public String getDetailInfo(Model m) {
        /*
         * 현금영수증 1건의 상세정보를 조회합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetDetailInfo
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            Cashbill cashbill = cashbillService.getDetailInfo(testCorpNum, mgtKey);

            m.addAttribute("Cashbill", cashbill);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Cashbill/Cashbill";
    }
    
    @RequestMapping(value = "assignMgtKey", method = RequestMethod.GET)
    public String assignMgtKey(Model m) {
        /*
         * 팝빌 사이트에서 작성한 현금영수증에 파트너의 문서번호를 할당합니다.
         */

        // 현금영수증 아이템키, 문서 목록조회(Search) API의 반환항목중 ItemKey 참조
        String itemKey = "020080716195300001";

        // 할당할 문서번호, 숫자, 영문 '-', '_' 조합으로 1~24자리까지
        // 사업자번호별 중복없는 고유번호 할당
        String mgtKey = "20200807-100";

        try {

            Response response = cashbillService.assignMgtKey(testCorpNum, itemKey, mgtKey);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /*
         * 검색조건을 사용하여 현금영수증 목록을 조회합니다.
         * - https://docs.popbill.com/cashbill/java/api#Search
         */

        // 일자유형, R-등록일자, T-거래일자, I-발행일자
        String DType = "T";

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20181201";

        // 종료일자, 날짜형식(yyyyMMdd)
        String EDate = "20190103";

        // 상태코드 배열, 2,3번째 자리에 와일드카드(*) 사용 가능
        String[] State = {"100", "2**", "3**", "4**"};

        // 문서형태 배열,  N-승인거래, C-취소거래
        String[] TradeType = {"N", "C"};

        // 거래구분 배열, P-소득공제용, C-지출증빙용
        String[] TradeUsage = {"P", "C"};

        // 거래유형 배열, N-일반, B-도서공연, T-대중교통
        String[] TradeOpt = {"N", "B", "T"};

        // 과세형태 배열, T-과세, N-비과세
        String[] TaxationType = {"T", "N"};

        // 식별번호 조회, 미기재시 전체조회
        String QString = "";

        // 페이지 번호
        int Page = 1;

        // 페이지당 목록개수, 최대 1000건
        int PerPage = 20;

        // 정렬방향, A-오름차순,  D-내림차순
        String Order = "D";


        try {

            CBSearchResult searchResult = cashbillService.search(testCorpNum, DType, SDate, EDate,
                    State, TradeType, TradeUsage, TradeOpt, TaxationType, QString,
                    Page, PerPage, Order);

            m.addAttribute("SearchResult", searchResult);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Cashbill/SearchResult";
    }

    @RequestMapping(value = "getLogs", method = RequestMethod.GET)
    public String getLogs(Model m) {
        /*
         * 현금영수증 상태 변경이력을 확인합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetLogs
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {
            CashbillLog[] cashbillLogs = cashbillService.getLogs(testCorpNum, mgtKey);

            m.addAttribute("CashbillLogs", cashbillLogs);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Cashbill/CashbillLog";
    }

    @RequestMapping(value = "getURL", method = RequestMethod.GET)
    public String getURL(Model m) {
        /*
         * 팝빌 현금영수증 문서함 팝업 URL을 반환합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetURL
         */

        // TBOX : 임시문서함 , PBOX : 매출문서함, WRITE : 현금영수증 작성
        String TOGO = "WRITE";

        try {

            String url = cashbillService.getURL(testCorpNum, TOGO);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getPopUpURL", method = RequestMethod.GET)
    public String getPopUpURL(Model m) {
        /*
         * 1건의 현금영수증 보기 팝업 URL을 반환합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetPopUpURL
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            String url = cashbillService.getPopUpURL(testCorpNum, mgtKey);

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
         * 1건의 현금영수증 인쇄팝업 URL을 반환합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetPrintURL
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            String url = cashbillService.getPrintURL(testCorpNum, mgtKey);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }
    
    @RequestMapping(value = "getPDFURL", method = RequestMethod.GET)
    public String getPDFURL(Model m) {
        /*
         * 1건의 현금영수증 PDF 다운로드 URL을 반환합니다.
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            String url = cashbillService.getPDFURL(testCorpNum, mgtKey);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getEPrintURL", method = RequestMethod.GET)
    public String getEPrintURL(Model m) {
        /*
         * 현금영수증 인쇄(공급받는자) URL을 반환합니다.
         * - URL 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            String url = cashbillService.getEPrintURL(testCorpNum, mgtKey);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getMassPrintURL", method = RequestMethod.GET)
    public String getMassPrintURL(Model m) {
        /*
         * 대량의 현금영수증 인쇄팝업 URL을 반환합니다. (최대 100건)
         * - https://docs.popbill.com/cashbill/java/api#GetMassPrintURL
         */

        // 문서번호 배열, 최대 100건
        String[] mgtKeyList = new String[]{"20190104-001", "20190104-002", "20190104-003"};

        try {

            String url = cashbillService.getMassPrintURL(testCorpNum, mgtKeyList);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getMailURL", method = RequestMethod.GET)
    public String getMailURL(Model m) {
        /*
         * 현금영수증 수신메일 링크주소를 반환합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetMailURL
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        try {

            String url = cashbillService.getMailURL(testCorpNum, mgtKey);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "sendEmail", method = RequestMethod.GET)
    public String sendEmail(Model m) {
        /*
         * 현금영수증 발행 안내메일을 재전송합니다.
         * - https://docs.popbill.com/cashbill/java/api#SendEmail
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        // 수신자 메일주소
        String receiver = "test@test.com";

        try {
            Response response = cashbillService.sendEmail(testCorpNum, mgtKey, receiver);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "sendSMS", method = RequestMethod.GET)
    public String sendSMS(Model m) {
        /*
         * 알림문자를 전송합니다. (단문/SMS - 한글 최대 45자)
         * - 알림문자 전송시 포인트가 차감됩니다. (전송실패시 환불처리)
         * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [문자] > [전송내역] 탭에서 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#SendSMS
         */


        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        // 발신번호
        String sender = "07043042991";

        // 수신번호
        String receiver = "010111222";

        // 문자 전송 내용 (90Byte 초과시 길이가 조정되어 전송)
        String contents = "현금영수증 문자메시지 전송 테스트입니다.";

        try {

            Response response = cashbillService.sendSMS(testCorpNum, mgtKey,
                    sender, receiver, contents);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "sendFAX", method = RequestMethod.GET)
    public String sendFAX(Model m) {
        /*
         * 현금영수증을 팩스전송합니다.
         * - 팩스 전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
         * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [팩스] > [전송내역] 메뉴에서 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/cashbill/java/api#SendFAX
         */

        // 현금영수증 문서번호
        String mgtKey = "20190104-001";

        // 발신자 번호
        String sender = "07043042991";

        // 수신자 팩스번호
        String receiver = "010111222";

        try {

            Response response = cashbillService.sendFAX(testCorpNum, mgtKey,
                    sender, receiver);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "listEmailConfig", method = RequestMethod.GET)
    public String listEmailConfig(Model m) {
        /*
         * 현금영수증 관련 메일전송 항목에 대한 전송여부를 목록을 반환합니다.
         * - https://docs.popbill.com/cashbill/java/api#ListEmailConfig
         */

        try {

            EmailSendConfig[] emailSendConfigs = cashbillService.listEmailConfig(testCorpNum);

            m.addAttribute("EmailSendConfigs", emailSendConfigs);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Cashbill/EmailSendConfig";
    }

    @RequestMapping(value = "updateEmailConfig", method = RequestMethod.GET)
    public String updateEmailConfig(Model m) {
        /*
         * 현금영수증 관련 메일전송 항목에 대한 전송여부를 수정합니다.
         * - https://docs.popbill.com/cashbill/java/api#UpdateEmailConfig
         *
         * 메일전송유형
         * CSH_ISSUE : 고객에게 현금영수증이 발행 되었음을 알려주는 메일 입니다.
         * CSH_CANCEL : 고객에게 현금영수증이 발행취소 되었음을 알려주는 메일 입니다.
         * 
         */

        // 메일 전송 유형
        String emailType = "CSH_ISSUE";

        // 전송 여부 (true = 전송, false = 미전송)
        Boolean sendYN = true;

        try {

            Response response = cashbillService.updateEmailConfig(testCorpNum,
                    emailType, sendYN);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
    public String getUnitCost(Model m) {
        /*
         * 현금영수증 발행단가를 확인합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetUnitCost
         */

        try {

            float unitCost = cashbillService.getUnitCost(testCorpNum);

            m.addAttribute("Result", unitCost);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
    public String chargeInfo(Model m) {
        /*
         * 현금영수증 API 서비스 과금정보를 확인합니다.
         * - https://docs.popbill.com/cashbill/java/api#GetChargeInfo
         */

        try {

            ChargeInfo chrgInfo = cashbillService.getChargeInfo(testCorpNum);

            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

}
