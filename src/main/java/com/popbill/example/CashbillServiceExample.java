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

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.CashbillService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.cashbill.CBSearchResult;
import com.popbill.api.cashbill.Cashbill;
import com.popbill.api.cashbill.CashbillInfo;
import com.popbill.api.cashbill.CashbillLog;

/**
 * 팝빌 현금영수증 API 예제.
 */

@Controller
@RequestMapping("CashbillService")
public class CashbillServiceExample {

	@Autowired
	private CashbillService cashbillService;
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Cashbill/index";
	}
	
	@RequestMapping(value = "checkMgtKeyInUse", method = RequestMethod.GET)
	public String checkMgtKeyInUse( Model m) {
		/**
		 * 문서관리번호 사용여부 확인
		 * 최대 24자리, 영문, 숫자, '-', '_' 조합하여 구성
		 */
		
		String mgtKey = "20150320-01";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		String isUseStr;
		
		try {
			boolean IsUse = cashbillService.checkMgtKeyInUse(testCorpNum, mgtKey);
			
			isUseStr = (IsUse) ?  "사용중" : "미사용중";
			
			m.addAttribute("Result",isUseStr);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register( Model m) {
		/*
		 * 현금영수증 등록(임시저장) 
		 * 현금영수증 입력항목에 관한 자세한 사항은 [현금영수증 API 연동매뉴얼> 4.1 현금영수증 구성] 참조  	
		 */ 

		Cashbill cashbill = new Cashbill();
	
		cashbill.setMgtKey("20150320-01");			// 문서관리번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
		cashbill.setTradeType("승인거래");				// 현금영수증 형태, {승인거래, 취소거래} 중 기재
		//cashbill.setOrgConfirmNum("");  			// 취소거래시 기재, 원본현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
		cashbill.setTradeUsage("소득공제용");			// 거래유형, {소득공제용, 지출증빙용} 중 기재
		cashbill.setTaxationType("과세");				// 과세형태, {과세, 비과세} 중 기재
		
		cashbill.setIdentityNum("01043245117");			// 거래처 식별번호, 거래유형(TradeUsage) : 소득공제용인 경우 - (주민등록/휴대폰/카드)번호 기재
														//					거래유형(TradeUsage) : 지출증빙용인 경우 - 사업자번호 기재
	
		cashbill.setFranchiseCorpNum("1234567890");		// 발행자 사업자번호, '-'제외 10자리 
		cashbill.setFranchiseCorpName("발행자 상호");	
		cashbill.setFranchiseCEOName("발행자 대표자");
		cashbill.setFranchiseAddr("발행자 주소");
		cashbill.setFranchiseTEL("07075103710");	
		cashbill.setSmssendYN(false);					// 발행시 알림문자 자동전송여부
	
		cashbill.setCustomerName("고객명");
		cashbill.setItemName("상품명");
		cashbill.setOrderNumber("주문번호");
		cashbill.setEmail("test@test.com");
		cashbill.setHp("01043245117");
		cashbill.setFax("07075103710");
	
		cashbill.setSupplyCost("10000");				// 공급가액, 숫자만 가능
		cashbill.setTax("1000");						// 세액, 숫자만 가능
		cashbill.setServiceFee("0");					// 봉사료, 숫자만 가능
		cashbill.setTotalAmount("11000");				// 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 세액
			
		try {
			
			Response response = cashbillService.register(testCorpNum, cashbill, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update( Model m) {
		/*
		 * 현금영수증 수정, 기재 항목의 수정은 등록(임시저장)상태인 현금영수증만 가능
		 * 현금영수증 입력항목에 관한 자세한 사항은 [현금영수증 API 연동매뉴얼> 4.1 현금영수증 구성] 참조  
		 */
		
		Cashbill cashbill = new Cashbill();
		
		cashbill.setMgtKey("20150320-01");			// 수정시 문서관리번호를 다른값으로 입력하더라도 변경되지 않음
		cashbill.setTradeType("승인거래");				// 현금영수증 형태, {승인거래, 취소거래} 중 기재
		//cashbill.setOrgConfirmNum("");  			// 취소거래시 기재, 원본현금영수증 국세청 승인번호
		cashbill.setTradeUsage("소득공제용");			// 거래유형, {소득공제용, 지출증빙용} 중 기재
		cashbill.setTaxationType("과세");				// 과세형태, {과세, 비과세} 중 기재
		
		cashbill.setIdentityNum("01043245117");			// 거래처 식별번호, 거래유형(TradeUsage) : 소득공제용인 경우 - (주민등록/휴대폰/카드)번호 기재
														//					거래유형(TradeUsage) : 지출증빙용인 경우 - 사업자번호 기재
	
		cashbill.setFranchiseCorpNum("1234567890");		// 발행자 사업자번호, '-'제외 10자리 
		cashbill.setFranchiseCorpName("발행자 상호_수정");	
		cashbill.setFranchiseCEOName("발행자 대표자_수정");
		cashbill.setFranchiseAddr("발행자 주소");
		cashbill.setFranchiseTEL("07075103710");	
		cashbill.setSmssendYN(false);					// 발행시 알림문자 자동전송여부
	
		cashbill.setCustomerName("고객명");
		cashbill.setItemName("상품명");
		cashbill.setOrderNumber("주문번호");
		cashbill.setEmail("test@test.com");
		cashbill.setHp("01043245117");
		cashbill.setFax("07075103710");
	
		cashbill.setSupplyCost("10000");				// 공급가액, 숫자만 가능
		cashbill.setTax("1000");						// 세액, 숫자만 가능
		cashbill.setServiceFee("0");					// 봉사료, 숫자만 가능
		cashbill.setTotalAmount("11000");				// 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 세액
		
		try {
			
			String mgtKey = "20150320-01";	// 수정할 현금영수증 문서관리번호
			
			Response response = cashbillService.update(testCorpNum, mgtKey, cashbill, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getInfo", method = RequestMethod.GET)
	public String getInfo( Model m) {
		/*
		 * 현금영수증 상태/요약 정보 확인
		 * 현금영수증 상태정보 항목에 대한 설명은 [현금영수증 API 연동매뉴얼 - 4.2 현금영수증 상태정보 구성] 참조 
		 */
		
		String mgtKey = "20150320-01";	// 문서관리번호
		
		try {
			
			CashbillInfo cashbillInfo = cashbillService.getInfo(testCorpNum, mgtKey);
			
			m.addAttribute("CashbillInfo",cashbillInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Cashbill/CashbillInfo";
	}
	
	@RequestMapping(value = "getInfos", method = RequestMethod.GET)
	public String getInfos( Model m) {
		// 다량 현금영수증 상태/요약정보 확인(최대 1000건)

		// 현금영수증 문서관리번호 배열 최대(1000건)
		String[] mgtKeyList = new String[] {"20150317-01", "20150317-02", "20150318-02", "20150319-01"};
		
		try {
			
			CashbillInfo[] cashbillInfos = cashbillService.getInfos(testCorpNum, mgtKeyList);
			
			m.addAttribute("CashbillInfos", cashbillInfos);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Cashbill/CashbillInfo";
	}
	
	
	
	@RequestMapping(value = "getDetailInfo", method = RequestMethod.GET)
	public String getDetailInfo( Model m) {
		/*
		 * 현금영수증 상세정보 확인
		 * 현금영수증 항목에 대한 설명은 [현금영수증 API 연동매뉴얼 > 4.1 현금영수증 구성] 참조  
		 */
		
		String mgtKey = "20150320-01";	// 문서관리번호
		
		try {
			
			Cashbill cashbill = cashbillService.getDetailInfo(testCorpNum, mgtKey);
			
			m.addAttribute("Cashbill",cashbill);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Cashbill/Cashbill";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete( Model m) {
		/*
		 * 현금영수증 삭제
		 * [등록(임시저장)], [발행취소] 상태의 현금영수증만 삭제가능
		 */
		
		String mgtKey = "20150320-01";	// 문서관리번호
		
		try {
			Response response = cashbillService.delete(testCorpNum, mgtKey);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	
	@RequestMapping(value = "getLogs", method = RequestMethod.GET)
	public String getLogs( Model m) {
		// 현금영수증 문서이력 확인
		
		String mgtKey = "20150320-01";	// 문서관리번호
		
		try {
			CashbillLog[] cashbillLogs = cashbillService.getLogs(testCorpNum, mgtKey);
			
			m.addAttribute("CashbillLogs",cashbillLogs);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Cashbill/CashbillLog";
	}
	
	@RequestMapping(value = "issue", method = RequestMethod.GET)
	public String issue( Model m) {
		// 현금영수증 발행
		
		String mgtKey = "20150320-01";	// 문서관리번호
		String memo = "발행메모";
		
		try {
			Response response = cashbillService.issue(testCorpNum, mgtKey, memo, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	
	@RequestMapping(value = "cancelIssue", method = RequestMethod.GET)
	public String cancelIssue( Model m) {
		/*
		 * 현금영수증 발행취소
		 * 발행취소된 현금영수증은 국세청 전송대상에서 제외됩니다.
		 */ 
		
		String mgtKey = "20150320-01";	// 문서관리번호
		String memo = "발행취소 메모";
		
		try {
			Response response = cashbillService.cancelIssue(testCorpNum, mgtKey, memo, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendEmail", method = RequestMethod.GET)
	public String sendEmail( Model m) {
		/*
		 * 현금영수증 이메일 재전송 요청
		 * [임시저장(등록)] 상태인 경우 이메일 재전송 불가  
		 */
		
		String mgtKey = "20150320-01";		// 현금영수증 문서관리번호
		String receiver = "test@test.com";	// 수신자 이메일주소
		
		try {
			Response response = cashbillService.sendEmail(testCorpNum, mgtKey, receiver, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendSMS", method = RequestMethod.GET)
	public String sendSMS( Model m) {
		/*
		 * 알림문자 전송 요청
		 * 문자전송내용의 길이가 90Byte를 초과한경우 길이가 조정되어 전송 
		 */
		
		String mgtKey = "20150320-01";				// 현금영수증 문서관리번호
		String sender = "07075103710";				// 발신번호
		String receiver = "010111222";			// 수신번호 
		String contents = "현금영수증 문자메시지 전송 테스트입니다.";		// 문자 전송 내용 (90Byte 초과시 길이가 조정되어 전송)
		
		try {
			Response response = cashbillService.sendSMS(testCorpNum, mgtKey, sender, receiver, contents, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendFAX", method = RequestMethod.GET)
	public String sendFAX( Model m) {
		// 현금영수증 팩스 전송 요청 
		
		String mgtKey = "20150320-01";				// 현금영수증 문서관리번호
		String sender = "07075103710";				// 발신자 번호
		String receiver = "010111222";				// 수신자 팩스번호
		
		try {
			Response response = cashbillService.sendFAX(testCorpNum, mgtKey, sender, receiver, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getURL", method = RequestMethod.GET)
	public String getURL( Model m) {
		
		// 현금영수증 관련 팝빌 URL
		
		String TOGO = "WRITE"; // TBOX : 임시문서함 , PBOX : 매출문서함, WRITE : 현금영수증 작성
		
		try {
			
			String url = cashbillService.getURL(testCorpNum, testUserID, TOGO);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPopUpURL", method = RequestMethod.GET)
	public String getPopUpURL( Model m) {
		// 현금영수증 문서 내용 보기 URL
		
		String mgtKey = "20150320-01";			// 현금영수증 문서관리번호
		
		try {
			String url = cashbillService.getPopUpURL(testCorpNum, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPrintURL", method = RequestMethod.GET)
	public String getPrintURL( Model m) {
		/*
		 * 현금영수증 인쇄 화면 URL
		 * [임시저장] 상태인경우 화면에 표시되지 않음
		 * 
		 * */
		String mgtKey = "20150320-01";			// 현금영수증 문서관리번호
		
		try {
			String url = cashbillService.getPrintURL(testCorpNum, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getEPrintURL", method = RequestMethod.GET)
	public String getEPrintURL( Model m) {
		
		 // 현금영수증  인쇄 화면(공급받는자용) URL 
		
		String mgtKey = "20150320-01";			// 현금영수증 문서관리번호
		
		try {
			String url = cashbillService.getEPrintURL(testCorpNum, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getMailURL", method = RequestMethod.GET)
	public String getMailURL( Model m) {
		
		// 현금영수증 공급받는자 메일 링크 URL 확인 
		
		String mgtKey = "20150320-01";			// 현금영수증 문서관리번호
		
		try {
			String url = cashbillService.getMailURL(testCorpNum, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getMassPrintURL", method = RequestMethod.GET)
	public String getMassPrintURL( Model m) {
		
		// 다량 현금영수증 인쇄 화면 URL 확인, 최대 100건 
		// 문서관리번호 배열, 최대 100건
		
		String[] MgtKeyList = new String[] {"20150320-01", "20150320-02", "20150320-03"}; 
		
		try {
			
			String url = cashbillService.getMassPrintURL(testCorpNum, MgtKeyList, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		
		// 현그영수증 발행단가 확인 
		
		try {
			
			float unitCost = cashbillService.getUnitCost(testCorpNum);
			
			m.addAttribute("Result",unitCost);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "registIssue", method = RequestMethod.GET)
	public String registIssue( Model m) {
		/*
		 * 현금영수증 즉시발행
		 * 현금영수증 입력항목에 관한 자세한 사항은 [현금영수증 API 연동매뉴얼> 4.1 현금영수증 구성] 참조  	
		 */ 
		
		String Memo = "현금영수증 즉시발행 메모";

		Cashbill cashbill = new Cashbill();
	
		cashbill.setMgtKey("20160119-02");			// 문서관리번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
		cashbill.setTradeType("승인거래");				// 현금영수증 형태, {승인거래, 취소거래} 중 기재
		//cashbill.setOrgConfirmNum("");  			// 취소거래시 기재, 원본현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
		cashbill.setTradeUsage("소득공제용");			// 거래유형, {소득공제용, 지출증빙용} 중 기재
		cashbill.setTaxationType("과세");				// 과세형태, {과세, 비과세} 중 기재
		
		cashbill.setIdentityNum("010-000-1234");			// 거래처 식별번호, 거래유형(TradeUsage) : 소득공제용인 경우 - (주민등록/휴대폰/카드)번호 기재
														//					거래유형(TradeUsage) : 지출증빙용인 경우 - 사업자번호 기재
	
		cashbill.setFranchiseCorpNum("1234567890");		// 발행자 사업자번호, '-'제외 10자리 
		cashbill.setFranchiseCorpName("발행자 상호");	
		cashbill.setFranchiseCEOName("발행자 대표자");
		cashbill.setFranchiseAddr("발행자 주소");
		cashbill.setFranchiseTEL("07075103710");	
		cashbill.setSmssendYN(false);					// 발행시 알림문자 자동전송여부
	
		cashbill.setCustomerName("고객명");
		cashbill.setItemName("상품명");
		cashbill.setOrderNumber("주문번호");
		cashbill.setEmail("test@test.com");
		cashbill.setHp("01043245117");
		cashbill.setFax("07075103710");
	
		cashbill.setSupplyCost("10000");				// 공급가액, 숫자만 가능
		cashbill.setTax("1000");						// 세액, 숫자만 가능
		cashbill.setServiceFee("0");					// 봉사료, 숫자만 가능
		cashbill.setTotalAmount("11000");				// 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 세액
			
		try {
			
			Response response = cashbillService.registIssue(testCorpNum, cashbill, Memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Model m){
		
		// 현금영수증 목록조회
		
		String DType = "R"; 								// 일자유형, R-등록일자, T-거래일자, I-발행일자 
		String SDate = "20151001"; 							// 시작일자, yyyyMMdd
		String EDate = "20160118"; 							// 종료일자, yyyyMMdd
		String[] State = {"100", "2**", "3**", "4**"};		// 현금영수증 상태코드 배열, 2,3번째 자리에 와일드카드(*) 사용 가능
		String[] TradeType = {"N", "C"};					// 현금영수증 형태배열,  N-일반현금영수증, C-취소현금영수증
		String[] TradeUsage = {"P", "C"};					// 거래용도 배열, P-소득공제용, C-지출증빙용
		String[] TaxationType = {"T", "N"};					// 과세형태 배열, T-과세, N-비과세 
		int Page = 1;										// 페이지 번호 
		int PerPage = 20;									// 페이지당 목록개수, 최대 1000건 
		String Order = "D";									// 정렬방향, A-오름차순,  D-내림차순 
		
		try {
			
			CBSearchResult searchResult = cashbillService.search(testCorpNum, DType, SDate, EDate, State, TradeType, TradeUsage, TaxationType, Page, PerPage, Order);
			m.addAttribute("SearchResult", searchResult);
			
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Cashbill/SearchResult";
	}
}
