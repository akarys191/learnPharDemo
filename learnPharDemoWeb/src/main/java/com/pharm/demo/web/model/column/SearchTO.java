package com.pharm.demo.web.model.column;

public class SearchTO {
    private String value;
    private Boolean regex;

    public SearchTO(String value, Boolean regex) {
        this.value = value;
        this.regex = regex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getRegex() {
        return regex;
    }

    public void setRegex(Boolean regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "SearchTO{" +
                "value='" + value + '\'' +
                ", regex=" + regex +
                '}';
    }
}
