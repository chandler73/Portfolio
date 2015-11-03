/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swguild.dvdlibrary.dao;

import com.swguild.dvdlibrary.model.DVD;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface DvdLibraryDao {
    // add the given Contact to the data store

    public DVD addDVD(DVD movie);

    // remove the Contact with the given id from the data store

    public void removeDVD(int dvdId);

    // update the given Contact in the data store

    public void updateDVD(DVD movie);

    // retrieve all Contacts from the data store

    public List<DVD> getAllDVDs();

    // retrieve the Contact with the given id from the data store

    public DVD getDVDById(int dvdId);

    // search for Contacts by the given search criteria values

    public List<DVD> searchDVDs(Map<SearchTerm, String> criteria);
}
