/*
 * 팝빌 기업정보조회 API Java SDK SpringMVC Example
 *
 * - SpringMVC SDK 연동환경 설정방법 안내 : https://docs.popbill.com/bizinfocheck/tutorial/java
 * - 업데이트 일자 : 2022-09-23
 * - 연동 기술지원 연락처 : 1600-9854
 * - 연동 기술지원 이메일 : code@linkhubcorp.com
 *
 * <테스트 연동개발 준비사항>
 * 1) src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에 선언된
 *    util:properties 의 링크아이디(LinkID)와 비밀키(SecretKey)를 연동신청 시 메일로
 *    발급받은 인증정보를 참조하여 변경합니다.
 *
 * Copyright 2006-2020 linkhub.co.kr, Inc. or its affiliates. All Rights Reserved.
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

import com.popbill.api.BizCheckInfo;
import com.popbill.api.BizInfoCheckService;
import com.popbill.api.ChargeInfo;
import com.popbill.api.PopbillException;

/*
 * 팝빌 기업정보조회 API 예제.
 */
@Controller
@RequestMapping("BizInfoCheckService")
public class BizInfoCheckServiceExample {

    @Autowired
    private BizInfoCheckService bizInfoCheckService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "BizInfoCheck/index";
    }

    @RequestMapping(value = "checkBizInfo", method = RequestMethod.GET)
    public String checkBizInfo(Model m) {
        /*
         * 사업자번호 1건에 대한 기업정보를 확인합니다.
         * - https://docs.popbill.com/bizinfocheck/java/api#CheckBizInfo
         */


        // 조회할 사업자번호
        String CorpNum = "6798700433";

        try {
            BizCheckInfo bizInfo = bizInfoCheckService.CheckBizInfo(testCorpNum, CorpNum);
            m.addAttribute("BizInfo", bizInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "BizInfoCheck/checkBizInfo";
    }

    @RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
    public String getUnitCost(Model m) {
        /*
         * 기업정보조회 시 과금되는 포인트 단가를 확인합니다.
         * - https://docs.popbill.com/bizinfocheck/java/api#GetUnitCost
         */

        try {
            float unitCost = bizInfoCheckService.getUnitCost(testCorpNum);
            m.addAttribute("Result", unitCost);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
    public String chargeInfo(Model m) {
        /*
         * 팝빌 기업정보조회 API 서비스 과금정보를 확인합니다.
         * - https://docs.popbill.com/bizinfocheck/java/api#GetChargeInfo
         */

        try {
            ChargeInfo chrgInfo = bizInfoCheckService.getChargeInfo(testCorpNum);
            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

}