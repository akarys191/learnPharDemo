package com.pharm.demo.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionCatchControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleMyException(Exception ex) {

        ModelAndView model = new ModelAndView();
        model.addObject("errMsg", ex.getMessage());
        model.setViewName("errorView");
        return model;
    }

}