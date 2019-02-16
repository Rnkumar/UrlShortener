package com.project.urlshortenerv1;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/name")
public class ErrorControlHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/niresh", method = RequestMethod.GET)
    public RedirectView gets(){
        RedirectView redirectView = new RedirectView("http://localhost:8443/v1/index.html");
        return redirectView;
    }
}
