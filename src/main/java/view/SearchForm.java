package view;

import model.Document;

import java.util.ArrayList;
import java.util.List;

public class SearchForm {
    private String searchInput;
    private List<Document> results = new ArrayList<>();

    public List<Document> getResults() {
        return results;
    }

    public void setResults(List<Document> results) {
        this.results = results;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }
}
