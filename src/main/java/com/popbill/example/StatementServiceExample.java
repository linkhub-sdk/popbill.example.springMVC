/*
 * 팝빌 전자명세서 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/statement/tutorial/java
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.AttachedFile;
import com.popbill.api.ChargeInfo;
import com.popbill.api.EmailSendConfig;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.StatementService;
import com.popbill.api.statement.Statement;
import com.popbill.api.statement.StatementDetail;
import com.popbill.api.statement.StatementInfo;
import com.popbill.api.statement.StatementLog;
import com.popbill.api.statement.StmtSearchResult;

/*
 * 팝빌 전자명세서 API 예제.
 */
@Controller
@RequestMapping("StatementService")
public class StatementServiceExample {

    @Autowired
    private StatementService statementService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "Statement/index";
    }

    @RequestMapping(value = "checkMgtKeyInUse", method = RequestMethod.GET)
    public String checkMgtKeyInUse(Model m) {
        /*
         * 문서번호 사용여부 확인
         * - 최대 24자리, 영문, 숫자, '-', '_' 조합하여 구성
         * - https://docs.popbill.com/statement/java/api#CheckMgtKeyInUse
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 사업자별로 중복되지 않도록 구성
        String mgtKey = "20191004-01";
        String isUseStr;

        try {
            boolean IsUse = statementService.checkMgtKeyInUse(testCorpNum, itemCode, mgtKey);

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
         * 1건의 전자명세서를 [즉시발행]합니다.
         * - https://docs.popbill.com/statement/java/api#RegistIssuex 
         */

        String Memo = "전자명세서 즉시발행 메모";
        
        // 발행안내 메일 제목, 미기재시 기본양식으로 메일 전송 
        String emailSubject = "";
        
        //  전자명세서 정보 객체
        Statement statement = new Statement();

        // [필수] 작성일자, 형태 yyyyMMdd
        statement.setWriteDate("20191004");

        // [필수] {영수, 청구} 중 기재
        statement.setPurposeType("영수");

        // [필수] {과세, 영세, 면세} 중 기재
        statement.setTaxType("과세");

        // 맞춤양식코드, 미기재시 기본양식으로 처리
        statement.setFormCode("");

        // [필수] 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        statement.setItemCode((short) 121);

        // [필수] 문서번호, 최대 24자리 영문, 숫자, '-', '_'로 구성
        statement.setMgtKey("20191022-002");


        /*********************************************************************
         *								발신자 정보
         *********************************************************************/

        // [필수] 발신자 사업자번호
        statement.setSenderCorpNum("1234567890");

        // [필수] 발신자 상호
        statement.setSenderCorpName("발신자 상호");

        // 발신자 주소
        statement.setSenderAddr("발신자 주소");

        // [필수] 발신자 대표자 성명
        statement.setSenderCEOName("발신자 대표자 성명");

        // 발신자 종사업장 식별번호, 숫자 4자리, 필요시 기재
        statement.setSenderTaxRegID("");

        // 발신자 종목
        statement.setSenderBizClass("업종");

        // 발신자 업태
        statement.setSenderBizType("업태");

        // 발신자 담당자명
        statement.setSenderContactName("발신자 담당자명");

        // 발신자 담당자 메일주소
        statement.setSenderEmail("test@test.com");

        // 발신자 담당자 연락처
        statement.setSenderTEL("070-7070-0707");

        // 발신자 담당자 휴대폰번호
        statement.setSenderHP("010-000-2222");


        /*********************************************************************
         *							수신자 정보
         *********************************************************************/

        // [필수] 수신자 사업자번호
        statement.setReceiverCorpNum("8888888888");

        // [필수] 수신자 상호
        statement.setReceiverCorpName("수신자 상호");

        // [필수] 수신자 대표자명
        statement.setReceiverCEOName("수신자 대표자 성명");

        // 수신자 주소
        statement.setReceiverAddr("수신자 주소");

        // 수신자 종목
        statement.setReceiverBizClass("수신자 종목");

        // 수신자 업태
        statement.setReceiverBizType("수신자 업태");

        // 수신자 담당자명
        statement.setReceiverContactName("수신자 담당자명");

        // 수신자 메일주소
        // 팝빌 개발환경에서 테스트하는 경우에도 안내 메일이 전송되므로,
        // 실제 거래처의 메일주소가 기재되지 않도록 주의
        statement.setReceiverEmail("code@linkhub.co.kr");


        /*********************************************************************
         *							전자명세서 기재정보
         *********************************************************************/

        // 공급가액 합계
        statement.setSupplyCostTotal("400000");

        // 세액 합계
        statement.setTaxTotal("40000");

        // 합계금액.  공급가액 + 세액
        statement.setTotalAmount("440000");

        // 기재상 일련번호 항목
        statement.setSerialNum("123");

        // 기재상 비고 항목
        statement.setRemark1("비고1");
        statement.setRemark2("비고2");
        statement.setRemark3("비고3");

        // 사업자등록증 이미지 첨부여부
        statement.setBusinessLicenseYN(false);

        // 통장사본 이미지 첨부여부
        statement.setBankBookYN(false);


        /*********************************************************************
         *							전자명세서 품목항목
         *********************************************************************/

        statement.setDetailList(new ArrayList<StatementDetail>());

        StatementDetail detail = new StatementDetail();        // 상세항목(품목) 배열

        detail.setSerialNum((short) 1);                    // 일련번호, 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);

        detail = new StatementDetail();                    // 상세항목(품목) 배열
        detail.setSerialNum((short) 2);                    // 일련번호 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);


        /*********************************************************************
         *							 전자명세서 추가속성
         * - 추가속성에 관한 자세한 사항은 "[전자명세서 API 연동매뉴얼] >
         *   5.2. 기본양식 추가속성 테이블"을 참조하시기 바랍니다.
         *********************************************************************/

        Map<String, String> propertyBag = new HashMap<String, String>();

        propertyBag.put("Balance", "15000");            // 전잔액
        propertyBag.put("Deposit", "5000");                // 입금액
        propertyBag.put("CBalance", "20000");            // 현잔액

        statement.setPropertyBag(propertyBag);

        try {
            Response response = statementService.registIssue(testCorpNum, statement, Memo, testUserID, emailSubject);

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
         * 1건의 전자명세서를 [임시저장]합니다.
         * - https://docs.popbill.com/statement/java/api#Register
         */

        //  전자명세서 정보 객체
        Statement statement = new Statement();

        // [필수] 작성일자, 형태 yyyyMMdd
        statement.setWriteDate("20190104");

        // [필수] {영수, 청구} 중 기재
        statement.setPurposeType("영수");

        // [필수] {과세, 영세, 면세} 중 기재
        statement.setTaxType("과세");

        // 맞춤양식코드, 미기재시 기본양식으로 처리
        statement.setFormCode("");

        // [필수] 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        statement.setItemCode((short) 121);

        // [필수] 문서번호, 최대 24자리 영문, 숫자, '-', '_'로 구성
        statement.setMgtKey("20190104-001");


        /*********************************************************************
         *								발신자 정보
         *********************************************************************/

        // [필수] 발신자 사업자번호
        statement.setSenderCorpNum("1234567890");

        // [필수] 발신자 상호
        statement.setSenderCorpName("발신자 상호");

        // 발신자 주소
        statement.setSenderAddr("발신자 주소");

        // [필수] 발신자 대표자 성명
        statement.setSenderCEOName("발신자 대표자 성명");

        // 발신자 종사업장 식별번호, 숫자 4자리, 필요시 기재
        statement.setSenderTaxRegID("");

        // 발신자 종목
        statement.setSenderBizClass("업종");

        // 발신자 업태
        statement.setSenderBizType("업태");

        // 발신자 담당자명
        statement.setSenderContactName("발신자 담당자명");

        // 발신자 담당자 메일주소
        statement.setSenderEmail("test@test.com");

        // 발신자 담당자 연락처
        statement.setSenderTEL("070-7070-0707");

        // 발신자 담당자 휴대폰번호
        statement.setSenderHP("010-000-2222");


        /*********************************************************************
         *							수신자 정보
         *********************************************************************/

        // [필수] 수신자 사업자번호
        statement.setReceiverCorpNum("8888888888");

        // [필수] 수신자 상호
        statement.setReceiverCorpName("수신자 상호");

        // [필수] 수신자 대표자명
        statement.setReceiverCEOName("수신자 대표자 성명");

        // 수신자 주소
        statement.setReceiverAddr("수신자 주소");

        // 수신자 종목
        statement.setReceiverBizClass("수신자 종목");

        // 수신자 업태
        statement.setReceiverBizType("수신자 업태");

        // 수신자 담당자명
        statement.setReceiverContactName("수신자 담당자명");

        // 수신자 메일주소
        // 팝빌 개발환경에서 테스트하는 경우에도 안내 메일이 전송되므로,
        // 실제 거래처의 메일주소가 기재되지 않도록 주의
        statement.setReceiverEmail("test@receiver.com");


        /*********************************************************************
         *							전자명세서 기재정보
         *********************************************************************/

        // 공급가액 합계
        statement.setSupplyCostTotal("400000");

        // 세액 합계
        statement.setTaxTotal("40000");

        // 합계금액.  공급가액 + 세액
        statement.setTotalAmount("440000");

        // 기재상 일련번호 항목
        statement.setSerialNum("123");

        // 기재상 비고 항목
        statement.setRemark1("비고1");
        statement.setRemark2("비고2");
        statement.setRemark3("비고3");

        // 사업자등록증 이미지 첨부여부
        statement.setBusinessLicenseYN(false);

        // 통장사본 이미지 첨부여부
        statement.setBankBookYN(false);


        /*********************************************************************
         *							전자명세서 품목항목
         *********************************************************************/

        statement.setDetailList(new ArrayList<StatementDetail>());

        StatementDetail detail = new StatementDetail();        // 상세항목(품목) 배열

        detail.setSerialNum((short) 1);                    // 일련번호, 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);

        detail = new StatementDetail();                    // 상세항목(품목) 배열
        detail.setSerialNum((short) 2);                    // 일련번호 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);


        /*********************************************************************
         *							 전자명세서 추가속성
         * - 추가속성에 관한 자세한 사항은 "[전자명세서 API 연동매뉴얼] >
         *   5.2. 기본양식 추가속성 테이블"을 참조하시기 바랍니다.
         *********************************************************************/

        Map<String, String> propertyBag = new HashMap<String, String>();

        propertyBag.put("Balance", "15000");            // 전잔액
        propertyBag.put("Deposit", "5000");                // 입금액
        propertyBag.put("CBalance", "20000");            // 현잔액

        statement.setPropertyBag(propertyBag);


        try {

            Response response = statementService.register(testCorpNum, statement);

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
         * 1건의 전자명세서를 [수정]합니다.
         * - [임시저장] 상태의 전자명세서만 수정할 수 있습니다.
         * - https://docs.popbill.com/statement/java/api#Update
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-01";

        //  전자명세서 정보 객체
        Statement statement = new Statement();

        // [필수] 작성일자, 형태 yyyyMmdd
        statement.setWriteDate("20191004");

        // [필수] {영수, 청구} 중 기재
        statement.setPurposeType("영수");

        // [필수] {과세, 영세, 면세} 중 기재
        statement.setTaxType("과세");

        // 맞춤양식코드, 미기재시 기본양식으로 처리
        statement.setFormCode("");

        // [필수] 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        statement.setItemCode((short) 121);

        // [필수] 문서번호, 최대 24자리 영문, 숫자, '-', '_'로 구성
        statement.setMgtKey("20190104-001");


        /*********************************************************************
         *								발신자 정보
         *********************************************************************/

        // [필수] 발신자 사업자번호
        statement.setSenderCorpNum("1234567890");

        // [필수] 발신자 상호
        statement.setSenderCorpName("발신자 상호");

        // 발신자 주소
        statement.setSenderAddr("발신자 주소_수정");

        // [필수] 발신자 대표자 성명
        statement.setSenderCEOName("발신자 대표자 성명_수정");

        // 발신자 종사업장 식별번호, 숫자 4자리, 필요시 기재
        statement.setSenderTaxRegID("");

        // 발신자 종목
        statement.setSenderBizClass("업종");

        // 발신자 업태
        statement.setSenderBizType("업태");

        // 발신자 담당자명
        statement.setSenderContactName("발신자 담당자명");

        // 발신자 담당자 메일주소
        statement.setSenderEmail("test@test.com");

        // 발신자 담당자 연락처
        statement.setSenderTEL("070-7070-0707");

        // 발신자 담당자 휴대폰번호
        statement.setSenderHP("010-000-2222");


        /*********************************************************************
         *							수신자 정보
         *********************************************************************/

        // [필수] 수신자 사업자번호
        statement.setReceiverCorpNum("8888888888");

        // [필수] 수신자 상호
        statement.setReceiverCorpName("수신자 상호");

        // [필수] 수신자 대표자명
        statement.setReceiverCEOName("수신자 대표자 성명");

        // 수신자 주소
        statement.setReceiverAddr("수신자 주소");

        // 수신자 종목
        statement.setReceiverBizClass("수신자 종목");

        // 수신자 업태
        statement.setReceiverBizType("수신자 업태");

        // 수신자 담당자명
        statement.setReceiverContactName("수신자 담당자명");

        // 수신자 메일주소
        // 팝빌 개발환경에서 테스트하는 경우에도 안내 메일이 전송되므로,
        // 실제 거래처의 메일주소가 기재되지 않도록 주의
        statement.setReceiverEmail("test@receiver.com");


        /*********************************************************************
         *							전자명세서 기재정보
         *********************************************************************/

        // 공급가액 합계
        statement.setSupplyCostTotal("400000");

        // 세액 합계
        statement.setTaxTotal("40000");

        // 합계금액.  공급가액 + 세액
        statement.setTotalAmount("440000");

        // 기재상 일련번호 항목
        statement.setSerialNum("123");

        // 기재상 비고 항목
        statement.setRemark1("비고1");
        statement.setRemark2("비고2");
        statement.setRemark3("비고3");

        // 사업자등록증 이미지 첨부여부
        statement.setBusinessLicenseYN(false);

        // 통장사본 이미지 첨부여부
        statement.setBankBookYN(false);


        /*********************************************************************
         *							전자명세서 품목항목
         *********************************************************************/

        statement.setDetailList(new ArrayList<StatementDetail>());

        StatementDetail detail = new StatementDetail();        // 상세항목(품목) 배열

        detail.setSerialNum((short) 1);                    // 일련번호, 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);

        detail = new StatementDetail();                    // 상세항목(품목) 배열
        detail.setSerialNum((short) 2);                    // 일련번호 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);


        /*********************************************************************
         *							 전자명세서 추가속성
         * - 추가속성에 관한 자세한 사항은 "[전자명세서 API 연동매뉴얼] >
         *   5.2. 기본양식 추가속성 테이블"을 참조하시기 바랍니다.
         *********************************************************************/

        Map<String, String> propertyBag = new HashMap<String, String>();

        propertyBag.put("Balance", "15000");            // 전잔액
        propertyBag.put("Deposit", "5000");                // 입금액
        propertyBag.put("CBalance", "20000");            // 현잔액

        statement.setPropertyBag(propertyBag);

        try {

            Response response = statementService.update(testCorpNum, itemCode,
                    mgtKey, statement);

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
         * 1건의 [임시저장] 상태의 전자명세서를 [발행]합니다
         * - https://docs.popbill.com/statement/java/api#StmIssue
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-001";

        // 메모
        String memo = "발행메모";

        try {

            Response response = statementService.issue(testCorpNum, itemCode,
                    mgtKey, memo);

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
         * 1건의 전자명세서를 [발행취소]합니다.
         * - https://docs.popbill.com/statement/java/api#Cancel
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-001";

        // 메모
        String memo = "발행취소 메모";

        try {

            Response response = statementService.cancel(testCorpNum, itemCode,
                    mgtKey, memo);

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
         * 1건의 전자명세서를 [삭제]합니다.
         * - 전자명세서를 삭제하면 사용된 문서번호(mgtKey)를 재사용할 수 있습니다.
         * - 삭제가능한 문서 상태 : [임시저장], [발행취소]
         * - https://docs.popbill.com/statement/java/api#Delete
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-01";

        try {

            Response response = statementService.delete(testCorpNum, itemCode, mgtKey);

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
         * 1건의 전자명세서 상태/요약 정보를 확인합니다.
         * - https://docs.popbill.com/statement/java/api#GetInfo
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20190104-001";

        try {

            StatementInfo statementInfo = statementService.getInfo(testCorpNum,
                    itemCode, mgtKey);

            m.addAttribute("StatementInfo", statementInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Statement/StatementInfo";
    }

    @RequestMapping(value = "getInfos", method = RequestMethod.GET)
    public String getInfos(Model m) {
        /*
         * 다수건의 전자명세서 상태/요약 정보를 확인합니다.
         * - https://docs.popbill.com/statement/java/api#GetInfos
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호 배열(최대 1000건)
        String[] MgtKeyList = new String[]{"20190103-001", "20190103-002", "20190103-003"};

        try {

            StatementInfo[] statementInfos = statementService.getInfos(testCorpNum,
                    itemCode, MgtKeyList);

            m.addAttribute("StatementInfos", statementInfos);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Statement/StatementInfo";
    }

    @RequestMapping(value = "getDetailInfo", method = RequestMethod.GET)
    public String getDetailInfo(Model m) {
        /*
         * 전자명세서 1건의 상세정보를 조회합니다.
         * - https://docs.popbill.com/statement/java/api#GetDetailInfo
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20190104-001";

        try {

            Statement statement = statementService.getDetailInfo(testCorpNum,
                    itemCode, mgtKey);

            m.addAttribute("Statement", statement);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Statement/Statement";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /*
         * 검색조건을 사용하여 전자명세서 목록을 조회합니다.
         * - https://docs.popbill.com/statement/java/api#Search
         */

        // 일자유형, R-등록일자, W-작성일자, I-발행일자
        String DType = "W";

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20181201";

        // 종료일자, 날짜형식(yyyyMMdd)
        String EDate = "20190103";

        // 전자명세서 상태코드 배열, 2,3번째 자리에 와일드카드(*) 사용 가능
        String[] State = {"100", "2**", "3**", "4**"};

        // 전자명세서 코드, 121-명세서, 122-청구서, 123-견적서, 124-발주서, 125-입금표, 126-영수증
        int[] ItemCode = {121, 122, 123, 124, 125, 126};

        // 통합검색어, 거래처 상호명 또는 거래처 사업자번호로 조회, 공백처리시 전체조회
        String QString = "";

        // 페이지 번호
        int Page = 1;

        // 페이지당 목록개수, 최대 1000건
        int PerPage = 20;

        // 정렬방향, A-오름차순,  D-내림차순
        String Order = "D";

        try {

            StmtSearchResult searchResult = statementService.search(testCorpNum,
                    DType, SDate, EDate, State, ItemCode, QString, Page, PerPage, Order);

            m.addAttribute("SearchResult", searchResult);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Statement/SearchResult";
    }

    @RequestMapping(value = "getLogs", method = RequestMethod.GET)
    public String getLogs(Model m) {
        /*
         * 전자명세서 상태 변경이력을 확인합니다.
         * - https://docs.popbill.com/statement/java/api#GetLogs
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-01";

        try {

            StatementLog[] statementLogs = statementService.getLogs(testCorpNum,
                    itemCode, mgtKey);

            m.addAttribute("StatementLogs", statementLogs);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Statement/StatementLog";
    }

    @RequestMapping(value = "getURL", method = RequestMethod.GET)
    public String getURL(Model m) {
        /*
         * 팝빌 전자명세서 문서함 관련 팝업 URL을 반환합니다.
         * - https://docs.popbill.com/statement/java/api#GetURL
         */

        // TBOX : 임시문서함 , SBOX : 매출문서함
        String TOGO = "SBOX";

        try {

            String url = statementService.getURL(testCorpNum, TOGO);

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
         * 1건의 전자명세서 보기 팝업 URL을 반환합니다.
         * - https://docs.popbill.com/statement/java/api#GetPopUpURL
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호
        String mgtKey = "20191004-001";

        try {

            String url = statementService.getPopUpURL(testCorpNum, itemCode,
                    mgtKey);

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
         * 1건의 전자명세서 인쇄팝업 URL을 반환합니다. (발신자/수신자용)
         * - https://docs.popbill.com/statement/java/api#GetPrintURL
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호
        String mgtKey = "20191004-001";

        try {

            String url = statementService.getPrintURL(testCorpNum, itemCode,
                    mgtKey);

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
         * 1건의 전자명세서 인쇄팝업 URL을 반환합니다. (수신자용)
         * - https://docs.popbill.com/statement/java/api#GetEPrintURL
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호
        String mgtKey = "20191004-001";

        try {

            String url = statementService.getEPrintURL(testCorpNum, itemCode,
                    mgtKey);

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
         * 다수건의 전자명세서 인쇄팝업 URL을 반환합니다. (최대 100건)
         * - https://docs.popbill.com/statement/java/api#GetMassPrintURL
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호 배열, 최대 100건
        String[] mgtKeyList = new String[]{"20191004-001", "20150318-01", "20150318-001", "20150319-01"};

        try {

            String url = statementService.getMassPrintURL(testCorpNum, itemCode,
                    mgtKeyList);

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
         * 수신자 메일링크 URL을 반환합니다.
         * - https://docs.popbill.com/statement/java/api#GetMailURL
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호
        String mgtKey = "20191004-001";

        try {
            String url = statementService.getMailURL(testCorpNum, itemCode, mgtKey);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "attachFile", method = RequestMethod.GET)
    public String attachFile(Model m) {
        /*
         * 전자명세서에 첨부파일을 등록합니다.
         * - 첨부파일 등록은 전자명세서가 [임시저장] 상태인 경우에만 가능합니다.
         * - 첨부파일은 최대 5개까지 등록할 수 있습니다.
         * - https://docs.popbill.com/statement/java/api#AttachFile
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-01";

        // 첨부파일 표시명
        String displayName = "첨부파일.jpg";

        //첨부할 파일의 InputStream. 예제는 resource에 테스트파일을 참조함.
        //FileInputStream으로 처리하는 것을 권함.
        InputStream stream = getClass().getClassLoader().getResourceAsStream("test.jpg");


        try {

            Response response = statementService.attachFile(testCorpNum, itemCode,
                    mgtKey, displayName, stream);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                }
        }


        return "response";
    }

    @RequestMapping(value = "deleteFile", method = RequestMethod.GET)
    public String deleteFile(Model m) {
        /*
         * 전자명세서에 첨부된 파일을 삭제합니다.
         * - 파일을 식별하는 파일아이디는 첨부파일 목록(GetFiles API) 의 응답항목
         *   중 파일아이디(AttachedFile) 값을 통해 확인할 수 있습니다.
         * - https://docs.popbill.com/statement/java/api#DeleteFile
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-01";

        // getFiles()로 해당 파일의 attachedFile 필드값 기재.
        String FileID = "57C0A91A-BF5A-494A-8E0D-B46FC9B5C8E2.PBF";

        try {

            Response response = statementService.deleteFile(testCorpNum, itemCode,
                    mgtKey, FileID);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getFiles", method = RequestMethod.GET)
    public String getFiles(Model m) {
        /*
         * 전자명세서에 첨부된 파일의 목록을 확인합니다.
         * - 응답항목 중 파일아이디(AttachedFile) 항목은 파일삭제(DeleteFile API)
         *   호출시 이용할 수 있습니다.
         * - https://docs.popbill.com/statement/java/api#GetFiles
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 문서번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
        String mgtKey = "20191004-01";

        try {

            AttachedFile[] attachedFiles = statementService.getFiles(testCorpNum,
                    itemCode, mgtKey);

            m.addAttribute("AttachedFiles", attachedFiles);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Statement/AttachedFile";
    }

    @RequestMapping(value = "sendEmail", method = RequestMethod.GET)
    public String sendEmail(Model m) {
        /*
         * 발행 안내메일을 재전송합니다.
         * - https://docs.popbill.com/statement/java/api#SendEmail
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호
        String mgtKey = "20191004-001";

        // 수신자 이메일주소
        String receiver = "test@test.com";

        try {

            Response response = statementService.sendEmail(testCorpNum, itemCode,
                    mgtKey, receiver);

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
         * 알림문자를 전송합니다. (단문/SMS- 한글 최대 45자)
         * - 알림문자 전송시 포인트가 차감됩니다. (전송실패시 환불처리)
         * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [문자] > [전송내역] 탭에서 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/statement/java/api#SendSMS
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호
        String mgtKey = "20191004-001";

        // 발신번호
        String sender = "07043042991";

        // 수신번호
        String receiver = "010111222";

        // 문자 전송 내용 (90Byte 초과시 길이가 조정되어 전송)
        String contents = "전자명세서 문자메시지 전송 테스트입니다.";

        try {

            Response response = statementService.sendSMS(testCorpNum, itemCode,
                    mgtKey, sender, receiver, contents);

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
         * 전자명세서를 팩스전송합니다.
         * - 팩스 전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
         * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [팩스] > [전송내역] 메뉴에서 전송결과를 확인할 수 있습니다.
         * - https://docs.popbill.com/statement/java/api#SendFAX
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        // 전자명세서 문서번호
        String mgtKey = "20191004-001";

        // 발신자 번호
        String sender = "07043042991";

        // 수신자 팩스번호
        String receiver = "070111222";

        try {

            Response response = statementService.sendFAX(testCorpNum, itemCode,
                    mgtKey, sender, receiver);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "FAXSend", method = RequestMethod.GET)
    public String FAXSend(Model m) {
        /*
         * 팝빌에 전자명세서를 등록하지 않고 수신자에게 팩스전송합니다.
         * - 팩스 전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
         * - 팩스 발행 요청시 작성한 문서번호는 팩스전송 파일명으로 사용됩니다.
         * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [팩스] > [전송내역] 메뉴에서 전송결과를 확인할 수 있습니다.
         * - 팩스 전송결과를 확인하기 위해서는 선팩스 전송 요청 시 반환받은 접수번호를 이용하여
         *   팩스 API의 전송결과 확인 (GetFaxDetail) API를 이용하면 됩니다.
         * - https://docs.popbill.com/statement/java/api#FAXSend
         */

        // 팩스전송 발신번호
        String sendNum = "07043042991";

        // 수신팩스번호
        String receiveNum = "00111222";

        //  전자명세서 정보 객체
        Statement statement = new Statement();

        // [필수] 작성일자, 형태 yyyyMmdd
        statement.setWriteDate("20191004");

        // [필수] {영수, 청구} 중 기재
        statement.setPurposeType("영수");

        // [필수] {과세, 영세, 면세} 중 기재
        statement.setTaxType("과세");

        // 맞춤양식코드, 미기재시 기본양식으로 처리
        statement.setFormCode("");

        // [필수] 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        statement.setItemCode((short) 121);

        // [필수] 문서번호, 최대 24자리 영문, 숫자, '-', '_'로 구성
        statement.setMgtKey("20191004-100");


        /*********************************************************************
         *								발신자 정보
         *********************************************************************/

        // [필수] 발신자 사업자번호
        statement.setSenderCorpNum("1234567890");

        // [필수] 발신자 상호
        statement.setSenderCorpName("발신자 상호");

        // 발신자 주소
        statement.setSenderAddr("발신자 주소");

        // [필수] 발신자 대표자 성명
        statement.setSenderCEOName("발신자 대표자 성명");

        // 발신자 종사업장 식별번호, 숫자 4자리, 필요시 기재
        statement.setSenderTaxRegID("");

        // 발신자 종목
        statement.setSenderBizClass("업종");

        // 발신자 업태
        statement.setSenderBizType("업태");

        // 발신자 담당자명
        statement.setSenderContactName("발신자 담당자명");

        // 발신자 담당자 메일주소
        statement.setSenderEmail("test@test.com");

        // 발신자 담당자 연락처
        statement.setSenderTEL("070-7070-0707");

        // 발신자 담당자 휴대폰번호
        statement.setSenderHP("010-000-2222");


        /*********************************************************************
         *							수신자 정보
         *********************************************************************/

        // [필수] 수신자 사업자번호
        statement.setReceiverCorpNum("8888888888");

        // [필수] 수신자 상호
        statement.setReceiverCorpName("수신자 상호");

        // [필수] 수신자 대표자명
        statement.setReceiverCEOName("수신자 대표자 성명");

        // 수신자 주소
        statement.setReceiverAddr("수신자 주소");

        // 수신자 종목
        statement.setReceiverBizClass("수신자 종목");

        // 수신자 업태
        statement.setReceiverBizType("수신자 업태");

        // 수신자 담당자명
        statement.setReceiverContactName("수신자 담당자명");

        // 수신자 메일주소
        // 팝빌 개발환경에서 테스트하는 경우에도 안내 메일이 전송되므로,
        // 실제 거래처의 메일주소가 기재되지 않도록 주의
        statement.setReceiverEmail("test@receiver.com");


        /*********************************************************************
         *							전자명세서 기재정보
         *********************************************************************/

        // 공급가액 합계
        statement.setSupplyCostTotal("400000");

        // 세액 합계
        statement.setTaxTotal("40000");

        // 합계금액.  공급가액 + 세액
        statement.setTotalAmount("440000");

        // 기재상 일련번호 항목
        statement.setSerialNum("123");

        // 기재상 비고 항목
        statement.setRemark1("비고1");
        statement.setRemark2("비고2");
        statement.setRemark3("비고3");

        // 사업자등록증 이미지 첨부여부
        statement.setBusinessLicenseYN(false);

        // 통장사본 이미지 첨부여부
        statement.setBankBookYN(false);


        /*********************************************************************
         *							전자명세서 품목항목
         *********************************************************************/

        statement.setDetailList(new ArrayList<StatementDetail>());

        StatementDetail detail = new StatementDetail();        // 상세항목(품목) 배열

        detail.setSerialNum((short) 1);                    // 일련번호, 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);

        detail = new StatementDetail();                    // 상세항목(품목) 배열
        detail.setSerialNum((short) 2);                    // 일련번호 1부터 순차기재
        detail.setItemName("품명");                        // 품목명
        detail.setPurchaseDT("20190104");                // 거래일자
        detail.setQty("1");                                // 수량
        detail.setSupplyCost("200000");                    // 공급가액
        detail.setTax("20000");                            // 세액

        statement.getDetailList().add(detail);


        /*********************************************************************
         *							 전자명세서 추가속성
         * - 추가속성에 관한 자세한 사항은 "[전자명세서 API 연동매뉴얼] >
         *   5.2. 기본양식 추가속성 테이블"을 참조하시기 바랍니다.
         *********************************************************************/

        Map<String, String> propertyBag = new HashMap<String, String>();

        propertyBag.put("Balance", "15000");            // 전잔액
        propertyBag.put("Deposit", "5000");                // 입금액
        propertyBag.put("CBalance", "20000");            // 현잔액

        statement.setPropertyBag(propertyBag);


        try {

            String receiptNum = statementService.FAXSend(testCorpNum, statement,
                    sendNum, receiveNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "result";
    }

    @RequestMapping(value = "attachStatement", method = RequestMethod.GET)
    public String attachStatement(Model m) {
        /*
         * 전자명세서에 다른 전자명세서 1건을 첨부합니다.
         * - https://docs.popbill.com/statement/java/api#AttachStatement
         */

        // 전자명세서 코드
        int itemCode = 121;

        // 전자명세서 관리번호
        String mgtKey = "20191004-001";


        // 첨부할 전자명세서 코드
        int subItemCode = 121;

        // 첨부할 전자명세서 관리번호
        String subMgtKey = "20191004-002";

        try {

            Response response = statementService.attachStatement(testCorpNum,
                    itemCode, mgtKey, subItemCode, subMgtKey);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "detachStatement", method = RequestMethod.GET)
    public String detachStatement(Model m) {
        /*
         * 전자명세서에 첨부된 다른 전자명세서를 첨부해제합니다.
         * - https://docs.popbill.com/statement/java/api#DetachStatement
         */

        // 전자명세서 코드
        int itemCode = 121;

        // 전자명세서 관리번호
        String mgtKey = "20191004-001";


        // 첨부해제할 전자명세서 코드
        int subItemCode = 121;

        // 첨부해제할 전자명세서 관리번호
        String subMgtKey = "20191004-002";

        try {

            Response response = statementService.detachStatement(testCorpNum,
                    itemCode, mgtKey, subItemCode, subMgtKey);

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
         * 전자명세서 관련 메일전송 항목에 대한 전송여부를 목록으로 반환합니다.
         * - https://docs.popbill.com/statement/java/api#ListEmailConfig
         */

        try {

            EmailSendConfig[] emailSendConfigs = statementService.listEmailConfig(testCorpNum);

            m.addAttribute("EmailSendConfigs", emailSendConfigs);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Statement/EmailSendConfig";
    }

    @RequestMapping(value = "updateEmailConfig", method = RequestMethod.GET)
    public String updateEmailConfig(Model m) {
        /*
         * 전자명세서 관련 메일전송 항목에 대한 전송여부를 수정합니다.
         * - https://docs.popbill.com/statement/java/api#UpdateEmailConfig
         *
         * 메일전송유형
         * SMT_ISSUE : 수신자에게 전자명세서가 발행 되었음을 알려주는 메일입니다.
         * SMT_ACCEPT : 발신자에게 전자명세서가 승인 되었음을 알려주는 메일입니다.
         * SMT_DENY : 발신자에게 전자명세서가 거부 되었음을 알려주는 메일입니다.
         * SMT_CANCEL : 수신자에게 전자명세서가 취소 되었음을 알려주는 메일입니다.
         * SMT_CANCEL_ISSUE : 수신자에게 전자명세서가 발행취소 되었음을 알려주는 메일입니다.
         */

        // 메일 전송 유형
        String emailType = "SMT_ISSUE";

        // 전송 여부 (true = 전송, false = 미전송)
        Boolean sendYN = true;

        try {

            Response response = statementService.updateEmailConfig(testCorpNum,
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
         * 전자명세서 발행단가를 확인합니다.
         * - https://docs.popbill.com/statement/java/api#GetUnitCost
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        try {

            float unitCost = statementService.getUnitCost(testCorpNum, itemCode);

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
         * 전자명세서 API 서비스 과금정보를 확인합니다.
         * - https://docs.popbill.com/statement/java/api#GetChargeInfo
         */

        // 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
        int itemCode = 121;

        try {

            ChargeInfo chrgInfo = statementService.getChargeInfo(testCorpNum, itemCode);

            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }
}

