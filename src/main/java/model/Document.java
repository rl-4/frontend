package model;

import java.util.Map;

public class Document {
    private String name;
    private String author;
    private String created;
    private Map<String, String> keyValuePairs;

    public Document(String name, String author, String created, Map<String, String> keyValuePairs) {
        this.name = name;
        this.author = author;
        this.created = created;
        this.keyValuePairs = keyValuePairs;
    }

    public Document() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
