package dhbw.frontend;

import jsonParser.Parser;
import model.FilterQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import services.HttpSearch;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ViewController {

    private HttpSearch httpSearch = new HttpSearch();

    private Parser parser = new Parser();

    @GetMapping("/test")
    public String getIndex(){
        try{
            Map<String, String> map = new HashMap<>();
            map.put("Category", "A");
            FilterQuery filterQuery = new FilterQuery(true, "Test", map);
            String jsonFilterQuery = parser.filterQueryToJson(filterQuery);

            URI uri = new URI("http://localhost:8080/fullTextSearch");
            httpSearch.searchPost(uri, jsonFilterQuery);
        } catch (Exception e){

        }
        return "";
    }
}
