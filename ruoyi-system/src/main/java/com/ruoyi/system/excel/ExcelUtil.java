package com.ruoyi.system.excel;


import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class ExcelUtil<T> {
       public void export(String sheetName, HttpServletResponse response,Class<T>  clazz , List<T> list) throws IOException, IllegalAccessException {
              response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
              response.setCharacterEncoding("utf-8");
              SXSSFWorkbook sheets = new SXSSFWorkbook(500);
              SXSSFSheet sheet = sheets.createSheet();
              sheets.setSheetName(0, sheetName);
              ArrayList<Field> fields = new ArrayList<>();
              fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
              fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
              SXSSFRow row = sheet.createRow(0);
              int rowNum=1;
              int excelRow=0;
              // 将属性作为第一排输入
              for (Field field : fields) {
                      if(field.isAnnotationPresent(Excel.class))
                      {
                             Excel annotation = field.getAnnotation(Excel.class);
                             String name = annotation.name();
                             SXSSFCell cell = row.createCell(excelRow);
                             cell.setCellValue(name);
                             excelRow++;
                      }
              }
              // 将数据输入
              for (T t : list) {
                     SXSSFRow row1 = sheet.createRow(rowNum);
                     excelRow=0;

                     for (Field field : fields) {
                            field.setAccessible(true);
                            Object o = field.get(t);
                            SXSSFCell cell = row1.createCell(excelRow);
                            if(o!=null)
                            cell.setCellValue(o instanceof String ? (String)o : o.toString());
                            excelRow++;
                     }
                     rowNum++;
              }
              // 输出
              sheets.write(response.getOutputStream());
       }

}
