package ru.sinitsynme.airportsearch.searchResult;

public abstract class SearchResult {

    private int id;

    public SearchResult(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getStringData();

}
