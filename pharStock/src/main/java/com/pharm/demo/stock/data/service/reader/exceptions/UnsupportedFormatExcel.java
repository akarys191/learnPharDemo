package com.pharm.demo.stock.data.service.reader.exceptions;

import lombok.Getter;

import java.io.File;

@Getter
public class UnsupportedFormatExcel extends Exception{

    protected final File file;

    public UnsupportedFormatExcel(String message, File s){
        super(message);
        file=s;
    }

}
