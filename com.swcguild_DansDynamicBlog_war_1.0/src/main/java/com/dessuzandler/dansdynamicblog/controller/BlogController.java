package com.dessuzandler.dansdynamicblog.controller;

import com.dessuzandler.dansdynamicblog.dao.BlogDAO;
import com.dessuzandler.dansdynamicblog.model.BlogPost;
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
public class BlogController {

    private BlogDAO dao;

    @Inject
    public BlogController(BlogDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/blog"}, method = RequestMethod.GET)
    public String displayBlogPage() {
        return "blog";
    }

    @RequestMapping(value = {"/blogs"}, method = RequestMethod.GET)
    @ResponseBody
    public List<BlogPost> getUnsubmittedBlogs() {
        return dao.getAllUnSubmittedBlogPosts();
    }

    @RequestMapping(value = {"/blogpost"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BlogPost createBlogPost(@Valid @RequestBody BlogPost blogpost) {
        dao.addBlogPost(blogpost);
        return blogpost;
    }

    @RequestMapping(value = "/blogpost/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBlogPost(@PathVariable("id") int id, @Valid @RequestBody BlogPost blogpost) {
        blogpost.setBlogID(id);
        dao.updateBlogPost(blogpost);
    }

    @RequestMapping(value = "/blogpost/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BlogPost getBlogPostById(@PathVariable("id") int id) {
        return dao.getBlogPostById(id);
    }
    
    @RequestMapping(value = "/blogpost/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteBlogPostById(@PathVariable("id") int id) {
        dao.deleteBlogPost(dao.getBlogPostById(id));
    }
}
