/*
 * 팝빌 팩스 API Java SDK SpringMVC Example
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

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.ChargeInfo;
import com.popbill.api.FaxService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.fax.FAXSearchResult;
import com.popbill.api.fax.FaxResult;
import com.popbill.api.fax.Receiver;
import com.popbill.api.fax.SenderNumber;

/*
 * 팝빌 팩스 API 예제.
 */
@Controller
@RequestMapping("FaxService")
public class FaxServiceExample {

    @Autowired
    private FaxService faxService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "Fax/index";
    }

    @RequestMapping(value = "getSenderNumberMgtURL", method = RequestMethod.GET)
    public String getSenderNumberMgtURL(Model m) {
        /*
         * 팩스 발신번호 관리 팝업 URL을 반합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */
        try {

            String url = faxService.getSenderNumberMgtURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getSenderNumberList", method = RequestMethod.GET)
    public String getSenderNumberList(Model m) {
        /*
         * 팝빌에 등록된 발신번호 목록을 확인합니다.
         */

        try {
            SenderNumber[] senderNumberList = faxService.getSenderNumberList(testCorpNum);
            m.addAttribute("SenderNumberList", senderNumberList);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "Fax/SenderNumber";
    }

    @RequestMapping(value = "sendFAX", method = RequestMethod.GET)
    public String sendFAX(Model m) throws URISyntaxException {
        /*
         * 팩스를 전송합니다. (전송할 파일 개수는 최대 20개까지 가능)
         * - 팩스전송 문서 파일포맷 안내 : http://blog.linkhub.co.kr/2561
         */

        // 발신번호
        String sendNum = "07043042995";

        // 수신번호
        String receiveNum = "010111222";

        // 수신자명
        String receiveName = "수신자 명칭";

        File[] files = new File[2];
        try {
            // 파일 전송 개수 최대 20개
            // 팩스전송 파일포맷 안내 : http://blog.linkhub.co.kr/2561/
            files[0] = new File(getClass().getClassLoader().getResource("nonbg_statement.pdf").toURI());
            files[1] = new File(getClass().getClassLoader().getResource("nonbg_statement.pdf").toURI());
        } catch (URISyntaxException e1) {
            throw e1;
        }

        // 전송 예약일시
        Date reserveDT = null;

        // 광고팩스 전송여부
        Boolean adsYN = false;

        // 팩스제목
        String title = "팩스 제목";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = faxService.sendFAX(testCorpNum, sendNum, receiveNum,
                    receiveName, files, reserveDT, testUserID, adsYN, title, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "sendFAX_Multi", method = RequestMethod.GET)
    public String sendFAX_Multi(Model m) throws URISyntaxException {
        /*
         * [대량전송] 팩스를 전송합니다. (전송할 파일 개수는 최대 20개까지 가능)
         * - 팩스전송 문서 파일포맷 안내 : http://blog.linkhub.co.kr/2561
         */

        // 발신번호
        String sendNum = "07043042991";

        // 수신자 정보 (최대 1000건)
        Receiver[] receivers = new Receiver[2];

        Receiver receiver1 = new Receiver();
        receiver1.setReceiveName("수신자1");		// 수신자명
        receiver1.setReceiveNum("010111222");	// 수신팩스번호
        receivers[0] = receiver1;

        Receiver receiver2 = new Receiver();
        receiver2.setReceiveName("수신자2");		// 수신자명
        receiver2.setReceiveNum("010333444");	// 수신팩스번호
        receivers[1] = receiver1;

        File[] files = new File[2];
        try {
            // 파일 전송 개수 최대 20개
            // 팩스전송 파일포맷 안내 : http://blog.linkhub.co.kr/2561/
            files[0] = new File(getClass().getClassLoader().getResource("nonbg_statement.pdf").toURI());
            files[1] = new File(getClass().getClassLoader().getResource("nonbg_statement.pdf").toURI());
        } catch (URISyntaxException e1) {
            throw e1;
        }

        // 전송예약일시
        Date reserveDT = null;

        // 광고팩스 전송여부
        Boolean adsYN = false;

        // 팩스제목
        String title = "팩스 동보전송 제목";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = faxService.sendFAX(testCorpNum, sendNum, receivers,
                    files, reserveDT, testUserID, adsYN, title, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "resendFAX", method = RequestMethod.GET)
    public String resendFAX(Model m) {
        /*
         * 팩스를 재전송합니다.
         * - 접수일로부터 60일이 경과된 경우 재전송할 수 없습니다.
         * - 팩스 재전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
         */

        // 원본 팩스 접수번호
        String orgReceiptNum = "019010316355100002";

        // 발신번호, 공백처리시 기존전송정보로 재전송
        String sendNum = "07043042991";

        // 발신자명, 공백처리시 기존전송정보로 재전송
        String sendName = "발신자명";

        // 수신번호/수신자명 모두 공백처리시 기존전송정보로 재전송
        // 수신번호
        String receiveNum = "";

        // 수신자명
        String receiveName = "";

        // 전송 예약일시
        Date reserveDT = null;

        // 팩스 제목
        String title = "팩스 재전송 제목";

        // 재전송 팩스의 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        // 재전송 팩스의 전송상태확인(getFaxResultRN) / 예약전송취소(cancelReserveRN) 에 이용됩니다.
        String requestNum = "";

        try {

            String receiptNum = faxService.resendFAX(testCorpNum, orgReceiptNum, sendNum,
                    sendName, receiveNum, receiveName, reserveDT, testUserID, title, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "resendFAX_Multi", method = RequestMethod.GET)
    public String resendFAX_Multi(Model m) {
        /*
         * [대량전송] 팩스를 재전송합니다.
         * - 접수일로부터 60일이 경과된 경우 재전송할 수 없습니다.
         * - 팩스 재전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
         */

        // 원본 팩스 접수번호
        String orgReceiptNum = "019010316355100002";

        // 발신번호, 공백처리시 기존전송정보로 재전송
        String sendNum = "07043042991";

        // 발신자명, 공백처리시 기존전송정보로 재전송
        String sendName = "발신자명";

        // 팩스수신정보를 기존전송정보와 동일하게 재전송하는 경우, receivers 변수 null 처리
        Receiver[] receivers = null;

//      팩스수신정보를 기존전송정보와 다르게 재전송하는 경우, 아래의 코드 적용 (최대 1000건)
//      Receiver[] receivers = new Receiver[2];

//		Receiver receiver1 = new Receiver();
//		receiver1.setReceiveName("수신자1");		// 수신자명
//		receiver1.setReceiveNum("010111222");	// 수신팩스번호
//      receivers[0] = receiver1;

//		Receiver receiver2 = new Receiver();
//		receiver2.setReceiveName("수신자2");		// 수신자명
//		receiver2.setReceiveNum("010333444");	// 수신팩스번호
//      receivers[1] = receiver1;

        // 전송 예약일시
        Date reserveDT = null;

        // 팩스제목
        String title = "팩스 재전송(동보) 제목";

        // 재전송 팩스의 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        // 재전송 팩스의 전송상태확인(getFaxResultRN) / 예약전송취소(cancelReserveRN) 에 이용됩니다.
        String requestNum = "";

        try {

            String receiptNum = faxService.resendFAX(testCorpNum, orgReceiptNum, sendNum,
                    sendName, receivers, reserveDT, testUserID, title, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "resendFAXRN", method = RequestMethod.GET)
    public String resendFAXRN(Model m) {
        /*
         * 전송요청번호(requestNum)을 할당한 팩스를 재전송합니다.
         * - 접수일로부터 60일이 경과된 경우 재전송할 수 없습니다.
         * - 팩스 재전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
         */

        // 재전송 팩스의 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        // 재전송 팩스의 전송상태확인(getFaxResultRN) / 예약전송취소(cancelReserveRN) 에 이용됩니다.
        String requestNum = "";

        // 발신번호, 공백처리시 기존전송정보로 재전송
        String sendNum = "07043042991";

        // 발신자명, 공백처리시 기존전송정보로 재전송
        String sendName = "발신자명";

        // 수신번호/수신자명 모두 공백처리시 기존전송정보로 재전송
        // 수신번호
        String receiveNum = "";

        // 수신자명
        String receiveName = "";

        // 전송 예약일시
        Date reserveDT = null;

        // 팩스 제목
        String title = "팩스 재전송 제목";

        // 원본 팩스 전송시 할당한 전송요청번호(requestNum)
        String orgRequestNum = "20190104-001";

        try {

            String receiptNum = faxService.resendFAXRN(testCorpNum, requestNum, sendNum,
                    sendName, receiveNum, receiveName, reserveDT, testUserID, title, orgRequestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "resendFAXRN_Multi", method = RequestMethod.GET)
    public String resendFAXRN_Multi(Model m) {
        /*
         * [대량전송] 전송요청번호(requestNum)을 할당한 팩스를 재전송합니다.
         * - 접수일로부터 60일이 경과된 경우 재전송할 수 없습니다.
         * - 팩스 재전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
         */

        // 재전송 팩스의 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        // 재전송 팩스의 전송상태확인(getFaxResultRN) / 예약전송취소(cancelReserveRN) 에 이용됩니다.
        String requestNum = "";

        // 발신번호, 공백처리시 기존전송정보로 재전송
        String sendNum = "07043042991";

        // 발신자명, 공백처리시 기존전송정보로 재전송
        String sendName = "발신자명";

        // 팩스수신정보를 기존전송정보와 동일하게 재전송하는 경우, receivers 변수 null 처리
        Receiver[] receivers = null;

//      팩스수신정보를 기존전송정보와 다르게 재전송하는 경우, 아래의 코드 적용 (최대 1000건)
//      Receiver[] receivers = new Receiver[2];

//		Receiver receiver1 = new Receiver();
//		receiver1.setReceiveName("수신자1");		// 수신자명
//		receiver1.setReceiveNum("010111222");	// 수신팩스번호
//      receivers[0] = receiver1;

//		Receiver receiver2 = new Receiver();
//		receiver2.setReceiveName("수신자2");		// 수신자명
//		receiver2.setReceiveNum("010333444");	// 수신팩스번호
//      receivers[1] = receiver1;

        // 전송 예약일시
        Date reserveDT = null;

        // 팩스제목
        String title = "팩스 재전송(동보) 제목";

        // 원본 팩스 전송시 할당한 전송요청번호(requestNum)
        String orgRequestNum = "20190104-001";

        try {

            String receiptNum = faxService.resendFAXRN(testCorpNum, requestNum, sendNum,
                    sendName, receivers, reserveDT, testUserID, title, orgRequestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "cancelReserve", method = RequestMethod.GET)
    public String cancelReserve(Model m) {
        /*
         * 팩스전송요청시 발급받은 접수번호(receiptNum)로 팩스 예약전송건을 취소합니다.
         * - 예약전송 취소는 예약전송시간 10분전까지 가능하며, 팩스변환 이후 가능합니다.
         */

        // 전송요청(sendFAX)시 발급받은 팩스접수번호
        String receiptNum = "019010414344900001";

        try {
            Response response = faxService.cancelReserve(testCorpNum, receiptNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "cancelReserveRN", method = RequestMethod.GET)
    public String cancelReserveRN(Model m) {
        /*
         * 팩스전송요청시 할당한 전송요청번호(requestNum)로 팩스 예약전송건을 취소합니다.
         * - 예약전송 취소는 예약전송시간 10분전까지 가능하며, 팩스변환 이후 가능합니다.
         */

        // 예약팩스전송 요청시 할당한 전송요청번호
        String requestNum = "20190104-001";

        try {
            Response response = faxService.cancelReserveRN(testCorpNum, requestNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getFaxResult", method = RequestMethod.GET)
    public String getFaxResult(Model m) {
        /*
         * 팩스전송요청시 발급받은 접수번호(receiptNum)로 전송결과를 확인합니다
         * - 응답항목에 대한 자세한 사항은 "[팩스 API 연동매뉴얼] >  3.3.1 GetFaxDetail (전송내역 및 전송상태 확인)을 참조하시기 바랍니다.
         */

        // 전송요청(sendFAX)시 발급받은 접수번호
        String receiptNum = "019010414350900001";

        try {
            FaxResult[] faxResults = faxService.getFaxResult(testCorpNum, receiptNum);

            m.addAttribute("FaxResults", faxResults);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Fax/FaxResult";
    }

    @RequestMapping(value = "getFaxResultRN", method = RequestMethod.GET)
    public String getFaxResultRN(Model m) {
        /*
         * 팩스전송요청시 할당한 전송요청번호(requestNum)으로 전송결과를 확인합니다
         * - 응답항목에 대한 자세한 사항은 "[팩스 API 연동매뉴얼] >  3.3.2 GetFaxDetailRN (전송내역 및 전송상태 확인 - 요청번호 할당)을 참조하시기 바랍니다.
         */

        // 팩스전송 요청시 할당한 전송요청번호
        String requestNum = "20190104-001";

        try {
            FaxResult[] faxResults = faxService.getFaxResultRN(testCorpNum, requestNum);

            m.addAttribute("FaxResults", faxResults);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Fax/FaxResult";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /*
         * 검색조건을 사용하여 팩스전송 내역을 조회합니다.
         * - 최대 검색기간 : 6개월 이내
         */

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20181201";

        // 종료일자, 날짜형식(yyyyMMdd)
        String EDate = "20190104";

        // 전송상태 배열, 1-대기, 2-성공, 3-실패, 4-취소
        String[] State = {"1", "2", "3", "4"};

        // 예약여부, false-전체조회, true-예약전송건 조회
        Boolean ReserveYN = false;

        // 개인조회 여부, false- 전체조회, true-개인조회
        Boolean SenderOnly = false;

        // 페이지 번호
        int Page = 1;

        // 페이지당 목록개수 (최대 1000건)
        int PerPage = 100;

        // 정렬방향 D-내림차순, A-오름차순
        String Order = "D";

        // 조회 검색어.
        // 팩스 전송시 입력한 발신자명 또는 수신자명 기재.
        // 조회 검색어를 포함한 발신자명 또는 수신자명을 검색합니다.
        String QString = "";

        try {

            FAXSearchResult response = faxService.search(testCorpNum, SDate, EDate,
                    State, ReserveYN, SenderOnly, Page, PerPage, Order, QString);

            m.addAttribute("SearchResult", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "Fax/SearchResult";
    }

    @RequestMapping(value = "getSentListURL", method = RequestMethod.GET)
    public String getSentListURL(Model m) {
        /*
         * 팩스 전송내역 팝업 URL을 반환합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */
        try {

            String url = faxService.getSentListURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getPreviewURL", method = RequestMethod.GET)
    public String getPrevewURL(Model m) {
        /*
         * 접수한 팩스 전송건에 대한 미리보기 팝업 URL을 반환합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */
        try {

            // 팩스 접수번호
            String receiptNum = "019010414350900001";

            String url = faxService.getPreviewURL(testCorpNum, receiptNum);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
    public String getUnitCost(Model m) {
        /*
         * 팩스 전송단가를 확인합니다.
         */

        try {

            float unitCost = faxService.getUnitCost(testCorpNum);

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
         * 팩스 API 서비스 과금정보를 확인합니다.
         */

        try {

            ChargeInfo chrgInfo = faxService.getChargeInfo(testCorpNum);

            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }
}
