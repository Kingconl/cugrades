package com.connor.cu_grades.dto;

import java.util.List;

public class courseStatsReponse {
    private String subject;
    private List<CourseResponse> courses;

    courseStatsReponse(String subject, List<CourseResponse> courses){
        this.subject = subject;
        this.courses = courses;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public List<CourseResponse> getCourses() {
        return courses;
    }
    public void setCourses(List<CourseResponse> courses) {
        this.courses = courses;
    }
}
