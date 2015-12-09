package com.dessuzandler.dansdynamicblog.dao;

import com.dessuzandler.dansdynamicblog.model.Announcement;
import com.dessuzandler.dansdynamicblog.model.BlogPost;
import com.dessuzandler.dansdynamicblog.model.Category;
import com.dessuzandler.dansdynamicblog.model.WebPage;
import java.util.List;


public interface BlogDAO {
    
    public BlogPost addBlogPost(BlogPost blogPost);
    
    public void deleteBlogPost(BlogPost blogPost);
    
    public void updateBlogPost(BlogPost blogPost);
    
    public BlogPost getBlogPostById(int id);
    
    public List<BlogPost> getPublishedBlogPostsByCategory(int id);
    
    public List<BlogPost> getAllPublishedBlogPosts();
    
    public List<BlogPost> getAllUnpublishedBlogPosts();
    
    public List<BlogPost> getAllUnapprovedBlogPosts();
    
    public List<BlogPost> getAllUnSubmittedBlogPosts();
    
    public List<BlogPost> getAllExpiredBlogPosts();
    
    public WebPage addWebPage(WebPage webPage);
    
    public void deleteWebPage(WebPage webPage);
    
    public void updateWebPage(WebPage webPage);
    
    public WebPage getWebPageById(int id);
    
    public List<WebPage> getAllWebPages();
    
    public Category addCategory(Category category);
    
    public List<Category> getAllCategories();
    
    public List<Category> getAllUsedCategories();
    
    public List<Category> getCategoriesByBlogID(int id);
    
    public Category getCategoryById(int id);
    
    public Category getIdByCategory(String category);
    
    public Announcement addAnnouncement(Announcement announcement);
    
    public void updateAnnouncement(Announcement announcement);
    
    public void deleteAnnouncement(Announcement announcement);
    
    public List<Announcement> getAnnouncements();
    
    public List<Announcement> getPublishedAnnouncements();
    
    public Announcement getAnnouncementById(int id);
    
}
