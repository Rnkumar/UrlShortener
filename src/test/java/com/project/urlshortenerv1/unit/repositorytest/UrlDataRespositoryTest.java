package com.project.urlshortenerv1.unit.repositorytest;


import com.project.urlshortenerv1.IDGenerator;
import com.project.urlshortenerv1.UrlDataHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class UrlDataRespositoryTest {

    private static final String BASE_URL = "http://localhost:8443/";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void findAll(){

       UrlDataHolder urlDataHolder1 = new UrlDataHolder();
       urlDataHolder1.setOriginalUrl("https://www.test3.com");
       urlDataHolder1.setHits(0);
       String key1 = IDGenerator.getInstance().createUniqueID(System.currentTimeMillis());
       urlDataHolder1.setUrlKey(key1);
       urlDataHolder1.setShortenedUrl(BASE_URL+key1);
       mongoTemplate.save(urlDataHolder1);



        UrlDataHolder urlDataHolder2 = new UrlDataHolder();
        urlDataHolder2.setOriginalUrl("https://www.test4.com");
        urlDataHolder2.setHits(0);
        String key2 = IDGenerator.getInstance().createUniqueID(System.currentTimeMillis());
        urlDataHolder2.setUrlKey(key2);
        urlDataHolder2.setShortenedUrl(BASE_URL+key2);
        mongoTemplate.save(urlDataHolder2);

        List<UrlDataHolder> urlDataHolders = mongoTemplate.findAll(UrlDataHolder.class);


        assertThat(urlDataHolders.get(urlDataHolders.size()-2).getId()).isEqualTo(urlDataHolder1.getId());
        assertThat(urlDataHolders.get(urlDataHolders.size()-1).getId()).isEqualTo(urlDataHolder2.getId());
        mongoTemplate.remove(urlDataHolder1);
        mongoTemplate.remove(urlDataHolder2);
    }



    @Test
    public void findByUrlKey(){

        UrlDataHolder urlDataHolder1 = new UrlDataHolder();
        urlDataHolder1.setOriginalUrl("https://www.goggy.com");
        urlDataHolder1.setHits(0);
        String key1 = IDGenerator.getInstance().createUniqueID(System.currentTimeMillis());
        urlDataHolder1.setUrlKey(key1);
        urlDataHolder1.setShortenedUrl(BASE_URL+key1);
        mongoTemplate.save(urlDataHolder1);

        Query query = new Query();
        query.addCriteria(Criteria.where("urlKey").is(key1));
        UrlDataHolder urlDataHolder = mongoTemplate.findOne(query,UrlDataHolder.class);


        assertThat(urlDataHolder.getId()).isEqualTo(urlDataHolder1.getId());
        mongoTemplate.remove(urlDataHolder);
    }
}
