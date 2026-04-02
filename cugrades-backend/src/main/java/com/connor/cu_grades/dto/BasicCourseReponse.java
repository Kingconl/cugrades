package com.connor.cu_grades.dto;


public class BasicCourseReponse {
    private int id;
    private String courseNumber;
    private String title;
    private String subject;
    public BasicCourseReponse(int id, String courseNumber, String title, String subject) {
        this.id = id;
        this.courseNumber = courseNumber;
        this.title = title;
        this.subject = subject;
    }

    public int getId() { return id; }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }
}
