/*
 * 팝빌 현금영수증 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : http://blog.linkhub.co.kr/591/
 * - 업데이트 일자 : 2016-12-02
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

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.CashbillService;
import com.popbill.api.ChargeInfo;
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
	
	// 팝빌회원 사업자번호
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	
	// 팝빌회원 아이디
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Cashbill/index";
	}
	
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo( Model m) {
		/**
		 * 연동회원의 전자명세서 API 서비스 과금정보를 확인합니다.
		 */
		
		try {
			
			ChargeInfo chrgInfo = cashbillService.getChargeInfo(testCorpNum);
			
			m.addAttribute("ChargeInfo",chrgInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "getChargeInfo";
	}	
	
	@RequestMapping(value = "checkMgtKeyInUse", method = RequestMethod.GET)
	public String checkMgtKeyInUse( Model m) {
		/**
		 * 현금영수증 관리번호 중복여부를 확인합니다.
		 * - 관리번호는 1~24자리로 숫자, 영문 '-', '_' 조합으로 구성할 수 있습니다.
		 */
		
		// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		String mgtKey = "20150320-01";	
		
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
		/**
		 * 1건의 현금영수증을 임시저장 합니다.
		 * - [임시저장] 상태의 현금영수증은 발행(Issue API)을 호출해야만 국세청에 전송됩니다.
		 * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청
		 *   전송결과를 확인할 수 있습니다.
		 * - 현금영수증 국세청 전송 정책에 대한 정보는 "[현금영수증 API 연동매뉴얼]
		 *   > 1.4. 국세청 전송정책"을 참조하시기 바랍니다.
		 * - 취소현금영수증 작성방법 안내 - http://blog.linkhub.co.kr/702
		 */

		// 현금영수증 정보 객체
		Cashbill cashbill = new Cashbill();
	
		// 문서관리번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
		cashbill.setMgtKey("20161206-01");			
		
		// 현금영수증 형태, {승인거래, 취소거래} 중 기재
		cashbill.setTradeType("승인거래");				
		
		// 취소거래시 기재, 원본현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
		//cashbill.setOrgConfirmNum("");  			
				
		
		// 과세형태, {과세, 비과세} 중 기재
		cashbill.setTaxationType("과세");				
		
		// 거래처 식별번호, 거래유형에 따라 작성
	    // 소득공제용 - 주민등록/휴대폰/카드번호 기재가능
	    // 지출증빙용 - 사업자번호/주민등록/휴대폰/카드번호 기재가능
		cashbill.setIdentityNum("01011112222");
		
		// 거래유형, {소득공제용, 지출증빙용} 중 기재
		cashbill.setTradeUsage("소득공제용");

		// 공급가액, 숫자만 가능
		cashbill.setSupplyCost("10000");				
		
		// 세액, 숫자만 가능
		cashbill.setTax("1000");						
		
		// 봉사료, 숫자만 가능
		cashbill.setServiceFee("0");					
		
		// 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 세액
		cashbill.setTotalAmount("11000");				

		
		// 발행자 사업자번호, '-'제외 10자리
		cashbill.setFranchiseCorpNum("1234567890");
		
		// 발행자 상호
		cashbill.setFranchiseCorpName("발행자 상호");	
		
		// 발행자 대표자명
		cashbill.setFranchiseCEOName("발행자 대표자");
		
		// 발행자 주소
		cashbill.setFranchiseAddr("발행자 주소");
		
		// 발행자 연락처
		cashbill.setFranchiseTEL("07043042991");	
		
		// 발행안내 문자 전송여부
		cashbill.setSmssendYN(false);					
	
		
		// 고객명
		cashbill.setCustomerName("고객명");
		
		// 상품명
		cashbill.setItemName("상품명");
		
		// 주문번호
		cashbill.setOrderNumber("주문번호");
		
		// 고객 메일주소
		cashbill.setEmail("test@test.com");
		
		// 고객 휴대폰 번호
		cashbill.setHp("010111222");
		
		// 고객 연락처
		cashbill.setFax("07043042991");
	
			
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
		/**
		 * 1건의 현금영수증을 수정합니다.
		 * - [임시저장] 상태의 현금영수증만 수정할 수 있습니다.
		 * - 국세청에 신고된 현금영수증은 수정할 수 없으며, 취소 현금영수증을 발행하여
		 *   취소처리 할 수 있습니다.
		 * - 취소현금영수증 작성방법 안내 - http://blog.linkhub.co.kr/702
		 */
		
		
		// 수정할 현금영수증 문서관리번호
		String mgtKey = "20161206-01";	
		
		
		// 현금영수증 정보 객체
		Cashbill cashbill = new Cashbill();
	
		// 문서관리번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
		cashbill.setMgtKey("20161206-01");			
		
		// 현금영수증 형태, {승인거래, 취소거래} 중 기재
		cashbill.setTradeType("승인거래");				
		
		// 취소거래시 기재, 원본현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
		//cashbill.setOrgConfirmNum("");  			
				
		
		// 과세형태, {과세, 비과세} 중 기재
		cashbill.setTaxationType("과세");				
		
		// 거래처 식별번호, 거래유형에 따라 작성
	    // 소득공제용 - 주민등록/휴대폰/카드번호 기재가능
	    // 지출증빙용 - 사업자번호/주민등록/휴대폰/카드번호 기재가능
		cashbill.setIdentityNum("01011112222");
		
		// 거래유형, {소득공제용, 지출증빙용} 중 기재
		cashbill.setTradeUsage("소득공제용");

		// 공급가액, 숫자만 가능
		cashbill.setSupplyCost("10000");				
		
		// 세액, 숫자만 가능
		cashbill.setTax("1000");						
		
		// 봉사료, 숫자만 가능
		cashbill.setServiceFee("0");					
		
		// 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 세액
		cashbill.setTotalAmount("11000");				

		
		// 발행자 사업자번호, '-'제외 10자리
		cashbill.setFranchiseCorpNum("1234567890");
		
		// 발행자 상호
		cashbill.setFranchiseCorpName("발행자 상호_수정");	
		
		// 발행자 대표자명
		cashbill.setFranchiseCEOName("발행자 대표자_수정");
		
		// 발행자 주소
		cashbill.setFranchiseAddr("발행자 주소");
		
		// 발행자 연락처
		cashbill.setFranchiseTEL("07043042991");	
		
		// 발행안내 문자 전송여부
		cashbill.setSmssendYN(false);					
	
		
		// 고객명
		cashbill.setCustomerName("고객명");
		
		// 상품명
		cashbill.setItemName("상품명");
		
		// 주문번호
		cashbill.setOrderNumber("주문번호");
		
		// 고객 메일주소
		cashbill.setEmail("test@test.com");
		
		// 고객 휴대폰 번호
		cashbill.setHp("010111222");
		
		// 고객 연락처
		cashbill.setFax("07043042991");
		
		
		try {
			
			Response response = cashbillService.update(testCorpNum, mgtKey, 
					cashbill, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getInfo", method = RequestMethod.GET)
	public String getInfo( Model m) {
		/**
		 * 1건의 현금영수증 상태/요약 정보를 확인합니다.
		 * - 응답항목에 대한 자세한 정보는 "[현금영수증 API 연동매뉴얼] > 4.2.
		 *   현금영수증 상태정보 구성"을 참조하시기 바랍니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";	
		
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
		/**
		 * 다수건의 현금영수증 상태/요약 정보를 확인합니다.
		 * - 응답항목에 대한 자세한 정보는 "[현금영수증 API 연동매뉴얼] > 4.2.
		 *   현금영수증 상태정보 구성"을 참조하시기 바랍니다.
		 */

		// 현금영수증 문서관리번호 배열 최대(1000건)
		String[] mgtKeyList = new String[] {"20161206-01", "20150317-02", "20150318-02", "20150319-01"};
		
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
		/**
		 * 현금영수증 1건의 상세정보를 조회합니다.
		 * - 응답항목에 대한 자세한 사항은 "[현금영수증 API 연동매뉴얼] > 4.1.
		 *   현금영수증 구성" 을 참조하시기 바랍니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";	
		
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
		/**
		 * 1건의 현금영수증을 삭제합니다.
		 * - 현금영수증을 삭제하면 사용된 문서관리번호(mgtKey)를 재사용할 수 있습니다.
		 * - 삭제가능한 문서 상태 : [임시저장], [발행취소]
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";	
		
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
		/**
		 * 현금영수증 상태 변경이력을 확인합니다.
		 * - 상태 변경이력 확인(GetLogs API) 응답항목에 대한 자세한 정보는
		 *   "[현금영수증 API 연동매뉴얼] > 3.4.4 상태 변경이력 확인"
		 *   을 참조하시기 바랍니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";	
		
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
		/**
		 * [발행완료] 상태의 현금영수증을 [발행취소] 합니다.
		 * - 발행취소는 국세청 전송전에만 가능합니다.
		 * - 발행취소된 형금영수증은 국세청에 전송되지 않습니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";
		
		// 메모
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
		/**
		 * [발행완료] 상태의 현금영수증을 [발행취소] 합니다.
		 * - 발행취소는 국세청 전송전에만 가능합니다.
		 * - 발행취소된 형금영수증은 국세청에 전송되지 않습니다.
		 */
		
		// 문서관리번호
		String mgtKey = "20161206-01";
		
		// 메모
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
		/**
		 * 발행 안내메일을 재전송합니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";		
		
		// 수신자 메일주소
		String receiver = "test@test.com";	
		
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
		/**
		 * 알림문자를 전송합니다. (단문/SMS- 한글 최대 45자)
		 * - 알림문자 전송시 포인트가 차감됩니다. (전송실패시 환불처리)
		 * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [전송내역] 탭에서 전송결과를 확인할 수 있습니다.
		 */
		
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";				
		
		// 발신번호
		String sender = "07043042991";			
		
		// 수신번호
		String receiver = "010111222";			 
		
		// 문자 전송 내용 (90Byte 초과시 길이가 조정되어 전송)
		String contents = "현금영수증 문자메시지 전송 테스트입니다.";		
		
		try {
			
			Response response = cashbillService.sendSMS(testCorpNum, mgtKey, 
					sender, receiver, contents, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendFAX", method = RequestMethod.GET)
	public String sendFAX( Model m) {
		/**
		 * 현금영수증을 팩스전송합니다.
		 * - 팩스 전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
		 * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [팩스] > [전송내역]
		 *   메뉴에서 전송결과를 확인할 수 있습니다. 
		 */
		
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";				
		
		// 발신자 번호
		String sender = "07043042991";				
		
		// 수신자 팩스번호
		String receiver = "010111222";				
		
		try {
			
			Response response = cashbillService.sendFAX(testCorpNum, mgtKey, 
					sender, receiver, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getURL", method = RequestMethod.GET)
	public String getURL( Model m) {
		/**
		 * 팝빌 문서함 관련 팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		
		// TBOX : 임시문서함 , PBOX : 매출문서함, WRITE : 현금영수증 작성
		String TOGO = "WRITE"; 
		
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
		/**
		 * 1건의 현금영수증 보기 팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";			
		
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
		/**
		 * 1건의 현금영수증 인쇄팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";			
		
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
		/**
		 * 현금영수증 인쇄(공급받는자) URL을 반환합니다.
		 * - URL 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";
		
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
		/**
		 * 공급받는자 메일링크 URL을 반환합니다.
		 * - 메일링크 URL은 유효시간이 존재하지 않습니다.
		 */
		
		// 현금영수증 문서관리번호
		String mgtKey = "20161206-01";			
		
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
		/**
		 * 다수건의 현금영수증 인쇄팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		// 문서관리번호 배열, 최대 100건
		String[] mgtKeyList = new String[] {"20161206-01", "20150320-02", "20150320-03"}; 
		
		try {
			
			String url = cashbillService.getMassPrintURL(testCorpNum, mgtKeyList, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		/**
		 * 현금영수증 발행단가를 확인합니다.
		 */
		
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
		/**
		 * 1건의 현금영수증을 즉시발행합니다.
		 * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청
		 *   전송결과를 확인할 수 있습니다.
		 * - 현금영수증 국세청 전송 정책에 대한 정보는 "[현금영수증 API 연동매뉴얼]
		 *   > 1.4. 국세청 전송정책"을 참조하시기 바랍니다.
		 * - 취소현금영수증 작성방법 안내 - http://blog.linkhub.co.kr/702
		 */
		
		
		// 메모
		String Memo = "현금영수증 즉시발행 메모";

		// 현금영수증 정보 객체
		Cashbill cashbill = new Cashbill();
	
		// 문서관리번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성
		cashbill.setMgtKey("20161206-01");			
		
		// 현금영수증 형태, {승인거래, 취소거래} 중 기재
		cashbill.setTradeType("승인거래");				
		
		// 취소거래시 기재, 원본현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
		//cashbill.setOrgConfirmNum("");  			
				
		
		// 과세형태, {과세, 비과세} 중 기재
		cashbill.setTaxationType("과세");				
		
		// 거래처 식별번호, 거래유형에 따라 작성
	    // 소득공제용 - 주민등록/휴대폰/카드번호 기재가능
	    // 지출증빙용 - 사업자번호/주민등록/휴대폰/카드번호 기재가능
		cashbill.setIdentityNum("01011112222");
		
		// 거래유형, {소득공제용, 지출증빙용} 중 기재
		cashbill.setTradeUsage("소득공제용");

		// 공급가액, 숫자만 가능
		cashbill.setSupplyCost("10000");				
		
		// 세액, 숫자만 가능
		cashbill.setTax("1000");						
		
		// 봉사료, 숫자만 가능
		cashbill.setServiceFee("0");					
		
		// 합계금액, 숫자만 가능, 봉사료 + 공급가액 + 세액
		cashbill.setTotalAmount("11000");				

		
		// 발행자 사업자번호, '-'제외 10자리
		cashbill.setFranchiseCorpNum("1234567890");
		
		// 발행자 상호
		cashbill.setFranchiseCorpName("발행자 상호");	
		
		// 발행자 대표자명
		cashbill.setFranchiseCEOName("발행자 대표자");
		
		// 발행자 주소
		cashbill.setFranchiseAddr("발행자 주소");
		
		// 발행자 연락처
		cashbill.setFranchiseTEL("07043042991");	
		
		// 발행안내 문자 전송여부
		cashbill.setSmssendYN(false);					
	
		
		// 고객명
		cashbill.setCustomerName("고객명");
		
		// 상품명
		cashbill.setItemName("상품명");
		
		// 주문번호
		cashbill.setOrderNumber("주문번호");
		
		// 고객 메일주소
		cashbill.setEmail("test@test.com");
		
		// 고객 휴대폰 번호
		cashbill.setHp("010111222");
		
		// 고객 연락처
		cashbill.setFax("07043042991");
			
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
		/**
		 * 검색조건을 사용하여 현금영수증 목록을 조회합니다.
		 * - 응답항목에 대한 자세한 사항은 "[현금영수증 API 연동매뉴얼] >
		 *   4.2. 현금영수증 상태정보 구성" 을 참조하시기 바랍니다.
		 */
		
		// 일자유형, R-등록일자, T-거래일자, I-발행일자
		String DType = "R"; 								
		
		// 시작일자, 날짜형식(yyyyMMdd)
		String SDate = "20161001"; 							
		
		// 종료일자, 날짜형식(yyyyMMdd)
		String EDate = "20161231"; 							
		
		// 현금영수증 상태코드 배열, 2,3번째 자리에 와일드카드(*) 사용 가능
		String[] State = {"100", "2**", "3**", "4**"};		
		
		// 현금영수증 형태배열,  N-일반현금영수증, C-취소현금영수증
		String[] TradeType = {"N", "C"};					
		
		// 거래용도 배열, P-소득공제용, C-지출증빙용
		String[] TradeUsage = {"P", "C"};					
		
		// 과세형태 배열, T-과세, N-비과세
		String[] TaxationType = {"T", "N"};					
		
		// 식별번호 조회, 미기재시 전체조회
		String QString = "";								
		
		// 페이지 번호
		int Page = 1;										
		
		// 페이지당 목록개수, 최대 1000건
		int PerPage = 20;									
		
		// 정렬방향, A-오름차순,  D-내림차순
		String Order = "D";									 
		
		
		try {
			
			CBSearchResult searchResult = cashbillService.search(testCorpNum, 
					DType, SDate, EDate, State, TradeType, TradeUsage, TaxationType, 
					QString, Page, PerPage, Order);
			
			m.addAttribute("SearchResult", searchResult);
			
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Cashbill/SearchResult";
	}
}
