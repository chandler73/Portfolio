package com.dessuzandler.dansdynamicblog.model;

import java.sql.Date;
import java.util.Arrays;

public class BlogPost {

    private int blogID;
    private int userID;
    private String title;
    private int submitted;
    private Date creationDate;
    private Date publishDate;
    private Date expiryDate;
    private String content;
    private int[] categoryIDs;

    public int[] getCategoryIDs() {
        return categoryIDs;
    }

    public void setCategoryIDs(int[] categories) {
        this.categoryIDs = categories;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {

        this.submitted = submitted;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BlogPost other = (BlogPost) obj;
        if (this.blogID != other.blogID) {
            return false;
        }
        if (!(this.categoryIDs == null || other.categoryIDs == null)) {
            if (this.categoryIDs.length != other.categoryIDs.length) {
                return false;
            }
            for (int i = 0; i < this.categoryIDs.length; i++) {
                if (this.categoryIDs[i] != other.categoryIDs[i]) {
                    return false;
                }
            }
        }

        if (!(this.content.equals(other.content))) {
            return false;
        }

        if (this.creationDate.compareTo(other.creationDate) != 0) {
            return false;
        }
        if (this.expiryDate.compareTo(other.expiryDate) != 0 ) {
            return false;
        }
        if (this.publishDate.compareTo(other.publishDate) != 0) {
            return false;
        }
        if (this.submitted != other.submitted) {
            return false;
        }
        if (!(this.title.equals(other.title))) {
            return false;
        }
        if (this.userID != other.userID) {
            return false;
        }

        return true;
    }
}
