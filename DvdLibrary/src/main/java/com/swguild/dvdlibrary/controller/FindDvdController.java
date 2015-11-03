/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swguild.dvdlibrary.controller;

import com.swguild.dvdlibrary.dao.DvdLibraryDao;
import com.swguild.dvdlibrary.dao.SearchTerm;
import com.swguild.dvdlibrary.model.DVD;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@Controller
public class FindDvdController {
    
     private DvdLibraryDao dao;
    
     @Inject
    public FindDvdController(DvdLibraryDao dao) {
        this.dao = dao;
    }
    
    @RequestMapping(value={"/finddvd"}, method=RequestMethod.GET)
    public String displayFindDvd() {
        return "finddvd";
    }
    
        // This method will be invoked by Spring MVC when it sees a POST request for
 // ContactListMVC/search/contacts. It translates the entered search terms
 // from the given Map<String, String> to a Map<SearchTerm, String>, passes
 // the search criteria to the DAO, and returns the search results to the
 // caller.
 @RequestMapping(value="search/dvds", method=RequestMethod.POST)
 @ResponseBody
 public List<DVD> searchDVDs(@RequestBody Map<String, String>
searchMap) {
 // Create the map of search criteria to send to the DAO
 Map<SearchTerm, String> criteriaMap = new HashMap<>();

 // Determine which search terms have values, translate the String
 // keys into SearchTerm enums, and set the corresponding values
 // appropriately.
 String currentTerm = searchMap.get("title");
 if (!currentTerm.isEmpty()) {
 criteriaMap.put(SearchTerm.TITLE, currentTerm);
 }
 currentTerm = searchMap.get("director");
 if (!currentTerm.isEmpty()) {
 criteriaMap.put(SearchTerm.DIRECTOR, currentTerm);
 }
 currentTerm = searchMap.get("studio");
 if (!currentTerm.isEmpty()) {
 criteriaMap.put(SearchTerm.STUDIO, currentTerm);
 }
 currentTerm = searchMap.get("mpaaRating");
 if (!currentTerm.isEmpty()) {
 criteriaMap.put(SearchTerm.MPAA_RATING, currentTerm);
 }
 currentTerm = searchMap.get("releaseDate");
 if (!currentTerm.isEmpty()) {
 criteriaMap.put(SearchTerm.RELEASE_DATE, currentTerm);
 }

 return dao.searchDVDs(criteriaMap);
 }
}
