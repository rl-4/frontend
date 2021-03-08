package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class FilterQuery {
    @JsonProperty("regExMatch")
    public boolean regExMatch;

    @JsonProperty("searchQuery")
    public String searchQuery;

    @JsonProperty("keyValuePairs")
    public Map<String, String> keyValuePairs = new HashMap<>();

    public FilterQuery() {

    }

    public FilterQuery(boolean regExMatch, String searchQuery, Map<String, String> keyValuePairs) {
        this.regExMatch = regExMatch;
        this.searchQuery = searchQuery;
        this.keyValuePairs = keyValuePairs;
    }
}
