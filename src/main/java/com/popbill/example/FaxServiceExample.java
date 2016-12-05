/*
 * 팝빌 팩스 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : http://blog.linkhub.co.kr/591/
 * - 업데이트 일자 : 2016-12-05
 * - 연동 기술지원 연락처 : 1600-8536 / 070-4304-2991~2
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

/**
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
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		/**
		 * 팩스 전송단가를 확인합니다.
		 */
		
		try {
			
			float unitCost = faxService.getUnitCost(testCorpNum);
			
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
		 * 연동회원의 팩스 API 서비스 과금정보를 확인합니다.
		 */
		
		try {
			
			ChargeInfo chrgInfo = faxService.getChargeInfo(testCorpNum);
			
			m.addAttribute("ChargeInfo",chrgInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "getChargeInfo";
	}
	
	@RequestMapping(value = "getURL", method = RequestMethod.GET)
	public String getURL( Model m) {
		/**
		 * 팩스 전송내역 목록 팝업 URL을 반환합니다.
		 * 보안정책으로 인해 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		
		// TBOX : 팩스 전송 내역 조회 팝업
		String TOGO = "BOX"; 
		
		try {
			
			String url = faxService.getURL(testCorpNum, testUserID, TOGO);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFAX", method = RequestMethod.GET)
	public String sendFAX( Model m) throws URISyntaxException {
		
		// 발신번호
		String sendNum = "07043042991"; 
		
		// 수신번호
		String receiveNum = "070111222";
		
		// 수신자명
		String receiveName = "수신자 명칭";
		
		File file;
		
		try {
			file = new File(getClass().getClassLoader().getResource("사업자등록증.jpg").toURI());
		} catch (URISyntaxException e1) {
			throw e1;
		}
		
		Date reserveDT = null; //전송 예약일시
		
		try {
			
			String receiptNum = faxService.sendFAX(testCorpNum, sendNum, receiveNum, 
					receiveName, file, reserveDT, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "sendFAX_Multi", method = RequestMethod.GET)
	public String sendFAX_Multi( Model m) throws URISyntaxException {
		
		// 발신번호
		String sendNum = "07043042991"; 
		
		Receiver receiver1 = new Receiver();
		receiver1.setReceiveName("수신자1");		// 수신자명
		receiver1.setReceiveNum("010111222");	// 수신번호
		
		Receiver receiver2 = new Receiver();
		receiver2.setReceiveName("수신자2");		// 수신자
		receiver2.setReceiveNum("010333444");	// 수신번호
		
		
		// 팩스전송정보 배열, 최대 1000건
		Receiver[] receivers = new Receiver[] {receiver1 , receiver2};
		
		File file;
		
		try {
			file = new File(getClass().getClassLoader().getResource("사업자등록증.jpg").toURI());
		} catch (URISyntaxException e1) {
			throw e1;
		}
		
		// 전송예약일시
		Date reserveDT = null; 
		
		try {
			
			String receiptNum = faxService.sendFAX(testCorpNum, sendNum, receivers,
					file, reserveDT, testUserID);
			
			m.addAttribute("Result",receiptNum);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getFaxResult", method = RequestMethod.GET)
	public String getFaxResult( Model m) {
		/**
		 * 팩스 전송요청시 반환받은 접수번호(receiptNum)을 사용하여 팩스전송 결과를 확인합니다.
		 */
		
		// 팩스전송 접수번호
		String receiptNum = "016120511390400001";
		
		try {
			FaxResult[] faxResults = faxService.getFaxResult(testCorpNum, receiptNum);
			
			m.addAttribute("FaxResults",faxResults);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Fax/FaxResult";
	}
	
	@RequestMapping(value = "cancelReserve", method = RequestMethod.GET)
	public String cancelReserve( Model m) {
		/**
		 * 예약전송 팩스요청건을 취소합니다.
		 * - 예약전송 취소는 예약전송시간 10분전까지 가능합니다.
		 */
		
		// 팩스전송 접수번호
		String receiptNum = "016120511390400001";
		
		try {
			Response response = faxService.cancelReserve(testCorpNum, receiptNum, 
					testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Model m) {
		/**
		 * 검색조건을 사용하여 팩스전송 내역을 조회합니다.
		 */
		
		// 시작일자, 날짜형식(yyyyMMdd)
		String SDate = "20161001";				
		
		// 종료일자, 날짜형식(yyyyMMdd)
		String EDate = "20161231";				
		
		// 전송상태 배열, 1-대기, 2-성공, 3-실패, 4-취소
		String[] State = {"1", "2", "3","4"};	
		
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

		try {
			
			FAXSearchResult response = faxService.search(testCorpNum, SDate, EDate, 
					State, ReserveYN, SenderOnly, Page, PerPage, Order);
			
			m.addAttribute("SearchResult",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		return "Fax/SearchResult";
	}
}
