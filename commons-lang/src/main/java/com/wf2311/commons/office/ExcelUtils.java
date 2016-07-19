package com.wf2311.commons.office;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用导出excel文件类
 *
 * @author wf2311
 * @time 2016/06/24 14:21.
 */
public class ExcelUtils {
    /**
     * 导出excel文件
     *
     * @param fileName    EXCEL文件名称
     * @param titles      EXCEL文件第一行列标题集合
     * @param listContent EXCEL文件正文数据集合
     * @param file        请求
     * @return 返回提示信息
     */
    @SuppressWarnings("rawtypes")
    public final static String exportExcel( String[] titles,
                                           List<Object> listContent, File file) {
        String result = "系统提示：Excel文件导出成功！";
        // 以下开始输出到EXCEL
        try {

            /** **********创建工作簿************ */
            WritableWorkbook workbook = Workbook.createWorkbook(file);

            /** **********创建工作表************ */

            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            /** **********设置纵横打印（默认为纵打）、打印纸***************** */
            jxl.SheetSettings sheetset = sheet.getSettings();
            sheetset.setProtected(false);

            /** ************设置单元格字体************** */
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.BOLD);

            /** ************以下设置三种单元格样式，灵活备用************ */
            // 用于标题居中
            WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_center.setWrap(false); // 文字是否换行

            // 用于正文居左
            WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
            wcf_left.setWrap(false); // 文字是否换行

            /** ***************以下是EXCEL开头大标题，暂时省略********************* */
            // sheet.mergeCells(0, 0, colWidth, 0);
            // sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
            /** ***************以下是EXCEL第一行列标题********************* */
            for (int i = 0; i < titles.length; i++) {
                sheet.addCell(new Label(i, 0, titles[i], wcf_center));
            }
            /** ***************以下是EXCEL正文数据********************* */
            int i = 1;
            for (Object obj : listContent) {
                int j = 0;
                for (Object field : (Object[]) obj) {
                    if (field instanceof Map) {
                        if (field != null) {
                            Map temp = (Map) field;
                            if (temp.get("format") != null && temp.get("format") instanceof WritableCellFormat) {
                                sheet.addCell(new Label(j, i, field != null ? String.valueOf(temp.get("value")) : "", (WritableCellFormat) temp.get("format")));
                            } else {
                                sheet.addCell(new Label(j, i, field != null ? String.valueOf(field) : "", wcf_left));
                            }
                        }
                    } else {
                        sheet.addCell(new Label(j, i, field != null ? String.valueOf(field) : "", wcf_left));
                    }

                    j++;
                }
                i++;
            }
            /** **********将以上缓存中的内容写到EXCEL文件中******** */
            workbook.write();
            /** *********关闭文件************* */
            workbook.close();

        } catch (Exception e) {
            result = "系统提示：Excel文件导出失败，原因：" + e.toString();
            System.out.println(result);
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String[] titles=new String[]{"二二","房贷首付","发射点发生","地方大师傅","法撒旦飞洒地方","发士大夫十分"};
        List<Object> list=new ArrayList<>();
        for (int i=0;i<100;i++){
            exportExcel(titles,list,new File("V:\\test.xlsx"));
        }
    }
}
