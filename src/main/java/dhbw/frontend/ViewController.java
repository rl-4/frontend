package dhbw.frontend;

import jsonParser.Parser;
import model.DocumentMetaDataDto;
import model.DocumentMetaDataDtoWrapper;
import model.FilterQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import services.HttpSearch;
import view.SearchForm;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {

    private static Map<String, String> keyValuePairs = new HashMap<>();
    private HttpSearch httpSearch = new HttpSearch();
    private Parser parser = new Parser();

    @PostMapping("/search")
    public String fullTextSearch(@ModelAttribute SearchForm searchForm, Model model) {

        List<DocumentMetaDataDto> documentMetaDataDTOs = null;
        try {
            if(searchForm.getKeyValuePairs() == null){
                searchForm.setKeyValuePairs(new HashMap<>());
            }
            FilterQuery filterQuery = new FilterQuery(searchForm.isRegExMatch(), searchForm.getSearchQuery(), searchForm.getKeyValuePairs());
            String jsonFilterQuery = parser.filterQueryToJson(filterQuery);

            URI uri = new URI("http://app:8080/fullTextSearch");
            HttpResponse<String> response = httpSearch.searchPost(uri, jsonFilterQuery);
            String responseJson = response.body();
            responseJson = responseJson.replaceAll("\\[", "{\"documentMetaDataDTOs\":[");
            responseJson = responseJson.replaceAll("]", "]}");
            System.out.println(responseJson);
            //TODO check if parser works
            DocumentMetaDataDtoWrapper documentMetaDataDtoWrapper = parser.jsonToDocumentMetaDataDto(responseJson);
            documentMetaDataDTOs = documentMetaDataDtoWrapper.getDocumentMetaDataDTOs();
        } catch (Exception e) {
            e.printStackTrace();
            documentMetaDataDTOs = new ArrayList<>();
        }
        model.addAttribute(documentMetaDataDTOs);
        keyValuePairs.clear();
        return "index";
    }

    @GetMapping(value = {"/", "/index"})
    public String getIndex(Model model, @RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value) {
        if (key != null && value != null) {
            keyValuePairs.put(key, value);
        }

        System.out.println(keyValuePairs);
        SearchForm searchForm = new SearchForm();
        searchForm.setKeyValuePairs(keyValuePairs);
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("keyValuePairs", keyValuePairs);
        return "index";
    }

    @GetMapping("/upload")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("uploadFile")MultipartFile file) {
        try {
            ResponseEntity<String> response =  httpSearch.upload("http://fileservice:8081/api/addDocument",file);
            HttpStatus status = response.getStatusCode();
            if(status == HttpStatus.CREATED){
                return "uploadSuccess";
            }else{
                return "uploadFailed";
            }
        } catch (Exception e) {
            return "uploadFailed";
        }
    }
}
