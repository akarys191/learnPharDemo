package com.pharm.demo.order.data.exceptions;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends Exception{
    private Long id;

    public OrderNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
