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
	private String testCorpNum;
	
	// 팝빌회원 아이디
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Kakao/index";
	}
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		/**
		 *  카카오톡 전송단가를 확인합니다.
		 */
		
		// 카카오톡 전송유형, ATS-알림톡, FTS-친구톡 텍스트, FMS-친구톡 이미지
		KakaoType kakaoType= KakaoType.ATS;
		
		try {
			
			float unitCost = kakaoService.getUnitCost(testCorpNum, kakaoType);
			
			m.addAttribute("Result",unitCost);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo( Model m) {
		/**
		 *  카카오톡 과금정보를 확인합니다.
		 */		
		
		// 카카오톡 전송유형, ATS-알림톡, FTS-친구톡 텍스트, FMS-친구톡 이미지
		KakaoType kakaoType = KakaoType.ATS;  

		try {
			
			ChargeInfo chrgInfo = kakaoService.getChargeInfo(testCorpNum, kakaoType);	
			m.addAttribute("ChargeInfo",chrgInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "getChargeInfo";
	}
	
	@RequestMapping(value = "getURL_BOX", method = RequestMethod.GET)
	public String getURL( Model m) {
		/**
		 * 카카오톡 전송내역 팝업 URL을 반환합니다.
		 * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		
		// BOX - 카카오톡 전송내역 조회 
		String TOGO = "BOX";
		
		try {
			
			String url = kakaoService.getURL(testCorpNum, TOGO, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getURL_PLUSFRIEND", method = RequestMethod.GET)
	public String getURL_PLUSFRIEND( Model m) {
		/**
		 * 플러스친구 계정관리 팝업 URL을 반환합니다.
		 * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		
		// PLUSFRIEND - 플러스친구 계정관리 팝업
		String TOGO = "PLUSFRIEND";
		
		try {
			
			String url = kakaoService.getURL(testCorpNum, TOGO, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value ="listPlusFriendID", method = RequestMethod.GET)
	public String listPlusFriendID(Model m) throws PopbillException{
		/**
		 * 팝빌에 등록된 플러스친구 목록을 반환합니다.
		 */
		
		try {
			PlusFriendID[] response = kakaoService.listPlusFriendID(testCorpNum);
			
			m.addAttribute("listInfo", response);
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Kakao/listPlusFriend";		
	}
	
	@RequestMapping(value = "getURL_SENDER", method = RequestMethod.GET)
	public String getURL_SENDER( Model m) {
		/**
		 *  발신번호 관리 팝업 URL을 반환합니다.
		 * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		
		// SENDER - 발신번호 관리 팝업 
		String TOGO = "SENDER";
		
		try {
			
			String url = kakaoService.getURL(testCorpNum, TOGO, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getSenderNumberList", method = RequestMethod.GET)
	public String getSenderNumberList(Model m){
		/**
		 * 발신번호 목록을 확인합니다.
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
	
	@RequestMapping(value = "getURL_TEMPLATE", method = RequestMethod.GET)
	public String getURL_TEMPLATE( Model m) {
		/**
		 * 알림톡 템플릿 관리 팝업 URL을 반환합니다.
		 * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		
		// TEMPLATE - 알림톡 템플릿 관리 팝업 
		String TOGO = "TEMPLATE";
		
		try {
			
			String url = kakaoService.getURL(testCorpNum, TOGO, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value ="listATSTemplate", method = RequestMethod.GET)
	public String listATSTemplate(Model m) throws PopbillException{
		/**
		 * (주)카카오로부터 승인된 알림톡 템플릿 목록을 반환합니다.
		 */
		
		try {
			ATSTemplate[] response = kakaoService.listATSTemplate(testCorpNum);
			
			m.addAttribute("listTemplate", response);
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Kakao/listATSTemplate";		
	}
	
	@RequestMapping(value = "sendATS_one", method = RequestMethod.GET)
	public String sendATS_one( Model m) {
		/**
		 * 알림톡 전송을 요청합니다. (1건)
		 */
		
		// 알림톡 템플릿코드 / ListATSTemplate API 의 반환 항목 중 templateCode 기재
		String templateCode = "018020000001";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 알림톡 템플릿 내용, 최대 1000자
		String content = "[테스트] 테스트 템플릿입니다.";
				
		// 대체문자 내용
		String altContent = "대체문자 내용";
		
		// 대체문자 전송유형, 공백-미전송, C-알림톡 내용전송, A-대체문자 내용 전송
		String altSendType = "C";
		
		// 수신번호
		String receiverNum = "010000111";
		
		// 수신자명
		String receiverName = "수신자명";
		
		// 예약전송일시, 형태(yyyyMMddHHmmss)
		String sndDT = "";
		
		try {
			
			String receiptNum = kakaoService.sendATS(testCorpNum, templateCode, senderNum, content, altContent, altSendType, 
					receiverNum, receiverName, sndDT);	
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendATS_same", method = RequestMethod.GET)
	public String sendATS_same( Model m) {
		/**
		 * 알림톡 동일내용 대량전송을 요청합니다. 
		 */
		
		// 알림톡 템플릿코드 / ListATSTemplate API 의 반환 항목 중 templateCode 기재
		String templateCode = "018020000001";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 알림톡 템플릿 내용, 최대 1000자
		String content = "[테스트] 테스트 템플릿입니다.";
				
		// 대체문자 내용
		String altContent = "대체문자 내용";
		
		// 대체문자 전송유형, 공백-미전송, C-알림톡 내용전송, A-대체문자 내용 전송
		String altSendType = "C";
		
		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[100];
		for(int i=0; i<100; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010000111"); // 수신번호
			message.setReceiverName("수신자명"+i); // 수신자명
			receivers[i] = message;
		}
		
		// 예약전송일시, 형태(yyyyMMddHHmmss)
		String sndDT = "";
		
		try {
			
			String receiptNum = kakaoService.sendATS(testCorpNum, templateCode, senderNum, 
					content, altContent, altSendType, receivers, sndDT);
			m.addAttribute("Result",receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		return "result";
	}
	
	@RequestMapping(value = "sendATS_multi", method = RequestMethod.GET)
	public String sendATS_multi( Model m) {
		/**
		 * 알림톡 개별내용 대량전송을 요청합니다. 
		 */
		
		// 알림톡 템플릿코드 / ListATSTemplate API 의 반환 항목 중 templateCode 기재
		String templateCode = "018020000001";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 대체문자 전송유형, 공백-미전송, C-알림톡 내용전송, A-대체문자 내용 전송
		String altSendType = "C";
		
		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[100];
		for(int i=0; i<100; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010000111");	// 수신번호
			message.setReceiverName("수신자명"+i);	// 수신자명
			message.setMessage("[테스트] 템플릿입니다"+i);	// 알림톡 템플릿 내용, 최대 1000자
			message.setAltMessage("대체문자 개별내용입니다."+i); // 대체문자 내용
			receivers[i] = message;
		}
		
		// 예약전송일시, 형태(yyyyMMddHHmmss)
		String sndDT = "";
		
		try {
			
			String receiptNum = kakaoService.sendATS(testCorpNum, templateCode, senderNum, altSendType, receivers, sndDT);
			m.addAttribute("Result",receiptNum);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		return "result";
	}
	
	@RequestMapping(value = "sendFTS_one", method = RequestMethod.GET)
	public String sendFTS_one( Model m) {
		/**
		 * 친구톡(텍스트) 전송을 요청한다 (1건)
		 */
		
		// 팝빌에 등록된 플러스친구 아이디
		String plusFriendID = "@팝빌";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 친구톡 내용 (최대 1000자)
		String content = "친구톡 메시지 내용";
		
		// 대체문자 내용
		String altContent = "대체문자 내용";
		
		// 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
		String altSendType = "A";
		
		// 수신번호
		String receiverNum = "01043245117";
		
		// 수신자명
		String receiverName = "수신자명";
		
		// 예약전송일시, 형태(yyyyMmddHHmmss)
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
		
		try {
			
			String receiptNum = kakaoService.sendFTS(testCorpNum, plusFriendID, senderNum, content, 
					altContent, altSendType, btns, receiverNum, receiverName, sndDT, adsYN);	
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFTS_same", method = RequestMethod.GET)
	public String sendFTS_same( Model m) {
		/**
		 * 친구톡(텍스트) 동일내용 대량전송을 요청한다.
		 */
		
		// 팝빌에 등록된 플러스친구 아이디
		String plusFriendID = "@팝빌";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 친구톡 내용 (최대 1000자)
		String content = "친구톡 메시지 내용";
		
		// 대체문자 내용
		String altContent = "대체문자 내용";
		
		// 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
		String altSendType = "A";
		
		// 예약전송일시, 형태(yyyyMmddHHmmss)
		String sndDT = "";
		
		// 광고전송여부
		Boolean adsYN = false;
		
		
		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[100];
		for(int i=0; i<100; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010000111");
			message.setReceiverName("수신자명"+i);
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
		
		try {
			
			String receiptNum = kakaoService.sendFTS(testCorpNum, plusFriendID, senderNum, content, 
					altContent, altSendType, receivers, btns, sndDT, adsYN);	
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	
	@RequestMapping(value = "sendFTS_multi", method = RequestMethod.GET)
	public String sendFTS_multi( Model m) {
		/**
		 * 친구톡(텍스트) 개별내용 대량전송을 요청한다.
		 */
		
		// 팝빌에 등록된 플러스친구 아이디
		String plusFriendID = "@팝빌";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
		String altSendType = "A";
		
		// 예약전송일시, 형태(yyyyMmddHHmmss)
		String sndDT = "";
		
		// 광고전송여부
		Boolean adsYN = false;
		
		
		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[100];
		for(int i=0; i<100; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010000111"); // 수신번호
			message.setReceiverName("수신자명"+i);	// 수신자명
			message.setMessage("친구톡 개별내용"+i); // 친구톡 내용, 최대 1000자
			message.setAltMessage("대체문자 개별내용"+i); // 대체문자 내용
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
		
		try {
			
			String receiptNum = kakaoService.sendFTS(testCorpNum, plusFriendID, senderNum, altSendType, 
					receivers, btns, sndDT, adsYN);	
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFMS_one", method = RequestMethod.GET)
	public String sendFMS_one( Model m) {
		/**
		 * 친구톡(이미지) 전송을 요청한다 (1건)
		 */
		
		// 팝빌에 등록된 플러스친구 아이디
		String plusFriendID = "@팝빌";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 친구톡 내용 (최대 400자)
		String content = "친구톡 메시지 내용";
		
		// 대체문자 내용
		String altContent = "대체문자 내용";
		
		// 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
		String altSendType = "A";
		
		// 수신번호
		String receiverNum = "01043245117";
		
		// 수신자명
		String receiverName = "수신자명";
		
		// 예약전송일시, 형태(yyyyMmddHHmmss)
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
		
		try {
			
			String receiptNum = kakaoService.sendFMS(testCorpNum, plusFriendID, senderNum, content, 
					altContent, altSendType, btns, receiverNum, receiverName, sndDT, adsYN, file, imageURL);	
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFMS_same", method = RequestMethod.GET)
	public String sendFMS_same( Model m) {
		/**
		 * 친구톡(이미지) 동일내용 대량전송을 요청한다.
		 */
		
		// 팝빌에 등록된 플러스친구 아이디
		String plusFriendID = "@팝빌";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 친구톡 내용 (최대 400자)
		String content = "친구톡 메시지 내용";
		
		// 대체문자 내용
		String altContent = "대체문자 내용";
		
		// 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
		String altSendType = "A";
		
		// 예약전송일시, 형태(yyyyMmddHHmmss)
		String sndDT = "";
		
		// 광고전송여부
		Boolean adsYN = false;
		
		
		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[100];
		for(int i=0; i<100; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010000111"); // 수신번호
			message.setReceiverName("수신자명"+i); // 수신자명
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
		
		try {
			
			String receiptNum = kakaoService.sendFMS(testCorpNum, plusFriendID, senderNum, content, 
					altContent, altSendType, receivers, btns, sndDT, adsYN, file, imageURL);	
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFMS_multi", method = RequestMethod.GET)
	public String sendFMS_multi( Model m) {
		/**
		 * 친구톡(이미지) 개별내용 대량전송을 요청한다.
		 */
		
		// 팝빌에 등록된 플러스친구 아이디
		String plusFriendID = "@팝빌";
		
		// 팝빌에 등록된 발신번호
		String senderNum = "07043042993";
		
		// 대체문자 전송유형, 공백-미전송, C-친구톡내용 전송, A-알림톡 내용 전송
		String altSendType = "A";
		
		// 예약전송일시, 형태(yyyyMmddHHmmss)
		String sndDT = "20180306200000";
		
		// 광고전송여부
		Boolean adsYN = false;
		
		
		// 카카오톡 수신정보 배열, 최대 1000건
		KakaoReceiver[] receivers = new KakaoReceiver[100];
		for(int i=0; i<100; i++) {
			KakaoReceiver message = new KakaoReceiver();
			message.setReceiverNum("010000111"); // 수신번호
			message.setReceiverName("수신자명"+i); // 수신자명
			message.setMessage("친구톡 개별내용"+i); // 친구톡 내용, 최대 400자
			message.setAltMessage("대체문자 개별내용"+i); // 대체문자 내용
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
		
		try {
			
			String receiptNum = kakaoService.sendFMS(testCorpNum, plusFriendID, senderNum, altSendType, 
					receivers, btns, sndDT, adsYN, file, imageURL);	
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "cancelReserve", method = RequestMethod.GET)
	public String cancelReserve( Model m) {
		/**
		 * 예약전송을 취소합니다.
		 * - 예약취소는 예약전송시간 10분전까지만 가능합니다.
		 */
		
		// 예약전송 접수번호
		String receiptNum = "018030610185600001"; 
		
		try {
			Response response = kakaoService.cancelReserve(testCorpNum, receiptNum);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	
	@RequestMapping(value = "getMessages", method = RequestMethod.GET)
	public String getMessages( Model m) {
		/**
		 * 카카오톡 전송결과 상세정보를 확인합니다.
		 */
		
		// 카카오톡 접수번호
		String receiptNum = "018030609572900001"; 
		
		try {
			
			KakaoSentInfo sentInfos = kakaoService.getMessages(testCorpNum, receiptNum);
			
			m.addAttribute("sentInfos",sentInfos);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Kakao/getMessage";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Model m) {
		/**
		 * 검색조건에 따른 카카오톡 전송내역을 조회합니다.
		 */
		
		// 시작일자, 날짜형식(yyyyMMdd)
		String SDate = "20180306";				
		
		// 종료일자, 날짜형식(yyyyMMdd)
		String EDate = "20180306";				
		
		// 전송상태 배열, 0-대기, 1-전송중, 2-성공, 3-대체, 4-실패, 5-취소
		String[] State = {"0", "1", "2", "3","4"};	
		
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
		
		try {
			
			KakaoSearchResult response = kakaoService.search(testCorpNum, SDate, 
					EDate, State, Item, ReserveYN, SenderYN, Page, PerPage, Order);
			
			m.addAttribute("SearchResult",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		return "Kakao/searchResult";
	}

}