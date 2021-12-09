package com.pharm.demo.order.data.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception{

    private Long id;

    public UserNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
