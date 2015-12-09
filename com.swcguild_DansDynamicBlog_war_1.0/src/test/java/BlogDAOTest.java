
import com.dessuzandler.dansdynamicblog.dao.BlogDAO;
import com.dessuzandler.dansdynamicblog.model.Announcement;
import com.dessuzandler.dansdynamicblog.model.BlogPost;
import com.dessuzandler.dansdynamicblog.model.Category;
import com.dessuzandler.dansdynamicblog.model.WebPage;
import java.sql.Date;
import java.util.List;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class BlogDAOTest {

    private BlogDAO dao;

    public BlogDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        dao = (BlogDAO) ctx.getBean("blogDAO");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("delete from blog_post");
        cleaner.execute("delete from web_pages");
        cleaner.execute("delete from announcement");
        cleaner.execute("delete from category");
    }

    @After
    public void tearDown() {
    }

    // Tests for blogPost methods
    @Test
    public void addGetDeleteBlogPost() {
        BlogPost bp = new BlogPost();
        Date date = new Date(1447168607335l);
        bp.setUserID(1);
        bp.setTitle("Blog One");
        bp.setSubmitted(1);
        bp.setCreationDate(date);
        bp.setPublishDate(null);
        bp.setExpiryDate(null);
        bp.setContent("This is the content.");
        dao.addBlogPost(bp);
        BlogPost fromDb = dao.getBlogPostById(bp.getBlogID());
        assertEquals(fromDb.getBlogID(), bp.getBlogID());
        assertEquals(fromDb.getUserID(), bp.getUserID());
        assertEquals(fromDb.getTitle(), bp.getTitle());
        assertEquals(fromDb.getSubmitted(), bp.getSubmitted());
        assertEquals(fromDb.getCreationDate().toString(), bp.getCreationDate().toString());
        assertEquals(fromDb.getPublishDate(), bp.getPublishDate());
        assertEquals(fromDb.getExpiryDate(), bp.getExpiryDate());
        assertEquals(fromDb.getContent(), bp.getContent());
        dao.deleteBlogPost(bp);
        assertNull(dao.getBlogPostById(bp.getBlogID()));
    }

    @Test
    public void addUpdateBlogpost() {
        BlogPost bp = new BlogPost();
        Date date = new Date(1447173681344l);
        bp.setUserID(2);
        bp.setTitle("Blog Two");
        bp.setSubmitted(1);
        bp.setCreationDate(date);
        bp.setPublishDate(null);
        bp.setExpiryDate(null);
        bp.setContent("This is the real content.");
        dao.addBlogPost(bp);
        bp.setContent("This is the real, real content.");
        dao.updateBlogPost(bp);
        BlogPost fromDb = dao.getBlogPostById(bp.getBlogID());
        assertEquals(fromDb.getBlogID(), bp.getBlogID());
        assertEquals(fromDb.getUserID(), bp.getUserID());
        assertEquals(fromDb.getTitle(), bp.getTitle());
        assertEquals(fromDb.getSubmitted(), bp.getSubmitted());
        assertEquals(fromDb.getCreationDate().toString(), bp.getCreationDate().toString());
        assertEquals(fromDb.getPublishDate(), bp.getPublishDate());
        assertEquals(fromDb.getExpiryDate(), bp.getExpiryDate());
        assertEquals(fromDb.getContent(), bp.getContent());
    }

    @Test
    public void getAllBlogPosts() {
        BlogPost bp = new BlogPost();
        Date date = new Date(1447168607335l);
        Date pDate = new Date(1445317200000l);
        Date eDate = new Date(1447999200000l);
        bp.setUserID(2);
        bp.setTitle("Blog Two");
        bp.setSubmitted(0);
        bp.setCreationDate(date);
        bp.setPublishDate(pDate);
        bp.setExpiryDate(eDate);
        bp.setContent("This is a great post.");
        dao.addBlogPost(bp);
        BlogPost bp2 = new BlogPost();
        bp2.setUserID(1);
        bp2.setTitle("Blog Two");
        bp2.setSubmitted(1);
        bp2.setCreationDate(date);
        bp2.setPublishDate(pDate);
        bp2.setExpiryDate(eDate);
        bp2.setContent("This is a bad post.");
        dao.addBlogPost(bp2);
        List<BlogPost> arr = dao.getAllPublishedBlogPosts();
        assertEquals(arr.size(), 2);
    }

    @Test
    public void getPublishedBlogPostsByCategory() {
        BlogPost bp = new BlogPost();
        BlogPost bp2 = new BlogPost();
        BlogPost bp3 = new BlogPost();
        Category cat = new Category();
        Category cat1 = new Category();
        Category cat2 = new Category();
        Date date = new Date(1447168607335l);
        Date pDate = new Date(1447171298000l);
        Date eDate = new Date(2530003298000l);
        cat.setCategory("number one");
        dao.addCategory(cat);
        cat1.setCategory("stuff happens");
        dao.addCategory(cat1);
        cat2.setCategory("shouldn't matter");
        dao.addCategory(cat2);
        int[] arr = {cat.getCategoryID(), cat1.getCategoryID()};
        int[] arr1 = {cat.getCategoryID(), cat2.getCategoryID()};
        int[] arr2 = {cat1.getCategoryID(), cat2.getCategoryID()};
        bp.setUserID(2);
        bp.setTitle("Blog One");
        bp.setSubmitted(0);
        bp.setCreationDate(date);
        bp.setPublishDate(pDate);
        bp.setExpiryDate(eDate);
        bp.setContent("This is a great post.");
        bp.setCategoryIDs(arr);
        dao.addBlogPost(bp);
        bp2.setUserID(2);
        bp2.setTitle("Blog Two");
        bp2.setSubmitted(0);
        bp2.setCreationDate(date);
        bp2.setPublishDate(pDate);
        bp2.setExpiryDate(eDate);
        bp2.setContent("This is a good post.");
        bp2.setCategoryIDs(arr1);
        dao.addBlogPost(bp2);
        bp3.setUserID(2);
        bp3.setTitle("Blog Three");
        bp3.setSubmitted(0);
        bp3.setCreationDate(date);
        bp3.setPublishDate(pDate);
        bp3.setExpiryDate(eDate);
        bp3.setContent("This is a terrible post.");
        bp3.setCategoryIDs(arr2);
        dao.addBlogPost(bp3);

        List<BlogPost> list = dao.getPublishedBlogPostsByCategory(cat.getCategoryID());
        assertEquals(list.size(), 2);
    }

    @Test
    public void getAllPublishedAndUnpublishedBlogPosts() {
        BlogPost bp = new BlogPost();
        BlogPost bp2 = new BlogPost();
        BlogPost bp3 = new BlogPost();
        Date date = new Date(1447168607335l);
        Date pDate = new Date(1447171298000l);
        Date eDate = new Date(2530003298000l);
        Date pDate1 = new Date(32530780898000l);
        Date eDate1 = new Date(32530953698000l);
        int[] arr = {1, 2};
        int[] arr1 = {1, 3};
        int[] arr2 = {2, 3};
        bp.setUserID(1);
        bp.setTitle("Blog One");
        bp.setSubmitted(0);
        bp.setCreationDate(date);
        bp.setPublishDate(pDate);
        bp.setExpiryDate(eDate);
        bp.setContent("This is a great post.");
        bp.setCategoryIDs(arr);
        dao.addBlogPost(bp);
        bp2.setUserID(1);
        bp2.setTitle("Blog Two");
        bp2.setSubmitted(0);
        bp2.setCreationDate(date);
        bp2.setPublishDate(pDate);
        bp2.setExpiryDate(eDate);
        bp2.setContent("This is a good post.");
        bp2.setCategoryIDs(arr1);
        dao.addBlogPost(bp2);
        bp3.setUserID(1);
        bp3.setTitle("Blog Three");
        bp3.setSubmitted(0);
        bp3.setCreationDate(date);
        bp3.setPublishDate(pDate1);
        bp3.setExpiryDate(eDate1);
        bp3.setContent("This is a terrible post.");
        bp3.setCategoryIDs(arr2);
        dao.addBlogPost(bp3);
        List<BlogPost> list = dao.getAllPublishedBlogPosts();
        assertEquals(2, list.size());
        List<BlogPost> list1 = dao.getAllUnpublishedBlogPosts();
        assertEquals(1, list1.size());
    }

    @Test
    public void getAllUnapprovedAndExpiredBlogPosts() {
        BlogPost bp = new BlogPost();
        BlogPost bp2 = new BlogPost();
        BlogPost bp3 = new BlogPost();
        Date date = new Date(1447168607335l);
        Date pDate = new Date(1447171298000l);
        Date eDate = new Date(2530003298000l);
        Date pDate1 = new Date(1446393698000l);
        Date eDate1 = new Date(1447084898000l);
        int[] arr = {1, 2};
        int[] arr1 = {1, 3};
        int[] arr2 = {2, 3};
        bp.setBlogID(1);
        bp.setUserID(2);
        bp.setTitle("Blog One");
        bp.setSubmitted(1);
        bp.setCreationDate(date);
        bp.setPublishDate(pDate);
        bp.setExpiryDate(eDate);
        bp.setContent("This is a great post.");
        bp.setCategoryIDs(arr);
        dao.addBlogPost(bp);
        bp2.setBlogID(1);
        bp2.setUserID(2);
        bp2.setTitle("Blog Two");
        bp2.setSubmitted(1);
        bp2.setCreationDate(date);
        bp2.setPublishDate(pDate);
        bp2.setExpiryDate(eDate);
        bp2.setContent("This is a good post.");
        bp2.setCategoryIDs(arr1);
        dao.addBlogPost(bp2);
        bp3.setBlogID(1);
        bp3.setUserID(2);
        bp3.setTitle("Blog Three");
        bp3.setSubmitted(0);
        bp3.setCreationDate(date);
        bp3.setPublishDate(pDate1);
        bp3.setExpiryDate(eDate1);
        bp3.setContent("This is a terrible post.");
        bp3.setCategoryIDs(arr2);
        dao.addBlogPost(bp3);
        List<BlogPost> list = dao.getAllUnapprovedBlogPosts();
        assertEquals(2, list.size());
        List<BlogPost> list1 = dao.getAllExpiredBlogPosts();
        assertEquals(1, list1.size());
    }
    
    @Test
    public void getUnsubmittedBlogPosts() {
         BlogPost bp = new BlogPost();
        BlogPost bp2 = new BlogPost();
        BlogPost bp3 = new BlogPost();
        Date date = new Date(1447168607335l);
        Date pDate = new Date(1447171298000l);
        Date pDate1 = new Date(32503737698000l);
        bp.setBlogID(1);
        bp.setUserID(2);
        bp.setTitle("Blog One");
        bp.setSubmitted(1);
        bp.setCreationDate(date);
        bp.setPublishDate(pDate);
        bp.setContent("This is a great post.");
        dao.addBlogPost(bp);
        bp2.setBlogID(1);
        bp2.setUserID(2);
        bp2.setTitle("Blog Two");
        bp2.setSubmitted(0);
        bp2.setCreationDate(date);
        bp2.setPublishDate(pDate1);
        bp2.setContent("This is a good post.");
        dao.addBlogPost(bp2);
        bp3.setBlogID(1);
        bp3.setUserID(2);
        bp3.setTitle("Blog Three");
        bp3.setSubmitted(0);
        bp3.setCreationDate(date);
        bp3.setPublishDate(pDate1);
        bp3.setContent("This is a terrible post.");
        dao.addBlogPost(bp3);
        List<BlogPost> arr = dao.getAllUnSubmittedBlogPosts();
        assertEquals(arr.size(), 2);
    }

    // tests for webpage methods
    @Test
    public void addGetDeleteWebPage() {
        WebPage wp = new WebPage();
        wp.setTitle("This Title");
        wp.setContent("This is Dan's great thought that you should read");
        dao.addWebPage(wp);
        WebPage fromDb = dao.getWebPageById(wp.getPageID());
        assertEquals(fromDb.getTitle(), wp.getTitle());
        assertEquals(fromDb.getContent(), wp.getContent());
        dao.deleteWebPage(wp);
        assertNull(dao.getWebPageById(wp.getPageID()));
    }

    @Test
    public void addUpdateWebPage() {
        WebPage wp = new WebPage();
        wp.setTitle("This Title");
        wp.setContent("This is Dan's great thought that you should read");
        dao.addWebPage(wp);
        wp.setContent("This is the real, real content.");
        dao.updateWebPage(wp);
        WebPage fromDb = dao.getWebPageById(wp.getPageID());
        assertEquals(fromDb.getTitle(), wp.getTitle());
        assertEquals(fromDb.getContent(), wp.getContent());
    }

    @Test
    public void getAllWebPages() {
        WebPage wp = new WebPage();
        wp.setTitle("This Title");
        wp.setContent("This is Dan's great thought that you should read");
        dao.addWebPage(wp);
        WebPage wp2 = new WebPage();
        wp2.setTitle("This Other Title");
        wp2.setContent("This is Dan's great thought that you should read");
        dao.addWebPage(wp);
        List<WebPage> arr = dao.getAllWebPages();
        assertEquals(arr.size(), 2);
    }

    //tests for announcements
    @Test
    public void addGetDeleteAnnouncement() {
        Announcement an = new Announcement();
        Date pDate = new Date(1445317200000l);
        Date eDate = new Date(1447999200000l);
        an.setContent("Really really important stuff!");
        an.setPublishDate(pDate);
        an.setExpiryDate(eDate);
        dao.addAnnouncement(an);
        Announcement fromDb = dao.getAnnouncementById(an.getAnnouncementID());
        assertEquals(fromDb.getContent(), an.getContent());
        assertEquals(fromDb.getPublishDate().toString(), an.getPublishDate().toString());
        assertEquals(fromDb.getExpiryDate().toString(), an.getExpiryDate().toString());
        dao.deleteAnnouncement(an);
        assertNull(dao.getAnnouncementById(an.getAnnouncementID()));
    }

    @Test
    public void addUpdateAnnouncement() {
        Announcement an = new Announcement();
        Date pDate = new Date(1445317200000l);
        Date eDate = new Date(1447999200000l);
        an.setContent("Really important stuff!");
        an.setPublishDate(pDate);
        an.setExpiryDate(eDate);
        dao.addAnnouncement(an);
        an.setContent("This is the real, real important stuff.");
        dao.updateAnnouncement(an);
        Announcement fromDb = dao.getAnnouncementById(an.getAnnouncementID());
        assertEquals(fromDb.getContent(), an.getContent());
        assertEquals(fromDb.getPublishDate().toString(), an.getPublishDate().toString());
        assertEquals(fromDb.getExpiryDate().toString(), an.getExpiryDate().toString());
    }

    @Test
    public void getAllAnnouncements() {
        Announcement an = new Announcement();
        Date pDate = new Date(1445317200000l);
        Date eDate = new Date(1447999200000l);
        an.setContent("Really important stuff!");
        an.setPublishDate(pDate);
        an.setExpiryDate(eDate);
        dao.addAnnouncement(an);
        Announcement an1 = new Announcement();
        an1.setContent("Really important stuff!");
        an1.setPublishDate(pDate);
        an1.setExpiryDate(eDate);
        dao.addAnnouncement(an);
        List<Announcement> arr = dao.getAnnouncements();
        assertEquals(arr.size(), 2);
    }

    @Test
    public void getAllPublishedAnnouncements() {
        Announcement an = new Announcement();
        Announcement an1 = new Announcement();
        Announcement an2 = new Announcement();
        Date pDate = new Date(1447171298000l);
        Date eDate = new Date(2530003298000l);
        Date pDate1 = new Date(32530780898000l);
        Date eDate1 = new Date(32530953698000l);
        an.setContent("Published stuff.");
        an.setPublishDate(pDate);
        an.setExpiryDate(eDate);
        dao.addAnnouncement(an);
        an1.setContent("Unpublished stuff.");
        an1.setPublishDate(pDate1);
        an1.setExpiryDate(eDate1);
        dao.addAnnouncement(an1);
        an2.setContent("Published stuff.");
        an2.setPublishDate(pDate);
        an2.setExpiryDate(eDate);
        dao.addAnnouncement(an2);
        List<Announcement> arr = dao.getPublishedAnnouncements();
        assertEquals(arr.size(), 2);
    }

    //tests for categories
    @Test
    public void addGetCategory() {
        Category cat = new Category();
        cat.setCategory("stuff & things");
        dao.addCategory(cat);
        Category fromDb = dao.getCategoryById(cat.getCategoryID());
        assertEquals(fromDb.getCategory(), cat.getCategory());
        dao.getIdByCategory("stuff & things");
        assertEquals(fromDb.getCategory(), cat.getCategory());
    }

    @Test
    public void getAllUsedCategories() {
        BlogPost bp = new BlogPost();
        Category cat = new Category();
        Category cat1 = new Category();
        Category cat2 = new Category();
        Date date = new Date(1447168607335l);
        Date pDate = new Date(1445317200000l);
        Date eDate = new Date(1447999200000l);
        int[] arr = {1, 2};
        bp.setBlogID(1);
        bp.setUserID(2);
        bp.setTitle("Blog Two");
        bp.setSubmitted(0);
        bp.setCreationDate(date);
        bp.setPublishDate(pDate);
        bp.setExpiryDate(eDate);
        bp.setContent("This is a great post.");
        bp.setCategoryIDs(arr);
        dao.addBlogPost(bp);
        cat.setCategoryID(1);
        cat.setCategory("number one");
        dao.addCategory(cat);
        cat1.setCategoryID(2);
        cat1.setCategory("stuff happens");
        dao.addCategory(cat1);
        cat2.setCategoryID(3);
        cat2.setCategory("shouldn't matter");
        dao.addCategory(cat2);
        List<Category> list = dao.getAllUsedCategories();
        assertEquals(list.size(), 2);
    }

    @Test
    public void getAllCategories() {
        Category cat = new Category();
        cat.setCategory("really great");
        dao.addCategory(cat);
        Category cat1 = new Category();
        cat1.setCategory("not great");
        dao.addCategory(cat1);
        List<Category> arr = dao.getAllCategories();
        assertEquals(arr.size(), 2);
    }

}
