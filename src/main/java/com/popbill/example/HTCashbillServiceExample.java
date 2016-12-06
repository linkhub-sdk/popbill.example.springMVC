/*
 *  * 팝빌 홈택스 현금영수증 연계 API Java SDK SpringMVC Example
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
 * 3) 홈택스에서 이용가능한 공인인증서를 등록합니다.
 *    - 팝빌로그인 > [홈택스연계] > [환경설정] > [공인인증서 관리] 메뉴
 *    - 공인인증서 등록(GetCertificatePopUpURL API) 반환된 URL을 이용하여
 *      팝업 페이지에서 공인인증서 등록
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

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.ChargeInfo;
import com.popbill.api.FlatRateState;
import com.popbill.api.HTCashbillService;
import com.popbill.api.PopbillException;
import com.popbill.api.hometax.HTCashbillJobState;
import com.popbill.api.hometax.HTCashbillSearchResult;
import com.popbill.api.hometax.HTCashbillSummary;
import com.popbill.api.hometax.QueryType;
	

/**
 * 팝빌 홈택스연계 현금영수증 API 예제.
 */
@Controller
@RequestMapping("HTCashbillService")
public class HTCashbillServiceExample {
	
	
	@Autowired
	private HTCashbillService htCashbillService;
	
	// 팝빌회원 사업자번호
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	
	// 팝빌회원 아이디
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "HTCashbill/index";
	}
	
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo( Model m) {
		/**
		 * 연동회원의 홈택스 현금영수증 연계 API 서비스 과금정보를 확인합니다.
		 */
		
		try {
			ChargeInfo chrgInfo = htCashbillService.getChargeInfo(testCorpNum);	
			m.addAttribute("ChargeInfo",chrgInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "getChargeInfo";
	}	
	
	@RequestMapping(value = "requestJob", method = RequestMethod.GET)
	public String requestJob( Model m) {
		/**
		 * 현금영수증 매출/매입 내역 수집을 요청합니다
		 * - 매출/매입 연계 프로세스는 "[홈택스 현금영수증 연계 API 연동매뉴얼]
		 *   > 1.2. 프로세스 흐름도" 를 참고하시기 바랍니다.
		 * - 수집 요청후 반환받은 작업아이디(JobID)의 유효시간은 1시간 입니다.
		 */
		
		// 현금영수증 유형, SELL-매출, BUY-매입
		QueryType TIType = QueryType.SELL;
				
		// 시작일자, 표시형식(yyyyMMdd)
		String SDate = "20161001";
		
		// 종료일자, 표시형식(yyyyMMdd)
		String EDate = "20161231";
		
		try {
			String jobID = htCashbillService.requestJob(testCorpNum, TIType, SDate, EDate);
			m.addAttribute("Result",jobID);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getJobState", method = RequestMethod.GET)
	public String getJobState( Model m) {
		/**
		 * 수집 요청 상태를 확인합니다.
		 * - 응답항목 관한 정보는 "[홈택스 현금영수증 연계 API 연동매뉴얼
		 *   > 3.2.2. GetJobState (수집 상태 확인)" 을 참고하시기 바랍니다 .
		 */
		
		// 수집요청(requestJob)시 반환받은 작업아이디
		String jobID = "016120614000000002";
		
		try {
			HTCashbillJobState jobState = htCashbillService.getJobState(testCorpNum, jobID, testUserID);
			m.addAttribute("JobState",jobState);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTTaxinvoice/GetJobState";
	}
	
	@RequestMapping(value = "listActiveJob", method = RequestMethod.GET)
	public String listActiveJob( Model m) {
		/**
		 * 수집 요청건들에 대한 상태 목록을 확인합니다.
		 * - 수집 요청 작업아이디(JobID)의 유효시간은 1시간 입니다.
		 * - 응답항목에 관한 정보는 "[홈택스 현금영수증 연계 API 연동매뉴얼]
		 *   > 3.2.3. ListActiveJob (수집 상태 목록 확인)" 을 참고하시기 바랍니다.
		 */
		
		try {
			HTCashbillJobState[] jobStates = htCashbillService.listActiveJob(testCorpNum, testUserID);
			m.addAttribute("JobStates", jobStates);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTCashbill/ListActiveJob";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search ( Model m) {
		/**
		 * 검색조건을 사용하여 수집결과를 조회합니다.
		 * - 응답항목에 관한 정보는 "[홈택스 현금영수증 연계 API 연동매뉴얼]
		 *   > 3.3.1. Search (수집 결과 조회)" 을 참고하시기 바랍니다.
		 */
		
		// 수집 요청시 발급받은 작업아이디
		String jobID = "016120614000000002";	
		
		// 거래용도, P-소득공제용, C-지출증빙용
		String[] TradeUsage = {"P", "C"};		
		
		// 거래유형, N-일반 현금영수증, C-취소현금영수증
		String[] TradeType = {"N", "C"};		
	
		
		// 페이지번호
		int Page = 1;					
		
		// 페이지당 목록개수
		int PerPage = 10;			
		
		// 정렬방향 D-내림차순, A-오름차순
		String Order = "D";				
  		  
		try {
			HTCashbillSearchResult searchInfo = htCashbillService.search(testCorpNum, 
					jobID, TradeUsage, TradeType, Page, PerPage, Order);
			m.addAttribute("SearchResult", searchInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTCashbill/SearchResult";
	}
	
	@RequestMapping(value = "summary", method = RequestMethod.GET)
	public String summary ( Model m) {
		/**
		 * 검색조건을 사용하여 수집 결과 요약정보를 조회합니다.
		 * - 응답항목에 관한 정보는 "[홈택스 현금영수증 연계 API 연동매뉴얼]
		 *   > 3.3.2. Summary (수집 결과 요약정보 조회)" 을 참고하시기 바랍니다.
		 */
		
		// 수집 요청시 발급받은 작업아이디
		String jobID = "016120614000000002";	
		
		// 거래용도, P-소득공제용, C-지출증빙용
		String[] TradeUsage = {"P", "C"};		
		
		// 거래유형, N-일반 현금영수증, C-취소현금영수증
		String[] TradeType = {"N", "C"};		
		
		try {
			HTCashbillSummary summaryInfo = htCashbillService.summary(testCorpNum, 
					jobID, TradeUsage, TradeType);
			m.addAttribute("SummaryResult", summaryInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}	
		return "HTCashbill/Summary";
	}
	
	@RequestMapping(value = "getFlatRatePopUpURL", method = RequestMethod.GET)
	public String getFlatRatePopUpURL( Model m) {
		/**
		 * 정액제 신청 팝업 URL을 반환합니다.
		 * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		try {
			
			String url = htCashbillService.getFlatRatePopUpURL(testCorpNum, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getCertificatePopUpURL", method = RequestMethod.GET)
	public String getCertificatePopUpURL( Model m) {
		/**
		 * 홈택스 공인인증서 등록 팝업 URL을 반환합니다.
		 * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.		
		 */
		
		try {
			
			String url = htCashbillService.getCertificatePopUpURL(testCorpNum, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getCertificateExpireDate", method = RequestMethod.GET)
	public String getCertificateExpireDate( Model m) {
		/**
		 * 등록된 홈택스 공인인증서의 만료일자를 확인합니다.
		 */
		try {
			
			Date expireDate = htCashbillService.getCertificateExpireDate(testCorpNum);
			
			m.addAttribute("Result", expireDate);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getFlatRateState", method = RequestMethod.GET)
	public String getFlatRateState( Model m) {
		/**
		 * 연동회원의 정액제 서비스 이용상태를 확인합니다.
		 */
		
		try {
			
			FlatRateState flatRateInfo = htCashbillService.getFlatRateState(testCorpNum);
			
			m.addAttribute("State", flatRateInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTTaxinvoice/GetFlatRateState";
	}
}

