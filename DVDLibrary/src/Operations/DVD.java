/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

/**
 *
 * @author apprentice
 */
public class DVD {
    private String title;
    private String releaseDate;
    private String mpaa;
    private String directorsName;
    private String studio;
    private String userRating;
    
    public DVD(String title, String releaseDate, String mpaa, String directorsName, String studio, String userRating){
    this.title = title;
    this.releaseDate = releaseDate;
    this.mpaa = mpaa;
    this.directorsName = directorsName;
    this.studio = studio;
    this.userRating = userRating;
}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMpaa() {
        return mpaa;
    }

    public void setMpaa(String mpaa) {
        this.mpaa = mpaa;
    }

    public String getDirectorsName() {
        return directorsName;
    }

    public void setDirectorsName(String directorsName) {
        this.directorsName = directorsName;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
    
}
