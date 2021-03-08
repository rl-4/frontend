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
import org.springframework.web.multipart.MultipartFile;
import services.HttpSearch;
import view.SearchForm;

import java.net.URI;
import java.net.http.HttpResponse;
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
        keyValuePairs.clear();
        List<DocumentMetaDataDto> documentMetaDataDTOs = null;
        try {
            FilterQuery filterQuery = new FilterQuery(searchForm.isRegExMatch(), searchForm.getSearchQuery(), searchForm.getKeyValuePairs());
            String jsonFilterQuery = parser.filterQueryToJson(filterQuery);

            URI uri = new URI("http://app:8080/fullTextSearch");
            HttpResponse<String> response = httpSearch.searchPost(uri, jsonFilterQuery);
            String responseJson = response.body();
            //TODO check if parser works
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
        if (key != null && value != null) {
            keyValuePairs.put(key, value);
        }

        System.out.println(keyValuePairs);
        SearchForm searchForm = new SearchForm();
        searchForm.setKeyValuePairs(keyValuePairs);
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("keyValuePairs", keyValuePairs);
        return "index.html";
    }

    @GetMapping("/upload")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("uploadFile")MultipartFile file) {
        try {
            httpSearch.upload("http://localhost:8081/api/addDocument",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "upload";
    }

    @GetMapping("/download")
    public String downloadDocument(Model model, @RequestParam String id){
        try{
            URI uri = new URI("http:fileservice:8081/api/getDocument" + id);
            HttpResponse<String> response = httpSearch.searchGet(uri);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "index";
    }
}
