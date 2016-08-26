/*
 * Copyright 2006-2014 innopost.com, Inc. or its affiliates. All Rights Reserved.
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
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.ChargeInfo;
import com.popbill.api.MessageService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.message.AutoDeny;
import com.popbill.api.message.MSGSearchResult;
import com.popbill.api.message.Message;
import com.popbill.api.message.MessageType;
import com.popbill.api.message.SentMessage;

/**
 * 팝빌 문자메시지 API 예제.
 */
@Controller
@RequestMapping("MessageService")
public class MessageServiceExample {
	
	
	@Autowired
	private MessageService messageService;
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Message/index";
	}
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		try {
			//SMS 단문발송 단가 확인
			float unitCost = messageService.getUnitCost(testCorpNum,MessageType.SMS);
			
			m.addAttribute("Result",unitCost);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo( Model m) {
		MessageType msgType = MessageType.SMS; //전송형태, SMS-단문, LMS-장문, MMS-포토 

		try {
			ChargeInfo chrgInfo = messageService.getChargeInfo(testCorpNum, msgType);	
			m.addAttribute("ChargeInfo",chrgInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "getChargeInfo";
	}
	
	@RequestMapping(value = "getURL", method = RequestMethod.GET)
	public String getURL( Model m) {
		
		String TOGO = "BOX"; // TBOX : 문자 전송 내역 조회 팝업
		
		try {
			
			String url = messageService.getURL(testCorpNum,testUserID,TOGO);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendSMS", method = RequestMethod.GET)
	public String sendSMS( Model m) {
		
		String sender = "07075103710"; 		// 발신번호
		String receiver = "000111222"; 		// 수신번호
		String receiverName = "수신자"; 		// 수신자명 
		String content = "문자메시지 내용."; 	// 단문문자메시지는 90Byte초과시 초과된 내용은 제거되어 전송 
		Date reserveDT = null;				// 예약문자전송일시 
		Boolean adsYN = false; 				// 광고문자 전송여부 
		
		try {
			String receiptNum = messageService.sendSMS(testCorpNum, sender, receiver, receiverName, content, reserveDT, adsYN, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendSMS_Multi", method = RequestMethod.GET)
	public String sendSMS_Multi( Model m) {
				
		String sender = "07075103710"; 		// 대량문자 발신번호
		String content = "대량문자 메시지 내용";
		Date reserveDT = null;				// 문자전송 예약일시 
		Boolean adsYN = false;				// 광고문자 전송여부 
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("07075103710");
		msg1.setSenderName("발신자명_SMS1");
		msg1.setReceiver("000111222");
		msg1.setReceiverName("수신자1");
		msg1.setContent("메시지 내용1");
		
		Message msg2 = new Message();
		msg2.setSender("07075103710");
		msg2.setSenderName("발신자명_SMS2");
		msg2.setReceiver("000111222");
		msg2.setReceiverName("수신자2");
		msg2.setContent("메시지 내용2");
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			String receiptNum = messageService.sendSMS(testCorpNum, sender, content, messages, reserveDT, adsYN, testUserID);
						
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendLMS", method = RequestMethod.GET)
	public String sendLMS( Model m) {
		
		String sender = "07075103710"; 		// 발신번호
		String receiver = "000111222"; 		// 수신번호
		String receiverName = "수신자"; 		// 수신자 명칭.
		String subject = "장문문자 제목."; 
		String content = "장문 문자메시지 내용.";		// 장문문자메시지는 2000Byte초과시 Cutting됨.
		Date reserveDT = null;					// 예약문자 전송일시
		Boolean adsYN = true;					// 광고문자 전송여부 
		
		try {
			
			String receiptNum = messageService.sendLMS(testCorpNum, sender, receiver, receiverName, subject, content, reserveDT, adsYN, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendLMS_Multi", method = RequestMethod.GET)
	public String sendLMS_Multi( Model m) {
		
		String sender = "07075103710";
		String subject = "대량전송 제목";
		String content = "대량전송 내용";
		Date reserveDT = null;		
		Boolean adsYN = true;
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("07075103710");
		msg1.setSenderName("발신자명_LMS1");
		msg1.setReceiver("000111222");
		msg1.setReceiverName("수신자1");
		msg1.setSubject("장문 제목");
		msg1.setContent("메시지 내용1");
		
		Message msg2 = new Message();
		msg2.setSender("07075103710");
		msg2.setSenderName("발신자명_lms2");
		msg2.setReceiver("000111222");
		msg2.setReceiverName("수신자2");
		msg2.setSubject("장문 제목");
		msg2.setContent("메시지 내용2");
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			
			String receiptNum = messageService.sendLMS(testCorpNum, sender, subject, content, messages, reserveDT, adsYN, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendMMS", method = RequestMethod.GET)
	public String sendMMS( Model m) {
		String sender = "07075103710"; 			// 발신번호
		String receiver = "010111222"; 			// 수신번호
		String receiverName = "수신자"; 			// 수신자 명칭.
		String subject = "멀티 문자 제목."; 
		String content = "멀티 문자메시지 내용."; 	// 장문문자메시지는 2000Byte초과시 Cutting됨.
		File file = new File("C:/test2.jpg");
		Date reserveDT = null;					// 예약전송일시 
		Boolean adsYN = false;					// 광고문자 전송여부 
		
		try {
			
			String receiptNum = messageService.sendMMS(testCorpNum, sender, receiver, receiverName, subject, content, file, reserveDT, adsYN, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendMMS_Multi", method = RequestMethod.GET)
	public String sendMMS_Multi( Model m) {
		
		String sender = "07075103710"; 		// 대량전송 발신번호
		String subject = "대량전송 제목";
		String content = "대량전송 메시지 내용";
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("07075103710");
		msg1.setSenderName("발신자명_MMS_1");
		msg1.setReceiver("010111222");
		msg1.setReceiverName("수신자1");
		msg1.setSubject("멀티 메시지 제목");
		msg1.setContent("메시지 내용1");
		
		Message msg2 = new Message();
		msg2.setSender("07075103710");
		msg2.setSenderName("발신자명_MMS_2");
		msg2.setReceiver("010111222");
		msg2.setReceiverName("수신자2");
		msg2.setSubject("멀티 메시지 제목");
		msg2.setContent("메시지 내용2");
		
		File file = new File("C:/test2.jpg");
		
		Date reserveDT = null;				// 예약전송일시
		Boolean adsYN = false;				// 광고문자 전송여부 
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			String receiptNum = messageService.sendMMS(testCorpNum, sender, subject, content, messages, file, reserveDT, adsYN, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendXMS", method = RequestMethod.GET)
	public String sendXMS( Model m) {
		
		String sender = "07075103710"; 		//발신번호
		String receiver = "000111222"; 		//수신번호
		String receiverName = "수신자"; 		//수신자 명칭.
		String subject = "장문문자 제목."; 
		String content = "문자메시지 내용."; 	//문자메시지의 길이에 따라 90Byte를 기준으로 단문과 장문을 선택하여 전송함.
		Date reserveDT = null;			 	// 예약전송 일시
		Boolean adsYN = false; 			 	// 광고문자 전송여부 
		
		try {
			
			String receiptNum = messageService.sendXMS(testCorpNum, sender, receiver, receiverName, subject, content, reserveDT, adsYN, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendXMS_Multi", method = RequestMethod.GET)
	public String sendXMS_Multi( Model m) {

		String sender = "07075103710"; 		//발신번호
		String subject = "장문문자 제목."; 
		String content = "문자메시지 내용."; 	//문자메시지의 길이에 따라 90Byte를 기준으로 단문과 장문을 선택하여 전송함.
		Date reserveDT = null;			 	// 예약전송 일시
		Boolean adsYN = true; 			 	// 광고문자 전송여부 
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("07075103710");
		msg1.setSenderName("발신자명_XMS");
		msg1.setReceiver("000111222");
		msg1.setReceiverName("수신자1");
		msg1.setContent("메시지 내용1"); //단문으로 전송됨.
		
		Message msg2 = new Message();
		msg2.setSender("07075103710");
		msg2.setSenderName("발신자명_XMS");
		msg2.setReceiver("000111222");
		msg2.setReceiverName("수신자2");
		msg2.setSubject("장문 제목");
		msg2.setContent("메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 " +
				"내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2");// 장문으로 전송됨.
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			
			String receiptNum = messageService.sendXMS(testCorpNum, sender, subject, content, messages, reserveDT, adsYN, testUserID);
						
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	

	@RequestMapping(value = "getMessages", method = RequestMethod.GET)
	public String getMessages( Model m) {
		
		String receiptNum = "016011915000000001"; //전송시 접수번호.
		
		try {
			SentMessage[] sentMessages = messageService.getMessages(testCorpNum,receiptNum);
			
			m.addAttribute("SentMessages",sentMessages);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Message/SentMessage";
	}
	
	@RequestMapping(value = "cancelReserve", method = RequestMethod.GET)
	public String cancelReserve( Model m) {
		
		String receiptNum = "014101011000000006"; //전송시 접수번호.
		
		try {
			Response response = messageService.cancelReserve(testCorpNum,receiptNum,testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Model m) {
		
		// 문자전송내역 목록 조회 
		
		String SDate = "20160701";				// 시작일자, yyyyMMdd
		String EDate = "20160831";				// 종료일자, yyyyMMdd
		String[] State = {"1", "2", "3","4"};	// 전송상태 배열, 1-대기, 2-성공, 3-실패, 4-취소
		String[] Item = {"SMS", "LMS", "MMS"};	// 검색대상 배열, SMS-단문, LMS-장문, MMS-포토 
		Boolean ReserveYN = false;				// 예약여부, false-전체조회, true-예약전송건 조회 
		Boolean SenderYN = false;				// 개인조회 여부, false-전체조회, true-개인조회 
		int Page = 1;							// 페이지 번호 
		int PerPage = 20;						// 페이지당 목록개수 (최대 1000건) 
		String Order = "D";						// 정렬방향 D-내림차순, A-오름차순 
		
		try {
			MSGSearchResult response = messageService.search(testCorpNum, SDate, EDate, State, Item, ReserveYN, SenderYN, Page, PerPage, Order);
			
			m.addAttribute("SearchResult",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		return "Message/SearchResult";
	}
	
	@RequestMapping(value = "autoDenyList", method = RequestMethod.GET)
	public String getAutoDenyList(Model m){
		
		// 080수신거부 목록 조회 
		
		try {
			AutoDeny[] autoDenyList = messageService.getAutoDenyList(testCorpNum);
			m.addAttribute("AutoDenyList", autoDenyList);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		return "Message/AutoDeny";
	}
	
}
