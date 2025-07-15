/**
  * 팝빌 Java SDK SpringMVC Example
  *
  * SpringMVC 연동 튜토리얼 안내 : https://developers.popbill.com/guide/taxinvoice/java/getting-started/tutorial?fwn=springmvc
  * 연동 기술지원 연락처 : 1600-9854
  * 연동 기술지원 메일 : code@linkhubcorp.com
  *
  */
package com.popbill.example;

import com.popbill.api.ContactInfo;
import com.popbill.api.CorpInfo;
import com.popbill.api.JoinForm;
import com.popbill.api.PaymentForm;
import com.popbill.api.PaymentHistory;
import com.popbill.api.PaymentHistoryResult;
import com.popbill.api.PaymentResponse;
import com.popbill.api.PopbillException;
import com.popbill.api.RefundForm;
import com.popbill.api.RefundHistory;
import com.popbill.api.RefundHistoryResult;
import com.popbill.api.RefundResponse;
import com.popbill.api.Response;
import com.popbill.api.TaxinvoiceService;
import com.popbill.api.UseHistoryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("BaseService")
public class BaseServiceExample {

	@Autowired
	private TaxinvoiceService taxinvoiceService;

	// 팝빌회원 사업자번호
	@Value("#{EXAMPLE_CONFIG.TestCorpNum}")
	private String CorpNum;

	// 팝빌회원 아이디
	@Value("#{EXAMPLE_CONFIG.TestUserID}")
	private String UserID;

	// 링크아이디
	@Value("#{EXAMPLE_CONFIG.LinkID}")
	private String LinkID;

	@RequestMapping(value = "checkIsMember", method = RequestMethod.GET)
	public String checkIsMember(Model m) throws PopbillException {
		/**
		 * 사업자번호를 조회하여 연동회원 가입여부를 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#CheckIsMember
		 */

		// 팝빌회원 사업자번호, '-' 제외 10자리
		String corpNum = "1234567890";

		try {
			Response response = taxinvoiceService.checkIsMember(corpNum, LinkID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "getBalance", method = RequestMethod.GET)
	public String getBalance(Model m) throws PopbillException {
		/**
		 * 연동회원의 잔여포인트를 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetBalance
		 */

		try {
			double remainPoint = taxinvoiceService.getBalance(CorpNum);
			m.addAttribute("Result", remainPoint);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getPartnerBalance", method = RequestMethod.GET)
	public String getPartnerBalance(Model m) throws PopbillException {
		/**
		 * 파트너의 잔여포인트를 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetPartnerBalance
		 */

		try {
			double remainPoint = taxinvoiceService.getPartnerBalance(CorpNum);
			m.addAttribute("Result", remainPoint);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getUseHistory", method = RequestMethod.GET)
	public String getUseHistory(Model m) throws PopbillException {
		/**
		 * 연동회원의 포인트 사용내역을 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetUseHistory
		 */

		// 검색 시작일자 (형식 : yyyyMMdd)
		String SDate = "20250711";

		// 검색 종료일자 (형식 : yyyyMMdd)
		String EDate = "20250731";

		// 목록 페이지번호 (기본값 1)
		Integer Page = 1;

		// 페이지당 표시할 목록 개수 (기본값 500, 최대 1,000)
		Integer PerPage = 100;

		// 거래일자를 기준으로 하는 목록 정렬 방향 : "D" / "A" 중 택 1
		// └ "D" : 내림차순
		// └ "A" : 오름차순
		// ※ 미입력시 기본값 "D" 처리
		String Order = "D";

		try {
			UseHistoryResult useHistoryResult = taxinvoiceService.getUseHistory(CorpNum, SDate, EDate, Page, PerPage,
					Order, UserID);
			m.addAttribute("UseHistoryResult", useHistoryResult);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "useHistoryResult";
	}

	@RequestMapping(value = "getPaymentHistory", method = RequestMethod.GET)
	public String getPaymentHistory(Model m) throws PopbillException {
		/**
		 * 연동회원의 포인트 결제내역을 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetPaymentHistory
		 */

		// 검색 시작일자 (형식 : yyyyMMdd)
		String SDate = "20250711";

		// 검색 종료일자 (형식 : yyyyMMdd)
		String EDate = "20250731";

		// 목록 페이지번호 (기본값 1)
		Integer Page = 1;

		// 페이지당 표시할 목록 개수 (기본값 500, 최대 1,000)
		Integer PerPage = 100;

		try {
			PaymentHistoryResult paymentHistoryResult = taxinvoiceService.getPaymentHistory(CorpNum, SDate, EDate, Page,
					PerPage, UserID);
			m.addAttribute("PaymentHistoryResult", paymentHistoryResult);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "paymentHistoryResult";
	}

	@RequestMapping(value = "getRefundHistory", method = RequestMethod.GET)
	public String getRefundHistory(Model m) throws PopbillException {
		/**
		 * 연동회원의 포인트 환불신청내역을 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetRefundHistory
		 */

		// 목록 페이지번호 (기본값 1)
		Integer Page = 1;

		// 페이지당 표시할 목록 개수 (기본값 500, 최대 1,000)
		Integer PerPage = 100;

		try {
			RefundHistoryResult refundHistoryResult = taxinvoiceService.getRefundHistory(CorpNum, Page, PerPage, UserID);
			m.addAttribute("RefundHistoryResult", refundHistoryResult);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "refundHistoryResult";
	}

	@RequestMapping(value = "refund", method = RequestMethod.GET)
	public String refund(Model m) throws PopbillException {
		/**
		 * 연동회원 포인트를 환불 신청합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#Refund
		 */

		// 환불신청 객체정보
		RefundForm refundForm = new RefundForm();

		// 담당자명
		refundForm.setContactName("담당자명");

		// 담당자 연락처
		refundForm.setTel("01077777777");

		// 환불 신청 포인트
		refundForm.setRequestPoint("10");

		// 은행명
		refundForm.setAccountBank("국민");

		// 계좌번호
		refundForm.setAccountNum("123123123-123");

		// 예금주명
		refundForm.setAccountName("예금주명");

		// 환불사유
		refundForm.setReason("환불사유");

		try {
			RefundResponse response = taxinvoiceService.refund(CorpNum, refundForm, UserID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "refundResponse";
	}

	@RequestMapping(value = "paymentRequest", method = RequestMethod.GET)
	public String paymentRequest(Model m) throws PopbillException {
		/**
		 * 연동회원 포인트 충전을 위해 무통장입금을 신청합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#PaymentRequest
		 */

		// 입금신청 객체정보
		PaymentForm paymentForm = new PaymentForm();

		// 담당자명
		paymentForm.setSettlerName("담당자명");

		// 담당자 메일
		paymentForm.setSettlerEmail("test@test.com");

		// 담당자 휴대폰
		// └ 무통장 입금 승인 알림톡이 전송될 번호
		paymentForm.setNotifyHP("01012341234");

		// 입금자명
		paymentForm.setPaymentName("입금자명");

		// 결제금액
		paymentForm.setSettleCost("11000");

		try {
			PaymentResponse paymentResponse = taxinvoiceService.paymentRequest(CorpNum, paymentForm, UserID);
			m.addAttribute("PaymentResponse", paymentResponse);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "paymentResponse";
	}

	@RequestMapping(value = "getSettleResult", method = RequestMethod.GET)
	public String getSettleResult(Model m) throws PopbillException {
		/**
		 * 연동회원 포인트 무통장 입금신청내역 1건을 확인합니다.
		 *  - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetSettleResult
		 */

		// 정산코드
		String settleCode = "202507110000000013";

		try {
			PaymentHistory paymentHistory = taxinvoiceService.getSettleResult(CorpNum, settleCode, UserID);
			m.addAttribute("PaymentHistory", paymentHistory);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "paymentHistory";
	}

	@RequestMapping(value = "getPartnerURL", method = RequestMethod.GET)
	public String getPartnerURL(Model m) throws PopbillException {
		/**
		 * 파트너 포인트를 충전하는 팝업 URL을 반환합니다.
		 * - 권장 사이즈 : width = 800px / height = 700px
		 * - 반환되는 URL은 30초 동안만 사용이 가능합니다.
		 * - 반환되는 URL에서만 유효한 세션을 포함하고 있습니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetPartnerURL
		 */

		// 고정값 : "CHRG"
		String TOGO = "CHRG";

		try {
			String url = taxinvoiceService.getPartnerURL(CorpNum, TOGO);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getAccessURL", method = RequestMethod.GET)
	public String getAccessURL(Model m) throws PopbillException {
		/**
		 * 팝빌 회원 로그인 상태의 팝업 URL을 반환합니다.
		 * - 권장 사이즈 : width = 1,280px (최소 1,000px) / height = 800px
		 * - 반환되는 URL은 30초 동안만 사용이 가능합니다.
		 * - 반환되는 URL은 팝빌회원의 로그인 세션을 포함하고 있으니 사용에 유의하여 주시기 바랍니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/api/etc#GetAccessURL
		 */

		try {
			String url = taxinvoiceService.getAccessURL(CorpNum, UserID);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getChargeURL", method = RequestMethod.GET)
	public String getChargeURL(Model m) throws PopbillException {
		/**
		 * 연동회원 포인트를 충전하는 팝업 URL을 반환합니다.
		 * - 권장 사이즈 : width = 800px / height = 700px
		 * - 반환되는 URL은 30초 동안만 사용이 가능합니다.
		 * - 반환되는 URL에서만 유효한 세션을 포함하고 있습니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetChargeURL
		 */

		try {
			String url = taxinvoiceService.getChargeURL(CorpNum, UserID);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getPaymentURL", method = RequestMethod.GET)
	public String getPaymentURL(Model m) throws PopbillException {
		/**
		 * 연동회원 포인트 결제내역 팝업 URL을 반환합니다.
		 * - 권장 사이즈 : width = 1,200px (최소 800px) / height = 600px
		 * - 반환되는 URL은 30초 동안만 사용이 가능합니다.
		 * - 반환되는 URL에서만 유효한 세션을 포함하고 있습니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetPaymentURL
		 */

		try {
			String url = taxinvoiceService.getPaymentURL(CorpNum, UserID);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "getUseHistoryURL", method = RequestMethod.GET)
	public String getUseHistoryURL(Model m) throws PopbillException {
		/**
		 * 연동회원 포인트 사용내역 팝업 URL을 반환합니다.
		 * - 권장 사이즈 : width = 1,200px (최소 800px) / height = 600px
		 * - 반환되는 URL은 30초 동안만 사용이 가능합니다.
		 * - 반환되는 URL에서만 유효한 세션을 포함하고 있습니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetUseHistoryURL
		 */

		try {
			String url = taxinvoiceService.getUseHistoryURL(CorpNum, UserID);
			m.addAttribute("Result", url);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "result";
	}

	@RequestMapping(value = "joinMember", method = RequestMethod.GET)
	public String joinMember(Model m) throws PopbillException {
		/**
		 * 프로그램 공급사의 고객사를 팝빌 연동회원으로 가입하는 API 입니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#JoinMember
		 */

		// 연동회원 객체정보
		JoinForm joinInfo = new JoinForm();

		// 아이디, 6자 이상 50자 미만
		joinInfo.setID("testkorea0328");

		// 비밀번호 (8자 이상 20자 이하) 영문, 숫자, 특수문자 조합
		joinInfo.setPassword("password123!@#");

		// 파트너 링크아이디
		joinInfo.setLinkID(LinkID);

		// 사업자번호 (하이픈 '-' 제외 10 자리)
		joinInfo.setCorpNum("1234567890");

		// 대표자 성명, 최대 100자
		joinInfo.setCEOName("대표자 성명");

		// 회사명, 최대 200자
		joinInfo.setCorpName("회사명");

		// 사업장 주소, 최대 300자
		joinInfo.setAddr("주소");

		// 업태, 최대 100자
		joinInfo.setBizType("업태");

		// 종목, 최대 100자
		joinInfo.setBizClass("종목");

		// 담당자 성명, 최대 100자
		joinInfo.setContactName("담당자 성명");

		// 담당자 메일, 최대 100자
		joinInfo.setContactEmail("test@test.com");

		// 담당자 휴대폰, 최대 20자
		joinInfo.setContactTEL("02-999-9999");

		try {
			Response response = taxinvoiceService.joinMember(joinInfo);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "getContactInfo", method = RequestMethod.GET)
	public String getContactInfo(Model m) throws PopbillException {
		/**
		 * 연동회원에 추가된 담당자 정보를 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#GetContactInfo
		 */

		// 담당자 아이디
		String contactID = "testkorea";

		try {
			ContactInfo response = taxinvoiceService.getContactInfo(CorpNum, contactID, UserID);
			m.addAttribute("ContactInfo", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "getContactInfo";
	}

	@RequestMapping(value = "listContact", method = RequestMethod.GET)
	public String listContact(Model m) throws PopbillException {
		/**
		 * 연동회원에 추가된 담당자 목록을 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#ListContact
		 */

		try {
			ContactInfo[] response = taxinvoiceService.listContact(CorpNum, UserID);
			m.addAttribute("ContactInfos", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "listContact";
	}

	@RequestMapping(value = "updateContact", method = RequestMethod.GET)
	public String updateContact(Model m) throws PopbillException {
		/**
		 * 연동회원에 추가된 담당자 정보를 수정합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#UpdateContact
		 */

		// 담당자 객체정보
		ContactInfo contactInfo = new ContactInfo();

		// 아이디, 6자 이상 50자 미만
		contactInfo.setId("testid");

		// 담당자 성명, 최대 100자
		contactInfo.setPersonName("담당자 수정 테스트");

		// 담당자 휴대폰, 최대 20자
		contactInfo.setTel("070-1234-1234");

		// 담당자 메일, 최대 100자
		contactInfo.setEmail("test1234@test.com");

		// 권한, 1 - 개인권한 / 2 - 읽기권한 / 3 - 회사권한
		contactInfo.setSearchRole(3);

		try {
			Response response = taxinvoiceService.updateContact(CorpNum, contactInfo, UserID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "registContact", method = RequestMethod.GET)
	public String registContact(Model m) throws PopbillException {
		/**
		 * 연동회원에 담당자를 추가합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#RegistContact
		 */

		// 담당자 객체정보
		ContactInfo contactInfo = new ContactInfo();

		// 아이디, 6자 이상 50자 미만
		contactInfo.setId("testid");

		// 비밀번호 (8자 이상 20자 이하) 영문, 숫자, 특수문자 조합
		contactInfo.setPassword("password123!@#");

		// 담당자 성명, 최대 100자
		contactInfo.setPersonName("담당자 성명");

		// 담당자 휴대폰, 최대 20자
		contactInfo.setTel("070-1234-1234");

		// 담당자 메일, 최대 100자
		contactInfo.setEmail("test1234@test.com");

		// 권한, 1 - 개인권한 / 2 - 읽기권한 / 3 - 회사권한
		contactInfo.setSearchRole(3);

		try {
			Response response = taxinvoiceService.registContact(CorpNum, contactInfo, UserID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "checkID", method = RequestMethod.GET)
	public String checkID(Model m) throws PopbillException {
		/**
		 * 사용하고자 하는 아이디의 중복여부를 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#CheckID
		 */

		// 중복여부를 확인할 아이디
		String CheckID = "testkorea";

		try {
			Response response = taxinvoiceService.checkID(CheckID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "getCorpInfo", method = RequestMethod.GET)
	public String getCorpInfo(Model m) throws PopbillException {
		/**
		 * 연동회원의 회사정보를 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#GetCorpInfo
		 */

		try {
			CorpInfo response = taxinvoiceService.getCorpInfo(CorpNum, UserID);
			m.addAttribute("CorpInfo", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "getCorpInfo";
	}

	@RequestMapping(value = "quitMember", method = RequestMethod.GET)
	public String quitMember(Model m) throws PopbillException {
		/**
		 * 팝빌 연동회원을 탈퇴 처리합니다.
		 *  - 관리자를 포함한 모든 담당자가 일괄 삭제 처리됩니다.
		 *  - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#QuitMember
		 */

		// 회원 탈퇴 사유
		String quitReason = "탈퇴 테스트 입니다";

		try {
			Response response = taxinvoiceService.quitMember(CorpNum, quitReason, UserID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "updateCorpInfo", method = RequestMethod.GET)
	public String updateCorpInfo(Model m) throws PopbillException {
		/**
		 * 연동회원의 회사정보를 수정합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#UpdateCorpInfo
		 */

		// 회사 객체정보
		CorpInfo corpInfo = new CorpInfo();

		// 대표자 성명, 최대 100자
		corpInfo.setCeoname("대표자 성명 수정 테스트");

		// 회사명, 최대 200자
		corpInfo.setCorpName("회사명 수정 테스트");

		// 주소, 최대 300자
		corpInfo.setAddr("주소 수정 테스트");

		// 업태, 최대 100자
		corpInfo.setBizType("업태 수정 테스트");

		// 종목, 최대 100자
		corpInfo.setBizClass("종목 수정 테스트");

		try {
			Response response = taxinvoiceService.updateCorpInfo(CorpNum, corpInfo, UserID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

	@RequestMapping(value = "getRefundInfo", method = RequestMethod.GET)
	public String getRefundInfo(Model m) throws PopbillException {
		/**
		 * 포인트 환불에 대한 상세정보 1건을 확인합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetRefundInfo
		 */

		// 환불코드
		String refundCode = "025070000028";

		try {
			RefundHistory response = taxinvoiceService.getRefundInfo(CorpNum, refundCode, UserID);
			m.addAttribute("RefundHistory", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "refundHistory";
	}

	@RequestMapping(value = "getRefundableBalance", method = RequestMethod.GET)
	public String getRefundableBalance(Model m) throws PopbillException {
		/**
		 * 환불 가능한 포인트를 확인합니다. (보너스 포인트는 환불가능포인트에서 제외됩니다.)
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/point#GetRefundableBalance
		 */

		try {
			double refundableBalance = taxinvoiceService.getRefundableBalance(CorpNum, UserID);
			m.addAttribute("refundableBalance", refundableBalance);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "refundableBalance";
	}


	@RequestMapping(value = "deleteContact", method = RequestMethod.GET)
	public String deleteContact(Model m) throws PopbillException {
		/**
		 * 연동회원에 추가된 담당자를 삭제합니다.
		 * - https://developers.popbill.com/reference/taxinvoice/java/common-api/member#DeleteContact
		 */

		// 삭제할 담당자 아이디
		String contactID = "";

		try {
			Response response = taxinvoiceService.deleteContact(CorpNum, contactID, UserID);
			m.addAttribute("Response", response);
		} catch (PopbillException e) {
			m.addAttribute("Exception", e);
			return "exception";
		}

		return "response";
	}

}