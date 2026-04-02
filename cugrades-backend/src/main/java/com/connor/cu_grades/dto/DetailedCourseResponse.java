package com.connor.cu_grades.dto;

import java.math.BigDecimal;
import java.util.List;

public class DetailedCourseResponse {

    private String subject;
    private String courseCode;
    private String title;
    private OverallStats overallStats;
    private List<ProfessorStats> professors;

    public DetailedCourseResponse(
            String subject,
            String courseCode,
            String title,
            OverallStats overallStats,
            List<ProfessorStats> professors
    ) {
        this.subject = subject;
        this.courseCode = courseCode;
        this.title = title;
        this.overallStats = overallStats;
        this.professors = professors;
    }

    public String getSubject() {
        return subject;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public OverallStats getOverallStats() {
        return overallStats;
    }

    public List<ProfessorStats> getProfessors() {
        return professors;
    }

    public static class OverallStats {
        private Double median;
        private Double mode;

        public OverallStats( Double median, Double mode) {
            this.median = median;
            this.mode = mode;
        }



        public Double getMedian() {
            return median;
        }

        public Double getMode() {
            return mode;
        }
    }

    public static class ProfessorStats {
        private String name;
        private int timesTaught;
        private List<GradeDistributionItem> distribution;
        private List<OfferingItem> offerings;

        public ProfessorStats(
                String name,
                int timesTaught,
                List<GradeDistributionItem> distribution,
                List<OfferingItem> offerings
        ) {
            this.name = name;
            this.timesTaught = timesTaught;
            this.distribution = distribution;
            this.offerings = offerings;
        }

        public String getName() {
            return name;
        }

        public int getTimesTaught() {
            return timesTaught;
        }



        public List<GradeDistributionItem> getDistribution() {
            return distribution;
        }

        public List<OfferingItem> getOfferings() {
            return offerings;
        }
    }

    public static class GradeDistributionItem {
        private String grade;
        private int count;

        public GradeDistributionItem(String grade, int count) {
            this.grade = grade;
            this.count = count;
        }

        public String getGrade() {
            return grade;
        }

        public int getCount() {
            return count;
        }
    }

    public static class OfferingItem {
        private String term;
        private String section;
        private BigDecimal median;
        private BigDecimal mode;

        public OfferingItem(
                String term,
                String section,
                BigDecimal median,
                BigDecimal mode
        ) {
            this.term = term;
            this.section = section;
            this.median = median;
            this.mode = mode;
        }

        public String getTerm() {
            return term;
        }

        public String getSection() {
            return section;
        }

        public BigDecimal getMedian() {
            return median;
        }

        public BigDecimal getMode() {
            return mode;
        }
    }
}
