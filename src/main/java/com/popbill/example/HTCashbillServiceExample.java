/*
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
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "HTCashbill/index";
	}
	
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo( Model m) {

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
		
		// 현금영수증 유형, SELL-매출, BUY-매입
		QueryType TIType = QueryType.SELL;
				
		// 시작일자, 표시형식(yyyyMMdd)
		String SDate = "20160501";
		
		// 종료일자, 표시형식(yyyyMMdd)
		String EDate = "20160801";
		
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
		
		// 수집요청(requestJob)시 반환받은 작업아이디
		String jobID = "016071815000000010";
		
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
		
		String jobID = "016071815000000010";	// 수집 요청시 발급받은 작업아이디
		String[] TradeUsage = {"P", "C"};		// 거래용도, P-소득공제용, C-지출증빙용
		String[] TradeType = {"N", "C"};		// 거래유형, N-일반 현금영수증, C-취소현금영수증
	
		int Page = 1;					// 페이지번호
		int PerPage = 10;				// 페이지당 목록개수
		String Order = "D";				// 정렬방향 D-내림차순, A-오름차순
  		  
		try {
			HTCashbillSearchResult searchInfo = htCashbillService.search(testCorpNum, jobID, TradeUsage, TradeType, Page, PerPage, Order);
			m.addAttribute("SearchResult", searchInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTCashbill/SearchResult";
	}
	
	@RequestMapping(value = "summary", method = RequestMethod.GET)
	public String summary ( Model m) {
		
		String jobID = "016071815000000010";	// 수집 요청시 발급받은 작업아이디
		String[] TradeUsage = {"P", "C"};		// 거래용도, P-소득공제용, C-지출증빙용
		String[] TradeType = {"N", "C"};		// 거래유형, N-일반 현금영수증, C-취소현금영수증
		
		try {
			HTCashbillSummary summaryInfo = htCashbillService.summary(testCorpNum, jobID, TradeUsage, TradeType);
			m.addAttribute("SummaryResult", summaryInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}	
		return "HTCashbill/Summary";
	}
	
	@RequestMapping(value = "getFlatRatePopUpURL", method = RequestMethod.GET)
	public String getFlatRatePopUpURL( Model m) {
				
		try {
			
			String url = htCashbillService.getFlatRatePopUpURL(testCorpNum,testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getCertificatePopUpURL", method = RequestMethod.GET)
	public String getCertificatePopUpURL( Model m) {
				
		try {
			
			String url = htCashbillService.getCertificatePopUpURL(testCorpNum,testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getCertificateExpireDate", method = RequestMethod.GET)
	public String getCertificateExpireDate( Model m) {
				
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

