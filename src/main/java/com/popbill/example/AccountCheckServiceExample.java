/*
 * 팝빌 예금주조회 API Java SDK SpringMVC Example
 *
 * - 업데이트 일자 : 2021-07-06
 * - 연동 기술지원 연락처 : 1600-9854 / 070-4304-2991~2
 * - 연동 기술지원 이메일 : code@linkhub.co.kr
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

import com.popbill.api.AccountCheckInfo;
import com.popbill.api.AccountCheckService;
import com.popbill.api.ChargeInfo;
import com.popbill.api.DepositorCheckInfo;
import com.popbill.api.PopbillException;

@Controller
@RequestMapping("AccountCheckService")
public class AccountCheckServiceExample {
	
	@Autowired
    private AccountCheckService accountCheckService;
	
	// 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String testCorpNum;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "AccountCheck/index";
    }
    
    @RequestMapping(value = "checkAccountInfo", method = RequestMethod.GET)
    public String checkAccountInfo(Model m) {
        /*
         * 1건의 계좌성명을 조회합니다.
         * - https://docs.popbill.com/accountcheck/java/api#CheckAccountInfo
         */

        // 기관코드
        String BankCode = "0004";
        
        // 계좌번호
        String AccountNumber = "9432451175834";

        try {

        	AccountCheckInfo accountInfo = accountCheckService.CheckAccountInfo(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("AccountInfo", accountInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "AccountCheck/checkAccountInfo";
    }
    
    @RequestMapping(value = "checkDepositorInfo", method = RequestMethod.GET)
    public String checkDepositorInfo(Model m) {
        /*
         * 1건의 계좌실명을 조회합니다.
         * - https://docs.popbill.com/accountcheck/java/api#CheckAccountInfo
         */

        // 기관코드
        String BankCode = "0004";
        
        // 계좌번호
        String AccountNumber = "9432451175834";
        
        //등록번호 유형
        String IdentityNumType ="P";
        
        //등록번호
        String IdentityNum = "901112";

        try {

        	DepositorCheckInfo depositorCheckInfo = accountCheckService.CheckDepositorInfo(testCorpNum, BankCode, AccountNumber, IdentityNumType, IdentityNum);

            m.addAttribute("DepositorCheckInfo", depositorCheckInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "AccountCheck/checkDepositorInfo";
    }
    
    @RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
    public String getUnitCost(Model m) {
        /*
         * 예금주 성명 조회시 과금되는 포인트 단가를 확인합니다.
         * - https://docs.popbill.com/accountcheck/java/api#GetUnitCost
         */

        try {

            float unitCost = accountCheckService.getUnitCost(testCorpNum,"성명");

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
         * 예금주조회 API 서비스 과금정보를 확인합니다.
         * - https://docs.popbill.com/accountcheck/java/api#GetChargeInfo
         */

        try {
            ChargeInfo chrgInfo = accountCheckService.getChargeInfo(testCorpNum,"성명");
            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }
    
    @RequestMapping(value = "getUnitCost_", method = RequestMethod.GET)
    public String getUnitCost_(Model m) {
    	/*
    	 * 계좌 실명 조회시 과금되는 포인트 단가를 확인합니다.
    	 * - https://docs.popbill.com/accountcheck/java/api#GetUnitCost
    	 */
    	
    	try {
    		
    		float unitCost = accountCheckService.getUnitCost(testCorpNum,"실명");
    		
    		m.addAttribute("Result", unitCost);
    		
    	} catch (PopbillException e) {
    		m.addAttribute("Exception", e);
    		return "exception";
    	}
    	
    	return "result";
    }
    
    @RequestMapping(value = "getChargeInfo_", method = RequestMethod.GET)
    public String chargeInfo_(Model m) {
    	/*
    	 * 예금주조회 API 서비스 과금정보를 확인합니다.
    	 * - https://docs.popbill.com/accountcheck/java/api#GetChargeInfo
    	 */
    	
    	try {
    		ChargeInfo chrgInfo = accountCheckService.getChargeInfo(testCorpNum,"실명");
    		m.addAttribute("ChargeInfo", chrgInfo);
    		
    	} catch (PopbillException e) {
    		m.addAttribute("Exception", e);
    		return "exception";
    	}
    	
    	return "getChargeInfo";
    }
}
