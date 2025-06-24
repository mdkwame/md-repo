package com.bookingsystem.dto;

import java.util.List;

public class SearchResponse<T> {
    private List<T> results;
    private int totalCount;
    private int page;
    private int pageSize;
    private String searchId;

    public SearchResponse() {}

    public SearchResponse(List<T> results, int totalCount, int page, int pageSize, String searchId) {
        this.results = results;
        this.totalCount = totalCount;
        this.page = page;
        this.pageSize = pageSize;
        this.searchId = searchId;
    }

    // Getters and Setters
    public List<T> getResults() { return results; }
    public void setResults(List<T> results) { this.results = results; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public String getSearchId() { return searchId; }
    public void setSearchId(String searchId) { this.searchId = searchId; }
}