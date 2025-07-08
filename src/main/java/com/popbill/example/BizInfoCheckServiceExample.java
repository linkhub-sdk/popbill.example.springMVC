/**
  * 팝빌 기업정보조회 API Java SDK SpringMVC Example
  *
  * SpringMVC 연동 튜토리얼 안내 : https://developers.popbill.com/guide/bizinfocheck/java/getting-started/tutorial?fwn=springmvc
  * 연동 기술지원 연락처 : 1600-9854
  * 연동 기술지원 이메일 : code@linkhubcorp.com
  *
  */
package com.popbill.example;

import java.util.Locale;
import com.popbill.api.BizCheckInfo;
import com.popbill.api.BizInfoCheckService;
import com.popbill.api.ChargeInfo;
import com.popbill.api.PopbillException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 팝빌 기업정보조회 API 예제.
 */
@Controller
@RequestMapping("BizInfoCheckService")
public class BizInfoCheckServiceExample {

    @Autowired
    private BizInfoCheckService bizInfoCheckService;

    // 팝빌회원 사업자번호
    @Value("#{EXAMPLE_CONFIG.TestCorpNum}")
    private String CorpNum;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "BizInfoCheck/index";
    }

    @RequestMapping(value = "checkBizInfo", method = RequestMethod.GET)
    public String checkBizInfo(Model m) {
        /**
         * 사업자번호 1건에 대한 기업정보를 확인합니다.
         * 기업정보조회 상태코드 [https://developers.popbill.com/reference/bizinfocheck/java/response-code#result-code]
         * - https://developers.popbill.com/reference/bizinfocheck/java/api/check#CheckBizInfo
         */

        // 조회할 사업자번호
        String CorpNum = "6798700433";

        try {
            BizCheckInfo bizInfo = bizInfoCheckService.CheckBizInfo(CorpNum, CorpNum);
            m.addAttribute("BizInfo", bizInfo);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "BizInfoCheck/checkBizInfo";
    }

    @RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
    public String getUnitCost(Model m) {
        /**
         * 기업정보 조회시 과금되는 포인트 단가를 확인합니다.
         * - https://developers.popbill.com/reference/bizinfocheck/java/common-api/point#GetUnitCost
         */

        try {
            float unitCost = bizInfoCheckService.getUnitCost(CorpNum);
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
         * 팝빌 기업정보조회 API 서비스 과금정보를 확인합니다.
         * - https://developers.popbill.com/reference/bizinfocheck/java/common-api/point#GetChargeInfo
         */

        try {
            ChargeInfo chrgInfo = bizInfoCheckService.getChargeInfo(CorpNum);
            m.addAttribute("ChargeInfo", chrgInfo);
        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

}
