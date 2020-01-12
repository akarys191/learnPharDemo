package com.pharm.demo.web.model;

import java.util.List;

public class DataTableResponseTO {
    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List<List<String>> data;

    public DataTableResponseTO(List<List<String>> data, Integer draw, Integer recordsTotal,
                               Integer recordsFiltered) {
        this.data = data;
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public Integer getDraw() {
        return draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public List<List<String>> getData() {
        return data;
    }
}
