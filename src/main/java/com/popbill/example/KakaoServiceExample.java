/**
  * 팝빌 카카오톡 API Java SDK SpringMVC Example
  *
  * SpringMVC 연동 튜토리얼 안내 : https://developers.popbill.com/guide/kakaotalk/java/getting-started/tutorial?fwn=springmvc
  * 연동 기술지원 연락처 : 1600-9854
  * 연동 기술지원 이메일 : code@linkhubcorp.com
  *
  * <테스트 연동개발 준비사항>
  *  1) API Key 변경 (연동신청 시 메일로 전달된 정보)
  *     - LinkID : 링크허브에서 발급한 링크아이디
  *     - SecretKey : 링크허브에서 발급한 비밀키
  *  2) SDK 환경설정 옵션 설정
  *     - IsTest : 연동환경 설정, true-테스트, false-운영(Production), (기본값:true)
  *     - IPRestrictOnOff : 인증토큰 IP 검증 설정, true-사용, false-미사용, (기본값:true)
  *     - UseStaticIP : 통신 IP 고정, true-사용, false-미사용, (기본값:false)
  *     - UseLocalTimeYN : 로컬시스템 시간 사용여부, true-사용, false-미사용, (기본값:true)
  *  3) 비즈니스 채널 등록 및 알림톡 템플릿을 신청합니다.
  *    - 1. 비즈니스 채널 등록 (등록방법은 사이트/API 두가지 방식이 있습니다.)
  *       └ 팝빌 사이트 로그인 [문자/팩스] > [카카오톡] > [카카오톡 관리] > '비즈니스 채널 관리' 메뉴에서 등록
  *       └ GetPlusFriendMgtURL API 를 통해 반환된 URL을 이용하여 등록
  *    - 2. 알림톡 템플릿 신청 (등록방법은 사이트/API 두가지 방식이 있습니다.)
  *       └ 팝빌 사이트 로그인 [문자/팩스] > [카카오톡] > [카카오톡 관리] > '알림톡 템플릿 관리' 메뉴에서 등록
  *       └ GetATSTemplateMgtURL API 를 통해 URL을 이용하여 등록
  */
package com.popbill.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import com.popbill.api.Attachment;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 팝빌 카카오톡 API 예제.
 */
@Controller
@RequestMapping("KakaoService")
public class KakaoServiceExample {

	@Autowired
	private KakaoService kakaoService;

	// 팝빌회원 사업자번호
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String CorpNum;

	// 팝빌회원 아이디
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String UserID;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Kakao/index";
	}

	@RequestMapping(value = "getPlusFriendMgtURL", method = RequestMethod.GET)
	public String getPlusFriendMgtURL(Model m) {
        /**
         * 비즈니스 채널을 등록하고 내역을 확인하는 비즈니스 채널 관리 페이지 팝업 URL을 반환합니다.
         * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
         * - https://developers.popbill.com/reference/kakaotalk/java/api/channel#GetPlusFriendMgtURL
         */

        try {
            String url = kakaoService.getPlusFriendMgtURL(CorpNum, UserID);
            m.addAttribute("Result", url);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
	}

	@RequestMapping(value = "listPlusFriendID", method = RequestMethod.GET)
	public String listPlusFriendID(Model m) {
		/**
		 * 팝빌에 등록한 연동회원의 비즈니스 채널 목록을 확인합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/channel#ListPlusFriendID
		 */

		try {
			PlusFriendID[] response = kakaoService.listPlusFriendID(CorpNum);
			m.addAttribute("listInfo", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "Kakao/listPlusFriend";
	}

	@RequestMapping(value = "checkSenderNumber", method = RequestMethod.GET)
	public String checkSenderNumber(Model m) {
		/**
		 * 카카오톡 발신번호 등록여부를 확인합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/sendnum#CheckSenderNumber
		 */
		try {
			// 확인할 발신번호
			String sender = "070-4304-2991";
			Response response = kakaoService.checkSenderNumber(CorpNum, sender);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "getSenderNumberMgtURL", method = RequestMethod.GET)
	public String getSenderNumberMgtURL(Model m) {
		/**
		 * 발신번호를 등록하고 내역을 확인하는 카카오톡 발신번호 관리 페이지 팝업 URL을 반환합니다.
		 * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/sendnum#GetSenderNumberMgtURL
		 */
		try {
			String url = kakaoService.getSenderNumberMgtURL(CorpNum, UserID);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getSenderNumberList", method = RequestMethod.GET)
	public String getSenderNumberList(Model m) {
		/**
		 * 팝빌에 등록한 연동회원의 카카오톡 발신번호 목록을 확인합니다.
		 * https://developers.popbill.com/reference/kakaotalk/java/api/sendnum#GetSenderNumberList
		 */

		try {
			SenderNumber[] senderNumberList = kakaoService.getSenderNumberList(CorpNum);
			m.addAttribute("SenderNumberList", senderNumberList);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "Kakao/SenderNumber";
	}

	@RequestMapping(value = "getATSTemplateMgtURL", method = RequestMethod.GET)
	public String getATSTemplateMgtURL(Model m) {
		/**
		 * 알림톡 템플릿을 신청하고 승인심사 결과를 확인하며 등록 내역을 확인하는 알림톡 템플릿 관리 페이지 팝업 URL을 반환합니다.
		 * 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/template#GetATSTemplateMgtURL
		 */

		try {
			String url = kakaoService.getATSTemplateMgtURL(CorpNum, UserID);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getATSTemplate", method = RequestMethod.GET)
	public String getATSTemplate(Model m) {
		/**
		 * 승인된 알림톡 템플릿 정보를 확인합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/template#GetATSTemplate
		 */

		// 확인할 알림톡 템플릿 코드
		String templateCode = "022070000353";

		try {
			ATSTemplate response = kakaoService.getATSTemplate(CorpNum, templateCode);
			m.addAttribute("Template", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "Kakao/getATSTemplate";
	}

	@RequestMapping(value = "listATSTemplate", method = RequestMethod.GET)
	public String listATSTemplate(Model m) {
		/**
		 * 승인된 알림톡 템플릿 목록을 확인합니다.
		 * - 반환항목중 템플릿코드(templateCode)는 알림톡 전송시 사용됩니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/template#ListATSTemplate
		 */

		try {
			ATSTemplate[] response = kakaoService.listATSTemplate(CorpNum);
			m.addAttribute("listTemplate", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "Kakao/listATSTemplate";
	}

	@RequestMapping(value = "sendATS_one", method = RequestMethod.GET)
	public String sendATS_one(Model m) {
		/**
		 * 승인된 템플릿의 내용을 작성하여 1건의 알림톡 전송을 팝빌에 접수합니다.
		 * - 사전에 승인된 템플릿의 내용과 알림톡 전송내용(content)이 다를 경우 전송실패 처리됩니다.
		 * - 전송실패 시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendATSOne
		 */

		// 승인된 알림톡 템플릿코드
		// └ 알림톡 템플릿 관리 팝업 URL(GetATSTemplateMgtURL API) 함수, 알림톡 템플릿 목록
		// 확인(ListATStemplate API) 함수를 호출하거나
		// 팝빌사이트에서 승인된 알림톡 템플릿 코드를 확인 가능.
		String templateCode = "022070000338";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 알림톡 내용 (최대 1000자)
		String content = "[ 팝빌 ]\n" + "신청하신 #{템플릿코드}에 대한 심사가 완료되어 승인 처리되었습니다.\n" + "해당 템플릿으로 전송 가능합니다.\n\n"
				+ "문의사항 있으시면 파트너센터로 편하게 연락주시기 바랍니다.\n\n" + "팝빌 파트너센터 : 1600-8536\n" + "support@linkhub.co.kr";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		String altSubject = "대체문자 제목1234";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "대체문자 내용";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 알림톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 수신번호
		String receiverNum = "01022223333";

		// 수신자명
		String receiverName = "수신자명";

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "20230113180000";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "20230113_ats_02";

		// 알림톡 버튼정보를 템플릿 신청시 기재한 버튼정보와 동일하게 전송하는 경우 null 처리.
		KakaoButton[] btns = null;

		// 알림톡 버튼 URL에 #{템플릿변수}를 기재한경우 템플릿변수 영역을 변경하여 버튼정보 구성
		// KakaoButton[] btns = new KakaoButton[1];

		// KakaoButton button = new KakaoButton();
		// button.setN("버튼명"); // 버튼명
		// button.setT("WL"); // 버튼타입
		// button.setU1("https://www.popbill.com"); // 버튼링크1
		// button.setU2("http://test.popbill.com"); // 버튼링크2
		// button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		// btns[0] = button;

		try {
			String receiptNum = kakaoService.sendATS(CorpNum, templateCode, senderNum, content, altSubject, altContent,
					altSendType, receiverNum, receiverName, sndDT, UserID, requestNum, btns);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendATS_multi", method = RequestMethod.GET)
	public String sendATS_multi(Model m) {
		/**
		 * 승인된 템플릿의 내용을 작성하여 다수건의 알림톡 전송을 팝빌에 접수하며, 수신자 별로 개별 내용을 전송합니다. (최대 1,000건)
		 * - 사전에 승인된 템플릿의 내용과 알림톡 전송내용(content)이 다를 경우 전송실패 처리됩니다.
		 * - 전송실패 시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendATSMulti
		 */

		// 승인된 알림톡 템플릿코드
		// └ 알림톡 템플릿 관리 팝업 URL(GetATSTemplateMgtURL API) 함수, 알림톡 템플릿 목록
		// 확인(ListATStemplate API) 함수를 호출하거나
		// 팝빌사이트에서 승인된 알림톡 템플릿 코드를 확인 가능.
		String templateCode = "022070000338";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 알림톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 전송 정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[3];

		for (int i = 0; i < 3; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010111222"); // 수신번호
			message.setReceiverName("수신자명" + i); // 수신자명
			message.setMessage("[ 팝빌 ]\n" + "신청하신 #{템플릿코드}에 대한 심사가 완료되어 승인 처리되었습니다.\n" + "해당 템플릿으로 전송 가능합니다.\n\n"
					+ "문의사항 있으시면 파트너센터로 편하게 연락주시기 바랍니다.\n\n" + "팝빌 파트너센터 : 1600-8536\n" + "support@linkhub.co.kr"
					+ "To. 수신자" + i); // 알림톡 템플릿 내용, 최대 1000자
			message.setAltSubject("대체문자 개별제목입니다." + i); // 대체문자 제목
			message.setAltMessage("대체문자 개별내용입니다." + i); // 대체문자 내용

			// 수신자별 개별 버튼정보
			// KakaoButton button = new KakaoButton();
			// button.setN("타입1 버튼명"+i); // 버튼명
			// button.setT("WL"); // 버튼타입
			// button.setU1("http://"+i+"popbill.com"); // 버튼링크1
			// button.setU2("http://"+i+"test.popbill.com"); // 버튼링크2
			// button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)

			// KakaoButton button02 = new KakaoButton();
			// button02.setN("타입2 버튼명"+i); // 버튼명
			// button02.setT("WL"); // 버튼타입
			// button02.setU1("http://"+i+"popbill.com"); // 버튼링크1
			// button02.setU2("http://"+i+"test.popbill.com"); // 버튼링크2
			// button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)

			// 수신자별로 각기다른 버튼정보 추가.
			// message.setBtns(new ArrayList<KakaoButton>());
			// message.getBtns().add(button);
			// message.getBtns().add(button02);
			receivers[i] = message;
		}

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		// 알림톡 버튼정보를 템플릿 신청시 기재한 버튼정보와 동일하게 전송하는 경우 null 처리.
		KakaoButton[] btns = null;

		// 알림톡 버튼 URL에 #{템플릿변수}를 기재한경우 템플릿변수 영역을 변경하여 버튼정보 구성
		// KakaoButton[] btns = new KakaoButton[1];

		// KakaoButton button = new KakaoButton();
		// button.setN("버튼명"); // 버튼명
		// button.setT("WL"); // 버튼타입
		// button.setU1("https://www.popbill.com"); // 버튼링크1
		// button.setU2("http://test.popbill.com"); // 버튼링크2
		// button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		// btns[0] = button;

		try {
			String receiptNum = kakaoService.sendATS(CorpNum, templateCode, senderNum, altSendType, receivers, sndDT,
					UserID, requestNum, btns);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendATS_same", method = RequestMethod.GET)
	public String sendATS_same(Model m) {
		/**
		 * 승인된 템플릿 내용을 작성하여 다수건의 알림톡 전송을 팝빌에 접수하며, 모든 수신자에게 동일 내용을 전송합니다. (최대 1,000건)
		 * - 사전에 승인된 템플릿의 내용과 알림톡 전송내용(content)이 다를 경우 전송실패 처리됩니다.
		 * - 전송실패시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendATSSame
		 */

		// 승인된 알림톡 템플릿코드
		// └ 알림톡 템플릿 관리 팝업 URL(GetATSTemplateMgtURL API) 함수, 알림톡 템플릿 목록
		// 확인(ListATStemplate API) 함수를 호출하거나
		// 팝빌사이트에서 승인된 알림톡 템플릿 코드를 확인 가능.
		String templateCode = "022070000338";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 알림톡 내용 (최대 1000자)
		String content = "[ 팝빌 ]\n" + "신청하신 #{템플릿코드}에 대한 심사가 완료되어 승인 처리되었습니다.\n" + "해당 템플릿으로 전송 가능합니다.\n\n"
				+ "문의사항 있으시면 파트너센터로 편하게 연락주시기 바랍니다.\n\n" + "팝빌 파트너센터 : 1600-8536\n" + "support@linkhub.co.kr";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		// String altSubject = "대체문자 제목";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "대체문자 내용";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 알림톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[2];

		for (int i = 0; i < 2; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010111222"); // 수신번호
			message.setReceiverName("수신자명" + i); // 수신자명
			message.setAltSubject("대체문자제목" + i); // 수신자명
			receivers[i] = message;
		}

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		// 알림톡 버튼정보를 템플릿 신청시 기재한 버튼정보와 동일하게 전송하는 경우 null 처리.
		KakaoButton[] btns = null;

		// 알림톡 버튼 URL에 #{템플릿변수}를 기재한경우 템플릿변수 영역을 변경하여 버튼정보 구성
		// KakaoButton[] btns = new KakaoButton[1];

		// KakaoButton button = new KakaoButton();
		// button.setN("버튼명"); // 버튼명
		// button.setT("WL"); // 버튼타입
		// button.setU1("https://www.popbill.com"); // 버튼링크1
		// button.setU2("http://test.popbill.com"); // 버튼링크2
		// button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		// btns[0] = button;

		try {
			String receiptNum = kakaoService.sendATS(CorpNum, templateCode, senderNum, content, altContent, altSendType,
					receivers, sndDT, UserID, requestNum, btns);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFTS_one", method = RequestMethod.GET)
	public String sendFTS_one(Model m) {
		/**
		 * 텍스트로 구성된 1건의 친구톡 전송을 팝빌에 접수합니다.
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFTSOne
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 친구톡 내용 (최대 1000자)
		String content = "[친구톡 테스트]\n\n" + "친구톡 개별내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
				+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
				+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		String altSubject = "대체문자 제목";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "대체문자 내용";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 친구톡 버튼 배열, 최대 5개
		KakaoButton[] btns = new KakaoButton[2];

		KakaoButton button = new KakaoButton();
		button.setN("버튼명"); // 버튼명
		button.setT("WL"); // 버튼타입
		button.setU1("http://www.popbill.com"); // 버튼링크1
		button.setU2("http://test.popbill.com"); // 버튼링크2
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 수신번호
		String receiverNum = "010111222";

		// 수신자명
		String receiverName = "수신자명";

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFTS(CorpNum, plusFriendID, senderNum, content, altSubject, altContent,
					altSendType, btns, receiverNum, receiverName, sndDT, adsYN, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFTS_multi", method = RequestMethod.GET)
	public String sendFTS_multi(Model m) {
		/**
		 * 텍스트로 구성된 다수건의 친구톡 전송을 팝빌에 접수하며, 수신자 별로 개별 내용을 전송합니다. (최대 1,000건)
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFTSMulti
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[3];
		for (int i = 0; i < 3; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010111222"); // 수신번호
			message.setReceiverName("수신자명" + i); // 수신자명
			message.setMessage("[친구톡 테스트]\n\n" + "친구톡 개별내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
					+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
					+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n" + "To. 수신자" + i); // 친구톡 내용, 최대 1000자
			message.setAltSubject("대체문자 개별제목" + i); // 대체문자 제목
			message.setAltMessage("대체문자 개별내용" + i); // 대체문자 내용
			message.setInterOPRefKey("");

			KakaoButton button = new KakaoButton();
			button.setN("타입1 버튼명" + i); // 버튼명
			button.setT("WL"); // 버튼타입
			button.setU1("http://" + i + "popbill.com"); // 버튼링크1
			button.setU2("http://" + i + "test.popbill.com"); // 버튼링크2
			button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)

			KakaoButton button02 = new KakaoButton();
			button02.setN("타입2 버튼명" + i); // 버튼명
			button02.setT("WL"); // 버튼타입
			button02.setU1("http://" + i + "popbill.com"); // 버튼링크1
			button02.setU2("http://" + i + "test.popbill.com"); // 버튼링크2
			button02.setTg("out");  // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)

			// 수신자별로 각기다른 버튼정보 추가.
			message.setBtns(new ArrayList<KakaoButton>());
			message.getBtns().add(button);
			message.getBtns().add(button02);

			receivers[i] = message;
		}

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFTS(CorpNum, plusFriendID, senderNum, altSendType, receivers,
					null, sndDT, adsYN, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFTS_same", method = RequestMethod.GET)
	public String sendFTS_same(Model m) {
		/**
		 * 텍스트로 구성된 다수건의 친구톡 전송을 팝빌에 접수하며, 모든 수신자에게 동일 내용을 전송합니다. (최대 1,000건)
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFMSSame
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 친구톡 내용 (최대 1000자)
		String content = "[친구톡 테스트]\n\n" + "친구톡 동보내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
				+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
				+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		String altSubject = "대체문자 동보제목";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "대체문자 내용";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[2];
		for (int i = 0; i < 2; i++) {
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
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFTS(CorpNum, plusFriendID, senderNum, content, altSubject, altContent,
					altSendType, receivers, btns, sndDT, adsYN, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFMS_one", method = RequestMethod.GET)
	public String sendFMS_one(Model m) {
		/**
		 * 이미지가 첨부된 1건의 친구톡 전송을 팝빌에 접수합니다.
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - 대체문자의 경우, 포토문자(MMS) 형식은 지원하고 있지 않습니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFMSOne
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 친구톡 내용 (최대 400자)
		String content = "친구톡 메시지 내용";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		String altSubject = "친구톡 이미지 대체문자 제목";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "[친구톡이미지 대체문자]\n\n" + "친구톡 이미지 대체문자 내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
				+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
				+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "A";

		// 친구톡 버튼 배열, 최대 5개
		KakaoButton[] btns = new KakaoButton[2];

		KakaoButton button = new KakaoButton();
		button.setN("버튼명"); // 버튼명
		button.setT("WL"); // 버튼타입
		button.setU1("http://www.popbill.com"); // 버튼링크1
		button.setU2("http://test.popbill.com"); // 버튼링크2
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 수신번호
		String receiverNum = "010111222";

		// 수신자명
		String receiverName = "수신자명";

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 첨부이미지 파일 경로
		// - 이미지 파일 규격: 전송 포맷 – JPG 파일 (.jpg, .jpeg), 용량 – 최대 500 Kbyte, 크기 – 가로 500px
		// 이상, 가로 기준으로 세로 0.5~1.3배 비율 가능
		File file = new File("C:/Users/Public/Pictures/Image.jpg");

		// 이미지 링크 URL
		// └ 수신자가 친구톡 상단 이미지 클릭시 호출되는 URL
		// - 미입력시 첨부된 이미지를 링크 기능 없이 표시
		String imageURL = "http://test.popbill.com";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFMS(CorpNum, plusFriendID, senderNum, content, altSubject, altContent,
					altSendType, btns, receiverNum, receiverName, sndDT, adsYN, file, imageURL, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFMS_multi", method = RequestMethod.GET)
	public String sendFMS_multi(Model m) {
		/**
		 * 이미지가 첨부된 다수건의 친구톡 전송을 팝빌에 접수하며, 수신자 별로 개별 내용을 전송합니다. (최대 1,000건)
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - 대체문자의 경우, 포토문자(MMS) 형식은 지원하고 있지 않습니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFMSMulti
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[3];
		for (int i = 0; i < 3; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010111222"); // 수신번호
			message.setReceiverName("수신자명" + i); // 수신자명
			message.setMessage("[친구톡 이미지 테스트]\n\n" + "친구톡 이미지 개별내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
					+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
					+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n" + "To. 수신자" + i); // 친구톡 내용, 최대 400자
			message.setAltSubject("대체문자 개별제목" + i); // 대체문자 제목
			message.setAltMessage("대체문자 개별내용" + i); // 대체문자 내용
			receivers[i] = message;

			// 수신자별 개별 버튼 정보
			// KakaoButton button = new KakaoButton();
			// button.setN("타입1 버튼명"+i); // 버튼명
			// button.setT("WL"); // 버튼타입
			// button.setU1("http://"+i+"popbill.com"); // 버튼링크1
			// button.setU2("http://"+i+"test.popbill.com"); // 버튼링크2

			// KakaoButton button02 = new KakaoButton();
			// button02.setN("타입2 버튼명"+i); // 버튼명
			// button02.setT("WL"); // 버튼타입
			// button02.setU1("http://"+i+"popbill.com"); // 버튼링크1
			// button02.setU2("http://"+i+"test.popbill.com"); // 버튼링크2

			// 수신자별로 각기다른 버튼정보 추가.
			// message.setBtns(new ArrayList<KakaoButton>());
			// message.getBtns().add(button);
			// message.getBtns().add(button02);

		}

		// 수신자별 동일 버튼 정보
		// 친구톡 버튼 배열, 최대 5개
		KakaoButton[] btns = new KakaoButton[2];

		KakaoButton button = new KakaoButton();
		button.setN("버튼명"); // 버튼명
		button.setT("WL"); // 버튼타입
		button.setU1("http://www.popbill.com"); // 버튼링크1
		button.setU2("http://test.popbill.com"); // 버튼링크2
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 첨부이미지 파일 경로
		// - 이미지 파일 규격: 전송 포맷 – JPG 파일 (.jpg, .jpeg), 용량 – 최대 500 Kbyte, 크기 – 가로 500px
		// 이상, 가로 기준으로 세로 0.5~1.3배 비율 가능
		File file = new File("C:/Users/Public/Pictures/Image.jpg");

		// 이미지 링크 URL
		// └ 수신자가 친구톡 상단 이미지 클릭시 호출되는 URL
		// - 미입력시 첨부된 이미지를 링크 기능 없이 표시
		String imageURL = "http://test.popbill.com";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFMS(CorpNum, plusFriendID, senderNum, altSendType, receivers, btns,
					sndDT, adsYN, file, imageURL, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFMS_same", method = RequestMethod.GET)
	public String sendFMS_same(Model m) {
		/**
		 * 이미지가 첨부된 다수건의 친구톡 전송을 팝빌에 접수하며, 모든 수신자에게 동일 내용을 전송합니다. (최대 1,000건)
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'altSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - 대체문자의 경우, 포토문자(MMS) 형식은 지원하고 있지 않습니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFMSSame
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 친구톡 내용 (최대 400자)
		String content = "[친구톡 이미지 테스트]\n\n" + "친구톡 이미지 동보내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
				+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
				+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		String altSubject = "대체문자 제목";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "대체문자 내용";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[2];
		for (int i = 0; i < 2; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010111222"); // 수신번호
			message.setReceiverName("수신자명" + i); // 수신자명
			receivers[i] = message;
		}

		// 수신자별 동일 버튼 정보
		// 친구톡 버튼 배열, 최대 5개
		KakaoButton[] btns = new KakaoButton[2];

		KakaoButton button = new KakaoButton();
		button.setN("버튼명"); // 버튼명
		button.setT("WL"); // 버튼타입
		button.setU1("http://www.popbill.com"); // 버튼링크1
		button.setU2("http://test.popbill.com"); // 버튼링크2
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 첨부이미지 파일 경로
		// - 이미지 파일 규격: 전송 포맷 – JPG 파일 (.jpg, .jpeg), 용량 – 최대 500 Kbyte, 크기 – 가로 500px
		// 이상, 가로 기준으로 세로 0.5~1.3배 비율 가능
		File file = new File("C:/Users/Public/Pictures/Image.jpg");

		// 이미지 링크 URL
		// └ 수신자가 친구톡 상단 이미지 클릭시 호출되는 URL
		// - 미입력시 첨부된 이미지를 링크 기능 없이 표시
		String imageURL = "http://test.popbill.com";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFMS(CorpNum, plusFriendID, senderNum, content, altSubject, altContent,
					altSendType, receivers, btns, sndDT, adsYN, file, imageURL, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFMS_one_binary", method = RequestMethod.GET)
	public String sendFMS_one_binary(Model m) throws FileNotFoundException {
		/**
		 * 바이너리 형식의 이미지 데이터가 첨부된 1건의 친구톡 전송을 팝빌에 접수합니다.
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'AltSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - 대체문자의 경우, 포토문자(MMS) 형식은 지원하고 있지 않습니다.
		 * - 팝빌 서비스의 안정적인 제공을 위하여 동시호출이 제한될 수 있습니다.
		 * - 동시에 1,000건 이상 요청하는 경우 동보전송 또는 대량전송으로 이용하시는 것을 권장합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFMSBinaryOne
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 친구톡 내용 (최대 400자)
		String content = "친구톡 메시지 내용";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		String altSubject = "친구톡 이미지 대체문자 제목";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "[친구톡이미지 대체문자]\n\n" + "친구톡 이미지 대체문자 내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
				+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
				+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "A";

		// 친구톡 버튼 배열, 최대 5개
		KakaoButton[] btns = new KakaoButton[2];

		KakaoButton button = new KakaoButton();
		button.setN("버튼명"); // 버튼명
		button.setT("WL"); // 버튼타입
		button.setU1("http://www.popbill.com"); // 버튼링크1
		button.setU2("http://test.popbill.com"); // 버튼링크2
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 수신번호
		String receiverNum = "010111222";

		// 수신자명
		String receiverName = "수신자명";

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 첨부이미지 파일 경로
		// - 이미지 파일 규격: 전송 포맷 – JPG 파일 (.jpg, .jpeg), 용량 – 최대 500 Kbyte, 크기 – 가로 500px
		// 이상, 가로 기준으로 세로 0.5~1.3배 비율 가능
		File file = new File("C:/Users/Public/Pictures/Image.jpg");
		InputStream inputStream = new FileInputStream(file);

		Attachment attachment = new Attachment();
		attachment.setFileName(file.getName());
		attachment.setFileData(inputStream);

		// 이미지 링크 URL
		// └ 수신자가 친구톡 상단 이미지 클릭시 호출되는 URL
		// - 미입력시 첨부된 이미지를 링크 기능 없이 표시
		String imageURL = "http://test.popbill.com";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFMSBinary(CorpNum, plusFriendID, senderNum, content, altSubject,
					altContent, altSendType, btns, receiverNum, receiverName, sndDT, adsYN, attachment, imageURL,
					UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFMS_multi_binary", method = RequestMethod.GET)
	public String sendFMS_multi_binary(Model m) throws FileNotFoundException {
		/**
		 * 바이너리 형식의 이미지 데이터가 첨부된 다수건의 친구톡 전송을 팝빌에 접수하며, 수신자 별로 개별 내용을 전송합니다. (최대 1,000건)
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'AltSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - 대체문자의 경우, 포토문자(MMS) 형식은 지원하고 있지 않습니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFMSBinaryMulti
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[3];
		for (int i = 0; i < 3; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010111222"); // 수신번호
			message.setReceiverName("수신자명" + i); // 수신자명
			message.setMessage("[친구톡 이미지 테스트]\n\n" + "친구톡 이미지 개별내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
					+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
					+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n" + "To. 수신자" + i); // 친구톡 내용, 최대 400자
			message.setAltSubject("대체문자 개별제목" + i); // 대체문자 제목
			message.setAltMessage("대체문자 개별내용" + i); // 대체문자 내용
			receivers[i] = message;

			// 수신자별 개별 버튼 정보
			// KakaoButton button = new KakaoButton();
			// button.setN("타입1 버튼명"+i); // 버튼명
			// button.setT("WL"); // 버튼타입
			// button.setU1("http://"+i+"popbill.com"); // 버튼링크1
			// button.setU2("http://"+i+"test.popbill.com"); // 버튼링크2

			// KakaoButton button02 = new KakaoButton();
			// button02.setN("타입2 버튼명"+i); // 버튼명
			// button02.setT("WL"); // 버튼타입
			// button02.setU1("http://"+i+"popbill.com"); // 버튼링크1
			// button02.setU2("http://"+i+"test.popbill.com"); // 버튼링크2

			// 수신자별로 각기다른 버튼정보 추가.
			// message.setBtns(new ArrayList<KakaoButton>());
			// message.getBtns().add(button);
			// message.getBtns().add(button02);

		}

		// 수신자별 동일 버튼 정보
		// 친구톡 버튼 배열, 최대 5개
		KakaoButton[] btns = new KakaoButton[2];

		KakaoButton button = new KakaoButton();
		button.setN("버튼명"); // 버튼명
		button.setT("WL"); // 버튼타입
		button.setU1("http://www.popbill.com"); // 버튼링크1
		button.setU2("http://test.popbill.com"); // 버튼링크2
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 첨부이미지 파일 경로
		// - 이미지 파일 규격: 전송 포맷 – JPG 파일 (.jpg, .jpeg), 용량 – 최대 500 Kbyte, 크기 – 가로 500px
		// 이상, 가로 기준으로 세로 0.5~1.3배 비율 가능
		File file = new File("C:/Users/Public/Pictures/Image.jpg");
		InputStream inputStream = new FileInputStream(file);

		Attachment attachment = new Attachment();
		attachment.setFileName(file.getName());
		attachment.setFileData(inputStream);

		// 이미지 링크 URL
		// └ 수신자가 친구톡 상단 이미지 클릭시 호출되는 URL
		// - 미입력시 첨부된 이미지를 링크 기능 없이 표시
		String imageURL = "http://test.popbill.com";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFMSBinary(CorpNum, plusFriendID, senderNum, altSendType, receivers,
					btns, sndDT, adsYN, attachment, imageURL, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "sendFMS_same_binary", method = RequestMethod.GET)
	public String sendFMS_same_binary(Model m) throws FileNotFoundException {
		/**
		 * 바이너리 형식의 이미지 데이터가 첨부된 다수건의 친구톡 전송을 팝빌에 접수하며, 모든 수신자에게 동일 내용을 전송합니다. (최대 1,000건)
		 * - 친구톡의 경우 야간 전송은 제한됩니다. (20:00 ~ 익일 08:00)
		 * - 전송실패시 사전에 지정한 변수 'AltSendType' 값으로 대체문자를 전송할 수 있고, 이 경우 문자(SMS/LMS) 요금이 과금됩니다.
		 * - 대체문자의 경우, 포토문자(MMS) 형식은 지원하고 있지 않습니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#SendFMSBinarySame
		 */

		// 팝빌에 등록된 비즈니스 채널 아이디
		String plusFriendID = "@팝빌";

		// 팝빌에 사전 등록된 발신번호
		// altSendType = 'C' / 'A' 일 경우, 대체문자를 전송할 발신번호
		// altSendType = '' 일 경우, null 또는 공백 처리
		// ※ 대체문자를 전송하는 경우에는 사전에 등록된 발신번호 입력 필수
		String senderNum = "07043042991";

		// 친구톡 내용 (최대 400자)
		String content = "[친구톡 이미지 테스트]\n\n" + "친구톡 이미지 동보내용입니다.\n" + "대체문자를 친구톡 메시지 내용 그대로 전송할 수 있습니다.\n"
				+ "또는 대체문자 내용을 작송하여 전송할 수도 있습니다.\n" + "하지만 대체문자 내용이 길어지게 되면 LMS 로 전송될 수 있습니다.\n\n"
				+ "수신을 원치 않으시면 1600-9854로 전화주시길 바랍니다.\n";

		// 대체문자 제목
		// - 메시지 길이(90byte)에 따라 장문(LMS)인 경우에만 적용.
		String altSubject = "대체문자 제목";

		// 대체문자 유형(altSendType)이 "A"일 경우, 대체문자로 전송할 내용 (최대 2000byte)
		// └ 팝빌이 메시지 길이에 따라 단문(90byte 이하) 또는 장문(90byte 초과)으로 전송처리
		String altContent = "대체문자 내용";

		// 대체문자 유형 (null , "C" , "A" 중 택 1)
		// null = 미전송, C = 친구톡과 동일 내용 전송 , A = 대체문자 내용(altContent)에 입력한 내용 전송
		String altSendType = "C";

		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[2];
		for (int i = 0; i < 2; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010111222"); // 수신번호
			message.setReceiverName("수신자명" + i); // 수신자명
			receivers[i] = message;
		}

		// 수신자별 동일 버튼 정보
		// 친구톡 버튼 배열, 최대 5개
		KakaoButton[] btns = new KakaoButton[2];

		KakaoButton button = new KakaoButton();
		button.setN("버튼명"); // 버튼명
		button.setT("WL"); // 버튼타입
		button.setU1("http://www.popbill.com"); // 버튼링크1
		button.setU2("http://test.popbill.com"); // 버튼링크2
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[0] = button;

		button = new KakaoButton();
		button.setN("버튼명2");
		button.setT("WL");
		button.setU1("http://www.popbill.com");
		button.setU2("http://test.popbill.com");
		button.setTg("out"); // 디바이스 기본 브라우저 사용 (공백(기본값) : 카카오톡 인앱 브라우저 사용)
		btns[1] = button;

		// 예약전송일시, 형태(yyyyMMddHHmmss)
		// - 분단위 전송, 미입력 시 즉시 전송
		String sndDT = "";

		// 광고성 메시지 여부 ( true , false 중 택 1)
		// └ true = 광고 , false = 일반
		// - 미입력 시 기본값 false 처리
		Boolean adsYN = false;

		// 첨부이미지 파일 경로
		// - 이미지 파일 규격: 전송 포맷 – JPG 파일 (.jpg, .jpeg), 용량 – 최대 500 Kbyte, 크기 – 가로 500px
		// 이상, 가로 기준으로 세로 0.5~1.3배 비율 가능
		File file = new File("C:/Users/Public/Pictures/Image.jpg");
		InputStream inputStream = new FileInputStream(file);

		Attachment attachment = new Attachment();
		attachment.setFileName(file.getName());
		attachment.setFileData(inputStream);

		// 이미지 링크 URL
		// └ 수신자가 친구톡 상단 이미지 클릭시 호출되는 URL
		// - 미입력시 첨부된 이미지를 링크 기능 없이 표시
		String imageURL = "http://test.popbill.com";

		// 전송요청번호
		// 팝빌이 접수 단위를 식별할 수 있도록 파트너가 할당한 식별번호.
		// 1~36자리로 구성. 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 팝빌 회원별로 중복되지 않도록 할당.
		String requestNum = "";

		try {
			String receiptNum = kakaoService.sendFMSBinary(CorpNum, plusFriendID, senderNum, content, altSubject,
					altContent, altSendType, receivers, btns, sndDT, adsYN, attachment, imageURL, UserID, requestNum);
			m.addAttribute("Result", receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "cancelReserve", method = RequestMethod.GET)
	public String cancelReserve(Model m) {
		/**
		 * 팝빌에서 반환받은 접수번호를 통해 예약접수된 카카오톡을 전송 취소합니다. (예약시간 10분 전까지 가능) -
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#CancelReserve
		 */

		// 카카오톡 예약전송 접수시 팝빌로부터 반환받은 접수번호
		String receiptNum = "022021810443200001";

		try {
			Response response = kakaoService.cancelReserve(CorpNum, receiptNum);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "cancelReservebyRCV", method = RequestMethod.GET)
	public String cancelReservebyRCV(Model m) {
		/**
		 * 팝빌에서 반환받은 접수번호와 수신번호를 통해 예약접수된 카카오톡을 취소합니다. (예약시간 10분 전까지 가능)
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#cancelReservebyRCV
		 */

		// 카카오톡 예약전송 접수시 팝빌로부터 반환 받은 접수번호
		String receiptNum = "023011313394100001";

		// 카카오톡 예약전송 접수시 팝빌로 요청한 수신번호
		String receiveNum = "01022223334";

		try {
			Response response = kakaoService.cancelReservebyRCV(CorpNum, receiptNum, receiveNum);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "cancelReserveRN", method = RequestMethod.GET)
	public String cancelReserveRN(Model m) {
		/**
		 * 파트너가 할당한 전송요청 번호를 통해 예약접수된 카카오톡을 전송 취소합니다. (예약시간 10분 전까지 가능)
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#CancelReserveRN
		 */

		// 카카오톡 예약전송 접수시 파트너가 할당한 전송요청 번호
		String requestNum = "";

		try {
			Response response = kakaoService.cancelReserveRN(CorpNum, requestNum);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "cancelReserveRNbyRCV", method = RequestMethod.GET)
	public String cancelReserveRNbyRCV(Model m) {
		/**
		 * 파트너가 할당한 전송요청 번호와 수신번호를 통해 예약접수된 카카오톡을 취소합니다. (예약시간 10분 전까지 가능)
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/send#CancelReserveRNbyRCV
		 */

		// 카카오톡 예약전송 접수시 파트너가 할당한 전송요청 번호
		String requestNum = "20230113_ats_02";

		// 카카오톡 예약전송 접수시 팝빌로 요청한 수신번호
        String receiveNum = "01022223333";

		try {
			Response response = kakaoService.cancelReserveRNbyRCV(CorpNum, requestNum, receiveNum);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "getMessages", method = RequestMethod.GET)
	public String getMessages(Model m) {
		/**
		 * 팝빌에서 반환받은 접수번호를 통해 알림톡/친구톡 전송상태 및 결과를 확인합니다.
		 * 카카오톡 상태코드 [https://developers.popbill.com/reference/kakaotalk/java/response-code#state-code]
		 * 카카오 결과코드(카카오톡) [https://developers.popbill.com/reference/kakaotalk/java/response-code#kakao-result-code]
		 * 통신사 결과코드(대체문자) [https://developers.popbill.com/reference/kakaotalk/java/response-code#telecom-result-code]
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/info#GetMessages
		 */

		// 카카오톡 전송 접수시 팝빌로부터 반환받은 접수번호
		String receiptNum = "022100612053100001";

		try {
			KakaoSentInfo sentInfos = kakaoService.getMessages(CorpNum, receiptNum);
			m.addAttribute("sentInfos", sentInfos);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "Kakao/getMessage";
	}

	@RequestMapping(value = "getMessagesRN", method = RequestMethod.GET)
	public String getMessagesRN(Model m) {
		/**
		 * 파트너가 할당한 전송요청 번호를 통해 알림톡/친구톡 전송상태 및 결과를 확인합니다.
		 * 카카오톡 상태코드 [https://developers.popbill.com/reference/kakaotalk/java/response-code#state-code]
		 * 카카오 결과코드(카카오톡) [https://developers.popbill.com/reference/kakaotalk/java/response-code#kakao-result-code]
		 * 통신사 결과코드(대체문자) [https://developers.popbill.com/reference/kakaotalk/java/response-code#telecom-result-code]
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/info#GetMessagesRN
		 */

		// 카카오톡 전송 접수시 파트너가 할당한 전송요청 번호
		String requestNum = "";

		try {
			KakaoSentInfo sentInfos = kakaoService.getMessagesRN(CorpNum, requestNum);
			m.addAttribute("sentInfos", sentInfos);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "Kakao/getMessage";
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Model m) {
		/**
		 * 검색조건에 해당하는 카카오톡 전송내역을 조회합니다. (조회기간 단위 : 최대 2개월)
		 * - 카카오톡 접수일시로부터 6개월 이내 접수건만 조회할 수 있습니다.
		 * 카카오톡 상태코드 [https://developers.popbill.com/reference/kakaotalk/java/response-code#state-code]
		 * 카카오 결과코드(카카오톡) [https://developers.popbill.com/reference/kakaotalk/java/response-code#kakao-result-code]
		 * 통신사 결과코드(대체문자) [https://developers.popbill.com/reference/kakaotalk/java/response-code#telecom-result-code]
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/info#Search
		 */

		// 시작일자, 날짜형식(yyyyMMdd)
		String SDate = "20230102";

		// 종료일자, 날짜형식(yyyyMMdd)
		String EDate = "20230131";

		// 전송상태 배열 ("0" , "1" , "2" , "3" , "4" , "5" 중 선택, 다중 선택 가능)
		// └ 0 = 전송대기 , 1 = 전송중 , 2 = 전송성공 , 3 = 대체문자 전송 , 4 = 전송실패 , 5 = 전송취소
		// - 미입력 시 전체조회
		String[] State = { "0", "1", "2", "3", "4" };

		// 검색대상 배열 ("ATS", "FTS", "FMS" 중 선택, 다중 선택 가능)
		// └ ATS = 알림톡 , FTS = 친구톡(텍스트) , FMS = 친구톡(이미지)
		// - 미입력 시 전체조회
		String[] Item = { "ATS", "FTS", "FMS" };

		// 전송유형별 조회 (null , "0" , "1" 중 택 1)
		// └ null = 전체 , 0 = 즉시전송건 , 1 = 예약전송건
		// - 미입력 시 전체조회
		String ReserveYN = null;

		// 사용자권한별 조회
		// └ true = 팝빌회원 아이디(UserID)로 전송한 카카오톡만 조회
		// └ false = 전송한 카카오톡 전체 조회 : 기본값
		Boolean SenderOnly = false;

		// 페이지 번호
		int Page = 1;

		// 페이지당 목록개수 (최대 1000건)
		int PerPage = 20;

		// 알림톡 / 친구톡 접수일시를 기준으로 하는 목록 정렬 방향 ("D" , "A" 중 택 1)
		// └ D = 내림차순(기본값) , A = 오름차순
		String Order = "D";

		// 조회하고자 하는 수신자명
		// - 미입력시 전체조회
		String QString = "";

		try {
			KakaoSearchResult response = kakaoService.search(CorpNum, SDate, EDate, State, Item, ReserveYN, SenderOnly,
					Page, PerPage, Order, UserID, QString);
			m.addAttribute("SearchResult", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "Kakao/searchResult";
	}

	@RequestMapping(value = "getSentListURL", method = RequestMethod.GET)
	public String getSentListURL(Model m) {
		/**
		 * 카카오톡 전송내역을 확인하는 페이지의 팝업 URL을 반환합니다.
		 * - 반환되는 URL은 보안 정책상 30초 동안 유효하며, 시간을 초과한 후에는 해당 URL을 통한 페이지 접근이 불가합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/api/info#GetSentListURL
		 */

		try {
			String url = kakaoService.getSentListURL(CorpNum, UserID);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost(Model m) {
		/**
		 * 카카오톡 전송시 과금되는 포인트 단가를 확인합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/common-api/point#GetUnitCost
		 */

		// 카카오톡 전송유형, ATS-알림톡, FTS-친구톡 텍스트, FMS-친구톡 이미지
		KakaoType kakaoType = KakaoType.ATS;

		try {
			float unitCost = kakaoService.getUnitCost(CorpNum, kakaoType);
			m.addAttribute("Result", unitCost);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo(Model m) {
		/**
		 * 팝빌 카카오톡 API 서비스 과금정보를 확인합니다.
		 * - https://developers.popbill.com/reference/kakaotalk/java/common-api/point#GetChargeInfo
		 */

		// 카카오톡 전송유형, ATS-알림톡, FTS-친구톡 텍스트, FMS-친구톡 이미지
		KakaoType kakaoType = KakaoType.ATS;

		try {
			ChargeInfo chrgInfo = kakaoService.getChargeInfo(CorpNum, kakaoType);
			m.addAttribute("ChargeInfo", chrgInfo);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "getChargeInfo";
	}

}