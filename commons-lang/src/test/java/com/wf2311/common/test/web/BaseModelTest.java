package com.wf2311.common.test.web;

import com.wf2311.commons.web.BaseModel;
import org.junit.Test;

/**
 * @author wf2311
 * @time 2016/06/03 14:44.
 */
public class BaseModelTest {
    @Test
    public void test1(){

        BaseModel<Object> model=new BaseModel<>();
        model.setCode(2);
        model.setMsg("测试");
        System.out.println(model);
    }
}
