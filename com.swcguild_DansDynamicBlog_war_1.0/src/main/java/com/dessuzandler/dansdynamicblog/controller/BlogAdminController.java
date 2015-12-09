package com.dessuzandler.dansdynamicblog.controller;

import com.dessuzandler.dansdynamicblog.dao.BlogDAO;
import com.dessuzandler.dansdynamicblog.model.BlogPost;
import com.dessuzandler.dansdynamicblog.model.Category;
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
public class BlogAdminController {

    private BlogDAO dao;

    @Inject
    public BlogAdminController(BlogDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/blog-admin"}, method = RequestMethod.GET)
    public String displayBlogPage() {
        return "blog-admin";
    }

    @RequestMapping(value = "/blogs-admin", method = RequestMethod.GET)
    @ResponseBody
    public List<BlogPost> getUnapprovedBlogPosts() {
        return dao.getAllUnapprovedBlogPosts();
    }

    @RequestMapping(value = "/blogs-unpublished", method = RequestMethod.GET)
    @ResponseBody
    public List<BlogPost> getUnpublishedBlogPosts() {
        return dao.getAllUnpublishedBlogPosts();
    }

    @RequestMapping(value = "/searchCategory/{category-name}", method = RequestMethod.GET)
    @ResponseBody
    public Category getIDByCategory(@PathVariable("category-name") String cat) {
        return dao.getIdByCategory(cat);
    }
    
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Category addCategory(@Valid @RequestBody Category category) {
        dao.addCategory(category);
        return category;
    }
    
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getCategoriesByBlogID(@PathVariable("id") int id) {
        return dao.getCategoriesByBlogID(id);
    }
}
