package com.pharm.demo.web.model.raw;

import java.util.HashMap;
import java.util.List;

public class RawDataTableRequestDTO {

    private int draw;
    private List<HashMap<String, String>> columns;
    private List<HashMap<String, String>> order;
    private int start;
    private int length;
    private String myKey;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<HashMap<String, String>> getColumns() {
        return columns;
    }

    public void setColumns(List<HashMap<String, String>> columns) {
        this.columns = columns;
    }

    public List<HashMap<String, String>> getOrder() {
        return order;
    }

    public void setOrder(List<HashMap<String, String>> order) {
        this.order = order;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMyKey() {
        return myKey;
    }

    public void setMyKey(String myKey) {
        this.myKey = myKey;
    }

    @Override
    public String toString() {
        return "DataTableRequestDTO{" +
                "draw=" + draw +
                ", columns=" + columns +
                ", order=" + order +
                ", start=" + start +
                ", length=" + length +
                '}';
    }
}