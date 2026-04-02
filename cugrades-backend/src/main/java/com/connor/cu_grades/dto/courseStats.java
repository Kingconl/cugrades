package com.connor.cu_grades.dto;

import java.math.BigDecimal;

public class courseStats {
    private String courseCode;
    private String title;
    private BigDecimal median;
    private BigDecimal mode;
    private int offeringsCount;
    public courseStats(String courseCode, String title, BigDecimal median, BigDecimal mode, int offeringsCount) {
        this.courseCode = courseCode;
        this.title = title;
        this.median = median;
        this.mode = mode;
        this.offeringsCount = offeringsCount;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getMedian() {
        return median;
    }

    public void setMedian(BigDecimal median) {
        this.median = median;
    }

    public BigDecimal getMode() {
        return mode;
    }

    public void setMode(BigDecimal mode) {
        this.mode = mode;
    }

    public int getOfferingsCount() {
        return offeringsCount;
    }

    public void setOfferingsCount(int offeringsCount) {
        this.offeringsCount = offeringsCount;
    }
}
