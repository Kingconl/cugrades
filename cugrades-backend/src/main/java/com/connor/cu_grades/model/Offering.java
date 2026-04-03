package com.connor.cu_grades.model;
import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(
        name = "offerings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_offering_course_term_prof_section",
                        columnNames = {"course_id", "term_id", "professor_id", "section"}
                )
        }
)
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(length = 20)
    private String section;

    @Column(name = "students_count")
    private Integer studentsCount;

    @Column(name = "letter_grade_count")
    private Integer letterGradeCount;


    @Column(precision = 5, scale = 2)
    private BigDecimal mean;

    @Column(precision = 5, scale = 2)
    private BigDecimal mode;
    public Offering() {}

    public Offering(Integer id, Course course, Term term, Professor professor, String section,
                    Integer studentsCount, BigDecimal mean, BigDecimal mode) {
        this.id = id;
        this.course = course;
        this.term = term;
        this.professor = professor;
        this.section = section;
        this.studentsCount = studentsCount;
        this.mean = mean;
        this.mode = mode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public BigDecimal getMode() {
        return mode;
    }

    public void setMode(BigDecimal mode) {
        this.mode = mode;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public Integer getLetterGradeCount() {
        return letterGradeCount;
    }

    public void setLetterGradeCount(Integer letterGradeCount) {
        this.letterGradeCount = letterGradeCount;
    }

    public BigDecimal getMean() {
        return mean;
    }

    public void setMean(BigDecimal mean) {
        this.mean = mean;
    }
}
