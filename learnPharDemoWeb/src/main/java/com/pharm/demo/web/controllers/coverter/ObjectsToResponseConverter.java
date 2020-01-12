package com.pharm.demo.web.controllers.coverter;

import com.pharm.demo.web.model.DataTableResponseTO;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ObjectsToResponseConverter {
    public DataTableResponseTO convert(List<? extends Object> objectData, Integer draw, Integer totalData, Integer filteredData) {
        return new DataTableResponseTO(collectListOfObjectValues(objectData), draw, totalData, filteredData);
    }

    private List<List<String>> collectListOfObjectValues(List<? extends Object> objectData) {
        return objectData.stream()
                .map(data -> {
                    Class<?> dataClass = data.getClass();
                    List<Field> fields = filterSyntheticFields(dataClass);
                    return retrieveFieldsData(fields, data);
                }).collect(Collectors.toList());
    }

    private List<String> retrieveFieldsData(List<Field> fields, Object objectData) {
        final List<String> data = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                data.add(getData(objectData, field));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private String getData(Object objectData, Field field) throws IllegalAccessException {
        Object data = field.get(objectData);
        if (Objects.nonNull(data) && isNumeric(data)) {
            return data.toString();
        } else if (Objects.nonNull(data) && isDate(data)) {
            return getDateAsString(data);
        } else if (data instanceof List) {
            return getListSizeAsString((List) data);
        }
        return null;
    }

    private String getDateAsString(Object data) {
        if (data instanceof LocalDate) {
            return ((LocalDate) data).toString();
        } else if (data instanceof LocalDateTime) {
            return ((LocalDateTime) data).toString();
        }
        return null;
    }

    private boolean isDate(Object data) {
        return data instanceof LocalDate || data instanceof LocalDateTime;
    }

    private String getListSizeAsString(List data) {
        return ((Integer) data.size()).toString();
    }

    private boolean isNumeric(Object data) {
        return data instanceof Long || data instanceof Integer || data instanceof Double;
    }

    private List<Field> filterSyntheticFields(Class<?> dataClass) {
        return Arrays.stream(dataClass.getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .collect(Collectors.toList());
    }


}
