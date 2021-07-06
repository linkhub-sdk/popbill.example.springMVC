/*
 * 팝빌 계좌조회 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/easyfinbank/tutorial/java
 * - 업데이트 일자 : 2021-07-06
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
import com.popbill.api.easyfin.EasyFinBankAccountForm;
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
     * 계좌조회 서비스를 이용할 은행계좌를 등록한다.
     */
	@RequestMapping(value = "registBankAccount", method = RequestMethod.GET)
    public String registBankAccount(Model m) {
		
		// 계좌정보 클래스 인스턴스 생성
		EasyFinBankAccountForm bankInfo = new EasyFinBankAccountForm();
		
		// [필수] 은행코드
	    // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
	    // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
	    // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
		bankInfo.setBankCode("");
		
		// [필수] 계좌번호 하이픈('-') 제외
		bankInfo.setAccountNumber("");
		
		// [필수] 계좌비밀번호
		bankInfo.setAccountPWD("");
		
		// [필수] 계좌유형, "법인" 또는 "개인" 입력
		bankInfo.setAccountType("");
		
		// [필수] 예금주 식별정보 (‘-‘ 제외)
	    // 계좌유형이 “법인”인 경우 : 사업자번호(10자리)
	    // 계좌유형이 “개인”인 경우 : 예금주 생년월일 (6자리-YYMMDD)
		bankInfo.setIdentityNumber("");
		
		// 계좌 별칭
		bankInfo.setAccountName("");
		
		// 인터넷뱅킹 아이디 (국민은행 필수)
		bankInfo.setBankID("");
		
		// 조회전용 계정 아이디 (대구은행, 신협, 신한은행 필수)
		bankInfo.setFastID("");
		
		// 조회전용 계정 비밀번호 (대구은행, 신협, 신한은행 필수
		bankInfo.setFastPWD("");
		
		// 결제기간(개월), 1~12 입력가능, 미기재시 기본값(1) 처리
	    // - 파트너 과금방식의 경우 입력값에 관계없이 1개월 처리
		bankInfo.setUsePeriod(1);
		
		// 메모
		bankInfo.setMemo("");
		
        try {

            Response response = easyFinBankService.registBankAccount(testCorpNum, bankInfo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }
	
	/*
	 * 팝빌에 등록된 은행 계좌정보를 수정합니다.
	 */
	@RequestMapping(value = "updateBankAccount", method = RequestMethod.GET)
    public String updateBankAccount(Model m) {
		
		// 계좌정보 클래스 인스턴스 생성
		EasyFinBankAccountForm bankInfo = new EasyFinBankAccountForm();
		
		// [필수] 은행코드
	    // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
	    // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
	    // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
		bankInfo.setBankCode("");
		
		// [필수] 계좌번호 하이픈('-') 제외
		bankInfo.setAccountNumber("");
		
		// [필수] 계좌비밀번호
		bankInfo.setAccountPWD("");
		
		// 계좌 별칭
		bankInfo.setAccountName("");
		
		// 인터넷뱅킹 아이디 (국민은행 필수)
		bankInfo.setBankID("");
		
		// 조회전용 계정 아이디 (대구은행, 신협, 신한은행 필수)
		bankInfo.setFastID("");
		
		// 조회전용 계정 비밀번호 (대구은행, 신협, 신한은행 필수
		bankInfo.setFastPWD("");
				
		// 메모
		bankInfo.setMemo("");
		
        try {

            Response response = easyFinBankService.updateBankAccount(testCorpNum, bankInfo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }
	
	/*
	 * 팝빌에 등록된 은행계좌의 정액제 해지를 요청합니다.
	 */
	@RequestMapping(value = "closeBankAccount", method = RequestMethod.GET)
    public String closeBankAccount(Model m) {
		
		// [필수] 은행코드
	    // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
	    // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
	    // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
		String BankCode = "";
		
		// [필수] 계좌번호 하이픈('-') 제외
		String AccountNumber = "";
				
		// [필수] 해지유형, “일반”, “중도” 중 선택 기재
	    // 일반해지 – 이용중인 정액제 사용기간까지 이용후 정지
	    // 중도해지 – 요청일 기준으로 정지, 정액제 잔여기간은 일할로 계산되어 포인트 환불 (무료 이용기간 중 중도해지 시 전액 환불)
		String CloseType = "중도";
		
        try {

            Response response = easyFinBankService.closeBankAccount(testCorpNum, BankCode, AccountNumber, CloseType);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }
	
	/*
	 * 팝빌에 등록된 은행계좌의 정액제 해지요청을 취소합니다.
	 */
	@RequestMapping(value = "revokeCloseBankAccount", method = RequestMethod.GET)
    public String revokeCloseBankAccount(Model m) {
		
		// [필수] 은행코드
	    // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
	    // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
	    // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
		String BankCode = "";
		
		// [필수] 계좌번호 하이픈('-') 제외
		String AccountNumber = "";
				
        try {

            Response response = easyFinBankService.revokeCloseBankAccount(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }
	
	/*
	 *  종량제 계좌를 삭제합니다.
	 *  - https://docs.popbill.com/easyfinbank/java/api#DeleteBankAccount
	 */
	
	@RequestMapping(value = "deleteBankAccount", method = RequestMethod.GET)
	public String deleteBankAccount(Model m) {
		
		// [필수] 은행코드
	    // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
	    // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
	    // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
		String BankCode = "";
		
		// [필수] 계좌번호 하이픈('-') 제외
		String AccountNumber = "";
		
		try {
			Response response = easyFinBankService.deleteBankAccount(testCorpNum, BankCode, AccountNumber);
			
			m.addAttribute("Response", response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	/*
	 * 팝빌에 등록된 은행 계좌정보를 확인합니다.
	 */
	@RequestMapping(value = "getBankAccountInfo", method = RequestMethod.GET)
    public String getBankAccountInfo(Model m) {
		
		
		// [필수] 은행코드
	    // 산업은행-0002 / 기업은행-0003 / 국민은행-0004 /수협은행-0007 / 농협은행-0011 / 우리은행-0020
	    // SC은행-0023 / 대구은행-0031 / 부산은행-0032 / 광주은행-0034 / 제주은행-0035 / 전북은행-0037
	    // 경남은행-0039 / 새마을금고-0045 / 신협은행-0048 / 우체국-0071 / KEB하나은행-0081 / 신한은행-0088 /씨티은행-0027
		String BankCode = "";
		
		// [필수] 계좌번호 하이픈('-') 제외
		String AccountNumber = "";
		
        try {

            EasyFinBankAccount bankAccountInfo = easyFinBankService.getBankAccountInfo(testCorpNum, BankCode, AccountNumber);
    		
            m.addAttribute("Account", bankAccountInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "EasyFinBank/GetBankAccount";
    }

    /*
     * 은행 계좌 관리 팝업 URL을 반환한다. 
     * - 반환된 URL은 보안정책에 따라 30초의 유효시간을 갖습니다.
     * - https://docs.popbill.com/easyfinbank/java/api#GetBankAccountMgtURL
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
	 * - https://docs.popbill.com/easyfinbank/java/api#ListBankAccount
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
	 * - https://docs.popbill.com/easyfinbank/java/api#RequestJob
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
	 * - https://docs.popbill.com/easyfinbank/java/api#GetJobState
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
	 * - https://docs.popbill.com/easyfinbank/java/api#ListActiveJob
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
	 * - https://docs.popbill.com/easyfinbank/java/api#Search
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model m) {
       
        // 수집 요청시 발급받은 작업아이디
        String jobID = "020080715000000001";

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
	 * - https://docs.popbill.com/easyfinbank/java/api#Summary
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
	 * - https://docs.popbill.com/easyfinbank/java/api#SaveMemo
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
     * - https://docs.popbill.com/easyfinbank/java/api#GetFlatRatePopUpURL
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
     * - https://docs.popbill.com/easyfinbank/java/api#GetFlatRateState
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
     * - https://docs.popbill.com/easyfinbank/java/api#GetChargeInfo
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
