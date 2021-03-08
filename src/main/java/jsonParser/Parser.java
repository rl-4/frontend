package jsonParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.DocumentMetaDataDtoWrapper;
import model.FilterQuery;

public class Parser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String filterQueryToJson(FilterQuery filterQuery) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(filterQuery);
        System.out.println(json);
        return json;
    }

    public FilterQuery jsonToFilterQuery(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, FilterQuery.class);
    }

    public DocumentMetaDataDtoWrapper jsonToDocumentMetaDataDto(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, DocumentMetaDataDtoWrapper.class);
    }
}
