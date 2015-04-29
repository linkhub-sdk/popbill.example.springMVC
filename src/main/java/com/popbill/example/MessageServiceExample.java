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
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.MessageService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
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
		
		String sender = "11122223333"; //발신번호
		String receiver = "11133334444"; //수신번호
		String receiverName = "수신자"; //수신자 명칭.
		String content = "문자메시지 내용."; //단문문자메시지는 90Byte초과시 Cutting됨.
		
		try {
			
			String receiptNum = messageService.sendSMS(testCorpNum,sender,receiver,receiverName,content,null,testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendSMS_Multi", method = RequestMethod.GET)
	public String sendSMS_Multi( Model m) {
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("111122223333");
		msg1.setReceiver("11133334444");
		msg1.setReceiverName("수신자1");
		msg1.setContent("메시지 내용1");
		
		Message msg2 = new Message();
		msg2.setSender("111122223333");
		msg2.setReceiver("11133334444");
		msg2.setReceiverName("수신자2");
		msg2.setContent("메시지 내용2");
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			
			String receiptNum = messageService.sendSMS(testCorpNum,messages,null,testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendLMS", method = RequestMethod.GET)
	public String sendLMS( Model m) {
		
		String sender = "11122223333"; //발신번호
		String receiver = "11133334444"; //수신번호
		String receiverName = "수신자"; //수신자 명칭.
		String subject = "장문문자 제목."; 
		String content = "장문 문자메시지 내용."; //장문문자메시지는 2000Byte초과시 Cutting됨.
		
		try {
			
			String receiptNum = messageService.sendLMS(testCorpNum,sender,receiver,receiverName,subject,content,null,testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendLMS_Multi", method = RequestMethod.GET)
	public String sendLMS_Multi( Model m) {
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("11122223333");
		msg1.setReceiver("11133334444");
		msg1.setReceiverName("수신자1");
		msg1.setSubject("장문 제목");
		msg1.setContent("메시지 내용1");
		
		Message msg2 = new Message();
		msg2.setSender("11122223333");
		msg2.setReceiver("11133334444");
		msg2.setReceiverName("수신자2");
		msg2.setSubject("장문 제목");
		msg2.setContent("메시지 내용2");
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			
			String receiptNum = messageService.sendLMS(testCorpNum,messages,null,testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendMMS", method = RequestMethod.GET)
	public String sendMMS( Model m) {
		
		String sender = "07075103710"; //발신번호
		String receiver = "010111222"; //수신번호
		String receiverName = "수신자"; //수신자 명칭.
		String subject = "멀티 문자 제목."; 
		String content = "멀티 문자메시지 내용."; //장문문자메시지는 2000Byte초과시 Cutting됨.
		
		File file = new File("C:/test2.jpg");
		
		try {
			
			String receiptNum = messageService.sendMMS(testCorpNum, sender, receiver, receiverName, subject, content, file, null, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendMMS_Multi", method = RequestMethod.GET)
	public String sendMMS_Multi( Model m) {
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("07075103710");
		msg1.setReceiver("010111222");
		msg1.setReceiverName("수신자1");
		msg1.setSubject("멀티 메시지 제목");
		msg1.setContent("메시지 내용1");
		
		Message msg2 = new Message();
		msg2.setSender("07075103710");
		msg2.setReceiver("010111222");
		msg2.setReceiverName("수신자2");
		msg2.setSubject("멀티 메시지 제목");
		msg2.setContent("메시지 내용2");
		
		File file = new File("C:/test2.jpg");
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			
			String receiptNum = messageService.sendMMS(testCorpNum, messages, file, null, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendXMS", method = RequestMethod.GET)
	public String sendXMS( Model m) {
		
		String sender = "11122223333"; //발신번호
		String receiver = "11133334444"; //수신번호
		String receiverName = "수신자"; //수신자 명칭.
		String subject = "장문문자 제목."; 
		String content = "문자메시지 내용."; //문자메시지의 길이에 따라 90Byte를 기준으로 단문과 장문을 선택하여 전송함.
		
		try {
			
			String receiptNum = messageService.sendXMS(testCorpNum,sender,receiver,receiverName,subject,content,null,testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendXMS_Multi", method = RequestMethod.GET)
	public String sendXMS_Multi( Model m) {
		
		//최대 1000건.
		Message msg1 = new Message();
		msg1.setSender("11122223333");
		msg1.setReceiver("11133334444");
		msg1.setReceiverName("수신자1");
		msg1.setContent("메시지 내용1"); //단문으로 전송됨.
		
		Message msg2 = new Message();
		msg2.setSender("11122223333");
		msg2.setReceiver("11133334444");
		msg2.setReceiverName("수신자2");
		msg2.setSubject("장문 제목");
		msg2.setContent("메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 " +
				"내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2메시지 내용2");// 장문으로 전송됨.
		
		Message[] messages = new Message[] {msg1,msg2};
		
		try {
			
			String receiptNum = messageService.sendXMS(testCorpNum,messages,null,testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	

	@RequestMapping(value = "getMessages", method = RequestMethod.GET)
	public String getMessages( Model m) {
		
		String receiptNum = "014101011000000008"; //전송시 접수번호.
		
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
	
}
