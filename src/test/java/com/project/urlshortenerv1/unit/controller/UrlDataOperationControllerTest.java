package com.project.urlshortenerv1.unit.controller;


import com.project.urlshortenerv1.IDGenerator;
import com.project.urlshortenerv1.UrlDataHolder;
import com.project.urlshortenerv1.UrlDataOperationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlDataOperationControllerTest {

    private MockMvc mvc;


    @Autowired
    WebApplicationContext wac;

    @MockBean
    private UrlDataOperationController controller;

    private static final String BASE_URL = "http://localhost:8443/";

    @Test
    public void getUrls() throws Exception {
        UrlDataHolder urlDataHolder = new UrlDataHolder();
        urlDataHolder.setOriginalUrl("https://www.goggy.com");
        urlDataHolder.setHits(0);
        String key1 = IDGenerator.getInstance().createUniqueID(System.currentTimeMillis());
        urlDataHolder.setUrlKey(key1);
        urlDataHolder.setShortenedUrl(BASE_URL+key1);
        List<UrlDataHolder> urls = Collections.singletonList(urlDataHolder);

        given(controller.getAllUrls()).willReturn(urls);

        mvc.perform(get("/urls/getAllUrls").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].shortenedUrl",is(urlDataHolder.getShortenedUrl())))
                .andExpect(jsonPath("$[0].originalUrl",is(urlDataHolder.getOriginalUrl())))
                .andExpect(jsonPath("$[0].id",is(urlDataHolder.getId())))
                .andExpect(jsonPath("$[0].hits",is(urlDataHolder.getHits())))
                .andExpect(jsonPath("$[0].urlKey",is(urlDataHolder.getUrlKey())));
    }

    @Test
    public void createUrlTest() throws Exception {
        UrlDataHolder urlDataHolder = new UrlDataHolder();

        given(controller.createUrl("https://www.testers.com")).willReturn(urlDataHolder);
        mvc.perform(post("/urls/create").content("https://www.testers.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl",is(urlDataHolder.getOriginalUrl())));

    }

    @Before
    public void setup(){
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
