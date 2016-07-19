package com.wf2311.common.test.lang;

import com.wf2311.commons.lang.date.DateUtils;
import com.wf2311.commons.lang.date.Month;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wf2311
 * @time 2016/05/13 12:03.
 */
public class DateTest {
    @Test
    public void test1(){
//        String date=new String("2013年09月06日 12:13");
//        String date=new String("2013:09:06 12");
        String date=new String("2015年03月12号 12点06分05秒");
        String date2=new String("2015-03-12 12:06:05");

        System.out.println(DateUtils.parseToDate(date2).getTime());
    }

    @Test
    public void test2(){
        System.out.println(Month.getMonthByNum(2).getChineseName());
    }

    @Test
    public void test3(){
        String a="aBCd";
        String b="AbcD";
        System.out.println(a.equalsIgnoreCase(b));
    }

    /**
     *
     */
    @Test
    public void test4(){
        String date="2016年5月9日";
        Assert.assertEquals("星期一", DateUtils.getDefinedWeekOfWeek(DateUtils.parseToDate(date)).getChineseName());
    }
}
