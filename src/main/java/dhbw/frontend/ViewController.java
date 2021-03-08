package dhbw.frontend;

import jsonParser.Parser;
import model.FilterQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import services.HttpSearch;

import java.net.URI;

@Controller
public class ViewController {

    private HttpSearch httpSearch = new HttpSearch();

    private Parser parser = new Parser();

    @GetMapping("/test")
    public String getIndex(){
        try{
            URI uri = new URI("http://localhost:8080/fullTextSearch");
            FilterQuery filterQuery = new FilterQuery();
            String jsonFilterQuery = parser.filterQueryToJson(filterQuery);
            httpSearch.searchPost(uri, jsonFilterQuery);
        } catch (Exception e){

        }
        return "";
    }
}
