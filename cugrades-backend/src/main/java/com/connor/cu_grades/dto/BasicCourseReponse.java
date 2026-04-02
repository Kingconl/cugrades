package com.connor.cu_grades.dto;


public class BasicCourseReponse {
    private int id;
    private String courseNumber;
    private String title;
    public BasicCourseReponse(int id, String courseNumber, String title) {
        this.id = id;
        this.courseNumber = courseNumber;
        this.title = title;
    }

    public int getId() { return id; }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getTitle() {
        return title;
    }
}
