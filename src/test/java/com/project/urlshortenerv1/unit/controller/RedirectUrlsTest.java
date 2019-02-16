package com.project.urlshortenerv1.unit.controller;

import com.project.urlshortenerv1.ErrorControlHandler;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedirectUrlsTest {

    private MockMvc mvc;


    @Autowired
    WebApplicationContext wac;

    @MockBean
    private UrlDataOperationController controller;

    @MockBean
    private ErrorControlHandler handler;

    private static final String BASE_URL = "http://localhost:8443/";


    @Test
    public void testErrorRender() throws Exception {
        RedirectView redirectView = new RedirectView("http://localhost:8443/v1/error.html");
        given(controller.getError()).willReturn(redirectView);
        mvc.perform(get("/error")).andExpect(redirectedUrl("http://localhost:8443/v1/error.html"));
    }

    @Test
    public void testHomePageRender() throws Exception {
        RedirectView redirectView = new RedirectView("http://localhost:8443/v1/index.html");
        given(controller.get()).willReturn(redirectView);
        mvc.perform(get("/")).andExpect(redirectedUrl("http://localhost:8443/v1/index.html"));
    }


    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                //.setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void getUrlByIdKey() throws Exception {
        UrlDataHolder url = new UrlDataHolder();
        url.setOriginalUrl("https://www.tester.com");
        url.setUrlKey("tester");
        url.setShortenedUrl(BASE_URL+"tester");
        url.setHits(0);

        RedirectView redirectView = new RedirectView(url.getOriginalUrl());
        given(controller.getUrlByIdKey(url.getUrlKey())).willReturn(redirectView);
        mvc.perform(get("/"+url.getUrlKey())).andExpect(redirectedUrl(url.getOriginalUrl()));
    }

}
