package com.wf2311.commons.lang.utils;

/**
 * @author wf2311
 * @date 2016/01/15 15:01
 */
public interface RMBConsts {
    /**
     * 汉语中数字大写
     */
    String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    String[] CN_UPPER_MONETRAY_UNIT = {"分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟",
            "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟"};
    /**
     * 特殊字符: 整
     */
    String CN_FULL = "整";

    /**
     * 特殊字符: 负
     */
    String CN_NEGATIVE = "负";

    /**
     * 金额的精度，默认值为2
     */
    int MONEY_PRECISION = 2;

    /**
     * 特殊字符：零元整
     */
    String CN_ZERO_FULL = "零元整";
}
