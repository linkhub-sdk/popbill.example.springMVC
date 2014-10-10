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
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.AttachedFile;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.TaxinvoiceService;
import com.popbill.api.taxinvoice.EmailPublicKey;
import com.popbill.api.taxinvoice.MgtKeyType;
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
	
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String testCorpNum;
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String testUserID;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Taxinvoice/index";
	}
	@RequestMapping(value = "checkMgtKeyInUse", method = RequestMethod.GET)
	public String checkMgtKeyInUse( Model m) {
		
		try {
			boolean IsUse = taxinvoiceService.checkMgtKeyInUse(testCorpNum,MgtKeyType.SELL,"1234");
			
			m.addAttribute("Result",IsUse);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register( Model m) {
		
		Taxinvoice taxinvoice = new Taxinvoice();

		taxinvoice.setWriteDate("20141002"); // 필수, 기재상 작성일자
		taxinvoice.setChargeDirection("정과금"); // 필수, {정과금, 역과금}
		taxinvoice.setIssueType("정발행"); // 필수, {정발행, 역발행, 위수탁}
		taxinvoice.setPurposeType("영수"); // 필수, {영수, 청구}
		taxinvoice.setIssueTiming("직접발행"); // 필수, {직접발행, 승인시자동발행}
		taxinvoice.setTaxType("과세"); // 필수, {과세, 영세, 면세}

		//공급자 정보 기재 
		taxinvoice.setInvoicerCorpNum(testCorpNum);	
		taxinvoice.setInvoicerTaxRegID(""); // 종사업자 식별번호. 필요시 기재. 형식은 숫자 4자리.
		taxinvoice.setInvoicerCorpName("공급자 상호");  //필수
		taxinvoice.setInvoicerMgtKey("1234"); // 공급자 발행까지 API로 발행하고자 할경우 정발행과 동일한 형태로 추가 기재.
		taxinvoice.setInvoicerCEOName("공급자 대표자 성명"); //필수
		taxinvoice.setInvoicerAddr("공급자 주소");
		taxinvoice.setInvoicerBizClass("공급자 업종");
		taxinvoice.setInvoicerBizType("공급자 업태,업태2");
		taxinvoice.setInvoicerContactName("공급자 담당자명");
		taxinvoice.setInvoicerEmail("test@test.com");
		taxinvoice.setInvoicerTEL("070-7070-0707");
		taxinvoice.setInvoicerHP("010-000-2222");
		taxinvoice.setInvoicerSMSSendYN(true); // 발행시 문자발송기능 사용시 활용

		//공급받는자 정보 기재
		taxinvoice.setInvoiceeType("사업자");			//사업자 , 개인 , 외국인
		taxinvoice.setInvoiceeCorpNum("8888888888"); // 개인의 경우 주민등록번호, 외국인의 경우 "9999999999999" 기재후 비고에 여권번호 또는 외국인등록번호 기재.
		taxinvoice.setInvoiceeCorpName("공급받는자 상호"); //필수
		taxinvoice.setInvoiceeMgtKey(""); // 문서관리번호 1~24자리까지 공급받는자 사업자번호별 중복없는 고유번호 할당
		taxinvoice.setInvoiceeCEOName("공급받는자 대표자 성명"); //필수
		taxinvoice.setInvoiceeAddr("공급받는자 주소");
		taxinvoice.setInvoiceeBizClass("공급받는자 업종");
		taxinvoice.setInvoiceeBizType("공급받는자 업태");
		taxinvoice.setInvoiceeContactName1("공급받는자 담당자명");
		taxinvoice.setInvoiceeEmail1("test@invoicee.com");

		taxinvoice.setSupplyCostTotal("100000"); // 필수 공급가액 합계"
		taxinvoice.setTaxTotal("10000"); // 필수 세액 합계
		taxinvoice.setTotalAmount("110000"); // 필수 합계금액. 공급가액 + 세액

		taxinvoice.setModifyCode(null); // 수정세금계산서 작성시 1~6까지 선택기재.
		taxinvoice.setOriginalTaxinvoiceKey(""); // 수정세금계산서 작성시 원본세금계산서의 ItemKey기재. ItemKey는 getInfo로 확인.
		taxinvoice.setSerialNum("123"); //일련번호 항목
		taxinvoice.setCash(""); // 현금
		taxinvoice.setChkBill(""); // 수표
		taxinvoice.setNote(""); // 어음
		taxinvoice.setCredit(""); // 외상미수금
		taxinvoice.setRemark1("비고1");
		taxinvoice.setRemark2("비고2");
		taxinvoice.setRemark3("비고3");
		taxinvoice.setKwon((short) 1);
		taxinvoice.setHo((short) 1);

		taxinvoice.setBusinessLicenseYN(false); // 사업자등록증 이미지 첨부시 설정.
		taxinvoice.setBankBookYN(false); // 통장사본 이미지 첨부시 설정.

		taxinvoice.setDetailList(new ArrayList<TaxinvoiceDetail>());

		TaxinvoiceDetail detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 1); // 일련번호
		detail.setPurchaseDT("20140319"); // 거래일자
		detail.setItemName("품목명");
		detail.setSpec("규격");
		detail.setQty("1"); // 수량
		detail.setUnitCost("100000"); // 단가
		detail.setSupplyCost("100000"); // 공급가액
		detail.setTax("10000"); // 세액
		detail.setRemark("품목비고");

		taxinvoice.getDetailList().add(detail);

		detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 2);
		detail.setItemName("품목명");

		taxinvoice.getDetailList().add(detail);
		
		taxinvoice.setAddContactList(new ArrayList<TaxinvoiceAddContact>());
		
		TaxinvoiceAddContact addContact = new TaxinvoiceAddContact();
		
		addContact.setSerialNum(1);
		addContact.setContactName("추가 담당자명");
		addContact.setEmail("test2@test.com");
		
		taxinvoice.getAddContactList().add(addContact);
		
		try {
			Response response = taxinvoiceService.register(testCorpNum,taxinvoice,testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update( Model m) {
		
		Taxinvoice taxinvoice = new Taxinvoice();

		taxinvoice.setWriteDate("20141002"); // 필수, 기재상 작성일자
		taxinvoice.setChargeDirection("정과금"); // 필수, {정과금, 역과금}
		taxinvoice.setIssueType("정발행"); // 필수, {정발행, 역발행, 위수탁}
		taxinvoice.setPurposeType("영수"); // 필수, {영수, 청구}
		taxinvoice.setIssueTiming("직접발행"); // 필수, {직접발행, 승인시자동발행}
		taxinvoice.setTaxType("과세"); // 필수, {과세, 영세, 면세}

		//공급자 정보 기재 
		taxinvoice.setInvoicerCorpNum(testCorpNum);	
		taxinvoice.setInvoicerTaxRegID(""); // 종사업자 식별번호. 필요시 기재. 형식은 숫자 4자리.
		taxinvoice.setInvoicerCorpName("공급자 상호_수정분");
		taxinvoice.setInvoicerMgtKey("1234"); // 공급자 발행까지 API로 발행하고자 할경우 정발행과 동일한 형태로 추가 기재.
		taxinvoice.setInvoicerCEOName("공급자 대표자 성명");
		taxinvoice.setInvoicerAddr("공급자 주소");
		taxinvoice.setInvoicerBizClass("공급자 업종");
		taxinvoice.setInvoicerBizType("공급자 업태,업태2");
		taxinvoice.setInvoicerContactName("공급자 담당자명");
		taxinvoice.setInvoicerEmail("test@test.com");
		taxinvoice.setInvoicerTEL("070-7070-0707");
		taxinvoice.setInvoicerHP("010-000-2222");
		taxinvoice.setInvoicerSMSSendYN(true); // 발행시 문자발송기능 사용시 활용

		//공급받는자 정보 기재
		taxinvoice.setInvoiceeType("사업자");
		taxinvoice.setInvoiceeCorpNum("8888888888");
		taxinvoice.setInvoiceeCorpName("공급받는자 상호");
		taxinvoice.setInvoiceeMgtKey(""); // 문서관리번호 1~24자리까지 공급받는자 사업자번호별 중복없는 고유번호 할당
		taxinvoice.setInvoiceeCEOName("공급받는자 대표자 성명");
		taxinvoice.setInvoiceeAddr("공급받는자 주소");
		taxinvoice.setInvoiceeBizClass("공급받는자 업종");
		taxinvoice.setInvoiceeBizType("공급받는자 업태");
		taxinvoice.setInvoiceeContactName1("공급받는자 담당자명");
		taxinvoice.setInvoiceeEmail1("test@invoicee.com");

		taxinvoice.setSupplyCostTotal("100000"); // 필수 공급가액 합계"
		taxinvoice.setTaxTotal("10000"); // 필수 세액 합계
		taxinvoice.setTotalAmount("110000"); // 필수 합계금액. 공급가액 + 세액

		taxinvoice.setModifyCode(null); // 수정세금계산서 작성시 1~6까지 선택기재.
		taxinvoice.setOriginalTaxinvoiceKey(""); // 수정세금계산서 작성시 원본세금계산서의 ItemKey기재. ItemKey는 getInfo로 확인.
		taxinvoice.setSerialNum("123"); //일련번호 항목
		taxinvoice.setCash(""); // 현금
		taxinvoice.setChkBill(""); // 수표
		taxinvoice.setNote(""); // 어음
		taxinvoice.setCredit(""); // 외상미수금
		taxinvoice.setRemark1("비고1");
		taxinvoice.setRemark2("비고2");
		taxinvoice.setRemark3("비고3");
		taxinvoice.setKwon((short) 1);
		taxinvoice.setHo((short) 1);

		taxinvoice.setBusinessLicenseYN(false); // 사업자등록증 이미지 첨부시 설정.
		taxinvoice.setBankBookYN(false); // 통장사본 이미지 첨부시 설정.

		taxinvoice.setDetailList(new ArrayList<TaxinvoiceDetail>());

		TaxinvoiceDetail detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 1); // 일련번호
		detail.setPurchaseDT("20140319"); // 거래일자
		detail.setItemName("품목명");
		detail.setSpec("규격");
		detail.setQty("1"); // 수량
		detail.setUnitCost("100000"); // 단가
		detail.setSupplyCost("100000"); // 공급가액
		detail.setTax("10000"); // 세액
		detail.setRemark("품목비고");

		taxinvoice.getDetailList().add(detail);

		detail = new TaxinvoiceDetail();

		detail.setSerialNum((short) 2);
		detail.setItemName("품목명");

		taxinvoice.getDetailList().add(detail);
		
		try {
			Response response = taxinvoiceService.update(testCorpNum,MgtKeyType.SELL,"1234", taxinvoice);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getInfo", method = RequestMethod.GET)
	public String getInfo( Model m) {
		
		try {
			TaxinvoiceInfo taxinvoiceInfo = taxinvoiceService.getInfo(testCorpNum,MgtKeyType.SELL,"1234");
			
			m.addAttribute("TaxinvoiceInfo",taxinvoiceInfo);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/TaxinvoiceInfo";
	}
	
	@RequestMapping(value = "getInfos", method = RequestMethod.GET)
	public String getInfos( Model m) {
		String[] MgtKeyList = new String[] {"1234","12345","123456"};
		
		try {
			
			TaxinvoiceInfo[] taxinvoiceInfos = taxinvoiceService.getInfos(testCorpNum,MgtKeyType.SELL,MgtKeyList);
			
			m.addAttribute("TaxinvoiceInfos",taxinvoiceInfos);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/TaxinvoiceInfo";
	}
	
	@RequestMapping(value = "getDetailInfo", method = RequestMethod.GET)
	public String getDetailInfo( Model m) {
		
		try {
			Taxinvoice taxinvoice = taxinvoiceService.getDetailInfo(testCorpNum,MgtKeyType.SELL,"1234");
			
			m.addAttribute("Taxinvoice",taxinvoice);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/Taxinvoice";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete( Model m) {
		
		try {
			Response response = taxinvoiceService.delete(testCorpNum,MgtKeyType.SELL,"1234");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getLogs", method = RequestMethod.GET)
	public String getLogs( Model m) {
		
		try {
			TaxinvoiceLog[] taxinvoiceLogs = taxinvoiceService.getLogs(testCorpNum,MgtKeyType.SELL,"1234");
			
			m.addAttribute("TaxinvoiceLogs",taxinvoiceLogs);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/TaxinvoiceLog";
	}
	

	
	@RequestMapping(value = "attachFile", method = RequestMethod.GET)
	public String attachFile( Model m) {
		
		//첨부할 파일의 InputStream. 예제는 resource에 테스트파일을 참조함.
		//FileInputStream으로 처리하는 것을 권함.
		InputStream stream = getClass().getClassLoader().getResourceAsStream("test.jpg");
		
		try {
			
			Response response = taxinvoiceService.attachFile(testCorpNum,MgtKeyType.SELL,"1234","첨부파일.jpg",stream);
			
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
		
		try {
			AttachedFile[] attachedFiles = taxinvoiceService.getFiles(testCorpNum,MgtKeyType.SELL,"1234");
			
			m.addAttribute("AttachedFiles",attachedFiles);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/AttachedFile";
	}
	
	
	@RequestMapping(value = "deleteFile", method = RequestMethod.GET)
	public String deleteFile( Model m) {
		
		String FileID = " 418DD6F7-5358-46A8-B430-04F79CC3D9DA.PBF"; // getFiles()로 확인한 AttachedFile의 attachedFile 참조.
		
		try {
			Response response = taxinvoiceService.deleteFile(testCorpNum,MgtKeyType.SELL,"1234",FileID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	
	
	
	@RequestMapping(value = "send", method = RequestMethod.GET)
	public String send( Model m) {
		
		try {
			Response response = taxinvoiceService.send(testCorpNum,MgtKeyType.SELL,"1234","발행예정 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "cancelSend", method = RequestMethod.GET)
	public String cancelSend( Model m) {
		
		try {
			Response response = taxinvoiceService.cancelSend(testCorpNum,MgtKeyType.SELL,"1234","발행예정취소 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "accept", method = RequestMethod.GET)
	public String accept( Model m) {
		
		try {
			Response response = taxinvoiceService.accept(testCorpNum,MgtKeyType.BUY,"1234","발행예정 승인 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "deny", method = RequestMethod.GET)
	public String deny( Model m) {
		
		try {
			Response response = taxinvoiceService.deny(testCorpNum,MgtKeyType.BUY,"1234","발행예정 거부 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "issue", method = RequestMethod.GET)
	public String issue( Model m) {
		
		try {
			Response response = taxinvoiceService.issue(testCorpNum,MgtKeyType.SELL,"1234","발행 메모",false,testUserID);
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "cancelIssue", method = RequestMethod.GET)
	public String cancelIssue( Model m) {
		
		try {
			Response response = taxinvoiceService.cancelIssue(testCorpNum,MgtKeyType.SELL,"1234","발행 취소 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "request", method = RequestMethod.GET)
	public String request( Model m) {
		
		try {
			Response response = taxinvoiceService.request(testCorpNum,MgtKeyType.BUY,"1234","역)발행요청 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "cancelRequest", method = RequestMethod.GET)
	public String cancelRequest( Model m) {
		
		try {
			Response response = taxinvoiceService.cancelRequest(testCorpNum,MgtKeyType.BUY,"1234","역)발행요청 취소 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "refuse", method = RequestMethod.GET)
	public String refuse( Model m) {
		
		try {
			Response response = taxinvoiceService.refuse(testCorpNum,MgtKeyType.SELL,"1234","역)발행요청 거부 메모");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendToNTS", method = RequestMethod.GET)
	public String sendToNTS( Model m) {
		
		try {
			Response response = taxinvoiceService.sendToNTS(testCorpNum,MgtKeyType.SELL,"1234");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendEmail", method = RequestMethod.GET)
	public String sendEmail( Model m) {
		
		try {
			Response response = taxinvoiceService.sendEmail(testCorpNum,MgtKeyType.SELL,"1234","test@test.com");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendSMS", method = RequestMethod.GET)
	public String sendSMS( Model m) {
		
		try {
			Response response = taxinvoiceService.sendSMS(testCorpNum,MgtKeyType.SELL,"1234","01011112222","01022223333","문자메시지 내용");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "sendFAX", method = RequestMethod.GET)
	public String sendFAX( Model m) {
		
		try {
			Response response = taxinvoiceService.sendFAX(testCorpNum,MgtKeyType.SELL,"1234","07075106766","77711112222");
			
			m.addAttribute("Response",response);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "response";
	}
	
	@RequestMapping(value = "getURL", method = RequestMethod.GET)
	public String getURL( Model m) {
		
		String TOGO = "SBOX"; // TBOX : 임시문서함 , SBOX : 매출문서함 , PBOX : 매입문서함 , WRITE : 매출작성
		
		try {
			
			String url = taxinvoiceService.getURL(testCorpNum,testUserID,TOGO);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPopUpURL", method = RequestMethod.GET)
	public String getPopUpURL( Model m) {
		
		try {
			String url = taxinvoiceService.getPopUpURL(testCorpNum,MgtKeyType.SELL,"1234",testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getPrintURL", method = RequestMethod.GET)
	public String getPrintURL( Model m) {
		
		try {
			String url = taxinvoiceService.getPrintURL(testCorpNum,MgtKeyType.SELL,"1234",testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getEPrintURL", method = RequestMethod.GET)
	public String getEPrintURL( Model m) {
		
		try {
			String url = taxinvoiceService.getEPrintURL(testCorpNum,MgtKeyType.SELL,"1234",testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getMailURL", method = RequestMethod.GET)
	public String getMailURL( Model m) {
		
		try {
			String url = taxinvoiceService.getMailURL(testCorpNum,MgtKeyType.SELL,"1234",testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getMassPrintURL", method = RequestMethod.GET)
	public String getMassPrintURL( Model m) {
		String[] MgtKeyList = new String[] {"1234","12345","123456"};
		
		try {
			
			String url = taxinvoiceService.getMassPrintURL(testCorpNum,MgtKeyType.SELL,MgtKeyList,testUserID);
			
			m.addAttribute("Result",url);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "result";
	}
	
	@RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
	public String getUnitCost( Model m) {
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
		try {
			
			EmailPublicKey[] emailPublicKeys = taxinvoiceService.getEmailPublicKeys(testCorpNum);
			
			m.addAttribute("EmailPublicKeys",emailPublicKeys);
			
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}
		
		return "Taxinvoice/EmailPublicKey";
	}
}
