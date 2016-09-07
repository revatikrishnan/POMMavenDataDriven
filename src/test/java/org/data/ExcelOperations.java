package org.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelOperations {

    public static Hashtable<String, String> dataHash[];
    public static Hashtable<String, String> statusHash[];
    public static String[] arrColNames ;
    public static int rowCount;
    
    
    public static void reset(){
         dataHash=null;
         statusHash=null;
         arrColNames=null ;
         rowCount=0;
    }
    
    public static int getRowCount(String filePath,String sheetName) {    
        FileInputStream fis;
        Workbook wb;
        int rowCount=0;
        try {
            fis = new FileInputStream(filePath);
            wb = WorkbookFactory.create(fis);
            Sheet sh = wb.getSheet(sheetName);
            rowCount = sh.getLastRowNum()+1;
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        return rowCount;
    }
    
    @SuppressWarnings({ "unchecked", "resource" })
    public static void readData(String excelPath,String sheetname,String testCaseId){
        try{
        
        Workbook wb = new HSSFWorkbook(new FileInputStream(new File(excelPath)));
        Sheet sheet=wb.getSheet(sheetname);
        
         rowCount =getRowCount(excelPath,sheetname);
        //System.out.println(rowCount);
        
        if (rowCount > 0) {
            dataHash = new Hashtable[rowCount];
            statusHash = new Hashtable[rowCount];
        }
        
        /*
         * In this code chuck i am getting the first row which has the headers/column names
         * and storing the index which has column name 'STAT' , so that i can separate the 
         * input and output into different array
         */
        Row headerRow=sheet.getRow(0);
        int colCount=headerRow.getLastCellNum();
        //System.out.println(colCount);
        int indexSTAT=0;
        arrColNames= new String[headerRow.getLastCellNum()];
        for(int c=0;c<colCount;c++){
            arrColNames[c] =headerRow.getCell(c).getStringCellValue();
            if(arrColNames[c].contains("STAT")){
                indexSTAT=c;
            }
        }
        //System.out.println(index);
        /*//this code will display all the column names
         for(String each:arrColNames){
            System.out.println(each);
        }*/
        
       
        //here i am iterating from row value 1, as first row has only headers
        for(int r=1;r<rowCount;r++){
            Row row=sheet.getRow(r);
            Hashtable<String, String> eachdata=new Hashtable<String,String>();
            Hashtable<String, String> eachresult=new Hashtable<String,String>();
            //here i am only picking those testcases with a specific testid
            if(row.getCell(0).getStringCellValue().toString().equals(testCaseId)){
                for(int i=0;i<indexSTAT;i++){
                    eachdata.put(arrColNames[i], row.getCell(i).getStringCellValue());  
                }
                for(int j=indexSTAT+1;j<colCount;j++){
                    eachresult.put(arrColNames[j], row.getCell(j).getStringCellValue());  
                }
            }
            //System.out.println(eachdata);
            //System.out.println(eachresult);
            dataHash[r]=eachdata;
            statusHash[r]=eachresult;
        }
        }
        catch(Exception e){
            System.out.println("DEBUG:ERROR while reading from excel "+e.getMessage());
        }
        
    }
    
    
}
