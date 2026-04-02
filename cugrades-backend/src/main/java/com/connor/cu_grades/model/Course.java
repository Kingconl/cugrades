package com.connor.cu_grades.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "courses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"subject_id", "course_number"})
        }
)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "course_number", nullable = false, length = 10)
    private String courseNumber;

    @Column
    private String title;

    public Course() {}

    public Course(Integer id, Subject subject, String courseNumber, String title) {
        this.id = id;
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
