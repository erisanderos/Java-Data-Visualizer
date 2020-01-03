package com.javaFX;

import java.util.Arrays;

public class ArraySetup {
    //ArraySetup is meant to be a class that gives the user the ability to manipulate data from an excel spreadsheet
    private String[][] mainArray;

    public String[][] setArray(String excelFP){
        String[][] nArray = new ExcelHandling().readExcel(excelFP);
        //call makeExcel with nArray to save to a new file
        this.mainArray = nArray;
        System.out.println(Arrays.deepToString(nArray));
        return nArray;
    }

    public static void sendArray(String excelFP, String[][] nArray){
        new ExcelHandling().makeExcel(excelFP, nArray);
    }

    public String[][] getArray(){
        return this.mainArray;
    }
}
