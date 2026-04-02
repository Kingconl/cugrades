package com.connor.cu_grades.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "grade_distributions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_grade_distribution_offering_label",
                        columnNames = {"offering_id", "grade_label"}
                )
        }
)
public class GradeDistribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

    @Column(name = "grade_label", nullable = false, length = 10)
    private String gradeLabel;

    @Column(nullable = false)
    private Integer count;

    public GradeDistribution() {}

    public GradeDistribution(Integer id, Offering offering, String gradeLabel, Integer count) {
        this.id = id;
        this.offering = offering;
        this.gradeLabel = gradeLabel;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public Offering getOffering() {
        return offering;
    }

    public String getGradeLabel() {
        return gradeLabel;
    }

    public Integer getCount() {
        return count;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    public void setGradeLabel(String gradeLabel) {
        this.gradeLabel = gradeLabel;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
