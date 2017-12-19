package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        Map<String,Object> headers = new HashMap<String,Object>();
        if(q.contains("max:")){
            headers.put("CamelTwitterKeyWords",q.substring(0,q.indexOf("max")));
            headers.put("CamelTwitterCount",q.substring(q.indexOf(":")+1,q.length()));
        }
        else{
            headers.put("CamelTwitterKeyWords",q);
        }
        return producerTemplate.requestBodyAndHeader("direct:search", "",headers);
}