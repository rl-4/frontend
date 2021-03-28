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
import view.UploadForm;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {

    private static Map<String, String> searchKeyValuePairs = new HashMap<>();
    private static Map<String, String> uploadKeyValuePairs = new HashMap<>();
    private HttpSearch httpSearch = new HttpSearch();
    private Parser parser = new Parser();

    @PostMapping("/search")
    public String fullTextSearch(@ModelAttribute SearchForm searchForm, Model model) {
        System.out.println(searchForm.getKeyValuePairs());
        List<DocumentMetaDataDto> documentMetaDataDTOs = null;
        try {
            FilterQuery filterQuery = new FilterQuery(searchForm.isRegExMatch(), searchForm.getSearchQuery(), searchKeyValuePairs);
            String jsonFilterQuery = parser.filterQueryToJson(filterQuery);
            URI uri = new URI("http://app:8080/fullTextSearch");
            HttpResponse<String> response = httpSearch.searchPost(uri, jsonFilterQuery);
            String responseJson = response.body();
            responseJson = responseJson.replaceAll("\\[", "{\"documentMetaDataDTOs\":[");
            responseJson = responseJson.replaceAll("]", "]}");
            DocumentMetaDataDtoWrapper documentMetaDataDtoWrapper = parser.jsonToDocumentMetaDataDto(responseJson);
            documentMetaDataDTOs = documentMetaDataDtoWrapper.getDocumentMetaDataDTOs();
        } catch (Exception e) {
            e.printStackTrace();
            documentMetaDataDTOs = new ArrayList<>();
        }
        model.addAttribute(documentMetaDataDTOs);
        searchKeyValuePairs.clear();
        return "index";
    }

    @GetMapping(value = {"/", "/index"})
    public String getIndex(Model model, @RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value) {
        System.out.println("key: "+key+"value: "+value);
        if (key != null && value != null) {
            searchKeyValuePairs.put(key, value);
        }

        SearchForm searchForm = new SearchForm();
        searchForm.setKeyValuePairs(searchKeyValuePairs);
        model.addAttribute("searchForm", searchForm);
        return "index";
    }

    @GetMapping("/upload")
    public String uploadForm(Model model, @RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value) {
        if (key != null && value != null && !key.isEmpty() && !value.isEmpty()) {
            uploadKeyValuePairs.put(key, value);
        }
        UploadForm uploadForm = new UploadForm();
        uploadForm.setKeyValuePairs(uploadKeyValuePairs);
        model.addAttribute("uploadForm", uploadForm);
        model.addAttribute("keyValuePairs", uploadKeyValuePairs);
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("uploadFile")MultipartFile file, @ModelAttribute UploadForm uploadForm, Model model) {
        try {
            ResponseEntity<String> response =  httpSearch.upload("http://fileservice:8081/api/addDocument",file);
            HttpStatus status = response.getStatusCode();
            int id = Integer.parseInt(response.getBody().split("id\": ")[1].split("\n")[0]);
            if( uploadKeyValuePairs != null && !uploadKeyValuePairs.isEmpty()){

                for (Map.Entry<String,String> entry: uploadKeyValuePairs.entrySet()) {
                    httpSearch.uploadMetaData("http://fileservice:8081/api/addMetadata", id ,entry.getKey(), entry.getValue());
                }
            }
            uploadKeyValuePairs.clear();
            if(status == HttpStatus.CREATED){
                model.addAttribute("uploadMessage","Upload successful !");
                return "upload";
            }else{
                model.addAttribute("uploadMessage","Upload failed !");
                return "upload";
            }
        } catch (Exception e) {
            model.addAttribute("uploadMessage","Upload failed !");
            uploadKeyValuePairs.clear();
            return "upload";
        }
    }
}
