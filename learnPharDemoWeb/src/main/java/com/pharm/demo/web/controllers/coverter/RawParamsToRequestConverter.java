package com.pharm.demo.web.controllers.coverter;

import com.pharm.demo.web.model.DataTableRequestTO;
import com.pharm.demo.web.model.column.ColumnTO;
import com.pharm.demo.web.model.column.OrderTO;
import com.pharm.demo.web.model.column.SearchTO;
import com.pharm.demo.web.model.raw.RawDataTableRequestDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RawParamsToRequestConverter {
    public DataTableRequestTO convert(RawDataTableRequestDTO rawDataTableRequestDTO) {
        List<String> columnsNameList = rawDataTableRequestDTO.getColumns().stream()
                .map(this::getData).collect(Collectors.toList());
        return new DataTableRequestTO(rawDataTableRequestDTO.getDraw(),
                rawColumnsTOColumnsMap(rawDataTableRequestDTO.getColumns()),
                rawOrdersTOMap(rawDataTableRequestDTO.getOrder(), columnsNameList),
                rawDataTableRequestDTO.getStart(), rawDataTableRequestDTO.getLength(),
                rawDataTableRequestDTO.getMyKey());
    }

    private Map<String, ColumnTO> rawColumnsTOColumnsMap(List<HashMap<String, String>> columnsListMap) {
        Map<String, ColumnTO> columnValuesMap = new HashMap<>();
        for (int i = 0; i < columnsListMap.size(); i++) {
            Map<String, String> rawColumnMap = columnsListMap.get(i);
            String columnName = getData(rawColumnMap);
            columnValuesMap.put(columnName, rawToColumn(rawColumnMap));
        }
        return columnValuesMap;
    }

    private ColumnTO rawToColumn(Map<String, String> rawColumnMap) {
        return new ColumnTO(getData(rawColumnMap),
                getSearchable(rawColumnMap), getOrderable(rawColumnMap), rawToSearch(rawColumnMap));
    }

    private SearchTO rawToSearch(Map<String, String> rawColumnMap) {
        return new SearchTO(getSearchValue(rawColumnMap), getSearchRegex(rawColumnMap));
    }

    private String getData(Map<String, String> rawColumnMap) {
        return rawColumnMap.get("data");
    }

    private Boolean getSearchRegex(Map<String, String> rawColumnMap) {
        return Boolean.valueOf(rawColumnMap.get("searchRegex"));
    }

    private String getSearchValue(Map<String, String> rawColumnMap) {
        return rawColumnMap.get("searchValue");
    }

    private Boolean getOrderable(Map<String, String> rawColumnMap) {
        return Boolean.valueOf(rawColumnMap.get("orderable"));
    }

    private Boolean getSearchable(Map<String, String> rawColumnMap) {
        return Boolean.valueOf(rawColumnMap.get("searchable"));
    }

    private Map<String, OrderTO> rawOrdersTOMap(List<HashMap<String, String>> columnsOrderMapList,
                                                List<String> columnsList) {
        Map<String, OrderTO> orderMap = new HashMap<>();
        columnsOrderMapList.stream().forEach(rawOrder -> {
            Integer orderIndex = getColumnAsIndex(rawOrder);
            String columnName = columnsList.get(orderIndex);
            orderMap.put(columnName, new OrderTO(columnName, getDirection(rawOrder)));
        });

        return orderMap;
    }

    private String getDirection(HashMap<String, String> orderMap) {
        return orderMap.get("dir");
    }

    private Integer getColumnAsIndex(HashMap<String, String> orderMap) {
        return Integer.valueOf(orderMap.get("column"));
    }
}
