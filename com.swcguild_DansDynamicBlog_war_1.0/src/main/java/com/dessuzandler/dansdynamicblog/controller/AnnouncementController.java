package com.dessuzandler.dansdynamicblog.controller;

import com.dessuzandler.dansdynamicblog.dao.BlogDAO;
import com.dessuzandler.dansdynamicblog.model.Announcement;
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
public class AnnouncementController {

    private BlogDAO dao;
    
    @Inject
    public AnnouncementController (BlogDAO dao){
        this.dao = dao;
    }

    @RequestMapping(value = {"/announcement-admin"}, method = RequestMethod.GET)
    public String displayAnnouncementPage() {
        return "announcement-admin";
    }

    

    @RequestMapping(value = "/announcements-all", method = RequestMethod.GET)
    @ResponseBody
    public List<Announcement> getAllAnnouncements() {
        return dao.getAnnouncements();
    }
    
    @RequestMapping(value = "/announcements-published", method = RequestMethod.GET)
    @ResponseBody
    public List<Announcement> getPublishedAnnouncements() {
        return dao.getPublishedAnnouncements();
    }
    
    @RequestMapping(value = {"/announcement"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Announcement createAnnouncement(@Valid @RequestBody Announcement announcement) {
        dao.addAnnouncement(announcement);
        return announcement;
    }
    
    @RequestMapping(value = "/announcement/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAnnouncement(@PathVariable("id") int id, @Valid @RequestBody Announcement announcement) {
        announcement.setAnnouncementID(id);
        dao.updateAnnouncement(announcement);
    }
    
    @RequestMapping(value = "/announcement/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAnnouncementById(@PathVariable("id") int id) {
        dao.deleteAnnouncement(dao.getAnnouncementById(id));
    }
    
    @RequestMapping(value = "/announcement/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Announcement getAnnouncementById(@PathVariable("id") int id) {
        return dao.getAnnouncementById(id);
    }
}

