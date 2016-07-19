package com.wf2311.common.test.lang;

import com.wf2311.commons.qrcode.QrCodeGenerator;
import junit.framework.TestCase;

import java.io.File;

/**
 * @author wf2311
 * @time 2016/07/19 11:12.
 */
public class QrCodeTest extends TestCase {
    public void test1(){
        File file=QrCodeGenerator.create().content("wf2311").name("11111")
                .logoPath("C:\\Users\\wf2311\\Google 云端硬盘\\Google 相册\\wf2311.jpg").generate();
        System.out.println(file.getAbsolutePath());
    }
}
