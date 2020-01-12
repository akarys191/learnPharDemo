package com.pharm.demo.web.model;

import com.pharm.demo.web.model.column.ColumnTO;
import com.pharm.demo.web.model.column.OrderTO;

import java.util.Map;

public class DataTableRequestTO {
    private Integer draw;
    private Map<String, ColumnTO> columns;
    private Map<String, OrderTO> order;
    private Integer start;
    private Integer length;
    private String myKey;

    public DataTableRequestTO(Integer draw, Map<String, ColumnTO> columns, Map<String,
            OrderTO> order, Integer start, Integer length, String myKey) {
        this.draw = draw;
        this.columns = columns;
        this.order = order;
        this.start = start;
        this.length = length;
        this.myKey = myKey;
    }

    public Integer getDraw() {
        return draw;
    }

    public Map<String, ColumnTO> getColumns() {
        return columns;
    }

    public Map<String, OrderTO> getOrder() {
        return order;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLength() {
        return length;
    }

    public String getMyKey() {
        return myKey;
    }

    @Override
    public String toString() {
        return "DataTableRequestParam{" +
                "draw=" + draw +
                ", columns=" + columns +
                ", order=" + order +
                ", start=" + start +
                ", length=" + length +
                ", myKey='" + myKey + '\'' +
                '}';
    }
}
