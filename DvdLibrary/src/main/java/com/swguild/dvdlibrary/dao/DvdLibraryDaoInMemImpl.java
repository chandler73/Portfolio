/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swguild.dvdlibrary.dao;
import com.swguild.dvdlibrary.model.DVD;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 *
 * @author apprentice
 */
public class DvdLibraryDaoInMemImpl implements DvdLibraryDao{
       // holds all of our Contact objects - simulates the database
    private Map<Integer, DVD> dvdMap = new HashMap<>();
 // used to assign ids to Contacts - simulates the auto increment
    // primary key for Contacts in a database
    private static int dvdIdCounter = 0;

    @Override
    public DVD addDVD(DVD movie) {
        // assign the current counter values as the contactid
        movie.setDvdId(dvdIdCounter);
        // increment the counter so it is ready for use for the next contact
        dvdIdCounter++;
        dvdMap.put(movie.getDvdId(), movie);
        return movie;
    }

    @Override
    public void removeDVD(int dvdId) {
        dvdMap.remove(dvdId);
    }

    @Override
    public void updateDVD(DVD movie) {
        dvdMap.put(movie.getDvdId(), movie);
    }

    @Override
    public List<DVD> getAllDVDs() {
        Collection<DVD> c = dvdMap.values();
        return new ArrayList(c);
    }

    @Override
    public DVD getDVDById(int dvdId) {
        return dvdMap.get(dvdId);
    }

    @Override
    public List<DVD> searchDVDs(Map<SearchTerm, String> criteria) {
        // Get all the search terms from the map
        String titleCriteria = criteria.get(SearchTerm.TITLE);
        String directorCriteria = criteria.get(SearchTerm.DIRECTOR);
        String studioCriteria = criteria.get(SearchTerm.STUDIO);
        String mpaaRatingCriteria = criteria.get(SearchTerm.MPAA_RATING);
        // Declare all the predicate conditions
        Predicate<DVD> titleMatches;
        Predicate<DVD> directorMatches;
        Predicate<DVD> studioMatches;
        Predicate<DVD> mpaaRatingMatches;
 // Placeholder predicate - always returns true. Used for search terms
        // that are empty
        Predicate<DVD> truePredicate = (c) -> {
            return true;
        };
 // Assign values to predicates. If a given search term is empty, just
        // assign the default truePredicate, otherwise assign the predicate that
        // properly filters for the given term.
        titleMatches = (titleCriteria == null || titleCriteria.isEmpty())
                ? truePredicate
                : (c) -> c.getTitle().equals(titleCriteria);

        directorMatches = (directorCriteria == null || directorCriteria.isEmpty())
                ? truePredicate
                : (c) -> c.getDirector().equals(directorCriteria);

        studioMatches = (studioCriteria == null || studioCriteria.isEmpty())
                ? truePredicate
                : (c) -> c.getStudio().equals(studioCriteria);

        mpaaRatingMatches = (mpaaRatingCriteria == null || mpaaRatingCriteria.isEmpty())
                ? truePredicate
                : (c) -> c.getMpaaRating().equals(mpaaRatingCriteria);

 // Return the list of Contacts that match the given criteria. To do this we
        // just AND all the predicates together in a filter operation.
        return dvdMap.values().stream()
                .filter(titleMatches
                        .and(directorMatches)
                        .and(studioMatches)
                        .and(mpaaRatingMatches))
                .collect(Collectors.toList());
    }
}
