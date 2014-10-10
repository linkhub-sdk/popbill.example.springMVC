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
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.FaxService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.fax.FaxResult;
import com.popbill.api.fax.Receiver;

/**
 * 팝빌 팩스 API 예제.
 */
@Controller
@RequestMapping("FaxService")
public class FaxServiceExample {
	
	
	@Autowired
	private FaxService faxService;
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Fax/index";
	}
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		try {
			//팩스 발송 단가 확인
			float unitCost = faxService.getUnitCost(testCorpNum);
			
			m.addAttribute("Result",unitCost);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getURL", method = RequestMethod.GET)
	public String getURL( Model m) {
		
		String TOGO = "BOX"; // TBOX : 팩스 전송 내역 조회 팝업
		
		try {
			
			String url = faxService.getURL(testCorpNum,testUserID,TOGO);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFAX", method = RequestMethod.GET)
	public String sendFAX( Model m) throws URISyntaxException {
		
		String sendNum = "07075106766"; //발신번호
		String receiveNum = "11122223333"; //수신번호
		String receiveName = "수신자 명칭";
		
		File file;
		try {
			file = new File(getClass().getClassLoader().getResource("사업자등록증.jpg").toURI());
		} catch (URISyntaxException e1) {
			throw e1;
		}
		
		Date reserveDT = null; //전송 예약일시
		
		try {
			
			String receiptNum = faxService.sendFAX(testCorpNum, sendNum, receiveNum, receiveName, file, reserveDT, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFAX_Multi", method = RequestMethod.GET)
	public String sendFAX_Multi( Model m) throws URISyntaxException {
		
		String sendNum = "07075106766"; //발신번호
		
		Receiver receiver1 = new Receiver();
		receiver1.setReceiveName("수신자1");
		receiver1.setReceiveNum("11122223333");
		
		Receiver receiver2 = new Receiver();
		receiver2.setReceiveName("수신자2");
		receiver2.setReceiveNum("11122224444");
		
		Receiver[] receivers = new Receiver[] {receiver1 , receiver2}; //최대 1000개.
		
		File file;
		try {
			file = new File(getClass().getClassLoader().getResource("사업자등록증.jpg").toURI());
		} catch (URISyntaxException e1) {
			throw e1;
		}
		
		Date reserveDT = null; //전송 예약일시
		
		try {
			
			
			String receiptNum = faxService.sendFAX(testCorpNum, sendNum, receivers, file, reserveDT, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	

	@RequestMapping(value = "getFaxResult", method = RequestMethod.GET)
	public String getFaxResult( Model m) {
		
		String receiptNum = "014101014353600001"; //전송시 접수번호.
		
		try {
			FaxResult[] faxResults = faxService.getFaxResult(testCorpNum,receiptNum);
			
			m.addAttribute("FaxResults",faxResults);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Fax/FaxResult";
	}
	
	@RequestMapping(value = "cancelReserve", method = RequestMethod.GET)
	public String cancelReserve( Model m) {
		
		String receiptNum = "014101014055500001"; //전송시 접수번호.
		
		try {
			Response response = faxService.cancelReserve(testCorpNum,receiptNum,testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
}
