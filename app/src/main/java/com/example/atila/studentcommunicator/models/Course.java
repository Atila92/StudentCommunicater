package com.example.atila.studentcommunicator.models;

/**
 * Created by Atila on 30-04-2015.
 */
public class Course {
    private String courseName;
    private String email;
    private int courseId;

    public Course(String courseName, int courseId) {
        this.courseName = courseName;
        this.email = email;
        this.courseId = courseId;
    }

    public String getName() {
        return courseName;
    }

    public void setName(String courseName) {
        this.courseName = courseName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
