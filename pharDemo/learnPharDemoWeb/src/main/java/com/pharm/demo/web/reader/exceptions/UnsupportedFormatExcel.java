package com.pharm.demo.web.reader.exceptions;

import lombok.Getter;

@Getter
public class UnsupportedFormatExcel extends Exception{

    protected final String filePath;

    public UnsupportedFormatExcel(String message, String s){
        super(message);
        filePath=s;
    }

}
