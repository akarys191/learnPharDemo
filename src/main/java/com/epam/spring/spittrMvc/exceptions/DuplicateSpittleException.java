package com.epam.spring.spittrMvc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST,
reason="Duplicate Spittle Found")
public class DuplicateSpittleException extends RuntimeException {
}
