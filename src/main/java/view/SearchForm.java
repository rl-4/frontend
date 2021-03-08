package view;

import java.util.Map;

public class SearchForm {

    private String searchQuery;

    private boolean regExMatch;

    private Map<String, String> keyValuePairs;

    public Map<String, String> getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setKeyValuePairs(Map<String, String> keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
    }

    public SearchForm(String searchQuery, boolean regExMatch, Map<String, String> keyValuePairs) {
        this.searchQuery = searchQuery;
        this.regExMatch = regExMatch;
        this.keyValuePairs = keyValuePairs;
    }

    public SearchForm() {
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public boolean isRegExMatch() {
        return regExMatch;
    }

    public void setRegExMatch(boolean regExMatch) {
        this.regExMatch = regExMatch;
    }
}
