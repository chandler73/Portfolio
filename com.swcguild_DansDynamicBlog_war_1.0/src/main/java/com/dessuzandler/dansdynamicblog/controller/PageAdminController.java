package com.dessuzandler.dansdynamicblog.controller;

import com.dessuzandler.dansdynamicblog.dao.BlogDAO;
import com.dessuzandler.dansdynamicblog.model.WebPage;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PageAdminController {

    private BlogDAO dao;

    @Inject
    public PageAdminController(BlogDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/page-admin"}, method = RequestMethod.GET)
    public String displayPagePage() {
        return "page-admin";
    }
    
    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    @ResponseBody
    public List<WebPage> getAllWebPages() {
        return dao.getAllWebPages();
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public WebPage addWebPage(@Valid @RequestBody WebPage webpage) {
         dao.addWebPage(webpage);
         return webpage;
    }
    
    @RequestMapping(value = "/page/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveWebPage(@PathVariable("id") int id, @Valid @RequestBody WebPage webpage) {
        webpage.setPageID(id);
        dao.updateWebPage(webpage);
    }
    
    @RequestMapping(value = "/page/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteWebPage(@PathVariable("id") int id) {
        dao.deleteWebPage(dao.getWebPageById(id));
    }
        

}
