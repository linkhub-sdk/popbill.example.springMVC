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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.JoinForm;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.TaxinvoiceService;

/**
 * 팝빌 BaseService API 예제.
 */
@Controller
@RequestMapping("BaseService")
public class BaseServiceExample {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceExample.class);
	
	@Autowired
	private TaxinvoiceService taxinvoiceService;
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	@Value("#{EXAMPLE_CONFIG.LinkID}")
	private String testLinkID;
	
	@RequestMapping(value = "checkIsMember", method = RequestMethod.GET)
	public String checkIsMember(Model m) throws PopbillException {
		
		try {
			Response response = taxinvoiceService.checkIsMember(testCorpNum,"TESTER");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			logger.error(e.getCode() + " | " + e.getMessage());
			throw e;
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getBalance", method = RequestMethod.GET)
	public String getBalance(Model m) throws PopbillException {
		
		try {
			double remainPoint = taxinvoiceService.getBalance(testCorpNum);
			
			m.addAttribute("Result",remainPoint);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPartnerBalance", method = RequestMethod.GET)
	public String getPartnerBalance(Model m) throws PopbillException {
		
		try {
			double remainPoint = taxinvoiceService.getPartnerBalance(testCorpNum);
			
			m.addAttribute("Result",remainPoint);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPopbillURL", method = RequestMethod.GET)
	public String getPopbillURL(Model m) throws PopbillException {
		
		String TOGO = "CHRG";  //CHRG : 포인트 충전, LOGIN : 메인 , CERT : 공인인증서 등록
		
		try {
			String url = taxinvoiceService.getPopbillURL(testCorpNum,testUserID , TOGO);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "joinMember", method = RequestMethod.GET)
	public String joinMember(Model m) throws PopbillException {
		
		JoinForm joinInfo = new JoinForm();

		joinInfo.setLinkID(testLinkID);
		joinInfo.setCorpNum("1231212312"); // 사업자번호 "-" 제외
		joinInfo.setCEOName("대표자성명");
		joinInfo.setCorpName("상호");
		joinInfo.setAddr("주소");
		joinInfo.setZipCode("500-100");
		joinInfo.setBizType("업태");
		joinInfo.setBizClass("업종");
		joinInfo.setID("userid"); // 6자 이상 20자 미만
		joinInfo.setPWD("pwd_must_be_long_enough"); // 6자 이상 20자 미만
		joinInfo.setContactName("담당자명");
		joinInfo.setContactTEL("02-999-9999");
		joinInfo.setContactHP("010-1234-5678");
		joinInfo.setContactFAX("02-999-9998");
		joinInfo.setContactEmail("test@test.com");
				
		try {
			
			Response response = taxinvoiceService.joinMember(joinInfo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
}
