package com.connor.cu_grades.model;



import jakarta.persistence.*;

@Entity
@Table(
        name = "terms"
)
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false, length = 10, unique = true)
    private String code;

    @Column(nullable = false, length = 10)
    private String semester;

    @Column(nullable = false)
    private Integer year;

    public Term() {}

    public Term(Integer id, String code, String semester, Integer year) {
        this.id = id;
        this.code = code;
        this.semester = semester;
        this.year = year;
    }
    public Integer getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getCode() {
        return code;
    }

    public String getSemester() {
        return semester;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
