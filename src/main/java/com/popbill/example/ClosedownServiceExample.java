/*
 * 팝빌 휴폐업조회 API Java SDK SpringMVC Example
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

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.popbill.api.ChargeInfo;
import com.popbill.api.CloseDownService;
import com.popbill.api.CorpState;
import com.popbill.api.PopbillException;

/**
 * 팝빌 휴폐업조회 API 예제.
 */
@Controller
@RequestMapping("CloseDownService")
public class ClosedownServiceExample {

    @Autowired
    private CloseDownService closedownService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;

    // 팝빌회원 아이디
    @Value("#{EXAMPLE_CONFIG.TestUserID}")
    private String testUserID;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "Closedown/index";
    }

    @RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
    public String getUnitCost(Model m) {
        /**
         * 휴폐업조회 단가를 확인합니다.
         */

        try {

            float unitCost = closedownService.getUnitCost(testCorpNum);

            m.addAttribute("Result", unitCost);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
    public String chargeInfo(Model m) {
        /**
         * 연동회원의 휴폐업조회 API 서비스 과금정보를 확인합니다.
         */

        try {
            ChargeInfo chrgInfo = closedownService.getChargeInfo(testCorpNum);
            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

    @RequestMapping(value = "checkCorpNum", method = RequestMethod.GET)
    public String checkCorpNum(@RequestParam(required = false) String CorpNum, Model m) {
        /**
         * 1건의 사업자에 대한 휴폐업여부를 조회합니다.
         */

        if (CorpNum != null && CorpNum != "") {

            try {
                CorpState corpState = closedownService.CheckCorpNum(testCorpNum, CorpNum);

                m.addAttribute("CorpState", corpState);

            } catch (PopbillException e) {
                m.addAttribute("Exception", e);
                return "exception";
            }

        } else {


        }
        return "Closedown/checkCorpNum";
    }

    @RequestMapping(value = "checkCorpNums", method = RequestMethod.GET)
    public String checkCorpNums(Model m) {
        /**
         * 다수의 사업자에 대한 휴폐업여부를 조회합니다.
         */

        // 조회할 사업자번호 배열, 최대 1000건
        String[] CorpNumList = new String[]{"1234567890", "6798700433"};

        try {

            CorpState[] corpStates = closedownService.CheckCorpNum(testCorpNum, CorpNumList);

            m.addAttribute("CorpStates", corpStates);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "Closedown/checkCorpNums";
    }
}
