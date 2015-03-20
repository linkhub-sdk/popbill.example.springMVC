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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.AttachedFile;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.StatementService;
import com.popbill.api.statement.Statement;
import com.popbill.api.statement.StatementDetail;
import com.popbill.api.statement.StatementInfo;
import com.popbill.api.statement.StatementLog;

/**
 * 팝빌 전자명세서 API 예제.
 */
@Controller
@RequestMapping("StatementService")
public class StatementServiceExample {
	
	@Autowired
	private StatementService statementService;
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Statement/index";
	}
	
	@RequestMapping(value = "checkMgtKeyInUse", method = RequestMethod.GET)
	public String checkMgtKeyInUse( Model m) {
		/**
		 * 문서관리번호 사용여부 확인
		 * 최대 24자리, 영문, 숫자, '-', '_' 조합하여 구성
		 */
		
		int itemCode = 121;				// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		String isUseStr;
		
		try {
			boolean IsUse = statementService.checkMgtKeyInUse(testCorpNum, itemCode, mgtKey);
			
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
		 * 전자명세서 등록(임시저장)
		 * 전자명세서 구성항목에 대한 설명은 [전자명세서 API 연동매뉴얼 > 4.1 전자명세서 구성] 참조
		 */
		
		Statement statement = new Statement();
		
		statement.setWriteDate("20150319");				// [필수] 작성일자, 형태 yyyyMmdd
		statement.setPurposeType("영수");				// [필수] {영수, 청구} 중 기재
		statement.setTaxType("과세");					// [필수] {과세, 영세, 면세} 중 기재
		statement.setFormCode("");						// 맞춤양식코드, 미기재시 기본양식으로 처리
		statement.setItemCode((short) 121);				// [필수] 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]	int 
		statement.setMgtKey("20150319-10");				// [필수] 문서관리번호, 최대 24자리 영문, 숫자, '-', '_'로 구성
		statement.setSenderCorpNum("1234567890");		// [필수] 공급자 사업자번호
		statement.setSenderCorpName("공급자 상호");
		statement.setSenderAddr("공급자 주소");
		statement.setSenderCEOName("공급자 대표자 성명");
		statement.setSenderTaxRegID("");				// 공급자 종사업장 식별번호, 숫자 4자리, 필요시 기재
		statement.setSenderBizClass("업종");
		statement.setSenderBizType("업태");
		statement.setSenderContactName("공급자 담당자명");
		statement.setSenderEmail("test@test.com");
		statement.setSenderTEL("070-7070-0707");
		statement.setSenderHP("010-000-2222");
		
		statement.setReceiverCorpNum("8888888888");		// [필수] 공급받는자 사업자번호
		statement.setReceiverCorpName("공급받는자 상호");
		statement.setReceiverCEOName("공급받는자 대표자 성명");
		statement.setReceiverAddr("공급받는자 주소");
		statement.setReceiverBizClass("공급받는자 업종");
		statement.setReceiverBizType("공급받는자 업태");
		statement.setReceiverContactName("공급받는자 담당자명");
		statement.setReceiverEmail("test@receiver.com");
		statement.setSupplyCostTotal("400000");         // [필수] 공급가액 합계
		statement.setTaxTotal("40000");                 // [필수] 세액 합계
		statement.setTotalAmount("440000");             // [필수] 합계금액.  공급가액 + 세액
		statement.setSerialNum("123");                  // 기재상 일련번호 항목
		statement.setRemark1("비고1");
		statement.setRemark2("비고2");
		statement.setRemark3("비고3");
		statement.setBusinessLicenseYN(false);			// 사업자등록증 이미지 첨부시 설정.
		statement.setBankBookYN(false);					// 통장사본 이미지 첨부시 설정.
				
		// 추가속성, 추가속성에 관한 정보는 [전자명세서 API 연동매뉴얼 > [5.2 기본양식 추가속성 테이블] 참조
		Map<String, String> propertyBag = new HashMap<String, String>();
		
		propertyBag.put("Balance", "15000");			// 전잔액
		propertyBag.put("Deposit", "5000");				// 입금액
		propertyBag.put("CBalance", "20000");			// 현잔액
		statement.setPropertyBag(propertyBag);
		
		statement.setDetailList(new ArrayList<StatementDetail>());
		
		StatementDetail detail = new StatementDetail();		// 상세항목(품목) 배열
		
		detail.setSerialNum((short) 1);					// 일련번호, 1부터 순차기재
		detail.setItemName("품명");						// 품목명
		detail.setPurchaseDT("20150317");				// 거래일자
		detail.setQty("1");								// 수량
		detail.setSupplyCost("200000");					// 공급가액
		detail.setTax("20000");							// 세액
		
		statement.getDetailList().add(detail);
		
		detail = new StatementDetail();					// 상세항목(품목) 배열
		detail.setSerialNum((short) 2);					// 일련번호 1부터 순차기재
		detail.setItemName("품명");						// 품목명
		detail.setPurchaseDT("20150317");				// 거래일자
		detail.setQty("1");								// 수량
		detail.setSupplyCost("200000");					// 공급가액
		detail.setTax("20000");							// 세액
		
		statement.getDetailList().add(detail);
		
		try {
			Response response = statementService.register(testCorpNum,statement,testUserID);
			
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
		 * 전자명세서 수정 - [등록(임서저장)] 상태인 경우만 가능
		 * 문서관리번호(mgtKey)를 제외한 모든정보 수정 가능
		 */
		
		Statement statement = new Statement();
		
		statement.setWriteDate("20150318");				// [필수] 작성일자, 형태 yyyyMmdd
		statement.setPurposeType("영수");					// [필수] {영수, 청구} 중 기재
		statement.setTaxType("과세");						// [필수] {과세, 영세, 면세} 중 기재
		statement.setFormCode("");						// 맞춤양식코드, 미기재시 기본양식으로 처리
		statement.setItemCode((short) 121);				// [필수] 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		statement.setMgtKey("20150319-01");				// 수정시 문서관리번호를 제외한 전자명세서 정보 수정가능
		statement.setSenderCorpNum("1234567890");		// [필수] 공급자 사업자번호
		statement.setSenderCorpName("공급자 상호");
		statement.setSenderAddr("공급자 주소");
		statement.setSenderCEOName("공급자 대표자 성명");
		statement.setSenderTaxRegID("");				// 공급자 종사업장 식별번호, 숫자 4자리, 필요시 기재
		statement.setSenderBizClass("업종");
		statement.setSenderBizType("업태");
		statement.setSenderContactName("공급자 담당자명");
		statement.setSenderEmail("test@test.com");
		statement.setSenderTEL("070-7070-0707");
		statement.setSenderHP("010-000-2222");
	
		statement.setReceiverCorpNum("8888888888");			// [필수] 공급받는자 사업자번호
		statement.setReceiverCorpName("공급받는자 상호");
		statement.setReceiverCEOName("공급받는자 대표자 성명");
		statement.setReceiverAddr("공급받는자 주소");
		statement.setReceiverBizClass("공급받는자 업종");
		statement.setReceiverBizType("공급받는자 업태");
		statement.setReceiverContactName("공급받는자 담당자명");
		statement.setReceiverEmail("test@receiver.com");
	
		statement.setSupplyCostTotal("400000");	       		// [필수] 공급가액 합계
		statement.setTaxTotal("40000");					    // [필수] 세액 합계
		statement.setTotalAmount("440000");				    // [필수] 합계금액.  공급가액 + 세액
	
		statement.setSerialNum("2015-01");                  // 기재상 일련번호 항목
		statement.setRemark1("비고1");
		statement.setRemark2("비고2");
		statement.setRemark3("비고3");
	
		statement.setBusinessLicenseYN(false);			// 사업자등록증 이미지 첨부시 설정.
		statement.setBankBookYN(false);					// 통장사본 이미지 첨부시 설정.
				
		// 추가속성, 추가속성에 대한 설명은 [전자명세서 API 연동매뉴얼 > 5.2 기본양식 추가속성 테이블] 참조
		Map<String, String> propertyBag = new HashMap<String, String>();
		
		propertyBag.put("Balance", "15000");			// 전잔액
		propertyBag.put("Deposit", "5000");				// 입금액
		propertyBag.put("CBalance", "20000");			// 현잔액
	
		statement.setPropertyBag(propertyBag);
	
		
		statement.setDetailList(new ArrayList<StatementDetail>());
		
		StatementDetail detail = new StatementDetail();		// 상세항목(품목)  배열
	
		detail.setSerialNum((short) 1);					// 일련번호, 1부터 순차기재
		detail.setItemName("품명");						// 품목명
		detail.setPurchaseDT("20150317");				// 거래일자
		detail.setQty("1");								// 수량
		detail.setSupplyCost("200000");					// 공급가액
		detail.setTax("20000");							// 세액
		
		statement.getDetailList().add(detail);
			
		detail = new StatementDetail();					// 상세항목(품목)  배열
	
		detail.setSerialNum((short) 2);					// 일련번호, 1부터 순차기재
		detail.setItemName("품명");						// 품목명
		detail.setPurchaseDT("20150317");				// 거래일자
		detail.setQty("1");								// 수량
		detail.setSupplyCost("200000");					// 공급가액
		detail.setTax("20000");							// 세액
	
		statement.getDetailList().add(detail);
		
		try {
			
			int itemCode = 121;				// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
			String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
			
			Response response = statementService.update(testCorpNum, itemCode, mgtKey, statement, testUserID);
			
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
		 * 전자명세서 상태/요약 정보 확인
		 * 상태정보 항목에 대한 설명은 [전자명세서 API 연동매뉴얼 > 4.2 전자명세서 상태정보 구성] 참조
		 */
		
		int itemCode = 121;				// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		
		try {
			
			StatementInfo statementInfo = statementService.getInfo(testCorpNum, itemCode, mgtKey);
			
			m.addAttribute("StatementInfo",statementInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Statement/StatementInfo";
	}
	
	
	@RequestMapping(value = "getInfos", method = RequestMethod.GET)
	public String getInfos( Model m) {
		/*
		 * 다량 전자명세서 상태/요약 정보 확인(최대 1000건)
		 * 상태정보 항목에 대한 설명은 [전자명세서 API 연동매뉴얼 > 4.2 전자명세서 상태정보 구성] 참조
		 */
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]

		// 전자명세서 문서관리번호 배열(최대 1000건)
		String[] MgtKeyList = new String[] {"20150318-01", "20150318-02", "20150318-03", "20150319-01"};
		
		try {
			
			StatementInfo[] statementInfos = statementService.getInfos(testCorpNum, itemCode, MgtKeyList);
			
			m.addAttribute("StatementInfos",statementInfos);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Statement/StatementInfo";
	}
	
	@RequestMapping(value = "getDetailInfo", method = RequestMethod.GET)
	public String getDetailInfo( Model m) {
		/*
		 * 전자명세서 상세정보확인 
		 * 전자명세서 항목에 관한 설명은 [전자명세서 API 연동매뉴얼 > 4.1 전자명세서 구성] 참조
		 */
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		
		try {
			Statement statement = statementService.getDetailInfo(testCorpNum, itemCode, mgtKey, testUserID);
			
			m.addAttribute("Statement",statement);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Statement/Statement";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete( Model m) {
		//전자명세서 삭제, [등록(임시저장)], [발행취소] 상태의 전자명세서만 삭제가능
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		
		try {
			Response response = statementService.delete(testCorpNum, itemCode, mgtKey);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getLogs", method = RequestMethod.GET)
	public String getLogs( Model m) {
		// 전자명세서 문서이력 확인
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		
		try {
			StatementLog[] statementLogs = statementService.getLogs(testCorpNum, itemCode, mgtKey);
			
			m.addAttribute("StatementLogs",statementLogs);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Statement/StatementLog";
	}
	
	@RequestMapping(value = "attachFile", method = RequestMethod.GET)
	public String attachFile( Model m) {
		/*
		 * 전자명세서 파일 첨부는 [등록(임시저장)] 상태인 경우만 가능
		 */
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		String displayName = "첨부파일명";  // 첨부파일 표시명
				
		//첨부할 파일의 InputStream. 예제는 resource에 테스트파일을 참조함.
		//FileInputStream으로 처리하는 것을 권함.
		InputStream stream = getClass().getClassLoader().getResourceAsStream("test.jpg");
		
		try {
			
			Response response = statementService.attachFile(testCorpNum, itemCode, mgtKey, displayName, stream, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		finally {
			if(stream != null)
			try {
				stream.close();
			} catch (IOException e) {}
		}
		
		
		return "response";
	}
	
	@RequestMapping(value = "getFiles", method = RequestMethod.GET)
	public String getFiles( Model m) {
		/*
		 * 첨부파일 목록 조회 
		 * 첨부파일 삭제(deleteFile API)시 응답전문의 AttachedFile값을 파일아이디(FileID)값에 기재하여 삭제가능 
		 */
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		
		try {
			AttachedFile[] attachedFiles = statementService.getFiles(testCorpNum, itemCode, mgtKey);
			
			m.addAttribute("AttachedFiles",attachedFiles);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Statement/AttachedFile";
	}
	
	
	@RequestMapping(value = "deleteFile", method = RequestMethod.GET)
	public String deleteFile( Model m) {
		/*
		 * 첨부파일 삭제 
		 * 첨부파일 목록 조회(getFiles)시 응답전문의 attachedFile 값을 삭제시 FileID로 기재하여 삭제가능 
		 */
		
		String FileID = "57C0A91A-BF5A-494A-8E0D-B46FC9B5C8E2.PBF"; // getFiles()로 확인한 AttachedFile의 attachedFile 참조.
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		
		try {
			Response response = statementService.deleteFile(testCorpNum, itemCode, mgtKey, FileID, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "issue", method = RequestMethod.GET)
	public String issue( Model m) {
		// 전자명세서 발행
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		String memo = "발행메모";
		
		try {
			Response response = statementService.issue(testCorpNum, itemCode, mgtKey, memo, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "cancelIssue", method = RequestMethod.GET)
	public String cancelIssue( Model m) {
		// 전자명세서 발행취소
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-10";	// 문서관리번호, 최대 24자리 영문, 숫자 , '-', '_'로 구성
		String memo = "발행취소 메모";
		
		try {
			Response response = statementService.cancel(testCorpNum, itemCode, mgtKey, memo, testUserID);
			
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
		 * 전자명세서 이메일 재전송 요청
		 * 등록(임시저장) 상태인 경우 이메일 재전송 불가  
		 */
		
		int itemCode = 121;					// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-01";		// 전자명세서 문서관리번호
		String receiver = "test@test.com";	// 수신자 이메일주소
		
		try {
			Response response = statementService.sendEmail(testCorpNum, itemCode, mgtKey, receiver, testUserID);
			
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
		
		int itemCode = 121;							// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150318-02";				// 전자명세서 문서관리번호
		String sender = "07075103710";				// 발신번호
		String receiver = "010111222";			// 수신번호 
		String contents = "전자명세서 문자메시지 전송 테스트입니다.";		// 문자 전송 내용 (90Byte 초과시 길이가 조정되어 전송)
		
		try {
			Response response = statementService.sendSMS(testCorpNum, itemCode, mgtKey, sender, receiver, contents, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendFAX", method = RequestMethod.GET)
	public String sendFAX( Model m) {
		// 전자명세서 팩스 호출 
		
		int itemCode = 121;							// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-01";				// 전자명세서 문서관리번호
		String sender = "07075103710";				// 발신자 번호
		String receiver = "070111222";				// 수신자 팩스번호
		
		try {
			Response response = statementService.sendFAX(testCorpNum, itemCode, mgtKey, sender, receiver, testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getURL", method = RequestMethod.GET)
	public String getURL( Model m) {
		// 전자명세서 관련 팝빌 URL
		
		String TOGO = "SBOX"; // TBOX : 임시문서함 , SBOX : 매출문서함 
		
		try {
			
			String url = statementService.getURL(testCorpNum, testUserID, TOGO);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPopUpURL", method = RequestMethod.GET)
	public String getPopUpURL( Model m) {
		// 전자명세서 문서 내용 보기 URL
		
		int itemCode = 121;						// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-01";			// 전자명세서 문서관리번호
		
		try {
			String url = statementService.getPopUpURL(testCorpNum, itemCode, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPrintURL", method = RequestMethod.GET)
	public String getPrintURL( Model m) {
		// 전자명세서 인쇄 화면 URL
		
		int itemCode = 121;						// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-01";			// 전자명세서 문서관리번호
		
		try {
			String url = statementService.getPrintURL(testCorpNum, itemCode, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getEPrintURL", method = RequestMethod.GET)
	public String getEPrintURL( Model m) {
		
		 // 전자명세서  인쇄 화면(공급받는자용) URL 
		
		int itemCode = 121;						// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-01";			// 전자명세서 문서관리번호
		
		try {
			String url = statementService.getEPrintURL(testCorpNum, itemCode, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getMailURL", method = RequestMethod.GET)
	public String getMailURL( Model m) {
		
		// 전자명세서 공급받는자 메일 링크 URL 확인 
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		String mgtKey = "20150319-01";			// 전자명세서 문서관리번호
		
		try {
			String url = statementService.getMailURL(testCorpNum, itemCode, mgtKey, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getMassPrintURL", method = RequestMethod.GET)
	public String getMassPrintURL( Model m) {
		// 다량 전자명세서 인쇄 화면 URL 확인, 최대 100건 
				
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		
		// 문서관리번호 배열, 최대 100건
		String[] MgtKeyList = new String[] {"20150318-01", "20150318-01", "20150318-02", "20150319-01"}; 
		
		try {
			
			String url = statementService.getMassPrintURL(testCorpNum, itemCode, MgtKeyList, testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
		// 전자명세서 발행단가 확인 
		
		int itemCode = 121;		// 명세서 코드, [121 - 거래명세서], [122 - 청구서], [123 - 견적서], [124 - 발주서], [125 - 입금표], [126 - 영수증]
		
		try {
			
			float unitCost = statementService.getUnitCost(testCorpNum, itemCode);
			
			m.addAttribute("Result",unitCost);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
}

















