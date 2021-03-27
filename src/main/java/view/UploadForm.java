package view;

import java.util.Map;

public class UploadForm {
    private Map<String, String> keyValuePairs;

    public Map<String, String> getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setKeyValuePairs(Map<String, String> keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
    }

    public UploadForm( Map<String, String> keyValuePairs){
        this.keyValuePairs = keyValuePairs;
    }
    public UploadForm(){}
}
