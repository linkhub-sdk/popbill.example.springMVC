/*
 * 팝빌 Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : http://blog.linkhub.co.kr/591/
 * - 업데이트 일자 : 2017-11-14
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.ContactInfo;
import com.popbill.api.CorpInfo;
import com.popbill.api.JoinForm;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.TaxinvoiceService;

/**
 * 팝빌 BaseService API 예제.
 */
@Controller
@RequestMapping("BaseService")
public class BaseServiceExample {

    @Autowired
    private TaxinvoiceService taxinvoiceService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    // 링크아이디
    @Value("#{EXAMPLE_CONFIG.LinkID}")
    private String testLinkID;

    @RequestMapping(value = "checkIsMember", method = RequestMethod.GET)
    public String checkIsMember(Model m) throws PopbillException {
        /*
         *  해당 사업자의 파트너 연동회원 가입여부를 확인합니다.
         * - LinkID는 인증정보로 설정되어 있는 링크아이디 값입니다.
         */

        // 조회할 사업자번호, '-' 제외 10자리
        String corpNum = "1234567890";

        try {
            Response response = taxinvoiceService.checkIsMember(corpNum, testLinkID);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "getBalance", method = RequestMethod.GET)
    public String getBalance(Model m) throws PopbillException {
        /*
         * 연동회원의 잔여포인트를 확인합니다.
         * - 과금방식이 파트너과금인 경우 파트너 잔여포인트(GetPartnerBalance API)
         *   를 통해 확인하시기 바랍니다.
         */

        try {
            double remainPoint = taxinvoiceService.getBalance(testCorpNum);

            m.addAttribute("Result", remainPoint);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getPartnerBalance", method = RequestMethod.GET)
    public String getPartnerBalance(Model m) throws PopbillException {
        /*
         * 파트너의 잔여포인트를 확인합니다.
         * - 과금방식이 연동과금인 경우 연동회원 잔여포인트(GetBalance API)를
         *   이용하시기 바랍니다.
         */

        try {
            double remainPoint = taxinvoiceService.getPartnerBalance(testCorpNum);

            m.addAttribute("Result", remainPoint);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getPartnerURL", method = RequestMethod.GET)
    public String getPartnerURL(Model m) throws PopbillException {
        /*
         * 파트너 포인트 충전 팝업 URL을 반환합니다.
         * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
         */

        // CHRG : 포인트 충전
        String TOGO = "CHRG";

        try {

            String url = taxinvoiceService.getPartnerURL(testCorpNum, TOGO);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getAccessURL", method = RequestMethod.GET)
    public String getAccessURL(Model m) throws PopbillException {
        /*
         * 팝빌 로그인 URL을 반환합니다.
         * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
         */
        try {

            String url = taxinvoiceService.getAccessURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getChargeURL", method = RequestMethod.GET)
    public String getChargeURL(Model m) throws PopbillException {
        /*
         * 팝빌 연동회원 포인트충전 팝업 URL을 반환합니다.
         * - 보안정책에 따라 반환된 URL은 30초의 유효시간을 갖습니다.
         */
        try {

            String url = taxinvoiceService.getChargeURL(testCorpNum, testUserID);

            m.addAttribute("Result", url);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "joinMember", method = RequestMethod.GET)
    public String joinMember(Model m) throws PopbillException {
        /*
         * 파트너의 연동회원으로 회원가입을 요청합니다.
         */

        JoinForm joinInfo = new JoinForm();

        // 링크아이디
        joinInfo.setLinkID(testLinkID);

        // 사업자등록번호
        joinInfo.setCorpNum("1234567890");

        // 대표자성명
        joinInfo.setCEOName("대표자성명");

        // 상호
        joinInfo.setCorpName("상호");

        // 주소
        joinInfo.setAddr("주소");

        // 업태
        joinInfo.setBizType("업태");

        // 종목
        joinInfo.setBizClass("종목");

        // 팝빌회원 아이디
        joinInfo.setID("testkorea0328");

        // 팝빌회원 비밀번호
        joinInfo.setPWD("pwd_must_be_long_enough");

        // 담당자명
        joinInfo.setContactName("담당자명");

        // 담당자 연락처
        joinInfo.setContactTEL("02-999-9999");

        // 담당자 휴대폰번호
        joinInfo.setContactHP("010-111-222");

        // 담당자 팩스번호
        joinInfo.setContactFAX("02-000-111");

        // 담당자 메일주소
        joinInfo.setContactEmail("test@test.com");

        try {

            Response response = taxinvoiceService.joinMember(joinInfo);

            m.addAttribute("Response", response);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }

    @RequestMapping(value = "listContact", method = RequestMethod.GET)
    public String listContact(Model m) throws PopbillException {
        /*
         * 연동회원의 담당자 목록을 확인합니다.
         */

        try {
            ContactInfo[] response = taxinvoiceService.listContact(testCorpNum);

            m.addAttribute("ContactInfos", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "listContact";
    }

    @RequestMapping(value = "updateContact", method = RequestMethod.GET)
    public String updateContact(Model m) throws PopbillException {
        /*
         * 연동회원의 담당자 정보를 수정합니다.
         */

        ContactInfo contactInfo = new ContactInfo();

        // 담당자 아이디
        contactInfo.setId(testUserID);

        // 담당자 이메일주소
        contactInfo.setEmail("test1234@test.com");

        // 담당자 팩스번호
        contactInfo.setFax("070-4304-2991");

        // 담당자 휴대폰번호
        contactInfo.setHp("010-1234-1234");

        // 담당자명
        contactInfo.setPersonName("담당지 수정 테스트");

        // 담당자 연락처
        contactInfo.setTel("070-1234-1234");

        // 회사조회 권한여부, true-회사조회, false-개인조회
        contactInfo.setSearchAllAllowYN(true);

        try {

            Response response = taxinvoiceService.updateContact(testCorpNum,
                    contactInfo, testUserID);

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
         * 연동회원의 담당자를 신규로 등록합니다.
         */

        ContactInfo contactInfo = new ContactInfo();

        // 담당자 아이디
        contactInfo.setId("testkorea");

        // 담당자 비밀번호
        contactInfo.setPwd("test12341234");

        // 담당자 이메일주소
        contactInfo.setEmail("test1234@test.com");

        // 담당자 팩스번호
        contactInfo.setFax("070-4304-2991");

        // 담당자 휴대폰번호
        contactInfo.setHp("010-1234-1234");

        // 담당자명
        contactInfo.setPersonName("담당지 수정 테스트");

        // 담당자 연락처
        contactInfo.setTel("070-1234-1234");

        try {

            Response response = taxinvoiceService.registContact(testCorpNum,
                    contactInfo);

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
         * 팝빌 회원아이디 중복여부를 확인합니다.
         */

        try {

            Response response = taxinvoiceService.checkID(testUserID);
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
         */

        try {
            CorpInfo response = taxinvoiceService.getCorpInfo(testCorpNum);
            m.addAttribute("CorpInfo", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getCorpInfo";
    }

    @RequestMapping(value = "updateCorpInfo", method = RequestMethod.GET)
    public String updateCorpInfo(Model m) throws PopbillException {
        /**
         * 연동회원의 회사정보를 수정합니다
         */

        CorpInfo corpInfo = new CorpInfo();

        // 주소, 최대 300자
        corpInfo.setAddr("주소 수정 테스트");

        // 종목, 최대 40자
        corpInfo.setBizClass("업종 수정 테스트");

        // 업태, 최대 40자
        corpInfo.setBizType("업태 수정 테스트");

        // 대표자 성명, 최대 30자
        corpInfo.setCeoname("대표자명 수정 테스트");

        // 상호, 최대 70자
        corpInfo.setCorpName("상호 수정 테스트");

        try {
            Response response = taxinvoiceService.updateCorpInfo(testCorpNum,
                    corpInfo);
            m.addAttribute("Response", response);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "response";
    }
}
