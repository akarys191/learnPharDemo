package com.pharm.demo.stock.data.exceptions;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class RetailSystemNotFound extends Exception{
    private BigInteger id;

    public RetailSystemNotFound(String s, BigInteger id) {
        super(s);
         this.id = id;
    }
}


