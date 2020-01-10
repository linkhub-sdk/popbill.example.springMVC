/*
 * 팝빌 계좌조회 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/easyfinbank/tutorial/java
 * - 업데이트 일자 : 2020-01-10
 * - 연동 기술지원 연락처 : 1600-9854 / 070-4304-2991~2
 * - 연동 기술지원 이메일 : code@linkhub.co.kr
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

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.ChargeInfo;
import com.popbill.api.EasyFinBankService;
import com.popbill.api.FlatRateState;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.easyfin.EasyFinBankAccount;
import com.popbill.api.easyfin.EasyFinBankJobState;
import com.popbill.api.easyfin.EasyFinBankSearchResult;
import com.popbill.api.easyfin.EasyFinBankSummary;

@Controller
@RequestMapping("EasyFinBankService")
public class EasyFinBankServiceExample {

	@Autowired
	private EasyFinBankService easyFinBankService;
	
	// 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;    
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "EasyFinBank/index";
    }

    /*
     * 은행 계좌 관리 팝업 URL을 반환한다. 
     * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
     */
	@RequestMapping(value = "getBankAccountMgtURL", method = RequestMethod.GET)
    public String getBankAccountMgtURL(Model m) {

        try {
            String url = easyFinBankService.getBankAccountMgtURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }
	
	/*
	 * 팝빌에 등록된 은행계좌 목록을 반환한다. 
	 */
	@RequestMapping(value = "listBankAccount", method = RequestMethod.GET)
    public String listBankAccount(Model m) {
       
        try {
        	
            EasyFinBankAccount[] bankList = easyFinBankService.listBankAccount(testCorpNum);
            m.addAttribute("BankAccountList", bankList);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/ListBankAccount";
    }
	
	/*
	 * 계좌 거래내역 수집을 요청한다.
	 * - 검색기간은 현재일 기준 90일 이내로만 요청할 수 있다.
	 */
	@RequestMapping(value = "requestJob", method = RequestMethod.GET)
    public String requestJob(Model m) {
        
		// 은행코드
        String BankCode = "0048";
        
        // 계좌번호
        String AccountNumber = "131020538600";

        // 시작일자, 날짜형식(yyyyMMdd)
        String SDate = "20190919";

        // 종료일자, 닐짜형식(yyyyMMdd)
        String EDate = "20191218";

        try {
        	
            String jobID = easyFinBankService.requestJob(testCorpNum, BankCode, AccountNumber, SDate, EDate);
            m.addAttribute("Result", jobID);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }
	
	/*
	 * 계좌 거래내역 수집 상태를 확인한다.
	 */
	@RequestMapping(value = "getJobState", method = RequestMethod.GET)
    public String getJobState(Model m) {
        

        // 수집요청(requestJob)시 반환받은 작업아이디
        String jobID = "019121815000000001";

        try {
            EasyFinBankJobState jobState = easyFinBankService.getJobState(testCorpNum, jobID);
            m.addAttribute("JobState", jobState);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/GetJobState";
    }
	
	/*
	 * 1시간 이내 수집 요청 목록을 확인한다. 
	 */
	@RequestMapping(value = "listActiveJob", method = RequestMethod.GET)
    public String listActiveJob(Model m) {
        

        try {
            EasyFinBankJobState[] jobState = easyFinBankService.listActiveJob(testCorpNum);
            m.addAttribute("JobStates", jobState);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/ListActiveJob";
    }
	
	/*
	 * 계좌 거래내역을 조회한다. 
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
       
        // 수집 요청시 발급받은 작업아이디
        String jobID = "019121816000000001";

        // 거래유형, I-입금, O-출금
        String[] TradeType = {"I", "O"};

        // 페이지번호
        int Page = 1;

        // 페이지당 목록개수
        int PerPage = 10;

        // 정렬방향 D-내림차순, A-오름차순
        String Order = "D";
        
        // 조회 검색어, 입금/출금액, 메모, 적요 like 검색
        String SearchString = "";
        
        try {
            EasyFinBankSearchResult searchInfo = easyFinBankService.search(testCorpNum,
                    jobID, TradeType, SearchString, Page, PerPage, Order, testUserID);
            m.addAttribute("SearchResult", searchInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/SearchResult";
    }
	
	/*
	 * 계좌 거래내역 요약정보를 조회한다. 
	 */
	@RequestMapping(value = "summary", method = RequestMethod.GET)
    public String summary(Model m) {
       
        // 수집 요청시 발급받은 작업아이디
        String jobID = "019121816000000001";

        // 거래유형, I-입금, O-출금
        String[] TradeType = {"I", "O"};
        
        // 조회 검색어, 입금/출금액, 메모, 적요 like 검색
        String SearchString = "";
        
        try {
        	
            EasyFinBankSummary summaryInfo = easyFinBankService.summary(testCorpNum,
                    jobID, TradeType, SearchString, testUserID);
            m.addAttribute("SummaryResult", summaryInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/Summary";
    }

	/*
	 * 계좌 거래내역에 메모를 저장한다.
	 */
    @RequestMapping(value = "saveMemo", method = RequestMethod.GET)
    public String saveMemo(Model m) {

        // 거래내역 아이디, SeachAPI 응답항목 중 tid
        String TID = "01912181100000000120191210000003";

        // 메모
        String Memo = "0191218-테스트";

        try {

            Response response = easyFinBankService.saveMemo(testCorpNum ,TID, Memo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }
    
    /*
     * 계좌조회 정액제 서비스 신청 팝업 URL을 반환한다. 
     * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
     */
	@RequestMapping(value = "getFlatRatePopUpURL", method = RequestMethod.GET)
    public String getFlatRatePopUpURL(Model m) {

        try {
            String url = easyFinBankService.getFlatRatePopUpURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }
	
	/*
     * 정액제 서비스 상태를 확인합니다.
     */
	@RequestMapping(value = "getFlatRateState", method = RequestMethod.GET)
    public String getFlatRateState(Model m) {

		// 은행코드
        String BankCode = "0048";
        
        // 계좌번호
        String AccountNumber = "131020538600";
		
        try {

            FlatRateState flatRateInfo = easyFinBankService.getFlatRateState(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("State", flatRateInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/GetFlatRateState";
    }
	
	/*
     * 계좌조회 API 서비스 과금정보를 확인합니다.
     */
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo(Model m) {
	    
	    try {
	
	    	ChargeInfo chrgInfo = easyFinBankService.getChargeInfo(testCorpNum);
	    	m.addAttribute("ChargeInfo", chrgInfo);
		
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
	
	    return "getChargeInfo";
	}
}
