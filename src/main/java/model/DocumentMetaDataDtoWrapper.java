package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DocumentMetaDataDtoWrapper {
    @JsonProperty
    private List<DocumentMetaDataDto> documentMetaDataDTOs;

    public DocumentMetaDataDtoWrapper(List<DocumentMetaDataDto> documentMetaDataDTOs) {
        this.documentMetaDataDTOs = documentMetaDataDTOs;
    }

    public DocumentMetaDataDtoWrapper() {
    }

    public List<DocumentMetaDataDto> getDocumentMetaDataDTOs() {
        return documentMetaDataDTOs;
    }

    public void setDocumentMetaDataDTOs(List<DocumentMetaDataDto> documentMetaDataDTOs) {
        this.documentMetaDataDTOs = documentMetaDataDTOs;
    }
}
