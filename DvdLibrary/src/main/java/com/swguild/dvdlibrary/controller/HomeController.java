/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swguild.dvdlibrary.controller;

import com.swguild.dvdlibrary.dao.DvdLibraryDao;
import com.swguild.dvdlibrary.model.DVD;
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

/**
 *
 * @author apprentice
 */
@Controller
public class HomeController {

    // The controller uses the dao to do all the heavy lifting of storing
    // and retrieving Contacts
    private DvdLibraryDao dao;

    // @Inject and @Autowired are synonyms
    // This annotation tells the Spring Framework to hand an object of type
    // ContactListDao to this constructor when it creates an instance of this
    // class (which happens when the web application starts). If there is no
    // object of type ContactListDao defined in the Spring application context,
    // Spring Framework will throw an exception.
    @Inject
    public HomeController(DvdLibraryDao dao) {
        this.dao = dao;
    }

 // This method will be invoked by Spring MVC when it sees a request for
    // ContactListMVC/mainAjaxPage.
    @RequestMapping(value = {"/mainAjaxPage"}, method = RequestMethod.GET)
    public String displayMainAjaxPage() {
 // This method does nothing except return the logical name of the
        // view component (/jsp/home.jsp) that should be invoked in response
        // to this request.
        return "mainAjaxPage";
    }

    @RequestMapping(value = {"/", "/dvdhome"}, method = RequestMethod.GET)
    public String displayHomePage() {
        return "dvdhome";
    }

    // This method will be invoked by Spring MVC when it sees a GET request for
    // ContactListMVC/contact/<some-contact-id>. It retrieves the Contact
    // associated with the given contact id (or null if no such Contact
    // exists).
    //
    // @ResponseBody indicates that the object returned by this method should
    // be put in the body of the response going back to the caller.
    //
    // @PathVariable indicates that the portion of the URL path marked by curly
    // braces {...} should be stripped out, converted to an int and passed into
    // this method when it is invoked.
    @RequestMapping(value = "/dvd/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DVD getDVD(@PathVariable("id") int id) {
        // retrieve the Contact associated with the given id and return it
        return dao.getDVDById(id);
    }

// This method will be invoked by Spring MVC when it sees a POST request for
// ContactListMVC/contact. It persists the given Contact to the data layer.
//
// @ResponseStatus tells Spring MVC to return an HTTP CREATED status upon success
//
// @ResponseBody indicates that the object returned by this method should
// be put in the body of the response going back to the caller.
//
// @RequestBody indicates that we expect a Contact object
// in the body of the incoming request.
    @RequestMapping(value = "/dvd", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DVD createDVD(@Valid @RequestBody DVD movie) {
        // persist the incoming contact
        dao.addDVD(movie);
        // The addContact call to the dao assigned a contactId to the incoming
        // Contact and set that value on the object. Now we return the updated
        // object to the caller.
        return movie;
    }

    // This method will be invoked by Spring MVC when it sees a DELETE request
    // for ContactListMVC/contact/<some-contact-id>. It deletes the Contact
    // associated with the give id from the data layer (it does nothing if there
    // is no such Contact).
    //
    // @ResponseStatus tells Spring MVC to return HTTP NO_CONTENT from this call
    // because this method has no return value.
    //
    // @PathVariable indicates that the portion of the URL path marked by curly
    // braces {...} should be stripped out, converted to an int and passed into 
    // this method when it is invoked.
    @RequestMapping(value = "/dvd/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDVD(@PathVariable("id") int id) {
        // remove the Contact associated with the given id from the data layer 
        dao.removeDVD(id);
    }
    // This method will be invoked by Spring MVC when it sees a PUT request for
    // ContactListMVC/contact/<some-contact-id>. It updates the given Contact
    // to the data layer.
    //
    // @ResponseStatus tells Spring MVC to return HTTP NO_CONTENT from this call
    // because this method has no return value.
    //
    // @PathVariable indicates that the portion of the URL path marked by curly
    // braces {...} should be stripped out, converted to an int and passed into
    // this method when it is invoked.
    //
    // @RequestBody indicates that we expect a Contact object in the body of the
    // incoming request.

    @RequestMapping(value = "/dvd/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putDVD(@PathVariable("id") int id, @RequestBody DVD movie) {
        // set the value of the PathVariable id on the incoming Contact object
        // to ensure that a) the contact id is set on the object and b) that
        // the value of the PathVariable id and the Contact object id are the
        // same.
        movie.setDvdId(id);
        // update the contact
        dao.updateDVD(movie);
    }

    // This method will be invoked by Spring MVC when it sees a GET request for
    // ContactListMVC/contacts. It retrieves all of the Contacts from the
    // data layer and returns them in a List.
    //
    // @ResponseBody indicates that the List returned by this method should
    // be put in the body of the response going back to the caller.
    @RequestMapping(value = "/dvds", method = RequestMethod.GET)
    @ResponseBody
    public List<DVD> getAllDVDs() {
        // get all of the Contacts from the data layer
        return dao.getAllDVDs();
    }
}
