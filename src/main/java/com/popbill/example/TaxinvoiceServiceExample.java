/*
 * 팝빌 전자세금계산서 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : http://blog.linkhub.co.kr/591/
 * - 업데이트 일자 : 2017-12-07
 * - 연동 기술지원 연락처 : 1600-9854 / 070-4304-2991~2
 * - 연동 기술지원 이메일 : code@linkhub.co.kr
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 * 	  util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 링크허브 가입시 메일로 
 *    발급받은 인증정보를 참조하여 변경합니다.
 * 2) 팝빌 개발용 사이트(test.popbill.com)에 연동회원으로 가입합니다.
 * 3) 전자세금계산서 발행을 위해 공인인증서를 등록합니다.
 *    - 팝빌사이트 로그인 > [전자세금계산서] > [환경설정] > [공인인증서 관리]
 *    - 공인인증서 등록 팝업 URL (GetPopbillURL API)을 이용하여 등록
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.AttachedFile;
import com.popbill.api.ChargeInfo;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.TaxinvoiceService;
import com.popbill.api.taxinvoice.EmailPublicKey;
import com.popbill.api.taxinvoice.MgtKeyType;
import com.popbill.api.taxinvoice.TISearchResult;
import com.popbill.api.taxinvoice.Taxinvoice;
import com.popbill.api.taxinvoice.TaxinvoiceAddContact;
import com.popbill.api.taxinvoice.TaxinvoiceDetail;
import com.popbill.api.taxinvoice.TaxinvoiceInfo;
import com.popbill.api.taxinvoice.TaxinvoiceLog;

/**
 * 팝빌 전자세금계산서 API 예제.
 */
@Controller
@RequestMapping("TaxinvoiceService")
public class TaxinvoiceServiceExample {
	
	
	@Autowired
	private TaxinvoiceService taxinvoiceService;
	
	// 팝빌회원 사업자번호
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	
	// 팝빌회원 아이디
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Taxinvoice/index";
	}
	
	@RequestMapping(value = "checkMgtKeyInUse", method = RequestMethod.GET)
	public String checkMgtKeyInUse( Model m) {
		/**
		 * 세금계산서 관리번호 중복여부를 확인합니다.
		 * - 관리번호는 1~24자리로 숫자, 영문 '-', '_' 조합으로 구성할 수 있습니다.
		 */
		
		// 세금계산서 유형 SELL-매출, BUY-매입, TRUSTEE-위수탁 
		MgtKeyType keyType = MgtKeyType.SELL;
		
		// 조회할 문서관리번호
		String mgtKey = "20161201-01";
		
		String isUseStr;
		
		try {
			
			boolean IsUse = taxinvoiceService.checkMgtKeyInUse(testCorpNum, 
					keyType, mgtKey);
			
			isUseStr = (IsUse) ?  "사용중" : "미사용중";
			
			m.addAttribute("Result", isUseStr);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register( Model m) {
		/**
		 * ' 1건의 세금계산서를 임시저장 합니다.
		 * - 세금계산서 임시저장(Register API) 호출후에는 발행(Issue API)을 호출해야만
		 *   국세청으로 전송됩니다.
		 * - 임시저장과 발행을 한번의 호출로 처리하는 즉시발행(RegistIssue API) 프로세스
		 *   연동을 권장합니다.
		 * - 세금계산서 항목별 정보는 "[전자세금계산서 API 연동매뉴얼] > 4.1. (세금)계산서
		 *   구성"을 참조하시기 바랍니다.
		*/
		
		
		// 세금계산서 정보 객체
		Taxinvoice taxinvoice = new Taxinvoice();

		// 작성일자, 날짜형식(yyyyMMdd)
		taxinvoice.setWriteDate("20170306"); 
		
		// 과금방향, [정과금, 역과금] 중 선택기재, 역과금의 경우 역발행세금계산서 발행시에만 가
		taxinvoice.setChargeDirection("정과금");
		
		// 발행유형, [정발행, 역발행, 위수탁] 중 기재
		taxinvoice.setIssueType("정발행"); 
		
		// [영수, 청구] 중 기재
		taxinvoice.setPurposeType("영수"); 
		
		// 발행시점, [직접발행, 승인시자동발행] 중 기재
		taxinvoice.setIssueTiming("직접발행"); 
		
		// 과세형태, [과세, 영세, 면세] 중 기재
		taxinvoice.setTaxType("과세"); 
		
		
		
		/**********************************************************************
		 *								공급자 정보	 
		 *********************************************************************/
		
		// 공급자 사업자번호
		taxinvoice.setInvoicerCorpNum(testCorpNum);
		
		// 공급자 종사업장 식별번호, 필요시 기재. 형식은 숫자 4자리.
		taxinvoice.setInvoicerTaxRegID(""); 
		
		// 공급자 상호
		taxinvoice.setInvoicerCorpName("공급자 상호");  
		
		// 공급자 문서관리번호, 1~24자리 (숫자, 영문, '-', '_') 조합으로 사업자 별로 중복되지 않도록 구성
		taxinvoice.setInvoicerMgtKey("20170306-11");
		
		// 공급자 대표자성명
		taxinvoice.setInvoicerCEOName("공급자 대표자 성명");
		
		// 공급자 주소 
		taxinvoice.setInvoicerAddr("공급자 주소");
		
		// 공급자 종목
		taxinvoice.setInvoicerBizClass("공급자 업종");
		
		// 공급자 업태
		taxinvoice.setInvoicerBizType("공급자 업태,업태2");
		
		// 공급자 담당자 성명
		taxinvoice.setInvoicerContactName("공급자 담당자명");
		
		// 공급자 담당자 메일주소
		taxinvoice.setInvoicerEmail("test@test.com");
		
		// 공급자 담당자 연락처
		taxinvoice.setInvoicerTEL("070-7070-0707");
		
		// 공급자 담당자 휴대폰번호
		taxinvoice.setInvoicerHP("010-000-2222");
		
		// 발행 안내문자메시지 전송여부
		// - 전송시 포인트 차감되며, 전송실패시 환불처리
		taxinvoice.setInvoicerSMSSendYN(false);

		
		
		
		/**********************************************************************
		 *							공급받는자 정보	 
		 *********************************************************************/
				
		// 공급받는자 구분, [사업자, 개인, 외국인] 중 기재
		taxinvoice.setInvoiceeType("사업자");	
		
		// 공급받는자 사업자번호, '-' 제외 10자리
		taxinvoice.setInvoiceeCorpNum("8888888888"); 
		
		// 공급받는자 상호 
		taxinvoice.setInvoiceeCorpName("공급받는자 상호");
		
		// [역발행시 필수] 공급받는자 문서관리번호, 1~24자리까지 사업자번호별 중복없는 고유번호 할당
		taxinvoice.setInvoiceeMgtKey("");
		
		// 공급받는자 대표자 성명
		taxinvoice.setInvoiceeCEOName("공급받는자 대표자 성명");
		
		// 공급받는자 주소
		taxinvoice.setInvoiceeAddr("공급받는자 주소");
		
		// 공급받는자 종목
		taxinvoice.setInvoiceeBizClass("공급받는자 업종");
		
		// 공급받는자 업태
		taxinvoice.setInvoiceeBizType("공급받는자 업태");
		
		// 공급받는자 담당자명
		taxinvoice.setInvoiceeContactName1("공급받는자 담당자명");
		
		// 공급받는자 담당자 메일주소 
		taxinvoice.setInvoiceeEmail1("test@invoicee.com");
		
		// 공급받는자 담당자 연락처
		taxinvoice.setInvoiceeTEL1("070-111-222");
		
		// 공급받는자 담당자 휴대폰번호
		taxinvoice.setInvoiceeHP1("010-111-222");
		
		// 역발행시 안내문자메시지 전송여부
		// - 전송시 포인트 차감되며, 전송실패시 환불처리
		taxinvoice.setInvoiceeSMSSendYN(false);

		
		
		/**********************************************************************
		 *							세금계산서 기재정보	 
		 *********************************************************************/
		
		// [필수] 공급가액 합계 
		taxinvoice.setSupplyCostTotal("100000");
		
		// [필수] 세액 합계
		taxinvoice.setTaxTotal("10000");
		
		// [필수] 합계금액, 공급가액 + 세액
		taxinvoice.setTotalAmount("110000");
			
		// 기재 상 일련번호
		taxinvoice.setSerialNum("123"); 
		
		// 기재 상 현금
		taxinvoice.setCash(""); 
		
		// 기재 상 수표 
		taxinvoice.setChkBill(""); 
		
		// 기재 상 어움
		taxinvoice.setNote(""); 
		
		// 기재 상 외상미수금
		taxinvoice.setCredit(""); 
		
		// 기재 상 비고
		taxinvoice.setRemark1("비고1");
		taxinvoice.setRemark2("비고2");
		taxinvoice.setRemark3("비고3");
		taxinvoice.setKwon((short) 1);
		taxinvoice.setHo((short) 1);

		// 사업자등록증 이미지 첨부여부
		taxinvoice.setBusinessLicenseYN(false); 
		
		// 통장사본 이미지 첨부여부
		taxinvoice.setBankBookYN(false); 
		
		
		
		/**********************************************************************
		 *				수정세금계산서 정보 (수정세금계산서 작성시 기재)
    	 * - 수정세금계산서 관련 정보는 연동매뉴얼 또는 개발가이드 링크 참조
    	 & - [참고] 수정세금계산서 작성방법 안내 [http://blog.linkhub.co.kr/650]	 
		 *********************************************************************/
		
		// 수정사유코드, 수정사우에 따라 1~6 중 선택기재.
		taxinvoice.setModifyCode(null); 
		
		// 원본세금계산서 ItemKey, 문서확인 (GetInfo API)의 응답결과 항목 중 ItemKey 확인
		taxinvoice.setOriginalTaxinvoiceKey(""); 
		
		
		
		/**********************************************************************
		 *							상세항목(품목) 정보
    	 *********************************************************************/
		
		taxinvoice.setDetailList(new ArrayList<TaxinvoiceDetail>());

		// 상세항목 객체
		TaxinvoiceDetail detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 1); // 일련번호, 1부터 순차기재
		detail.setPurchaseDT("20161206"); // 거래일자
		detail.setItemName("품목명");
		detail.setSpec("규격");
		detail.setQty("1"); // 수량
		detail.setUnitCost("50000"); // 단가
		detail.setSupplyCost("50000"); // 공급가액
		detail.setTax("5000"); // 세액
		detail.setRemark("품목비고");

		taxinvoice.getDetailList().add(detail);

		detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 2); // 일련번호, 1부터 순차기재
		detail.setPurchaseDT("20161206"); // 거래일자
		detail.setItemName("품목명2");
		detail.setSpec("규격");
		detail.setQty("1"); // 수량
		detail.setUnitCost("50000"); // 단가
		detail.setSupplyCost("50000"); // 공급가액
		detail.setTax("5000"); // 세액
		detail.setRemark("품목비고2");
		
		taxinvoice.getDetailList().add(detail);
		
		
		
		/**********************************************************************
		 *							추가담당자 정보
    	 *********************************************************************/
		
		taxinvoice.setAddContactList(new ArrayList<TaxinvoiceAddContact>());
		
		TaxinvoiceAddContact addContact = new TaxinvoiceAddContact();
		
		addContact.setSerialNum(1);
		addContact.setContactName("추가 담당자명");
		addContact.setEmail("test2@test.com");
		
		taxinvoice.getAddContactList().add(addContact);
		
		try {
			
			Response response = taxinvoiceService.register(testCorpNum, taxinvoice);
			
			m.addAttribute("Response", response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update( Model m) {
		/**
		 * [임시저장] 상태의 세금계산서의 항목을 수정합니다.
		 * - 세금계산서 항목별 정보는 "[전자세금계산서 API 연동매뉴얼] > 4.1. (세금)계산서 구성"을 
		 *   참조하시기 바랍니다.
		 */
		
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-01";
		
				
		// 세금계산서 정보 객체
		Taxinvoice taxinvoice = new Taxinvoice();

		// 작성일자, 날짜형식(yyyyMMdd)
		taxinvoice.setWriteDate("20161206"); 
		
		// 과금방향, [정과금, 역과금] 중 선택기재, 역과금의 경우 역발행세금계산서 발행시에만 가
		taxinvoice.setChargeDirection("정과금");
		
		// 발행유형, [정발행, 역발행, 위수탁] 중 기재
		taxinvoice.setIssueType("정발행"); 
		
		// [영수, 청구] 중 기재
		taxinvoice.setPurposeType("영수"); 
		
		// 발행시점, [직접발행, 승인시자동발행] 중 기재
		taxinvoice.setIssueTiming("직접발행"); 
		
		// 과세형태, [과세, 영세, 면세] 중 기재
		taxinvoice.setTaxType("과세"); 
		
		
		
		/**********************************************************************
		 *								공급자 정보	 
		 *********************************************************************/
		
		// 공급자 사업자번호
		taxinvoice.setInvoicerCorpNum(testCorpNum);
		
		// 공급자 종사업장 식별번호, 필요시 기재. 형식은 숫자 4자리.
		taxinvoice.setInvoicerTaxRegID(""); 
		
		// 공급자 상호
		taxinvoice.setInvoicerCorpName("공급자 상호");  
		
		// 공급자 문서관리번호, 1~24자리 (숫자, 영문, '-', '_') 조합으로 사업자 별로 중복되지 않도록 구성
		taxinvoice.setInvoicerMgtKey("20161206-01");
		
		// 공급자 대표자성명
		taxinvoice.setInvoicerCEOName("공급자 대표자 성명_수정");
		
		// 공급자 주소 
		taxinvoice.setInvoicerAddr("공급자 주소_수정");
		
		// 공급자 종목
		taxinvoice.setInvoicerBizClass("공급자 업종");
		
		// 공급자 업태
		taxinvoice.setInvoicerBizType("공급자 업태,업태2");
		
		// 공급자 담당자 성명
		taxinvoice.setInvoicerContactName("공급자 담당자명");
		
		// 공급자 담당자 메일주소
		taxinvoice.setInvoicerEmail("test@test.com");
		
		// 공급자 담당자 연락처
		taxinvoice.setInvoicerTEL("070-7070-0707");
		
		// 공급자 담당자 휴대폰번호
		taxinvoice.setInvoicerHP("010-000-2222");
		
		// 발행 안내문자메시지 전송여부
		// - 전송시 포인트 차감되며, 전송실패시 환불처리
		taxinvoice.setInvoicerSMSSendYN(false);

		
		
		
		/**********************************************************************
		 *							공급받는자 정보	 
		 *********************************************************************/
				
		// 공급받는자 구분, [사업자, 개인, 외국인] 중 기재
		taxinvoice.setInvoiceeType("사업자");	
		
		// 공급받는자 사업자번호, '-' 제외 10자리
		taxinvoice.setInvoiceeCorpNum("8888888888"); 
		
		// 공급받는자 상호 
		taxinvoice.setInvoiceeCorpName("공급받는자 상호");
		
		// [역발행시 필수] 공급받는자 문서관리번호, 1~24자리까지 사업자번호별 중복없는 고유번호 할당
		taxinvoice.setInvoiceeMgtKey("");
		
		// 공급받는자 대표자 성명
		taxinvoice.setInvoiceeCEOName("공급받는자 대표자 성명");
		
		// 공급받는자 주소
		taxinvoice.setInvoiceeAddr("공급받는자 주소");
		
		// 공급받는자 종목
		taxinvoice.setInvoiceeBizClass("공급받는자 업종");
		
		// 공급받는자 업태
		taxinvoice.setInvoiceeBizType("공급받는자 업태");
		
		// 공급받는자 담당자명
		taxinvoice.setInvoiceeContactName1("공급받는자 담당자명");
		
		// 공급받는자 담당자 메일주소 
		taxinvoice.setInvoiceeEmail1("test@invoicee.com");
		
		// 공급받는자 담당자 연락처
		taxinvoice.setInvoiceeTEL1("070-111-222");
		
		// 공급받는자 담당자 휴대폰번호
		taxinvoice.setInvoiceeHP1("010-111-222");
		
		// 역발행시 안내문자메시지 전송여부
		// - 전송시 포인트 차감되며, 전송실패시 환불처리
		taxinvoice.setInvoiceeSMSSendYN(false);

		
		
		/**********************************************************************
		 *							세금계산서 기재정보	 
		 *********************************************************************/
		
		// [필수] 공급가액 합계 
		taxinvoice.setSupplyCostTotal("100000");
		
		// [필수] 세액 합계
		taxinvoice.setTaxTotal("10000");
		
		// [필수] 합계금액, 공급가액 + 세액
		taxinvoice.setTotalAmount("110000");
			
		// 기재 상 일련번호
		taxinvoice.setSerialNum("123"); 
		
		// 기재 상 현금
		taxinvoice.setCash(""); 
		
		// 기재 상 수표 
		taxinvoice.setChkBill(""); 
		
		// 기재 상 어움
		taxinvoice.setNote(""); 
		
		// 기재 상 외상미수금
		taxinvoice.setCredit(""); 
		
		// 기재 상 비고
		taxinvoice.setRemark1("비고1");
		taxinvoice.setRemark2("비고2");
		taxinvoice.setRemark3("비고3");
		taxinvoice.setKwon((short) 1);
		taxinvoice.setHo((short) 1);

		// 사업자등록증 이미지 첨부여부
		taxinvoice.setBusinessLicenseYN(false); 
		
		// 통장사본 이미지 첨부여부
		taxinvoice.setBankBookYN(false); 
		
		
		
		/**********************************************************************
		 *				수정세금계산서 정보 (수정세금계산서 작성시 기재)
    	 * - 수정세금계산서 관련 정보는 연동매뉴얼 또는 개발가이드 링크 참조
    	 * - [참고] 수정세금계산서 작성방법 안내 [http://blog.linkhub.co.kr/650]	 
		 *********************************************************************/
		
		// 수정사유코드, 수정사유에 따라 1~6 중 선택기재.
		taxinvoice.setModifyCode(null); 
		
		// 원본세금계산서 ItemKey, 문서확인 (GetInfo API)의 응답결과 항목 중 ItemKey 확인
		taxinvoice.setOriginalTaxinvoiceKey(""); 
		
		
		
		/**********************************************************************
		 *							상세항목(품목) 정보
    	 *********************************************************************/
		
		taxinvoice.setDetailList(new ArrayList<TaxinvoiceDetail>());

		// 상세항목 객체
		TaxinvoiceDetail detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 1); // 일련번호, 1부터 순차기재 
		detail.setPurchaseDT("20161201"); // 거래일자
		detail.setItemName("품목명");	// 품목
		detail.setSpec("규격");	// 규격
		detail.setQty("1"); // 수량
		detail.setUnitCost("50000"); // 단가
		detail.setSupplyCost("50000"); // 공급가액
		detail.setTax("5000"); // 세액
		detail.setRemark("품목비고");

		taxinvoice.getDetailList().add(detail);

		detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 2); // 일련번호, 1부터 순차기재
		detail.setPurchaseDT("20161201"); // 거래일자
		detail.setItemName("품목명2");	// 품목명
		detail.setSpec("규격");
		detail.setQty("1"); // 수량
		detail.setUnitCost("50000"); // 단가
		detail.setSupplyCost("50000"); // 공급가액
		detail.setTax("5000"); // 세액
		detail.setRemark("품목비고2");
		
		taxinvoice.getDetailList().add(detail);
		
		
		
		/**********************************************************************
		 *							추가담당자 정보
    	 *********************************************************************/
		
		taxinvoice.setAddContactList(new ArrayList<TaxinvoiceAddContact>());
		
		TaxinvoiceAddContact addContact = new TaxinvoiceAddContact();
		
		addContact.setSerialNum(1);
		addContact.setContactName("추가 담당자명");
		addContact.setEmail("test2@test.com");
		
		taxinvoice.getAddContactList().add(addContact);
		
		try {
			
			Response response = taxinvoiceService.update(testCorpNum, mgtKeyType,
				mgtKey, taxinvoice);
			
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
		 * 1건의 세금계산서 상태/요약 정보를 확인합니다.
		 * - 세금계산서 상태정보(GetInfo API) 응답항목에 대한 자세한 정보는 "[전자세금계산서 API
		 *  연동매뉴얼] > 4.2. (세금)계산서 상태정보 구성" 을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161221-03";
		
		try {
			
			TaxinvoiceInfo taxinvoiceInfo = taxinvoiceService.getInfo(testCorpNum, 
				mgtKeyType, mgtKey);
			
			m.addAttribute("TaxinvoiceInfo",taxinvoiceInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/TaxinvoiceInfo";
	}
	
	@RequestMapping(value = "getInfos", method = RequestMethod.GET)
	public String getInfos( Model m) {
		/**
		 * 다량의 세금계산서 상태/요약 정보를 확인합니다. (최대 1000건)
		 * - 세금계산서 상태정보(GetInfos API) 응답항목에 대한 자세한 정보는
		 *  "[전자세금계산서 API 연동매뉴얼] > 4.2. (세금)계산서 상태정보 구성" 을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		
		// 세금계산서 관리번호 배열, 최대 1000건
		String[] MgtKeyList = new String[] {"20161221-03","12345","123456"};
		
		try {
			
			TaxinvoiceInfo[] taxinvoiceInfos = taxinvoiceService.getInfos(testCorpNum, 
					mgtKeyType ,MgtKeyList);
			
			m.addAttribute("TaxinvoiceInfos",taxinvoiceInfos);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/TaxinvoiceInfo";
	}
	
	@RequestMapping(value = "getDetailInfo", method = RequestMethod.GET)
	public String getDetailInfo( Model m) {
		/**
		 * 1건의 세금계산서 상세항목을 확인합니다.
		 * - 응답항목에 대한 자세한 사항은 "[전자세금계산서 API 연동매뉴얼]
		 *   > 4.1 (세금)계산서 구성" 을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161221-03";
		
		try {
			
			Taxinvoice taxinvoice = taxinvoiceService.getDetailInfo(testCorpNum, 
					mgtKeyType, mgtKey);
			
			m.addAttribute("Taxinvoice",taxinvoice);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/Taxinvoice";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete( Model m) {
		
		/**
		 * 1건의 전자세금계산서를 삭제합니다.
		 *  - 세금계산서를 삭제해야만 문서관리번호(mgtKey)를 재사용할 수 있습니다.
		 *  - 삭제가능한 문서 상태 : [임시저장], [발행취소], [발행예정 취소], [발행예정 거부]
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161205-01";
		
		try {
			
			Response response = taxinvoiceService.delete(testCorpNum, mgtKeyType, mgtKey);
			
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
		 * 세금계산서 상태 변경이력을 확인합니다.
		 * - 상태 변경이력 확인(GetLogs API) 응답항목에 대한 자세한 정보는
		 *   "[전자세금계산서 API 연동매뉴얼] > 3.6.4 상태 변경이력 확인" 을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-01";
		
		try {
			
			TaxinvoiceLog[] taxinvoiceLogs = taxinvoiceService.getLogs(testCorpNum, 
					mgtKeyType, mgtKey);
			
			m.addAttribute("TaxinvoiceLogs",taxinvoiceLogs);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/TaxinvoiceLog";
	}
	

	
	@RequestMapping(value = "attachFile", method = RequestMethod.GET)
	public String attachFile( Model m) {
		/**
		 * 세금계산서에 첨부파일을 등록합니다.
		 * - [임시저장] 상태의 세금계산서만 파일을 첨부할수 있습니다.
		 * - 첨부파일은 최대 5개까지 등록할 수 있습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-01";
		
		
		// 첨부파일 표시명
		String displayName = "첨부파일.jpg";
		
		//첨부할 파일의 InputStream. 예제는 resource에 테스트파일을 참조함.
		//FileInputStream으로 처리하는 것을 권함.
		InputStream stream = getClass().getClassLoader().getResourceAsStream("test.jpg");
		
				
		try {
			
			Response response = taxinvoiceService.attachFile(testCorpNum, mgtKeyType,
					mgtKey, displayName, stream);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		} finally {
			if ( stream != null ){
				try {
					stream.close();
				} catch (IOException e) {}
			}
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getFiles", method = RequestMethod.GET)
	public String getFiles( Model m) {
		/**
		 * 세금계산서에 첨부된 파일의 목록을 확인합니다.
		 * - 응답항목 중 파일아이디(AttachedFile) 항목은 파일삭제(DeleteFile API)
		 *   호출시 이용할 수 있습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
				
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-01";
		
		
		try {
			
			AttachedFile[] attachedFiles = taxinvoiceService.getFiles(testCorpNum,
					mgtKeyType, mgtKey);
			
			m.addAttribute("AttachedFiles",attachedFiles);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/AttachedFile";
	}
	
	
	@RequestMapping(value = "deleteFile", method = RequestMethod.GET)
	public String deleteFile( Model m) {
		/**
		 * 세금계산서에 첨부된 파일을 삭제합니다.
		 * - 파일을 식별하는 파일아이디는 첨부파일 목록(GetFileList API) 의 응답항목
		 *   중 파일아이디(AttachedFile) 값을 통해 확인할 수 있습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161201-01";
				
				
		// 파일아이디, getFiles()로 확인한 AttachedFile의 attachedFile 참조.
		String FileID = " 418DD6F7-5358-46A8-B430-04F79CC3D9DA.PBF"; 
		
		
		try {
			Response response = taxinvoiceService.deleteFile(testCorpNum, mgtKeyType,
					mgtKey,FileID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	
	@RequestMapping(value = "send", method = RequestMethod.GET)
	public String send( Model m) {
		/**
		 * 1건의 [임시저장] 상태의 세금계산서를 [발행예정] 처리합니다.
		 * - 발행예정이란 공급자와 공급받는자 사이에 세금계산서 확인 후 발행하는 방법입니다.
		 * - "[전자세금계산서 API 연동매뉴얼] > 1.3.1. 정발행 프로세스 흐름도
		 *   > 다. 임시저장 발행예정" 의 프로세스를 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20170306-11";
				
		// 메모
		String memo = "발행예정 메모";
		
		// 발행예정 제목, 공백으로 처리시 기본제목으로 전송
		String emailSubject = "";
		
		try {
			
			Response response = taxinvoiceService.send(testCorpNum, mgtKeyType, 
					mgtKey, memo, emailSubject);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "cancelSend", method = RequestMethod.GET)
	public String cancelSend( Model m) {
		/**
		 * 발행예정 세금계산서를 [취소] 처리 합니다.
		 * - [취소]된 세금계산서를 삭제(Delete API)하면 등록된 문서관리번호를 재사용할 수 있습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
				
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
				
		// 메모
		String memo = "발행예정 취소 메모";
		
		try {
			
			Response response = taxinvoiceService.cancelSend(testCorpNum, mgtKeyType,
					mgtKey, memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "accept", method = RequestMethod.GET)
	public String accept( Model m) {
		/**
		 * 발행예정 세금계산서를 [승인] 처리합니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
		
		// 메모
		String memo = "발행예정 승인 메모";
				
		try {
			
			Response response = taxinvoiceService.accept(testCorpNum, mgtKeyType, 
					mgtKey, memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "deny", method = RequestMethod.GET)
	public String deny( Model m) {
		/**
		 * 발행예정 세금계산서를 [거부]처리 합니다.
		 * - [거부]처리된 세금계산서를 삭제(Delete API)하면 등록된 문서관리번호를
		 *   재사용할 수 있습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
				
		// 메모
		String memo = "발행예정 거부 메모";
				
		try {
			
			Response response = taxinvoiceService.deny(testCorpNum, mgtKeyType, 
					mgtKey, memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "issue", method = RequestMethod.GET)
	public String issue( Model m) {
		/**
		 * [발행완료] 상태의 세금계산서를 [발행취소] 처리합니다.
		 * - [발행취소]는 국세청 전송전에만 가능합니다.
		 * - 발행취소된 세금계산서는 국세청에 전송되지 않습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
			
		// 메모
		String memo = "발행 메모";
		
		// 지연발행 강제여부
		Boolean forceIssue = false;
				
		try {
			
			Response response = taxinvoiceService.issue(testCorpNum, mgtKeyType, 
					mgtKey, memo, forceIssue, testUserID);
			
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
		 * [발행완료] 상태의 세금계산서를 [발행취소] 처리합니다.
		 * - [발행취소]는 국세청 전송전에만 가능합니다.
		 * - 발행취소된 세금계산서는 국세청에 전송되지 않습니다.
		 * - 발행취소 세금계산서에 기재된 문서관리번호를 재사용 하기 위해서는 삭제(Delete API)를 
		 *   호출하여 [삭제] 처리 하셔야 합니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
		
		// 메모
		String memo = "발행취소 메모";
				
		try {
			
			Response response = taxinvoiceService.cancelIssue(testCorpNum, mgtKeyType, 
					mgtKey, memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "request", method = RequestMethod.GET)
	public String request( Model m) {
		/**
		 * 공급받는자가 공급자에게 1건의 역발행 세금계산서를 요청합니다.
		 * - 역발행 세금계산서 프로세스를 구현하기 위해서는 공급자/공급받는자가 모두 팝빌회원이여야 합니다.
		 * - 역발행 요청후 공급자가 [발행] 처리시 포인트가 차감되며 역발행 세금계산서 항목중 
		 *   과금방향(ChargeDirection) 에 기재한 값에 따라 정과금(공급자과금) 또는 
		 *   역과금(공급받는자과금) 처리됩니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
				
		// 메모
		String memo = "역발행 요청 메모";
				
		try {
			
			Response response = taxinvoiceService.request(testCorpNum, mgtKeyType, 
					mgtKey, memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "cancelRequest", method = RequestMethod.GET)
	public String cancelRequest( Model m) {
		/**
		 * 역발행 세금계산서를 [취소] 처리합니다.
		 * - [취소]한 세금계산서의 문서관리번호를 재사용하기 위해서는 삭제 (Delete API)
		 *   를 호출해야 합니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
				
		// 메모
		String memo = "역발행 취소 메모";
				
				
		try {
			
			Response response = taxinvoiceService.cancelRequest(testCorpNum, mgtKeyType,
					mgtKey, memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "refuse", method = RequestMethod.GET)
	public String refuse( Model m) {
		/**
		 * 공급받는자에게 요청받은 역발행 세금계산서를 [거부]처리 합니다.
		 * - 세금계산서의 문서관리번호를 재사용하기 위해서는 삭제 (Delete API) 를 호출하여 
		 *   [삭제] 처리해야 합니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
		
		// 메모
		String memo = "역발행 거부 메모";
				
				
		try {
			
			Response response = taxinvoiceService.refuse(testCorpNum, mgtKeyType,
					mgtKey, memo);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendToNTS", method = RequestMethod.GET)
	public String sendToNTS( Model m) {
		/**
		 * [발행완료] 상태의 세금계산서를 국세청으로 즉시전송합니다.
		 * - 국세청 즉시전송을 호출하지 않은 세금계산서는 발행일 기준 익일 오후 3시에 팝빌 시스템에서 
		 *   일괄적으로 국세청으로 전송합니다.
		 * - 익일전송시 전송일이 법정공휴일인 경우 다음 영업일에 전송됩니다.
		 * - 국세청 전송에 관한 사항은 "[전자세금계산서 API 연동매뉴얼] > 1.4 국세청 전송 정책" 
		 *   을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
				
		try {
			
			Response response = taxinvoiceService.sendToNTS(testCorpNum, mgtKeyType, mgtKey);
			
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
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
		
		// 수신메일주소
		String receiverMail = "test@test.com";
				
				
		try {
			
			Response response = taxinvoiceService.sendEmail(testCorpNum, mgtKeyType, 
					mgtKey, receiverMail);
			
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
		 * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [전송내역] 탭에서 전송결과를 확인할 수
		 *   있습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
		
		// 발신번호
		String senderNum = "07043042991";
		
		// 수신번호
		String receiverNum = "010111222";
		
		// 메시지 내용, 90byte 초과시 길이가 조정되어 전송됨
		String contents = "문자 메시지 내용입니다. 세금계산서가 발행되었습니다.";
				
		try {
			
			Response response = taxinvoiceService.sendSMS(testCorpNum,mgtKeyType, 
					mgtKey, senderNum, receiverNum, contents);
			
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
		 * 전자세금계산서를 팩스전송합니다.
		 * - 팩스 전송 요청시 포인트가 차감됩니다. (전송실패시 환불처리)
		 * - 전송내역 확인은 "팝빌 로그인" > [문자 팩스] > [팩스] > [전송내역]
		 *   메뉴에서 전송결과를 확인할 수 있습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
				
		// 세금계산서 문서관리번호
		String mgtKey = "20161206-02";
				
		// 발신번호
		String senderNum = "07043042991";
		
		// 수신팩스번호
		String receiverNum = "070111222";
				
		try {
			
			Response response = taxinvoiceService.sendFAX(testCorpNum, mgtKeyType,
					mgtKey, senderNum, receiverNum);
			
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
		 * 팝빌 전자세금계산서 관련 문서함 팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		// TBOX : 임시문서함 , SBOX : 매출문서함 , PBOX : 매입문서함 , WRITE : 매출작성
		String TOGO = "SBOX"; 
		
		try {
			
			String url = taxinvoiceService.getURL(testCorpNum, TOGO);
			
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
		 * 1건의 전자세금계산서 보기 팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20170306-11";
				
		try {
			
			String url = taxinvoiceService.getPopUpURL(testCorpNum, mgtKeyType, 
					mgtKey);
			
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
		 * 1건의 전자세금계산서 인쇄 팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20170306-11";
				
		try {
			
			String url = taxinvoiceService.getPrintURL(testCorpNum, mgtKeyType, 
					mgtKey);
			
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
		 * 세금계산서 인쇄(공급받는자) URL을 반환합니다.
		 * - URL 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20170306-11";
				
		try {
			
			String url = taxinvoiceService.getEPrintURL(testCorpNum, mgtKeyType, 
					mgtKey);
			
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
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 문서관리번호
		String mgtKey = "20170306-11";
				
				
		try {
			
			String url = taxinvoiceService.getMailURL(testCorpNum, mgtKeyType, 
					mgtKey);
			
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
		 * 다수건의 전자세금계산서 인쇄팝업 URL을 반환합니다.
		 * - 보안정책으로 인해 반환된 URL의 유효시간은 30초입니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 문서관리번호 배열, 최대 100건
		String[] MgtKeyList = new String[] {"20170306-11","12345","123456"};
		
		try {
			
			String url = taxinvoiceService.getMassPrintURL(testCorpNum ,mgtKeyType, 
					MgtKeyList);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
	public String chargeInfo( Model m) {
		/**
		 * 연동회원의 전자세금계산서 API 서비스 과금정보를 확인합니다.
		 */
		
		try {
			
			ChargeInfo chrgInfo = taxinvoiceService.getChargeInfo(testCorpNum);
			
			m.addAttribute("ChargeInfo",chrgInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "getChargeInfo";
	}	
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		/**
		 * 전자세금계산서 발행단가를 확인합니다.
		 */
		
		try {
			
			float unitCost = taxinvoiceService.getUnitCost(testCorpNum);
			
			m.addAttribute("Result",unitCost);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getCertificateExpireDate", method = RequestMethod.GET)
	public String getCertificateExpireDate( Model m) {
		/**
		 * 팝빌에 등록되어 있는 공인인증서의 만료일자를 확인합니다.
		 * - 공인인증서가 갱신/재발급/비밀번호 변경이 되는 경우 해당 인증서를
		 *   재등록 하셔야 정상적으로 API를 이용하실 수 있습니다.
		 */
		
		try {
			
			Date expireDate = taxinvoiceService.getCertificateExpireDate(testCorpNum);
			
			m.addAttribute("Result",expireDate);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	

	@RequestMapping(value = "getEmailPublicKeys", method = RequestMethod.GET)
	public String getEmailPublicKeys( Model m) {
		/**
		 * 대용량 연계사업자 메일주소 목록을 반환합니다.
		 */
		
		try {
			
			EmailPublicKey[] emailPublicKeys = taxinvoiceService.getEmailPublicKeys(testCorpNum);
			
			m.addAttribute("EmailPublicKeys",emailPublicKeys);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/EmailPublicKey";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Model m){
		/**
		 * 검색조건을 사용하여 세금계산서 목록을 조회합니다.
		 * - 응답항목에 대한 자세한 사항은 "[전자세금계산서 API 연동매뉴얼] >
		 *   4.2. (세금)계산서 상태정보 구성" 을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
				
				
		// 일자유형, R-등록일자, W-작성일자, I-발행일자
		String DType = "W"; 					
		
		// 시작일자, 날짜형식(yyyyMMdd)
		String SDate = "20171101"; 				
		
		// 종료일자, 날짜형식(yyyyMMdd)
		String EDate = "20171231"; 				
		
		// 세금계산서 상태코드 배열, 2,3번째 자리에 와일드카드(*) 사용 가능
		String[] State = {"3**", "6**"};		
		
		// 문서유형 배열 N-일반세금계산서, M-수정세금계산서
		String[] Type = {"N", "M"}; 			
		
		// 과세형태 배열, T-과세, N-면세, Z-영세
		String[] TaxType = {"T", "N", "Z"}; 	
		
		// 발행형태 배열, N-정발행, R-역발행, T-위수탁
		String[] IssueType = {"N", "R", "T"};
		
		// 지연발행 여부, null 전체조회, true - 지연발행, false- 정상발행
		Boolean LateOnly = null; 				
		
		// 종사업장번호 유형, S-공급자, B-공급받는자, T-수탁자
		String TaxRegIDType = "";				
		
		// 종사업장번호 배열
		String TaxRegID = "";					
		
		// 종사업장번호 조회 유무
		String TaxRegIDYN = "";					
		
		// 통합검색어, 공급받는자 거래처명 또는 사업자등록 번호로 조회, 공백시 전체조회
		String QString = "";					
		
		// 페이지 번호
		int Page = 1;							
		
		// 페이지당 목록개수
		int PerPage = 20;						
		
		// 정렬방향,  A-오름차순,  D-내림차순
		String Order = "D";						 
		
		// 연동문서 여부, 공백-전체조회, 0-일반문서, 1-연동문서
		// 일반문서 - 세금계산서 작성시 API가 아닌 팝빌 사이트를 통해 등록한 문서
		String InterOPYN = "";
		
		try {
			
			TISearchResult searchResult = taxinvoiceService.Search(testCorpNum, 
					mgtKeyType, DType, SDate, EDate, State, Type, TaxType, IssueType, LateOnly, 
					TaxRegIDType, TaxRegID, TaxRegIDYN, QString, Page, PerPage, Order, InterOPYN);
			
			m.addAttribute("SearchResult", searchResult);
			
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/SearchResult";
	}
	
	@RequestMapping(value = "registIssue", method = RequestMethod.GET)
	public String registIssue(Model m){
		/**
		 * 1건의 세금계산서를 즉시발행 처리합니다.
		 * - 세금계산서 항목별 정보는 "[전자세금계산서 API 연동매뉴얼] > 4.1. (세금)계산서
		 *   구성"을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 정보 객체
		Taxinvoice taxinvoice = new Taxinvoice();

		// 작성일자, 날짜형식(yyyyMMdd)
		taxinvoice.setWriteDate("20161206"); 
		
		// 과금방향, [정과금, 역과금] 중 선택기재, "역과금"은 역발행세금계산서 발행에만 가능
		taxinvoice.setChargeDirection("정과금");
		
		// 발행유형, [정발행, 역발행, 위수탁] 중 기재
		taxinvoice.setIssueType("정발행"); 
		
		// [영수, 청구] 중 기재
		taxinvoice.setPurposeType("영수"); 
		
		// 발행시점, [직접발행, 승인시자동발행] 중 기재
		taxinvoice.setIssueTiming("직접발행"); 
		
		// 과세형태, [과세, 영세, 면세] 중 기재
		taxinvoice.setTaxType("과세"); 
		
		
		
		/**********************************************************************
		 *								공급자 정보	 
		 *********************************************************************/
		
		// 공급자 사업자번호
		taxinvoice.setInvoicerCorpNum(testCorpNum);
		
		// 공급자 종사업장 식별번호, 필요시 기재. 형식은 숫자 4자리.
		taxinvoice.setInvoicerTaxRegID(""); 
		
		// 공급자 상호
		taxinvoice.setInvoicerCorpName("공급자 상호");  
		
		// 공급자 문서관리번호, 1~24자리 (숫자, 영문, '-', '_') 조합으로 사업자 별로 중복되지 않도록 구성
		taxinvoice.setInvoicerMgtKey("20161206-02");
		
		// 공급자 대표자성명
		taxinvoice.setInvoicerCEOName("공급자 대표자 성명");
		
		// 공급자 주소 
		taxinvoice.setInvoicerAddr("공급자 주소");
		
		// 공급자 종목
		taxinvoice.setInvoicerBizClass("공급자 업종");
		
		// 공급자 업태
		taxinvoice.setInvoicerBizType("공급자 업태,업태2");
		
		// 공급자 담당자 성명
		taxinvoice.setInvoicerContactName("공급자 담당자명");
		
		// 공급자 담당자 메일주소
		taxinvoice.setInvoicerEmail("test@test.com");
		
		// 공급자 담당자 연락처
		taxinvoice.setInvoicerTEL("070-7070-0707");
		
		// 공급자 담당자 휴대폰번호
		taxinvoice.setInvoicerHP("010-000-2222");
		
		// 발행 안내문자메시지 전송여부
		// - 전송시 포인트 차감되며, 전송실패시 환불처리
		taxinvoice.setInvoicerSMSSendYN(false);

		
		
		
		/**********************************************************************
		 *							공급받는자 정보	 
		 *********************************************************************/
				
		// 공급받는자 구분, [사업자, 개인, 외국인] 중 기재
		taxinvoice.setInvoiceeType("사업자");	
		
		// 공급받는자 사업자번호, '-' 제외 10자리
		taxinvoice.setInvoiceeCorpNum("8888888888"); 
		
		// 공급받는자 상호 
		taxinvoice.setInvoiceeCorpName("공급받는자 상호");
		
		// [역발행시 필수] 공급받는자 문서관리번호, 1~24자리까지 사업자번호별 중복없는 고유번호 할당
		taxinvoice.setInvoiceeMgtKey("");
		
		// 공급받는자 대표자 성명
		taxinvoice.setInvoiceeCEOName("공급받는자 대표자 성명");
		
		// 공급받는자 주소
		taxinvoice.setInvoiceeAddr("공급받는자 주소");
		
		// 공급받는자 종목
		taxinvoice.setInvoiceeBizClass("공급받는자 업종");
		
		// 공급받는자 업태
		taxinvoice.setInvoiceeBizType("공급받는자 업태");
		
		// 공급받는자 담당자명
		taxinvoice.setInvoiceeContactName1("공급받는자 담당자명");
		
		// 공급받는자 담당자 메일주소 
		taxinvoice.setInvoiceeEmail1("test@invoicee.com");
		
		// 공급받는자 담당자 연락처
		taxinvoice.setInvoiceeTEL1("070-111-222");
		
		// 공급받는자 담당자 휴대폰번호
		taxinvoice.setInvoiceeHP1("010-111-222");
		
		// 역발행시 안내문자메시지 전송여부
		// - 전송시 포인트 차감되며, 전송실패시 환불처리
		taxinvoice.setInvoiceeSMSSendYN(false);

		
		
		/**********************************************************************
		 *							세금계산서 기재정보	 
		 *********************************************************************/
		
		// [필수] 공급가액 합계 
		taxinvoice.setSupplyCostTotal("100000");
		
		// [필수] 세액 합계
		taxinvoice.setTaxTotal("10000");
		
		// [필수] 합계금액, 공급가액 + 세액
		taxinvoice.setTotalAmount("110000");
			
		// 기재 상 일련번호
		taxinvoice.setSerialNum("123"); 
		
		// 기재 상 현금
		taxinvoice.setCash(""); 
		
		// 기재 상 수표 
		taxinvoice.setChkBill(""); 
		
		// 기재 상 어움
		taxinvoice.setNote(""); 
		
		// 기재 상 외상미수금
		taxinvoice.setCredit(""); 
		
		// 기재 상 비고
		taxinvoice.setRemark1("비고1");
		taxinvoice.setRemark2("비고2");
		taxinvoice.setRemark3("비고3");
		taxinvoice.setKwon((short) 1);
		taxinvoice.setHo((short) 1);

		// 사업자등록증 이미지 첨부여부
		taxinvoice.setBusinessLicenseYN(false); 
		
		// 통장사본 이미지 첨부여부
		taxinvoice.setBankBookYN(false); 
		
		
		
		/**********************************************************************
		 *				수정세금계산서 정보 (수정세금계산서 작성시 기재)
    	 * - 수정세금계산서 관련 정보는 연동매뉴얼 또는 개발가이드 링크 참조
    	 & - [참고] 수정세금계산서 작성방법 안내 [http://blog.linkhub.co.kr/650]	 
		 *********************************************************************/
		
		// 수정사유코드, 수정사우에 따라 1~6 중 선택기재.
		taxinvoice.setModifyCode(null); 
		
		// 원본세금계산서 ItemKey, 문서확인 (GetInfo API)의 응답결과 항목 중 ItemKey 확인
		taxinvoice.setOriginalTaxinvoiceKey(""); 
		
		
		
		/**********************************************************************
		 *							상세항목(품목) 정보
    	 *********************************************************************/
		
		taxinvoice.setDetailList(new ArrayList<TaxinvoiceDetail>());

		// 상세항목 객체
		TaxinvoiceDetail detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 1); // 일련번호, 1부터 순차기재
		detail.setPurchaseDT("20161201"); // 거래일자
		detail.setItemName("품목명");
		detail.setSpec("규격");
		detail.setQty("1"); // 수량
		detail.setUnitCost("50000"); // 단가
		detail.setSupplyCost("50000"); // 공급가액
		detail.setTax("5000"); // 세액
		detail.setRemark("품목비고");

		taxinvoice.getDetailList().add(detail);

		detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 2); // 일련번호, 1부터 순차기재
		detail.setPurchaseDT("20161201"); // 거래일자
		detail.setItemName("품목명2");
		detail.setSpec("규격");
		detail.setQty("1"); // 수량
		detail.setUnitCost("50000"); // 단가
		detail.setSupplyCost("50000"); // 공급가액
		detail.setTax("5000"); // 세액
		detail.setRemark("품목비고2");
		
		taxinvoice.getDetailList().add(detail);
		
		
		
		/**********************************************************************
		 *							추가담당자 정보
    	 *********************************************************************/
		
		taxinvoice.setAddContactList(new ArrayList<TaxinvoiceAddContact>());
		
		TaxinvoiceAddContact addContact = new TaxinvoiceAddContact();
		
		addContact.setSerialNum(1);
		addContact.setContactName("추가 담당자명");
		addContact.setEmail("test2@test.com");
		
		taxinvoice.getAddContactList().add(addContact);
		
		
		// 거래명세서 동시작성여부
		Boolean WriteSpecification = false;		
		
		// 거래명세서 관리번호
		String DealInvoiceKey = null;			
		
		// 즉시발행 메모
		String Memo = "즉시발행 메모";				
		
		// 지연발행 강제여부
	    // 발행마감일이 지난 세금계산서를 발행하는 경우, 가산세가 부과될 수 있습니다.
	    // 가산세가 부과되더라도 발행을 해야하는 경우에는 forceIssue의 값을
	    // true로 선언하여 발행(Issue API)를 호출하시면 됩니다.
		Boolean ForceIssue = false; 		
		
		try{
			
			Response response = taxinvoiceService.registIssue(testCorpNum, 
					taxinvoice,	WriteSpecification, Memo, ForceIssue, DealInvoiceKey);
			
			m.addAttribute("Response", response);
			
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "attachStatement", method = RequestMethod.GET)
	public String attachStatement(Model m){
		/**
		 * 1건의 세금계산서를 즉시발행 처리합니다.
		 * - 세금계산서 항목별 정보는 "[전자세금계산서 API 연동매뉴얼] > 4.1. (세금)계산서 구성"
		 *   을 참조하시기 바랍니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
								
		// 세금계산서 관리번호
		String mgtKey = "20161206-02";			
		
		// 첨부할 전자명세서 코드
		int subItemCode = 121;					
		
		// 첨부활 전자명세서 관리번호
		String subMgtKey = "20160119-05";		 
		
		try {
			
			Response response = taxinvoiceService.attachStatement(testCorpNum, 
					mgtKeyType, mgtKey, subItemCode, subMgtKey);
			
			m.addAttribute("Response", response);
			
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "detachStatement", method = RequestMethod.GET)
	public String detachStatement(Model m){
		/**
		 * 세금계산서에 첨부된 전자명세서 1건을 첨부해제합니다.
		 */
		
		// 세금계산서 유형, 매출-SELL, 매입-BUY, 위수탁-TRUSTEE
		MgtKeyType mgtKeyType = MgtKeyType.SELL;
		
		// 세금계산서 관리번호
		String mgtKey = "20161206-02";			
		
		// 첨부해제할 전자명세서 코드
		int subItemCode = 121;
		
		// 첨부해제할 전자명세서 관리번호
		String subMgtKey = "20160119-05";		 
		
		try {
			
			Response response = taxinvoiceService.detachStatement(testCorpNum, 
					mgtKeyType, mgtKey, subItemCode, subMgtKey);
			
			m.addAttribute("Response", response);
			
		} catch (PopbillException e){
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
}
