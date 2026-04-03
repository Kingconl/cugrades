package com.connor.cu_grades.service;

import com.connor.cu_grades.dto.BasicCourseReponse;
import com.connor.cu_grades.dto.CourseResponse;
import com.connor.cu_grades.dto.DetailedCourseResponse;
import com.connor.cu_grades.model.Course;
import com.connor.cu_grades.model.GradeDistribution;
import com.connor.cu_grades.model.Offering;
import com.connor.cu_grades.model.Professor;
import com.connor.cu_grades.repository.CourseRepository;
import com.connor.cu_grades.repository.GradeDistributionRepository;
import com.connor.cu_grades.repository.OfferingRepository;
import com.connor.cu_grades.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final OfferingRepository offeringRepository;
    private final GradeDistributionRepository gradeDistributionRepository;

    public CourseService(SubjectRepository subjectRepository, CourseRepository courseRepository, OfferingRepository offeringRepository, GradeDistributionRepository gradeDistributionRepository) {
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.offeringRepository = offeringRepository;
        this.gradeDistributionRepository = gradeDistributionRepository;
    }



    public List<CourseResponse> getCourseListPooled(String code, String prefix, Integer limit, Integer offset) {
        Optional<Integer> subjectIdOpt = subjectRepository.findIdByCodeIgnoreCase(code);
        limit = (limit == null || limit <= 0) ? 6 : limit;
        offset = (offset == null || offset < 0) ? 0 : offset;
        if (subjectIdOpt.isEmpty()) {
            return Collections.emptyList();
        }

        Integer subjectId = subjectIdOpt.get();
        List<Course> courses;
        courses = courseRepository.findCoursesWithPagination(subjectId, prefix, limit, offset);
        if (courses.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> courseIds = courses.stream()
                .map(Course::getId)
                .toList();

        List<Offering> offerings = offeringRepository.findAllByCourseIdIn(courseIds);
        if (offerings.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> offeringIds = offerings.stream()
                .map(Offering::getId)
                .toList();

        List<GradeDistribution> gradeDistributions =
                gradeDistributionRepository.findAllByOfferingIdIn(offeringIds);

        Map<Integer, List<Offering>> offeringsByCourseId = offerings.stream()
                .collect(Collectors.groupingBy(off -> off.getCourse().getId()));

        Map<Integer, List<GradeDistribution>> gradesByOfferingId = gradeDistributions.stream()
                .collect(Collectors.groupingBy(gd -> gd.getOffering().getId()));

        List<CourseResponse> result = new ArrayList<>();

        for (Course course : courses) {
            List<Offering> courseOfferings =
                    offeringsByCourseId.getOrDefault(course.getId(), Collections.emptyList());

            if (courseOfferings.isEmpty()) {
                continue;
            }

            int totalStudents = 0;
            int totalLetterGrades = 0;
            Map<String, Integer> pooledGrades = new HashMap<>();

            for (Offering offering : courseOfferings) {
                totalLetterGrades += offering.getLetterGradeCount() == null ? 0 : offering.getLetterGradeCount();
                totalStudents += offering.getStudentsCount() == null ? 0 : offering.getStudentsCount();

                List<GradeDistribution> offeringGrades =
                        gradesByOfferingId.getOrDefault(offering.getId(), Collections.emptyList());

                for (GradeDistribution gd : offeringGrades) {
                    String label = gd.getGradeLabel().toUpperCase();
                    pooledGrades.merge(label, gd.getCount(), Integer::sum);
                }
            }
            Double pooledMedian = calculateMedian12Point(pooledGrades);
            Double pooledMode = calculateMode12Point(pooledGrades);

            CourseResponse response = new CourseResponse(
                    course.getSubject().getCode(),
                    course.getCourseNumber(),
                    course.getTitle(),
                    totalStudents,
                    new CourseResponse.OverallStats(
                            pooledMedian == null ? null : BigDecimal.valueOf(pooledMedian),
                            pooledMode == null ? null : BigDecimal.valueOf(pooledMode)
                    ),
                    List.of(
                            new CourseResponse.GradeDistributionItem("A+", pooledGrades.getOrDefault("A+", 0)),
                            new CourseResponse.GradeDistributionItem("A",  pooledGrades.getOrDefault("A", 0)),
                            new CourseResponse.GradeDistributionItem("A-", pooledGrades.getOrDefault("A-", 0)),
                            new CourseResponse.GradeDistributionItem("B+", pooledGrades.getOrDefault("B+", 0)),
                            new CourseResponse.GradeDistributionItem("B",  pooledGrades.getOrDefault("B", 0)),
                            new CourseResponse.GradeDistributionItem("B-", pooledGrades.getOrDefault("B-", 0)),
                            new CourseResponse.GradeDistributionItem("C+", pooledGrades.getOrDefault("C+", 0)),
                            new CourseResponse.GradeDistributionItem("C",  pooledGrades.getOrDefault("C", 0)),
                            new CourseResponse.GradeDistributionItem("C-", pooledGrades.getOrDefault("C-", 0)),
                            new CourseResponse.GradeDistributionItem("D+", pooledGrades.getOrDefault("D+", 0)),
                            new CourseResponse.GradeDistributionItem("D",  pooledGrades.getOrDefault("D", 0)),
                            new CourseResponse.GradeDistributionItem("D-", pooledGrades.getOrDefault("D-", 0)),
                            new CourseResponse.GradeDistributionItem("F",  pooledGrades.getOrDefault("F", 0)),
                            new CourseResponse.GradeDistributionItem("SAT", pooledGrades.getOrDefault("SAT", 0)),
                            new CourseResponse.GradeDistributionItem("UNS", pooledGrades.getOrDefault("UNS", 0)),
                            new CourseResponse.GradeDistributionItem("WDN", pooledGrades.getOrDefault("WDN", 0))
                    )
            );

            result.add(response);
        }

        return result;
    }




    private static final List<String> GRADE_ORDER = List.of(
            "A+", "A", "A-",
            "B+", "B", "B-",
            "C+", "C", "C-",
            "D+", "D", "D-",
            "F"
    );

    private static final Map<String, Double> GRADE_POINTS = Map.ofEntries(
            Map.entry("A+", 12.0),
            Map.entry("A", 11.0),
            Map.entry("A-", 10.0),
            Map.entry("B+", 9.0),
            Map.entry("B", 8.0),
            Map.entry("B-", 7.0),
            Map.entry("C+", 6.0),
            Map.entry("C", 5.0),
            Map.entry("C-", 4.0),
            Map.entry("D+", 3.0),
            Map.entry("D", 2.0),
            Map.entry("D-", 1.0),
            Map.entry("F", 0.0)
    );
    private Double calculateMode12Point(Map<String, Integer> pooledGrades) {
        String modeGrade = null;
        int maxCount = 0;

        for (String grade : GRADE_ORDER) {
            int count = pooledGrades.getOrDefault(grade, 0);
            if (count > maxCount) {
                maxCount = count;
                modeGrade = grade;
            }
        }

        return modeGrade == null ? null : GRADE_POINTS.get(modeGrade);
    }

    private Double calculateMedian12Point(Map<String, Integer> pooledGrades) {
        int total = 0;
        for (String grade : GRADE_ORDER) {
            total += pooledGrades.getOrDefault(grade, 0);
        }

        if (total == 0) {
            return null;
        }

        if (total % 2 == 1) {
            int target = (total + 1) / 2;
            int running = 0;

            for (String grade : GRADE_ORDER) {
                running += pooledGrades.getOrDefault(grade, 0);
                if (running >= target) {
                    return GRADE_POINTS.get(grade);
                }
            }
        } else {
            int leftTarget = total / 2;
            int rightTarget = leftTarget + 1;

            String leftGrade = null;
            String rightGrade = null;
            int running = 0;

            for (String grade : GRADE_ORDER) {
                running += pooledGrades.getOrDefault(grade, 0);

                if (leftGrade == null && running >= leftTarget) {
                    leftGrade = grade;
                }
                if (rightGrade == null && running >= rightTarget) {
                    rightGrade = grade;
                    break;
                }
            }

            if (leftGrade != null && rightGrade != null) {
                return (GRADE_POINTS.get(leftGrade) + GRADE_POINTS.get(rightGrade)) / 2.0;
            }
        }

        return null;
    }

    public DetailedCourseResponse getDetailedCourseBySubject(String subjectCode, String courseCode) {
        Optional<Course> courseOpt = courseRepository
                .findBySubjectCodeIgnoreCaseAndCourseNumberIgnoreCase(subjectCode, courseCode);

        if (courseOpt.isEmpty()) {
            return null;
        }

        Course course = courseOpt.get();

        List<Offering> offerings = offeringRepository.findAllByCourseId(course.getId());
        if (offerings.isEmpty()) {
            return new DetailedCourseResponse(
                    course.getSubject().getCode(),
                    course.getCourseNumber(),
                    course.getTitle(),
                    new DetailedCourseResponse.OverallStats(null, null),
                    Collections.emptyList()
            );
        }

        List<Integer> offeringIds = offerings.stream()
                .map(Offering::getId)
                .toList();

        List<GradeDistribution> gradeDistributions =
                gradeDistributionRepository.findAllByOfferingIdIn(offeringIds);

        Map<Integer, List<GradeDistribution>> gradesByOfferingId = gradeDistributions.stream()
                .collect(Collectors.groupingBy(gd -> gd.getOffering().getId()));


        Map<String, Integer> pooledCourseGrades = new HashMap<>();
        for (GradeDistribution gd : gradeDistributions) {
            pooledCourseGrades.merge(
                    gd.getGradeLabel().toUpperCase(),
                    gd.getCount(),
                    Integer::sum
            );
        }

        Double overallMedian = calculateMedian12Point(pooledCourseGrades);
        Double overallMode = calculateMode12Point(pooledCourseGrades);

        Map<Integer, List<Offering>> offeringsByProfessorId = offerings.stream()
                .filter(o -> o.getProfessor() != null)
                .collect(Collectors.groupingBy(o -> o.getProfessor().getId()));

        List<DetailedCourseResponse.ProfessorStats> professorStats = new ArrayList<>();

        for (Map.Entry<Integer, List<Offering>> entry : offeringsByProfessorId.entrySet()) {
            List<Offering> professorOfferings = entry.getValue();
            Professor professor = professorOfferings.getFirst().getProfessor();

            int timesTaught = professorOfferings.size();
            Map<String, Integer> professorGradeMap = new HashMap<>();
            for (Offering offering : professorOfferings) {
                List<GradeDistribution> offeringGrades =
                        gradesByOfferingId.getOrDefault(offering.getId(), Collections.emptyList());

                for (GradeDistribution gd : offeringGrades) {
                    professorGradeMap.merge(
                            gd.getGradeLabel().toUpperCase(),
                            gd.getCount(),
                            Integer::sum
                    );
                }
            }

            List<DetailedCourseResponse.GradeDistributionItem> distribution = buildGradeDistribution(professorGradeMap);

            List<DetailedCourseResponse.OfferingItem> offeringItems = professorOfferings.stream()
                    .sorted(Comparator.comparing(
                            (Offering o) -> o.getTerm().getCode(),
                            Comparator.nullsLast(Comparator.reverseOrder())
                    ))
                    .map(o -> new DetailedCourseResponse.OfferingItem(
                            o.getTerm() != null ? o.getTerm().getCode() : null,
                            o.getSection(),
                            o.getMedian(),
                            o.getMode()
                    ))
                    .toList();

            professorStats.add(new DetailedCourseResponse.ProfessorStats(
                    professor.getName(),
                    timesTaught,
                    distribution,
                    offeringItems
            ));
        }

        professorStats.sort(Comparator.comparing(DetailedCourseResponse.ProfessorStats::getName));

        return new DetailedCourseResponse(
                course.getSubject().getCode(),
                course.getCourseNumber(),
                course.getTitle(),
                new DetailedCourseResponse.OverallStats(overallMedian, overallMode),
                professorStats
        );
    }

    private static final List<String> ALL_GRADES = List.of(
            "A+", "A", "A-",
            "B+", "B", "B-",
            "C+", "C", "C-",
            "D+", "D", "D-",
            "F", "SAT", "UNS", "WDN"
    );

    private static final List<String> GPA_GRADES = List.of(
            "A+", "A", "A-",
            "B+", "B", "B-",
            "C+", "C", "C-",
            "D+", "D", "D-",
            "F"
    );

    private List<DetailedCourseResponse.GradeDistributionItem> buildGradeDistribution(Map<String, Integer> gradeMap) {
        return ALL_GRADES.stream()
                .map(grade -> new DetailedCourseResponse.GradeDistributionItem(
                        grade,
                        gradeMap.getOrDefault(grade, 0)
                ))
                .toList();
    }

    public List<BasicCourseReponse> getCoursesByQuery(String query) {
        return courseRepository.findAllByCourseNumberContainingIgnoreCaseOrTitleContainingIgnoreCase(query.trim(), query.trim()).stream()
                .map(course -> new BasicCourseReponse(
                        course.getId(),
                        course.getCourseNumber(),
                        course.getTitle(),
                        course.getSubject().getCode()
                ))
                .toList();
    }



}
