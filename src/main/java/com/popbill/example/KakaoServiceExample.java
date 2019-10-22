/*
 * 팝빌 카카오톡 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/kakao/tutorial/spring
 * - 업데이트 일자 : 2019-01-04
 * - 연동 기술지원 연락처 : 1600-9854 / 070-4304-2991~2
 * - 연동 기술지원 이메일 : code@linkhub.co.kr
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 * 	  util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 링크허브 가입시 메일로
 *    발급받은 인증정보를 참조하여 변경합니다.
 * 2) 팝빌 개발용 사이트(test.popbill.com)에 연동회원으로 가입합니다.
 * 3) 발신번호 사전등록을 합니다. (등록방법은 사이트/API 두가지 방식이 있습니다.)
 *    - 1. 팝빌 사이트 로그인 > [문자/팩스] > [카카오톡] > [발신번호 사전등록] 메뉴에서 등록
 *    - 2. getSenderNumberMgtURL API를 통해 반환된 URL을 이용하여 발신번호 등록
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
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.ChargeInfo;
import com.popbill.api.KakaoService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.kakao.ATSTemplate;
import com.popbill.api.kakao.KakaoButton;
import com.popbill.api.kakao.KakaoReceiver;
import com.popbill.api.kakao.KakaoSearchResult;
import com.popbill.api.kakao.KakaoSentInfo;
import com.popbill.api.kakao.KakaoType;
import com.popbill.api.kakao.PlusFriendID;
import com.popbill.api.kakao.SenderNumber;

/*
 * 팝빌 카카오톡 API 예제.
 */
@Controller
@RequestMapping("KakaoService")
public class KakaoServiceExample {

    @Autowired
    private KakaoService kakaoService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "Kakao/index";
    }

    @RequestMapping(value = "getPlusFriendMgtURL", method = RequestMethod.GET)
    public String getPlusFriendMgtURL(Model m) {
        /*
         * 플러스친구 계정관리 팝업 URL을 반환합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */
        try {

            String url = kakaoService.getPlusFriendMgtURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "listPlusFriendID", method = RequestMethod.GET)
    public String listPlusFriendID(Model m)  {
        /*
         * 팝빌에 등록된 플러스친구 목록을 반환합니다.
         */

        try {
            PlusFriendID[] response = kakaoService.listPlusFriendID(testCorpNum);

            m.addAttribute("listInfo", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Kakao/listPlusFriend";
    }

    @RequestMapping(value = "getSenderNumberMgtURL", method = RequestMethod.GET)
    public String getSenderNumberMgtURL(Model m) {
        /*
         * 발신번호 관리 팝업 URL을 반환합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */
        try {

            String url = kakaoService.getSenderNumberMgtURL(testCorpNum,testUserID);

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
         * 팝빌에 등록된 발신번호 목록을 반환합니다.
         */

        try {
            SenderNumber[] senderNumberList = kakaoService.getSenderNumberList(testCorpNum);
            m.addAttribute("SenderNumberList", senderNumberList);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "Kakao/SenderNumber";
    }

    @RequestMapping(value = "getATSTemplateMgtURL", method = RequestMethod.GET)
    public String getATSTemplateMgtURL(Model m) {
        /*
         * 알림톡 템플릿관리 팝업 URL을 반환합니다.
         * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
         */
        try {

            String url = kakaoService.getATSTemplateMgtURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "listATSTemplate", method = RequestMethod.GET)
    public String listATSTemplate(Model m) {
        /*
         * (주)카카오로 부터 승인된 알림톡 템플릿 목록을 확인합니다.
         * - 반환항목중 템플릿코드(templateCode)는 알림톡 전송시 사용됩니다.
         */

        try {
            ATSTemplate[] response = kakaoService.listATSTemplate(testCorpNum);

            m.addAttribute("listTemplate", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Kakao/listATSTemplate";
    }

    @RequestMapping(value = "sendATS_one", method = RequestMethod.GET)
    public String sendATS_one(Model m) {
        /*
         * 알림톡 전송을 요청합니다.
         * 사전에 승인된 템플릿의 내용과 알림톡 전송내용(content)이 다를 경우 전송실패 처리됩니다.
         */

        // 알림톡 템플릿코드
        // 승인된 알림톡 템플릿 코드는 ListATStemplate API, GetATSTemplateMgtURL API, 또는 팝빌사이트에서 확인 가능합니다.
        String templateCode = "019020000163";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 알림톡 내용 (최대 1000자)
        String content = "[ 팝빌 ]\n";
        content += "신청하신 #{템플릿코드}에 대한 심사가 완료되어 승인 처리되었습니다.\n";
        content += "해당 템플릿으로 전송 가능합니다.\n\n";
        content += "문의사항 있으시면 파트너센터로 편하게 연락주시기 바랍니다.\n\n";
        content += "팝빌 파트너센터 : 1600-8536\n";
        content += "support@linkhub.co.kr";

        // 대체문자 내용 (최대 2000byte)
        String altContent = "대체문자 내용";

        // 대체문자 전송유형, 공백-미전송, C-알림톡 내용전송, A-대체문자 내용 전송
        String altSendType = "C";

        // 수신번호
        String receiverNum = "010111222";

        // 수신자명
        String receiverName = "수신자명";

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";
        
        // 알림톡 버튼정보를 템플릿 신청시 기재한 버튼정보와 동일하게 전송하는 경우 null 처리.
        KakaoButton[] btns = null;
        
        // 알림톡 버튼 URL에 #{템플릿변수}를 기재한경우 템플릿변수 영역을 변경하여 버튼정보 구성
//        KakaoButton[] btns = new KakaoButton[1];
//
//        KakaoButton button = new KakaoButton();
//        button.setN("버튼명"); // 버튼명
//        button.setT("WL"); // 버튼타입
//        button.setU1("https://www.popbill.com"); // 버튼링크1
//        button.setU2("http://test.popbill.com"); // 버튼링크2
//        btns[0] = button;

        try {

            String receiptNum = kakaoService.sendATS(testCorpNum, templateCode, senderNum, content, altContent, altSendType,
                    receiverNum, receiverName, sndDT, testUserID, requestNum, btns);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }


    @RequestMapping(value = "sendATS_multi", method = RequestMethod.GET)
    public String sendATS_multi(Model m) {
        /*
         * [대량전송] 알림톡 전송을 요청합니다.
         * 사전에 승인된 템플릿의 내용과 알림톡 전송내용(content)이 다를 경우 전송실패 처리됩니다.
         */

        // 알림톡 템플릿코드
        // 승인된 알림톡 템플릿 코드는 ListATStemplate API, GetATSTemplateMgtURL API, 또는 팝빌사이트에서 확인 가능합니다.
        String templateCode = "019020000163";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 대체문자 전송유형, 공백-미전송, C-알림톡 내용전송, A-대체문자 내용 전송
        String altSendType = "C";
        
        // 알림톡 내용 (최대 1000자)
        String content = "[ 팝빌 ]\n";
        content += "신청하신 #{템플릿코드}에 대한 심사가 완료되어 승인 처리되었습니다.\n";
        content += "해당 템플릿으로 전송 가능합니다.\n\n";
        content += "문의사항 있으시면 파트너센터로 편하게 연락주시기 바랍니다.\n\n";
        content += "팝빌 파트너센터 : 1600-8536\n";
        content += "support@linkhub.co.kr";

        // 카카오톡 수신정보 배열, 최대 1000건
        KakaoReceiver[] receivers = new KakaoReceiver[10];
        for (int i = 0; i < 10; i++) {
            KakaoReceiver message = new KakaoReceiver();
            message.setReceiverNum("010111222");    // 수신번호
            message.setReceiverName("수신자명" + i);    // 수신자명
            message.setMessage(content);    // 알림톡 템플릿 내용, 최대 1000자
            message.setAltMessage("대체문자 개별내용입니다." + i); // 대체문자 내용
            receivers[i] = message;
        }

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";
        
        // 알림톡 버튼정보를 템플릿 신청시 기재한 버튼정보와 동일하게 전송하는 경우 null 처리.
        KakaoButton[] btns = null;
        
        // 알림톡 버튼 URL에 #{템플릿변수}를 기재한경우 템플릿변수 영역을 변경하여 버튼정보 구성
//        KakaoButton[] btns = new KakaoButton[1];
//
//        KakaoButton button = new KakaoButton();
//        button.setN("버튼명"); // 버튼명
//        button.setT("WL"); // 버튼타입
//        button.setU1("https://www.popbill.com"); // 버튼링크1
//        button.setU2("http://test.popbill.com"); // 버튼링크2
//        btns[0] = button;

        try {

            String receiptNum = kakaoService.sendATS(testCorpNum, templateCode, senderNum, altSendType, receivers, sndDT,
                    testUserID, requestNum, btns);
            m.addAttribute("Result", receiptNum);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "result";
    }

    @RequestMapping(value = "sendATS_same", method = RequestMethod.GET)
    public String sendATS_same(Model m) {
        /*
         * [동보전송] 알림톡 전송을 요청합니다.
         * 사전에 승인된 템플릿의 내용과 알림톡 전송내용(content)이 다를 경우 전송실패 처리됩니다.
         */

        // 알림톡 템플릿코드
        // 승인된 알림톡 템플릿 코드는 ListATStemplate API, GetATSTemplateMgtURL API, 또는 팝빌사이트에서 확인 가능합니다.
        String templateCode = "019020000163";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 알림톡 내용 (최대 1000자)
        String content = "[ 팝빌 ]\n";
        content += "신청하신 #{템플릿코드}에 대한 심사가 완료되어 승인 처리되었습니다.\n";
        content += "해당 템플릿으로 전송 가능합니다.\n\n";
        content += "문의사항 있으시면 파트너센터로 편하게 연락주시기 바랍니다.\n\n";
        content += "팝빌 파트너센터 : 1600-8536\n";
        content += "support@linkhub.co.kr";

        // 대체문자 내용 (최대 2000byte)
        String altContent = "대체문자 내용";

        // 대체문자 전송유형, 공백-미전송, C-알림톡 내용전송, A-대체문자 내용 전송
        String altSendType = "C";

        // 카카오톡 수신정보 배열, 최대 1000건
        KakaoReceiver[] receivers = new KakaoReceiver[10];
        for (int i = 0; i < 10; i++) {
            KakaoReceiver message = new KakaoReceiver();
            message.setReceiverNum("010111222"); // 수신번호
            message.setReceiverName("수신자명" + i); // 수신자명
            receivers[i] = message;
        }

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";
        
        // 알림톡 버튼정보를 템플릿 신청시 기재한 버튼정보와 동일하게 전송하는 경우 null 처리.
        KakaoButton[] btns = null;
        
        // 알림톡 버튼 URL에 #{템플릿변수}를 기재한경우 템플릿변수 영역을 변경하여 버튼정보 구성
//        KakaoButton[] btns = new KakaoButton[1];
//
//        KakaoButton button = new KakaoButton();
//        button.setN("버튼명"); // 버튼명
//        button.setT("WL"); // 버튼타입
//        button.setU1("https://www.popbill.com"); // 버튼링크1
//        button.setU2("http://test.popbill.com"); // 버튼링크2
//        btns[0] = button;

        try {

            String receiptNum = kakaoService.sendATS(testCorpNum, templateCode, senderNum,
                    content, altContent, altSendType, receivers, sndDT, testUserID, requestNum, btns);
            m.addAttribute("Result", receiptNum);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "result";
    }

    @RequestMapping(value = "sendFTS_one", method = RequestMethod.GET)
    public String sendFTS_one(Model m) {
        /*
         * 친구톡(텍스트) 전송을 요청합니다.
         * - 친구톡은 심야 전송(20:00~08:00)이 제한됩니다.
         */

        // 팝빌에 등록된 플러스친구 아이디
        String plusFriendID = "@팝빌";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 친구톡 내용 (최대 1000자)
        String content = "친구톡 메시지 내용";

        // 대체문자 내용
        String altContent = "대체문자 내용";

        // 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
        String altSendType = "A";

        // 수신번호
        String receiverNum = "010111222";

        // 수신자명
        String receiverName = "수신자명";

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 광고전송여부
        Boolean adsYN = false;


        // 친구톡 버튼 배열, 최대 5개
        KakaoButton[] btns = new KakaoButton[2];

        KakaoButton button = new KakaoButton();
        button.setN("버튼명"); // 버튼명
        button.setT("WL"); // 버튼타입
        button.setU1("http://www.popbill.com"); // 버튼링크1
        button.setU2("http://test.popbill.com"); // 버튼링크2
        btns[0] = button;

        button = new KakaoButton();
        button.setN("버튼명2");
        button.setT("WL");
        button.setU1("http://www.popbill.com");
        button.setU2("http://test.popbill.com");
        btns[1] = button;

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = kakaoService.sendFTS(testCorpNum, plusFriendID, senderNum, content,
                    altContent, altSendType, btns, receiverNum, receiverName, sndDT, adsYN,
                    testUserID, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "sendFTS_multi", method = RequestMethod.GET)
    public String sendFTS_multi(Model m) {
        /*
         * [대량전송] 친구톡(텍스트) 전송을 요청합니다.
         * - 친구톡은 심야 전송(20:00~08:00)이 제한됩니다.
         */

        // 팝빌에 등록된 플러스친구 아이디
        String plusFriendID = "@팝빌";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
        String altSendType = "A";

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 광고전송여부
        Boolean adsYN = false;


        // 카카오톡 수신정보 배열, 최대 1000건
        KakaoReceiver[] receivers = new KakaoReceiver[100];
        for (int i = 0; i < 100; i++) {
            KakaoReceiver message = new KakaoReceiver();
            message.setReceiverNum("010111222"); // 수신번호
            message.setReceiverName("수신자명" + i);    // 수신자명
            message.setMessage("친구톡 개별내용" + i); // 친구톡 내용, 최대 1000자
            message.setAltMessage("대체문자 개별내용" + i); // 대체문자 내용
            receivers[i] = message;
        }


        // 친구톡 버튼 배열, 최대 5개
        KakaoButton[] btns = new KakaoButton[2];

        KakaoButton button = new KakaoButton();
        button.setN("버튼명"); // 버튼명
        button.setT("WL"); // 버튼타입
        button.setU1("http://www.popbill.com"); // 버튼링크1
        button.setU2("http://test.popbill.com"); // 버튼링크2
        btns[0] = button;

        button = new KakaoButton();
        button.setN("버튼명2");
        button.setT("WL");
        button.setU1("http://www.popbill.com");
        button.setU2("http://test.popbill.com");
        btns[1] = button;

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = kakaoService.sendFTS(testCorpNum, plusFriendID, senderNum, altSendType,
                    receivers, btns, sndDT, adsYN, testUserID, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "sendFTS_same", method = RequestMethod.GET)
    public String sendFTS_same(Model m) {
        /*
         * [동보전송] 친구톡(텍스트) 전송을 요청합니다.
         * - 친구톡은 심야 전송(20:00~08:00)이 제한됩니다.
         */

        // 팝빌에 등록된 플러스친구 아이디
        String plusFriendID = "@팝빌";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 친구톡 내용 (최대 1000자)
        String content = "친구톡 메시지 내용";

        // 대체문자 내용
        String altContent = "대체문자 내용";

        // 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
        String altSendType = "A";

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 광고전송여부
        Boolean adsYN = false;


        // 카카오톡 수신정보 배열, 최대 1000건
        KakaoReceiver[] receivers = new KakaoReceiver[100];
        for (int i = 0; i < 100; i++) {
            KakaoReceiver message = new KakaoReceiver();
            message.setReceiverNum("010111222");
            message.setReceiverName("수신자명" + i);
            receivers[i] = message;
        }


        // 친구톡 버튼 배열, 최대 5개
        KakaoButton[] btns = new KakaoButton[2];

        KakaoButton button = new KakaoButton();
        button.setN("버튼명"); // 버튼명
        button.setT("WL"); // 버튼타입
        button.setU1("http://www.popbill.com"); // 버튼링크1
        button.setU2("http://test.popbill.com"); // 버튼링크2
        btns[0] = button;

        button = new KakaoButton();
        button.setN("버튼명2");
        button.setT("WL");
        button.setU1("http://www.popbill.com");
        button.setU2("http://test.popbill.com");
        btns[1] = button;

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = kakaoService.sendFTS(testCorpNum, plusFriendID, senderNum, content,
                    altContent, altSendType, receivers, btns, sndDT, adsYN, testUserID, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "sendFMS_one", method = RequestMethod.GET)
    public String sendFMS_one(Model m) {
        /*
         * 친구톡(이미지) 전송을 요청합니다.
         * - 친구톡은 심야 전송(20:00~08:00)이 제한됩니다.
         * - 이미지 전송규격 / jpg 포맷, 용량 최대 500KByte, 이미지 높이/너비 비율 1.333 이하, 1/2 이상
         */

        // 팝빌에 등록된 플러스친구 아이디
        String plusFriendID = "@팝빌";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 친구톡 내용 (최대 400자)
        String content = "친구톡 메시지 내용";

        // 대체문자 내용 (최대 2000byte)
        String altContent = "대체문자 내용";

        // 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
        String altSendType = "A";

        // 수신번호
        String receiverNum = "010111222";

        // 수신자명
        String receiverName = "수신자명";

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 광고전송여부
        Boolean adsYN = false;


        // 친구톡 버튼 배열, 최대 5개
        KakaoButton[] btns = new KakaoButton[2];

        KakaoButton button = new KakaoButton();
        button.setN("버튼명"); // 버튼명
        button.setT("WL"); // 버튼타입
        button.setU1("http://www.popbill.com"); // 버튼링크1
        button.setU2("http://test.popbill.com"); // 버튼링크2
        btns[0] = button;

        button = new KakaoButton();
        button.setN("버튼명2");
        button.setT("WL");
        button.setU1("http://www.popbill.com");
        button.setU2("http://test.popbill.com");
        btns[1] = button;

        // 첨부이미지 파일
        File file = new File("/Users/John/Desktop/tmp/test03.jpg");

        // 이미지 파일 링크
        String imageURL = "http://test.popbill.com";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = kakaoService.sendFMS(testCorpNum, plusFriendID, senderNum, content,
                    altContent, altSendType, btns, receiverNum, receiverName, sndDT, adsYN, file, imageURL,
                    testUserID, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "sendFMS_multi", method = RequestMethod.GET)
    public String sendFMS_multi(Model m) {
        /*
         * [대량전송] 친구톡(이미지) 전송을 요청합니다.
         * - 친구톡은 심야 전송(20:00~08:00)이 제한됩니다.
         * - 이미지 전송규격 / jpg 포맷, 용량 최대 500KByte, 이미지 높이/너비 비율 1.333 이하, 1/2 이상
         */

        // 팝빌에 등록된 플러스친구 아이디
        String plusFriendID = "@팝빌";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
        String altSendType = "A";

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 광고전송여부
        Boolean adsYN = false;


        // 카카오톡 수신정보 배열, 최대 1000건
        KakaoReceiver[] receivers = new KakaoReceiver[100];
        for (int i = 0; i < 100; i++) {
            KakaoReceiver message = new KakaoReceiver();
            message.setReceiverNum("010111222"); // 수신번호
            message.setReceiverName("수신자명" + i); // 수신자명
            message.setMessage("친구톡 개별내용" + i); // 친구톡 내용, 최대 400자
            message.setAltMessage("대체문자 개별내용" + i); // 대체문자 내용
            receivers[i] = message;
        }


        // 친구톡 버튼 배열, 최대 5개
        KakaoButton[] btns = new KakaoButton[2];

        KakaoButton button = new KakaoButton();
        button.setN("버튼명"); // 버튼명
        button.setT("WL"); // 버튼타입
        button.setU1("http://www.popbill.com"); // 버튼링크1
        button.setU2("http://test.popbill.com"); // 버튼링크2
        btns[0] = button;

        button = new KakaoButton();
        button.setN("버튼명2");
        button.setT("WL");
        button.setU1("http://www.popbill.com");
        button.setU2("http://test.popbill.com");
        btns[1] = button;


        // 첨부이미지 파일
        File file = new File("/Users/John/Desktop/tmp/test03.jpg");

        // 이미지 파일 링크
        String imageURL = "http://test.popbill.com";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = kakaoService.sendFMS(testCorpNum, plusFriendID, senderNum, altSendType,
                    receivers, btns, sndDT, adsYN, file, imageURL, testUserID, requestNum);

            m.addAttribute("Result", receiptNum);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "sendFMS_same", method = RequestMethod.GET)
    public String sendFMS_same(Model m) {
        /*
         * [동보전송] 친구톡(이미지) 전송을 요청합니다.
         * - 친구톡은 심야 전송(20:00~08:00)이 제한됩니다.
         * - 이미지 전송규격 / jpg 포맷, 용량 최대 500KByte, 이미지 높이/너비 비율 1.333 이하, 1/2 이상
         */

        // 팝빌에 등록된 플러스친구 아이디
        String plusFriendID = "@팝빌";

        // 발신번호 (팝빌에 등록된 발신번호만 이용가능)
        String senderNum = "07043042991";

        // 친구톡 내용 (최대 400자)
        String content = "친구톡 메시지 내용";

        // 대체문자 내용 (최대 2000byte)
        String altContent = "대체문자 내용";

        // 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
        String altSendType = "A";

        // 예약전송일시, 형태(yyyyMMddHHmmss)
        String sndDT = "";

        // 광고전송여부
        Boolean adsYN = false;


        // 카카오톡 수신정보 배열, 최대 1000건
        KakaoReceiver[] receivers = new KakaoReceiver[100];
        for (int i = 0; i < 100; i++) {
            KakaoReceiver message = new KakaoReceiver();
            message.setReceiverNum("010111222"); // 수신번호
            message.setReceiverName("수신자명" + i); // 수신자명
            receivers[i] = message;
        }

        // 친구톡 버튼 배열, 최대 5개
        KakaoButton[] btns = new KakaoButton[2];

        KakaoButton button = new KakaoButton();
        button.setN("버튼명"); // 버튼명
        button.setT("WL"); // 버튼타입
        button.setU1("http://www.popbill.com"); // 버튼링크1
        button.setU2("http://test.popbill.com"); // 버튼링크2
        btns[0] = button;

        button = new KakaoButton();
        button.setN("버튼명2");
        button.setT("WL");
        button.setU1("http://www.popbill.com");
        button.setU2("http://test.popbill.com");
        btns[1] = button;


        // 첨부이미지 파일
        File file = new File("/Users/John/Desktop/tmp/test03.jpg");


        // 이미지 파일 링크
        String imageURL = "http://test.popbill.com";

        // 전송요청번호
        // 파트너가 전송 건에 대해 관리번호를 구성하여 관리하는 경우 사용.
        // 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
        String requestNum = "";

        try {

            String receiptNum = kakaoService.sendFMS(testCorpNum, plusFriendID, senderNum, content,
                    altContent, altSendType, receivers, btns, sndDT, adsYN, file, imageURL,
                    testUserID, requestNum);

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
         * 알림톡/친구톡 전송요청시 발급받은 접수번호(receiptNum)로 예약전송건을 취소합니다.
         * - 예약취소는 예약전송시간 10분전까지만 가능합니다.
         */

        // 예약전송 접수번호
        String receiptNum = "019010415153200001";

        try {
            Response response = kakaoService.cancelReserve(testCorpNum, receiptNum);

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
         * 전송요청번호(requestNum)를 할당한 알림톡/친구톡 예약전송건을 취소합니다.
         * - 예약전송 취소는 예약시간 10분전까지만 가능합니다.
         */

        // 예약전송 요청시 할당한 전송요청번호
        String requestNum = "20190104-001";

        try {
            Response response = kakaoService.cancelReserveRN(testCorpNum, requestNum);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getMessages", method = RequestMethod.GET)
    public String getMessages(Model m) {
        /*
         * 알림톡/친구톡 전송요청시 발급받은 접수번호(receiptNum)로 전송결과를 확인합니다.
         */

        // 카카오톡 접수번호
        String receiptNum = "019010415172000001";

        try {

            KakaoSentInfo sentInfos = kakaoService.getMessages(testCorpNum, receiptNum);

            m.addAttribute("sentInfos", sentInfos);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Kakao/getMessage";
    }

    @RequestMapping(value = "getMessagesRN", method = RequestMethod.GET)
    public String getMessagesRN(Model m) {
        /*
         * 전송요청번호(requestNum)를 할당한 알림톡/친구톡 전송내역 및 전송상태를 확인합니다.
         */

        // 카카오톡 접수번호
        String requestNum = "20190104-001";

        try {

            KakaoSentInfo sentInfos = kakaoService.getMessagesRN(testCorpNum, requestNum);

            m.addAttribute("sentInfos", sentInfos);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Kakao/getMessage";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
        /*
         * 검색조건을 사용하여 친구톡/알림톡 내역을 조회합니다.
         * - 최대 검색기간 : 6개월 이내
         */

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20181201";

        // 종료일자, 날짜형식(yyyyMMdd)
        String EDate = "20190104";

        // 전송상태 배열, 0-대기, 1-전송중, 2-성공, 3-대체, 4-실패, 5-취소
        String[] State = {"0", "1", "2", "3", "4"};

        // 검색대상 배열, ATS-알림톡, FTS-친구톡 텍스트, FMS-친구톡 이미지
        String[] Item = {"ATS", "FTS", "FMS"};

        // 예약전송여부, 공백-전체조회, 1-예약전송건 조회, 0-즉시전송건 조회
        String ReserveYN = "";

        // 개인조회 여부, false-전체조회, true-개인조회
        Boolean SenderYN = false;

        // 페이지 번호
        int Page = 1;

        // 페이지당 목록개수 (최대 1000건)
        int PerPage = 20;

        // 정렬방향 D-내림차순, A-오름차순
        String Order = "D";

        // 조회 검색어.
        // 카카오톡 전송시 입력한 수신자명 기재.
        // 조회 검색어를 포함한 수신자명을 검색합니다.
        String QString = "";

        try {

            KakaoSearchResult response = kakaoService.search(testCorpNum, SDate,
                    EDate, State, Item, ReserveYN, SenderYN, Page, PerPage, Order, testUserID, QString);

            m.addAttribute("SearchResult", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }
        return "Kakao/searchResult";
    }

    @RequestMapping(value = "getSentListURL", method = RequestMethod.GET)
    public String getSentListURL(Model m) {
        /*
         * 카카오톡 전송내역 팝업 URL을 반환합니다.
         * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
         */

        try {

            String url = kakaoService.getSentListURL(testCorpNum,testUserID);

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
         * 알림톡/친구톡 서비스 전송단가를 확인합니다.
         */

        // 카카오톡 전송유형, ATS-알림톡, FTS-친구톡 텍스트, FMS-친구톡 이미지
        KakaoType kakaoType = KakaoType.ATS;

        try {

            float unitCost = kakaoService.getUnitCost(testCorpNum, kakaoType);

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
         * 알림톡/친구톡 서비스 API 서비스 과금정보를 확인합니다.
         */

        // 카카오톡 전송유형, ATS-알림톡, FTS-친구톡 텍스트, FMS-친구톡 이미지
        KakaoType kakaoType = KakaoType.ATS;

        try {

            ChargeInfo chrgInfo = kakaoService.getChargeInfo(testCorpNum, kakaoType);
            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

}
