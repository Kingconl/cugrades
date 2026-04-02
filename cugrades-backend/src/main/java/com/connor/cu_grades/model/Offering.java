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
    private BigDecimal median;

    @Column(precision = 5, scale = 2)
    private BigDecimal mode;
    public Offering() {}

    public Offering(Integer id, Course course, Term term, Professor professor, String section,
                    Integer studentsCount, BigDecimal median, BigDecimal mode) {
        this.id = id;
        this.course = course;
        this.term = term;
        this.professor = professor;
        this.section = section;
        this.studentsCount = studentsCount;
        this.median = median;
        this.mode = mode;
    }

    public Integer getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public Integer getLetterGradeCount() {
        return letterGradeCount;
    }

    public void setLetterGradeCount(Integer letterGradeCount) {
        this.letterGradeCount = letterGradeCount;
    }

    public Term getTerm() {
        return term;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getSection() {
        return section;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public BigDecimal getMedian() {
        return median;
    }

    public BigDecimal getMode() {
        return mode;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setMedian(BigDecimal median) {
        this.median = median;
    }

    public void setMode(BigDecimal mode) {
        this.mode = mode;
    }

}
