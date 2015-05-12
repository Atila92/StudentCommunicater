package com.example.atila.studentcommunicator;

/**
 * Created by Atila on 01-05-2015.
 */
public class Post {
    private int postId;
    private String courseName;
    private String name;
    private String message;

    public Post(int postId, String courseName, String name, String message) {
        this.postId = postId;
        this.courseName = courseName;
        this.name = name;
        this.message = message;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
