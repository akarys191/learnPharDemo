package com.pharm.demo.web.model.column;

public class OrderTO {
    private String column;
    private String dir;

    public OrderTO(String column, String dir) {
        this.column = column;
        this.dir = dir;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
