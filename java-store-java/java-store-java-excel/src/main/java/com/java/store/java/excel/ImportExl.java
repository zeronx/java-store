package com.java.store.java.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import freemarker.template.Configuration;
import freemarker.template.Template;    
  
/**
 * 
 * @author Administrator
 *
 */
public class ImportExl {  
      
    public void read_Excel() throws UnsupportedEncodingException, SQLException, ClassNotFoundException {  
    	try {   
	        FileInputStream input = new FileInputStream(new File("D:\\temp\\templateOfImportingExportHg.xls"));  //读取的文件路径   
	        //读写xls和xlsx格式时，HSSFWorkbook针对xls，XSSFWorkbook针对xlsx
	        HSSFWorkbook wb = new HSSFWorkbook(input);   
	        int sheet_numbers = wb.getNumberOfSheets();//获取表的总数  
	        String[] sheetnames=new String[sheet_numbers];  
	        System.out.println("表数（sheet） = " + sheet_numbers);
	        for(int i = 0; i < sheet_numbers; i++) {//遍历所有表               
	            HSSFSheet sheet = wb.getSheetAt(i);  //获取 某个表       
	            sheetnames[i] = sheet.getSheetName();//获取表名，存入数组  
	            System.out.println("------>>>---正在读取Excel表数据，当前表：" + sheetnames[i]); 
	            System.out.println("行数  = " + sheet.getLastRowNum());
	           
	            for( int rows = 0; rows <= sheet.getLastRowNum(); rows++) {//有多少行  
	                HSSFRow row = sheet.getRow(rows);//取得某一行   对象   
	                if (row == null) {
	                	return ;
	                }
	                for( int columns = 0; columns < row.getLastCellNum(); columns++) {//读取所有列  
	                    //s[columns] = row.getCell(columns).getStringCellValue(); //取得某单元格内容，字符串类型   
	                    HSSFCell  cell = row.getCell(columns);   
	                    System.out.print("cell = " + ((cell != null && !cell.toString().isEmpty()) ? cell.toString().substring(0,1) : null) + "\t");
	                }  
	                System.out.println();
	            }  
	            
	            input.close();    
	        }              
	    } catch (IOException ex) {    
	        ex.printStackTrace();    
	    }  
    }  
    public Template getTemplate(String name) {
        try {
            // 通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("utf-8");
            // 设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(this.getClass(), "/");
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            System.out.println("======返回template==========");
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("==========出粗======");
        return null;
    }

    public void testFreemarket() {
    	System.out.println("========开始========");
    	Template template = this.getTemplate("templateOfImportingExportHg.ftl");
    	template.setEncoding("utf-8");
    	Map<String,Object> map = new HashMap<>();
    	try {
    		File outFile = new File("D:\\temp\\" + "test.xls");
            Writer writer = null;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), Charset.forName("utf-8")));//此处为输 出文档编码
			map.put("bah", "备案号测试数据二位465464");
			template.process(map, writer);
			writer.flush();
			writer.close();
			System.out.println("到出成功！！");
		} catch (Exception e) {
		}
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
		new ImportExl().testFreemarket();
	}
} 
