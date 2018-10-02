package com.epam.spring.spittrMvc.beans;

import com.epam.spring.spittrMvc.interfaces.Command;

public class AsyncCommand implements Command {

    public Object state;
    @Override
    public void setState(Object state) {
        this.state=(Object)state;
    }

    @Override
    public Object execute() {
        return (Object)state;
    }
}
