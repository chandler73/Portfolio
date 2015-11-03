package com.swguild.dvdlibrary.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author apprentice
 */
import com.swguild.dvdlibrary.dao.DvdLibraryDao;
import com.swguild.dvdlibrary.model.DVD;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class DvdLibraryDaoTest {

    private DvdLibraryDao dao;

    public DvdLibraryDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // NEW CODE START- this cleans up the database table before each test
        // Ask Spring for my DAO
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = (DvdLibraryDao) ctx.getBean("dvdLibraryDao");
        // Grab a JdbcTemplate to use for cleaning up
        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("delete from dvds");
        // NEW CODE STOP
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addGetDeleteContact() {
        // create new contact
        DVD na = new DVD();
        na.setTitle("Goonies");
        na.setReleaseDate("1985");
        na.setMpaaRating("PG");
        na.setDirector("Stephen Spielberg");
        na.setStudio("Warner Bros.");
        na.setNotes("Chunk!");
        dao.addDVD(na);
        DVD fromDb = dao.getDVDById(na.getDvdId());
        assertEquals(fromDb.getDvdId(), na.getDvdId());
        assertEquals(fromDb.getTitle(), na.getTitle());
        assertEquals(fromDb.getReleaseDate(), na.getReleaseDate());
        assertEquals(fromDb.getMpaaRating(), na.getMpaaRating());
        assertEquals(fromDb.getDirector(), na.getDirector());
        assertEquals(fromDb.getStudio(), na.getStudio());
        assertEquals(fromDb.getNotes(), na.getNotes());
        dao.removeDVD(na.getDvdId());
        assertNull(dao.getDVDById(na.getDvdId()));
    }

    @Test
    public void addUpdateContact() {
        // create new contact
        DVD na = new DVD();
        na.setTitle("Die Hard");
        na.setReleaseDate("1988");
        na.setMpaaRating("PG");
        na.setDirector("Ridley Scott");
        na.setStudio("Fox");
        na.setNotes("Yipee Ki Ai Ay motherf*&^$r");
        dao.addDVD(na);
        na.setMpaaRating("R");
        dao.updateDVD(na);
        DVD fromDb = dao.getDVDById(na.getDvdId());
        assertEquals(fromDb.getDvdId(), na.getDvdId());
        assertEquals(fromDb.getTitle(), na.getTitle());
        assertEquals(fromDb.getReleaseDate(), na.getReleaseDate());
        assertEquals(fromDb.getMpaaRating(), na.getMpaaRating());
        assertEquals(fromDb.getDirector(), na.getDirector());
        assertEquals(fromDb.getStudio(), na.getStudio());
        assertEquals(fromDb.getNotes(), na.getNotes());
    }

    @Test
    public void getAllContacts() {
        // create new contact
        DVD na = new DVD();
        na.setTitle("Back to the Future");
        na.setReleaseDate("1985");
        na.setMpaaRating("PG");
        na.setDirector("Stephen Spielberg");
        na.setStudio("Warner Bros.");
        na.setNotes("McFly!");
        dao.addDVD(na);
        // create new contact
        DVD na2 = new DVD();
        na2.setTitle("Batman");
        na2.setReleaseDate("1989");
        na2.setMpaaRating("PG-13");
        na2.setDirector("Tim Burton");
        na2.setStudio("Fox");
        na2.setNotes("Dancin' with the devil.");
        dao.addDVD(na);
        List<DVD> aArr = dao.getAllDVDs();
        assertEquals(aArr.size(), 2);
    }
}
