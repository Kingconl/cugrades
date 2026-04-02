package com.connor.cu_grades.dto;

import java.math.BigDecimal;
import java.util.List;

public class CourseResponse {
    private String subject;
    private String courseCode;
    private String title;
    private OverallStats overallStats;
    private List<GradeDistributionItem> distribution;
    private int studentCount;
    public CourseResponse() {}

    public CourseResponse(String subject, String courseCode, String title, int studentCount,
                          OverallStats overallStats,
                          List<GradeDistributionItem> distribution) {
        this.subject = subject;
        this.courseCode = courseCode;
        this.title = title;
        this.overallStats = overallStats;
        this.distribution = distribution;
        this.studentCount = studentCount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
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

    public OverallStats getOverallStats() {
        return overallStats;
    }

    public void setOverallStats(OverallStats overallStats) {
        this.overallStats = overallStats;
    }

    public List<GradeDistributionItem> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<GradeDistributionItem> distribution) {
        this.distribution = distribution;
    }

    public static class OverallStats {
        private BigDecimal median;
        private BigDecimal mode;

        public OverallStats() {}

        public OverallStats(BigDecimal median, BigDecimal mode) {
            this.median = median;
            this.mode = mode;
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
    }

    public static class GradeDistributionItem {
        private String grade;
        private Integer count;

        public GradeDistributionItem() {}

        public GradeDistributionItem(String grade, Integer count) {
            this.grade = grade;
            this.count = count;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
