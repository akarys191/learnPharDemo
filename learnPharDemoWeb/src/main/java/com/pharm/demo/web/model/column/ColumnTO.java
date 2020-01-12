package com.pharm.demo.web.model.column;

public class ColumnTO {
    private String name;
    private Boolean searchable;
    private Boolean orderable;
    private SearchTO search;

    public ColumnTO() {
    }

    public ColumnTO(String name, Boolean searchable, Boolean orderable, SearchTO search) {
        this.name = name;
        this.searchable = searchable;
        this.orderable = orderable;
        this.search = search;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getOrderable() {
        return orderable;
    }

    public void setOrderable(Boolean orderable) {
        this.orderable = orderable;
    }

    public SearchTO getSearch() {
        return search;
    }

    public void setSearch(SearchTO search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "ColumnTO{" +
                ", name='" + name + '\'' +
                ", searchable=" + searchable +
                ", orderable=" + orderable +
                ", search=" + search +
                '}';
    }
}
