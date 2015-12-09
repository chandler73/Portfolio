

package com.dessuzandler.dansdynamicblog.controller;

import com.dessuzandler.dansdynamicblog.dao.BlogDAO;
import com.dessuzandler.dansdynamicblog.model.Announcement;
import com.dessuzandler.dansdynamicblog.model.BlogPost;
import com.dessuzandler.dansdynamicblog.model.Category;
import com.dessuzandler.dansdynamicblog.model.WebPage;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    private BlogDAO dao;
    
    @Inject
    public HomeController (BlogDAO dao) {
        this.dao = dao;
    }
    
    @RequestMapping(value={"/","/home"}, method=RequestMethod.GET)
    public String displayHomePage(){
        return "home";
    }
    
    @RequestMapping(value="/blogposts", method=RequestMethod.GET)
    @ResponseBody
    public List <BlogPost> getPublishedBlogPosts() {
        return dao.getAllPublishedBlogPosts();
    }
    
    @RequestMapping(value="/blogposts/{id}", method=RequestMethod.GET)
    @ResponseBody
    public List<BlogPost> getAllBlogPostsByCategory(@PathVariable("id") int id) {
        return dao.getPublishedBlogPostsByCategory(id);
    }
    
    @RequestMapping(value="/webpages", method=RequestMethod.GET)
    @ResponseBody
    public List<WebPage> getAllWebPages () {
        return dao.getAllWebPages();
    }
    
    @RequestMapping(value="/webpage/{id}", method=RequestMethod.GET)
    @ResponseBody
    public WebPage getWebPageByID(@PathVariable ("id")int id) {
        return dao.getWebPageById(id);
    }
    
    @RequestMapping(value="/categories", method=RequestMethod.GET)
    @ResponseBody
    public List<Category> getUsedCategories() {
        return dao.getAllUsedCategories();
    }
    
    @RequestMapping(value="/category/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Category getCategoryById(@PathVariable("id") int id) {
        return dao.getCategoryById(id);
    }
    
    @RequestMapping(value="/announcements", method=RequestMethod.GET)
    @ResponseBody
    public List<Announcement> getPublishedAnnouncements() {
        return dao.getPublishedAnnouncements();
    }
    
}
