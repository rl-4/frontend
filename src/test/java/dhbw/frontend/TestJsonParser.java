package dhbw.frontend;

import jsonParser.Parser;
import model.FilterQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestJsonParser {
    private Parser parser;

    @BeforeEach
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void testFilterQueryToJson(){
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("Author", "A");
        keyValuePairs.put("Published", "B");
        String searchQuery = "Test";
        FilterQuery filterQuery = new FilterQuery(true, searchQuery, keyValuePairs);

        String actualJson = null;
        try{
            actualJson = parser.filterQueryToJson(filterQuery);
        } catch (Exception e){
            Assertions.fail();
        }

        String expectedJson = "{\"regExMatch\":true,\"searchQuery\":\"Test\",\"keyValuePairs\":{\"Author\":\"A\",\"Published\":\"B\"}}";

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testJsonToFilterQuery(){
        String json = "{\"regExMatch\":true,\"searchQuery\":\"Test\",\"keyValuePairs\":{\"Author\":\"A\",\"Published\":\"B\"}}";
        FilterQuery actualFilterQuery = null;
        try{
            actualFilterQuery = parser.jsonToFilterQuery(json);
        }catch (Exception e){
            Assertions.fail();
        }

        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("Author", "A");
        keyValuePairs.put("Published", "B");
        String searchQuery = "Test";
        FilterQuery expectedFilterQuery = new FilterQuery(true, searchQuery, keyValuePairs);

        Assertions.assertTrue(expectedFilterQuery.equals(actualFilterQuery));
    }
}
