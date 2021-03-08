package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class DocumentMetaDataDto {
    @JsonProperty
    private String name;
    @JsonProperty
    private String path;
    @JsonProperty
    private Map<String, String> metaData;

    public DocumentMetaDataDto(){

    }

    public DocumentMetaDataDto(String name, String path, Map<String, String> metaData) {
        this.name = name;
        this.path = path;
        this.metaData = metaData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }
}
