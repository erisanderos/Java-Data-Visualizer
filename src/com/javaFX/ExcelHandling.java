package com.javaFX;

import java.io.FileInputStream;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandling {
    //makeExcel takes newArray and a path name, and then creates a new excel sheet using the array
    public static void makeExcel(String excelPath, String[][] newArray) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Excel test");
        int rownum = 0;
        //Nested for loops iterate through newArray, and assign each element to a cell
        //Iterate through rows
        for(int r = 0; r < newArray.length; r++){
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            //Iterate through columns
            for(int c = 0; c < newArray[r].length; c++){
                Cell cell = row.createCell(cellnum++);
                cell.setCellValue((String) newArray[r][c]);
                //Print statement to test previous code
                System.out.print(newArray[r][c] + " ");
            }
        }

        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(excelPath));
            workbook.write(out);
            out.close();
            System.out.println(excelPath + "written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //readExcel takes an excel file path and reads the contents into an array, and then returns it.
    public static String[][] readExcel(String rFilePath) {
        String[][] stringArray = new String[0][];
        try {
            int cn = 0;
            FileInputStream rFile = new FileInputStream(new File(rFilePath));
            XSSFWorkbook rWorkbook = new XSSFWorkbook(rFile);
            XSSFSheet rSheet = rWorkbook.getSheetAt(cn);
            Iterator<Row> rowIterator = rSheet.iterator();
            int sheetCn = rWorkbook.getNumberOfSheets();
            stringArray = null;

            // for loop to calculate number of cells and set size of array
            for (cn = 0; cn < sheetCn; cn++) {
                int rows = rSheet.getPhysicalNumberOfRows();
                int cells = rSheet.getRow(cn).getPhysicalNumberOfCells();
                stringArray = new String[rows][cells];
                //System.out.print(rows + "x" + cells + "");
            }
            //Nested for loops to iterate through the rows and columns,
            // and assign each cell value to an array element.
            for (int r = 0; rowIterator.hasNext(); r++){
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                for (int c = 0; cellIterator.hasNext(); c++){
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            stringArray[r][c] =
                                    String.valueOf(cell.getNumericCellValue());
                            break;
                        case STRING:
                            stringArray[r][c] = cell.getStringCellValue();
                            break;

                    }
                }

            }
            rFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringArray;
    }

}
