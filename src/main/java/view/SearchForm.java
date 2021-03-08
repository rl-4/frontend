package view;

public class SearchForm {

    private String searchQuery;

    private boolean regExMatch;

    public SearchForm(String searchQuery, boolean regExMatch) {
        this.searchQuery = searchQuery;
        this.regExMatch = regExMatch;
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
