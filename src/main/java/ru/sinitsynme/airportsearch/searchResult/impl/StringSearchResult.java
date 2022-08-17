package ru.sinitsynme.airportsearch.searchResult.impl;

import ru.sinitsynme.airportsearch.searchResult.SearchResult;

public class StringSearchResult extends SearchResult implements Comparable<StringSearchResult> {

    private String data;

    public StringSearchResult(int id, String data) {
        super(id);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getStringData() {
        return data;
    }

    @Override
    public int compareTo(StringSearchResult o) {
        return data.compareTo(o.data);
    }
}
