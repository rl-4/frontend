package dhbw.frontend;

import jsonParser.Parser;
import model.DocumentMetaDataDto;
import model.DocumentMetaDataDtoWrapper;
import model.FilterQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.HttpSearch;
import view.SearchForm;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {

    private HttpSearch httpSearch = new HttpSearch();

    private Parser parser = new Parser();

    @PostMapping("/search")
    public String fullTextSearch(@ModelAttribute SearchForm searchForm, Model model) {
        List<DocumentMetaDataDto> documentMetaDataDTOs = null;
        try {
            Map<String, String> keyValuePairs = new HashMap<>();
            keyValuePairs.put("Category", "A");
            FilterQuery filterQuery = new FilterQuery(searchForm.isRegExMatch(), searchForm.getSearchQuery(), keyValuePairs);
            String jsonFilterQuery = parser.filterQueryToJson(filterQuery);

            //TODO change localhost
            URI uri = new URI("http://localhost:8080/fullTextSearch");
            HttpResponse<String> response = httpSearch.searchPost(uri, jsonFilterQuery);
            String responseJson = response.body();
            DocumentMetaDataDtoWrapper documentMetaDataDtoWrapper = parser.jsonToDocumentMetaDataDto(responseJson);
            documentMetaDataDTOs = documentMetaDataDtoWrapper.getDocumentMetaDataDTOs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute(documentMetaDataDTOs);
        return "index";
    }

    @GetMapping(value = {"/", "/index"})
    public String getIndex(Model model, @RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value) {
        SearchForm searchForm = new SearchForm();
        model.addAttribute(searchForm);
        return "index";
    }

    @GetMapping("/upload")
    public String uploadForm(){
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadDocument(){
        try{
            //TODO uri correct?
            URI uri = new URI("http://fileservice:8080/api/addDocument");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "upload";
    }
}
