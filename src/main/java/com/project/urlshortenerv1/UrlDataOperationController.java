package com.project.urlshortenerv1;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UrlDataOperationController{

    @Autowired
    UrlDataRepository urlDataRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/")
    public RedirectView get(){
        System.out.println("called");
        RedirectView redirectView = new RedirectView("http://localhost:8443/v1/index.html");
        return redirectView;
    }

    @RequestMapping(value = "/urls/getAllUrls", method = RequestMethod.GET)
    public List<UrlDataHolder> getAllUrls() {
        return urlDataRepository.findAll();
    }

    @RequestMapping(value = "/error")
    public RedirectView getError(){
        RedirectView redirectView = new RedirectView("http://localhost:8443/v1/error.html");
        return redirectView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RedirectView getUrlByIdKey(@PathVariable("id")  String uniqueName) {
        System.out.println("Value+"+uniqueName);
        Query query = new Query();
        query.addCriteria(Criteria.where("urlKey").is(uniqueName));
        UrlDataHolder url = mongoTemplate.findOne(query, UrlDataHolder.class);
        if(url!=null){
            url.setHits(url.getHits()+1);
            urlDataRepository.save(url);
            RedirectView redirectView = new RedirectView(url.getOriginalUrl());
            return redirectView;
        }else{
            System.out.println("Error");
            RedirectView redirectView = new RedirectView("http://localhost:8443/v1/errorfile.html");
            return redirectView;
        }
    }

    @RequestMapping(value = "/urls/create", method = RequestMethod.POST)
    public UrlDataHolder createUrl(@Valid @RequestBody String originalUrl) {
        UrlDataHolder urls = new UrlDataHolder();
        urls.setId(ObjectId.get());
        urls.setHits(0);
        urls.setOriginalUrl(originalUrl.trim());
        String key = IDGenerator.getInstance().createUniqueID(System.currentTimeMillis());
        urls.setUrlKey(key);
        urls.setShortenedUrl("http://localhost:8443/"+key.trim());
        try {
            urlDataRepository.save(urls);
            return urls;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/urls/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUrl(@PathVariable ObjectId id) {
        urlDataRepository.delete(urlDataRepository.findBy_id(id));
    }

}
