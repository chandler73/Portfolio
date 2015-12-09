package com.dessuzandler.dansdynamicblog.dao;

import com.dessuzandler.dansdynamicblog.model.Announcement;
import com.dessuzandler.dansdynamicblog.model.BlogPost;
import com.dessuzandler.dansdynamicblog.model.Category;
import com.dessuzandler.dansdynamicblog.model.WebPage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BlogDaoImpl implements BlogDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_BLOGPOST
            = "INSERT INTO blog_post (user_id, title, submitted, creation_date, publish_date, "
            + "expiry_date, content) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_BLOG_CATEGORY
            = "INSERT INTO blog_category (blog_id, category_id) values (?, ?)";

    private static final String SQL_DELETE_BLOGPOST
            = "DELETE FROM blog_post WHERE blog_id = ?";

    private static final String SQL_DELETE_BLOG_CATEGORY
            = "DELETE FROM blog_category WHERE blog_id = ?";

    private static final String SQL_UPDATE_BLOGPOST
            = "UPDATE blog_post SET user_id = ?, title = ?, submitted = ?, creation_date = ?, publish_date = ?, "
            + "expiry_date = ?, content  = ? WHERE blog_id = ?";

    private static final String SQL_SELECT_BLOGPOST
            = "SELECT * FROM blog_post WHERE blog_id = ?";

    private static final String SQL_SELECT_BLOG_CATEGORIES
            = "SELECT category_id FROM blog_category WHERE blog_id = ?";

    private static final String SQL_SELECT_ALL_PUBLISHED_BLOGPOSTS_BY_CATEGORY
            = "SELECT b.blog_id, user_id, title, submitted, creation_date, publish_date, expiry_date, content FROM blog_post AS b "
            + "LEFT JOIN blog_category AS bc ON b.blog_id = bc.blog_id "
            + "LEFT JOIN category as c ON bc.category_id = c.category_id "
            + "WHERE category = ? AND expiry_date >= CURDATE() AND publish_date <= CURDATE()";

    private static final String SQL_SELECT_ALL_PUBLISHED_BLOGPOSTS
            = "SELECT * FROM blog_post WHERE publish_date <= CURDATE() AND expiry_date >= CURDATE()";

    private static final String SQL_SELECT_ALL_UNPUBLISHED_BLOGPOSTS
            = "SELECT * FROM blog_post WHERE publish_date > CURDATE() AND user_id = 1";

    private static final String SQL_SELECT_ALL_UNAPPROVED_BLOGPOSTS
            = "SELECT * FROM blog_post WHERE submitted = 1";
    
    private static final String SQL_SELECT_ALL_UNSUBMITTED_BLOGPOSTS
            = "SELECT * FROM blog_post WHERE submitted = 0 AND publish_date = 30000101 AND user_id = 2";
    
    private static final String SQL_SELECT_ALL_EXPIRED_BLOGPOSTS
            = "SELECT * FROM blog_post WHERE expiry_date <= CURDATE()";

    private static final String SQL_INSERT_WEBPAGE
            = "INSERT INTO web_pages (title, content) values (?, ?)";

    private static final String SQL_DELETE_WEBPAGE
            = "DELETE FROM web_pages where page_id = ?";

    private static final String SQL_UPDATE_WEBPAGE
            = "UPDATE web_pages SET title = ?, content = ? WHERE page_id = ?";

    private static final String SQL_SELECT_WEBPAGE_BY_ID
            = "SELECT * FROM web_pages WHERE page_id = ?";

    private static final String SQL_SELECT_ALL_WEBPAGES
            = "SELECT * FROM web_pages";

    private static final String SQL_INSERT_CATEGORY
            = "INSERT INTO category (category) values (?)";

    private static final String SQL_SELECT_ALL_CATEGORIES
            = "SELECT * FROM category";

    private static final String SQL_SELECT_ALL_USED_CATEGORIES
            = "SELECT distinct bc.category_id, category "
            + "from blog_post as b "
            + "left join blog_category as bc "
            + "on b.blog_id = bc.blog_id "
            + "left join category as c "
            + "on bc.category_id = c.category_id "
            + "where expiry_date >= CURDATE() AND publish_date <= CURDATE();";

    private static final String SQL_SELECT_CATEGORIES_BY_BLOG_ID
            = "SELECT bc.category_id, category "
            + "from blog_post as b "
            + "left join blog_category as bc "
            + "on b.blog_id = bc.blog_id "
            + "left join category as c "
            + "on bc.category_id = c.category_id "
            + "where b.blog_id = ?";
    
    private static final String SQL_SELECT_CATEGORY_BY_ID
            = "SELECT * FROM category WHERE category_id = ?";
    
    private static final String SQL_SELECT_ID_BY_CATEGORY
            = "SELECT * FROM category WHERE category = ?";

    private static final String SQL_INSERT_ANNOUNCEMENT
            = "INSERT INTO announcement (content, publish_date, expiry_date) values (?, ?, ?)";

    private static final String SQL_SELECT_ALL_ANNOUNCEMENTS
            = "SELECT * FROM announcement";

    private static final String SQL_SELECT_ALL_PUBLISHED_ANNOUNCEMENTS
            = "SELECT * FROM announcement WHERE publish_date <= CURDATE() AND expiry_date >= CURDATE()";

    private static final String SQL_UPDATE_ANNOUNCEMENT
            = "UPDATE announcement SET content = ?, publish_date = ?, expiry_date = ? WHERE announcement_id = ?";

    private static final String SQL_SELECT_ANNOUNCEMENT_BY_ID
            = "SELECT * FROM announcement WHERE announcement_id = ?";

    private static final String SQL_DELETE_ANNOUNCEMENT
            = "DELETE FROM announcement WHERE announcement_id = ?";
    
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BlogPost addBlogPost(BlogPost blogPost) {
        jdbcTemplate.update(SQL_INSERT_BLOGPOST,
                blogPost.getUserID(),
                blogPost.getTitle(),
                blogPost.getSubmitted(),
                blogPost.getCreationDate(),
                blogPost.getPublishDate(),
                blogPost.getExpiryDate(),
                blogPost.getContent());
        blogPost.setBlogID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        insertBlogCategory(blogPost);
        return blogPost;
    }

    @Override
    public void deleteBlogPost(BlogPost blogPost) {
        jdbcTemplate.update(SQL_DELETE_BLOGPOST, blogPost.getBlogID());
        jdbcTemplate.update(SQL_DELETE_BLOG_CATEGORY, blogPost.getBlogID());
    }

    @Override
    public void updateBlogPost(BlogPost blogPost) {
        jdbcTemplate.update(SQL_UPDATE_BLOGPOST, blogPost.getUserID(), blogPost.getTitle(), blogPost.getSubmitted(),
                blogPost.getCreationDate(), blogPost.getPublishDate(), blogPost.getExpiryDate(), blogPost.getContent(), blogPost.getBlogID());
        insertBlogCategory(blogPost);
    }

    @Override
    public BlogPost getBlogPostById(int id) {
        try {
            BlogPost bp = jdbcTemplate.queryForObject(SQL_SELECT_BLOGPOST, new BlogPostMapper(), id);
            int[] idArray = getCategoryIdsForBlog(bp);
            bp.setCategoryIDs(idArray);
            return bp;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BlogPost> getPublishedBlogPostsByCategory(int id) {
        Category category = getCategoryById(id);
        List<BlogPost> blogList = jdbcTemplate.query(SQL_SELECT_ALL_PUBLISHED_BLOGPOSTS_BY_CATEGORY,
                new BlogPostMapper(),
                category.getCategory());
        for (BlogPost bp : blogList) {
            bp.setCategoryIDs(getCategoryIdsForBlog(bp));
        }
        return blogList;
    }

    @Override
    public List<BlogPost> getAllPublishedBlogPosts() {
        List<BlogPost> blogList = jdbcTemplate.query(SQL_SELECT_ALL_PUBLISHED_BLOGPOSTS,
                new BlogPostMapper());
        for (BlogPost bp : blogList) {
            bp.setCategoryIDs(getCategoryIdsForBlog(bp));
        }
        return blogList;
    }

    @Override
    public List<BlogPost> getAllUnpublishedBlogPosts() {
        List<BlogPost> blogList = jdbcTemplate.query(SQL_SELECT_ALL_UNPUBLISHED_BLOGPOSTS,
                new BlogPostMapper());
        for (BlogPost bp : blogList) {
            bp.setCategoryIDs(getCategoryIdsForBlog(bp));
        }
        return blogList;
    }

    @Override
    public List<BlogPost> getAllUnapprovedBlogPosts() {
        List<BlogPost> blogList = jdbcTemplate.query(SQL_SELECT_ALL_UNAPPROVED_BLOGPOSTS,
                new BlogPostMapper());
        for (BlogPost bp : blogList) {
            bp.setCategoryIDs(getCategoryIdsForBlog(bp));
        }
        return blogList;
    }
    
    @Override
    public List<BlogPost> getAllUnSubmittedBlogPosts() {
        List<BlogPost> blogList = jdbcTemplate.query(SQL_SELECT_ALL_UNSUBMITTED_BLOGPOSTS,
                new BlogPostMapper());
        for (BlogPost bp : blogList) {
            bp.setCategoryIDs(getCategoryIdsForBlog(bp));
        }
        return blogList;
    }

    @Override
    public List<BlogPost> getAllExpiredBlogPosts() {
        List<BlogPost> blogList = jdbcTemplate.query(SQL_SELECT_ALL_EXPIRED_BLOGPOSTS,
                new BlogPostMapper());
        for (BlogPost bp : blogList) {
            bp.setCategoryIDs(getCategoryIdsForBlog(bp));
        }
        return blogList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public WebPage addWebPage(WebPage webPage) {
        jdbcTemplate.update(SQL_INSERT_WEBPAGE,
                webPage.getTitle(),
                webPage.getContent());
        webPage.setPageID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        return webPage;
    }

    @Override
    public void deleteWebPage(WebPage webPage) {
        jdbcTemplate.update(SQL_DELETE_WEBPAGE, webPage.getPageID());
    }

    @Override
    public void updateWebPage(WebPage webPage) {
        jdbcTemplate.update(SQL_UPDATE_WEBPAGE,
                webPage.getTitle(),
                webPage.getContent(),
                webPage.getPageID());
    }

    @Override
    public WebPage getWebPageById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_WEBPAGE_BY_ID, new WebPageMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<WebPage> getAllWebPages() {
        return jdbcTemplate.query(SQL_SELECT_ALL_WEBPAGES, new WebPageMapper());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Category addCategory(Category category) {
        jdbcTemplate.update(SQL_INSERT_CATEGORY, category.getCategory());
        category.setCategoryID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(SQL_SELECT_ALL_CATEGORIES, new CategoryMapper());
    }

    @Override
    public List<Category> getAllUsedCategories() {
        return jdbcTemplate.query(SQL_SELECT_ALL_USED_CATEGORIES, new CategoryMapper());
    }

    @Override
    public List<Category> getCategoriesByBlogID(int id) {
        try {
            return jdbcTemplate.query(SQL_SELECT_CATEGORIES_BY_BLOG_ID, new CategoryMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public Category getCategoryById(int id) {
        try{
        return jdbcTemplate.queryForObject(SQL_SELECT_CATEGORY_BY_ID, new CategoryMapper(), id);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
        
    }
    
    @Override
    public Category getIdByCategory(String category) {
        try{
        return jdbcTemplate.queryForObject(SQL_SELECT_ID_BY_CATEGORY, new CategoryMapper(), category);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Announcement addAnnouncement(Announcement announcement) {
        jdbcTemplate.update(SQL_INSERT_ANNOUNCEMENT,
                announcement.getContent(),
                announcement.getPublishDate(),
                announcement.getExpiryDate());
        announcement.setAnnouncementID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        return announcement;
    }

    @Override
    public void updateAnnouncement(Announcement announcement) {
        jdbcTemplate.update(SQL_UPDATE_ANNOUNCEMENT,
                announcement.getContent(),
                announcement.getPublishDate(),
                announcement.getExpiryDate(),
                announcement.getAnnouncementID());
    }

    @Override
    public void deleteAnnouncement(Announcement announcement) {
        jdbcTemplate.update(SQL_DELETE_ANNOUNCEMENT, announcement.getAnnouncementID());
    }

    @Override
    public List<Announcement> getAnnouncements() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ANNOUNCEMENTS, new AnnouncementMapper());
    }

    @Override
    public List<Announcement> getPublishedAnnouncements() {
        return jdbcTemplate.query(SQL_SELECT_ALL_PUBLISHED_ANNOUNCEMENTS, new AnnouncementMapper());
    }

    @Override
    public Announcement getAnnouncementById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ANNOUNCEMENT_BY_ID, new AnnouncementMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final class BlogPostMapper implements ParameterizedRowMapper<BlogPost> {

        @Override
        public BlogPost mapRow(ResultSet rs, int i) throws SQLException {
            BlogPost blogpost = new BlogPost();
            blogpost.setBlogID(rs.getInt("blog_id"));
            blogpost.setUserID(rs.getInt("user_id"));
            blogpost.setTitle(rs.getString("title"));
            blogpost.setSubmitted(rs.getInt("submitted"));
            blogpost.setCreationDate(rs.getDate("creation_date"));
            blogpost.setPublishDate(rs.getDate("publish_date"));
            blogpost.setExpiryDate(rs.getDate("expiry_date"));
            blogpost.setContent(rs.getString("content"));
            return blogpost;
        }
    }

    private void insertBlogCategory(BlogPost blogPost) {
        final int blogId = blogPost.getBlogID();
        final int[] categoryIDs = blogPost.getCategoryIDs();
        try {
            jdbcTemplate.batchUpdate(SQL_INSERT_BLOG_CATEGORY, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, blogId);
                    ps.setInt(2, categoryIDs[i]);
                }

                @Override
                public int getBatchSize() {
                    return categoryIDs.length;
                }
            });

        } catch (NullPointerException e) {

        }

    }

    private int[] getCategoryIdsForBlog(BlogPost blogPost) {
        List<Integer> categoryIDs
                = jdbcTemplate.queryForList(SQL_SELECT_BLOG_CATEGORIES,
                        new Integer[]{blogPost.getBlogID()},
                        Integer.class
                );
        int[] idArray = new int[categoryIDs.size()];
        for (int i = 0;
                i < categoryIDs.size();
                i++) {
            idArray[i] = categoryIDs.get(i);
        }
        return idArray;
    }

    private static final class WebPageMapper implements ParameterizedRowMapper<WebPage> {

        @Override
        public WebPage mapRow(ResultSet rs, int i) throws SQLException {
            WebPage wp = new WebPage();
            wp.setPageID(rs.getInt("page_id"));
            wp.setTitle(rs.getString("title"));
            wp.setContent(rs.getString("content"));
            return wp;
        }
    }

    private static final class CategoryMapper implements ParameterizedRowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category cat = new Category();
            cat.setCategoryID(rs.getInt("category_id"));
            cat.setCategory(rs.getString("category"));
            return cat;
        }
    }

    private static final class AnnouncementMapper implements ParameterizedRowMapper<Announcement> {

        @Override
        public Announcement mapRow(ResultSet rs, int i) throws SQLException {
            Announcement announcement = new Announcement();
            announcement.setAnnouncementID(rs.getInt("announcement_id"));
            announcement.setContent(rs.getString("content"));
            announcement.setPublishDate(rs.getDate("publish_date"));
            announcement.setExpiryDate(rs.getDate("expiry_date"));
            return announcement;
        }
    }

}
