package model;
/**
 * @author Jacob Bagley
 */

import java.util.ArrayList;
import java.util.Date;

public class Comment {

    private String body;
    private User user;
    private ArrayList<Comment> comments;
    private Date dateOfComment;
    
    public Comment(String body, User user, Date dateOfComment){
        this.body = body;
        this.user = user;
        this.dateOfComment = dateOfComment;
    }
    public Comment(String body, User user, ArrayList<Comment> comments, Date dateOfComment) {
        this.body = body;
        this.user = user;
        this.comments = comments;
        this.dateOfComment = dateOfComment;
    }
    public Comment(String body, User user) {
        this.body = body;
        this.user = user;
        this.comments = new ArrayList<Comment>();
        this.dateOfComment = new Date();
    }
    public String getBody() {
        return body;
    }
    public User getUser() {
        return user;
    }
    public ArrayList<Comment> getComments() {
        return comments;
    }
    public Date getdateOfComment() {
        return dateOfComment;
    }

    public boolean makeComment(String comment, User user) {///////
        if (user == null || comment == null || comment == "") {
            return false;
        } 
        Comment newComment = new Comment(comment, user, new Date());
        comments.add(newComment);
        return true;
    }
}
