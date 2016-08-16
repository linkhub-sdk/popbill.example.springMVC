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
import com.popbill.api.HTTaxinvoiceService;
import com.popbill.api.PopbillException;
import com.popbill.api.hometax.HTTaxinvoice;
import com.popbill.api.hometax.HTTaxinvoiceJobState;
import com.popbill.api.hometax.HTTaxinvoiceSearchResult;
import com.popbill.api.hometax.HTTaxinvoiceSummary;
import com.popbill.api.hometax.HTTaxinvoiceXMLResponse;
import com.popbill.api.hometax.QueryType;


/**
 * 팝빌 홈택스연계 전자세금계산서 API 예제.
 */
@Controller
@RequestMapping("HTTaxinvoiceService")
public class HTTaxinvoiceExample {
	
	
	@Autowired
	private HTTaxinvoiceService htTaxinvoiceService;
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "HTTaxinvoice/index";
	}
	
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo( Model m) {

		try {
			ChargeInfo chrgInfo = htTaxinvoiceService.getChargeInfo(testCorpNum);	
			m.addAttribute("ChargeInfo",chrgInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "getChargeInfo";
	}	
	
	@RequestMapping(value = "requestJob", method = RequestMethod.GET)
	public String requestJob( Model m) {
		
		// 전자세금계산서 유형, SELL-매출, BUY-매입, TRUSTEE-수탁
		QueryType TIType = QueryType.SELL;
		
		// 일자유형, W-작성일자, I-발행일자, S-전송일자
		String DType = "W";
		
		// 시작일자, 표시형식(yyyyMMdd)
		String SDate = "20160501";
		
		// 종료일자, 표시형식(yyyyMMdd)
		String EDate = "20160801";
		
		try {
			String jobID = htTaxinvoiceService.requestJob(testCorpNum, TIType, DType, SDate, EDate);
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
		String jobID = "016071811000000010";
		
		try {
			HTTaxinvoiceJobState jobState = htTaxinvoiceService.getJobState(testCorpNum, jobID, testUserID);
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
			HTTaxinvoiceJobState[] jobStates = htTaxinvoiceService.listActiveJob(testCorpNum, testUserID);
			m.addAttribute("JobStates", jobStates);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTTaxinvoice/ListActiveJob";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search ( Model m) {
		
		String jobID = "016071813000000001";  // 수집 요청시 발급받은 작업아이디
		String[] Type = {"N", "M"};           // 문서형태, N-일반, M-수정
		String[] TaxType = {"T", "Z", "N"};   // 과세형태, T-과세, N-면세, Z-영세
		String[] PurposeType = {"R", "C", "N"}; // 영수/청구 R-영수, C-청구, N-없음
		String TaxRegIDYN = "";     			// 종사업장 유무, 공백-전체조회, 0-종사업장번호 없음, 1-종사업장번호 있음
		String TaxRegIDType = "S";      		// 종사업장 유형, S-공급자, B-공급받는자, T-수탁자
		String TaxRegID = "";      			// 종사업장번호, 다수기재시 콤마(",")로 구분하여 구성 ex) "0001,0002"
		int Page = 1;					// 페이지번호
		int PerPage = 10;				// 페이지당 목록개수
		String Order = "D";				// 정렬방향 D-내림차순, A-오름차순
  		  
		try {
			HTTaxinvoiceSearchResult searchInfo = htTaxinvoiceService.search(testCorpNum, jobID, Type, TaxType, PurposeType, TaxRegIDYN, TaxRegIDType, TaxRegID, Page, PerPage, Order);
			m.addAttribute("SearchResult", searchInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTTaxinvoice/SearchResult";
	}
	
	@RequestMapping(value = "summary", method = RequestMethod.GET)
	public String summary ( Model m) {
		
		String jobID = "016071812000000001";  // 수집 요청시 발급받은 작업아이디
		String[] Type = {"N", "M"};           // 문서형태, N-일반, M-수정
		String[] TaxType = {"T", "Z", "N"};   // 과세형태, T-과세, N-면세, Z-영세
		String[] PurposeType = {"R", "C", "N"}; // 영수/청구 R-영수, C-청구, N-없음
		String TaxRegIDYN = "";     			// 종사업장 유무, 공백-전체조회, 0-종사업장번호 없음, 1-종사업장번호 있음
		String TaxRegIDType = "S";      		// 종사업장 유형, S-공급자, B-공급받는자, T-수탁자
		String TaxRegID = "";      			// 종사업장번호, 다수기재시 콤마(",")로 구분하여 구성 ex) "0001,0002"
		
		try {
			HTTaxinvoiceSummary summaryInfo = htTaxinvoiceService.summary(testCorpNum, jobID, Type, TaxType, PurposeType, TaxRegIDYN, TaxRegIDType, TaxRegID);
			m.addAttribute("SummaryResult", summaryInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}	
		return "HTTaxinvoice/Summary";
	}
	
	
	@RequestMapping(value = "getTaxinvoice", method = RequestMethod.GET)
	public String getTaxinvoice ( Model m) {
		
		String ntsconfirmNum = "20160707410000290000030e";
		
		try {
			HTTaxinvoice taxinvoiceInfo = htTaxinvoiceService.getTaxinvoice(testCorpNum, ntsconfirmNum, testUserID);
			m.addAttribute("Taxinvoice", taxinvoiceInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTTaxinvoice/Taxinvoice";
	}
	
	@RequestMapping(value = "getXML", method = RequestMethod.GET)
	public String getXML ( Model m) {
		
		String ntsconfirmNum = "20160707410000290000030e";
		
		try {
			HTTaxinvoiceXMLResponse xmlResponse = htTaxinvoiceService.getXML(testCorpNum, ntsconfirmNum, testUserID);
			m.addAttribute("TaxinvoiceXML", xmlResponse);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTTaxinvoice/TaxinvoiceXML";
	}
	
	@RequestMapping(value = "getFlatRatePopUpURL", method = RequestMethod.GET)
	public String getFlatRatePopUpURL( Model m) {
				
		try {
			
			String url = htTaxinvoiceService.getFlatRatePopUpURL(testCorpNum,testUserID);
			
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
			
			String url = htTaxinvoiceService.getCertificatePopUpURL(testCorpNum,testUserID);
			
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
			
			Date expireDate = htTaxinvoiceService.getCertificateExpireDate(testCorpNum);
			
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
			
			FlatRateState flatRateInfo = htTaxinvoiceService.getFlatRateState(testCorpNum);
			
			m.addAttribute("State", flatRateInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "HTTaxinvoice/GetFlatRateState";
	}
}

