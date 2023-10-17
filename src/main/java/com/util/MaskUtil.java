package com.util;

import cn.hutool.core.util.DesensitizedUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hutool数据脱敏工具
 *
 * @Classname: DesensitizedUtil
 * @Date: 2023/10/17 下午 03:39
 * @Author: kalam_au
 * @Description:
 */


public class MaskUtil {
    private static final Logger log = LogManager.getLogger(MaskUtil.class.getName());


    public static void main(String[] args) {
        // testPhoneDesensitization();
        // testBankCardDesensitization();
        // testIdCardNumDesensitization();
        // testPasswordDesensitization();
        String bankCard = "6266 2345 345";

        System.out.println(bankCard(bankCard));
    }

    /**
     * mask mobile 头三尾四可见
     *
     * @param mobile
     * @return
     */
    public static String mobile(String mobile) {
        return DesensitizedUtil.mobilePhone(mobile);
    }

    /**
     * 银行卡 头四和尾四以内可见, 中间以四位一组mask,
     *
     * @param cardNumber
     * @return
     */
    public static String bankCard(String cardNumber) {
        return DesensitizedUtil.bankCard(cardNumber);
    }

    /**
     * 自定义mask
     *
     * @param value
     * @param start
     * @param end
     * @return
     */
    public static String getCustomMaskValue(String value, int start, int end) {
        return DesensitizedUtil.idCardNum(value, start, end); //输出：4110************21
    }

    /** 全部mask
     * @param password
     * @return
     */
    public static String getDesensitization(String password) {
        return DesensitizedUtil.password(password); //输出：****************
    }

}

