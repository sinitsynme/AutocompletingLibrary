package ru.sinitsynme.airportsearch.searchResult.impl;

import ru.sinitsynme.airportsearch.searchResult.SearchResult;
import ru.sinitsynme.airportsearch.utils.NumberUtils;

public class NumberSearchResult extends SearchResult implements Comparable<NumberSearchResult> {

    private double data;

    public NumberSearchResult(int id, double data) {
        super(id);
        this.data = data;
    }

    public double getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public String getStringData() {
        if(NumberUtils.isDoubleAnInteger(data)) {
            return Integer.toString((int) data);
        }
        return Double.toString(data);
    }

    @Override
    public int compareTo(NumberSearchResult o) {
        return Double.compare(data, o.data);
    }

}
