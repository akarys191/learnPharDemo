package com.pharm.demo.web.reader.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UnsupportedFormatExcel extends Exception{

    protected String filePath;

    public UnsupportedFormatExcel(String message, String s){
        super(message);
        filePath=s;
    }

}
