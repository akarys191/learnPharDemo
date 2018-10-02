package com.epam.spring.spittrMvc.interfaces;

public interface Command {
    public void setState(Object state);
    public Object execute();
}
