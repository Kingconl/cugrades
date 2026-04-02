package com.connor.cu_grades.service;

import com.connor.cu_grades.dto.CourseResponse;
import com.connor.cu_grades.dto.courseStatsReponse;
import com.connor.cu_grades.model.Course;
import com.connor.cu_grades.model.GradeDistribution;
import com.connor.cu_grades.model.Offering;
import com.connor.cu_grades.repository.CourseRepository;
import com.connor.cu_grades.repository.GradeDistributionRepository;
import com.connor.cu_grades.repository.OfferingRepository;
import com.connor.cu_grades.repository.SubjectRepository;
import com.connor.cu_grades.model.Subject;
import com.connor.cu_grades.dto.SubjectResponse;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final OfferingRepository offeringRepository;
    private final GradeDistributionRepository gradeDistributionRepository;

    public SubjectService(SubjectRepository subjectRepository, CourseRepository courseRepository, OfferingRepository offeringRepository, GradeDistributionRepository gradeDistributionRepository) {
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.offeringRepository = offeringRepository;
        this.gradeDistributionRepository = gradeDistributionRepository;
    }

    public List<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(subject -> new SubjectResponse(subject.getId(), subject.getCode(), subject.getName()))
                .toList();
    }

    public List<SubjectResponse> getSubjectByCode(String code) {
        return subjectRepository.findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(code.trim().toUpperCase(), code.trim().toUpperCase()).stream()
                .map(subject -> new SubjectResponse(
                        subject.getId(),
                        subject.getCode(),
                        subject.getName()
                ))
                .toList();
    }

    public Optional<Integer> getIdBycode(String code) {
        return subjectRepository.findIdByCodeIgnoreCase(code.trim());
    }

    public List<Course> getCoursesBySubject(String code) {
        Optional<Integer> id = subjectRepository.findIdByCodeIgnoreCase(code);
        if(id.isEmpty()) {
            return Collections.emptyList();
        }
        return courseRepository.findAllBySubjectId(id.get());
    }

//    public List<CourseResponse> getDetailedCourseList(String code) {
//        Optional<Integer> id = subjectRepository.findIdByCodeIgnoreCase(code);
//
//        if (id.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<CourseResponse> list = new ArrayList<>();
//        List<Course> courses = courseRepository.findAllBySubjectId(id.get());
//
//        for (Course course : courses) {
//            List<Offering> offerings = offeringRepository.findAllByCourse(course);
//
//            for (Offering offering : offerings) {
//                int offeringId = offering.getId();
//                Integer studentCount = offering.getStudentsCount();
//                BigDecimal median = offering.getMedian();
//                BigDecimal mode = offering.getMode();
//
//                CourseResponse response = new CourseResponse(
//                        course.getSubject().getCode(),
//                        course.getCourseNumber(),
//                        course.getTitle(),
//                        studentCount,
//                        new CourseResponse.OverallStats(median, mode),
//                        List.of(
//                                new CourseResponse.GradeDistributionItem("A+",
//                                        gradeDistributionRepository.findCountByOfferingIdAndGradeLabelIgnoreCase(offeringId, "A+").orElse(0)),
//                                new CourseResponse.GradeDistributionItem("A",
//                                        gradeDistributionRepository.findCountByOfferingIdAndGradeLabelIgnoreCase(offeringId, "A").orElse(0)),
//                                new CourseResponse.GradeDistributionItem("A-",
//                                        gradeDistributionRepository.findCountByOfferingIdAndGradeLabelIgnoreCase(offeringId, "A-").orElse(0)),
//                                new CourseResponse.GradeDistributionItem("B+",
//                                        gradeDistributionRepository.findCountByOfferingIdAndGradeLabelIgnoreCase(offeringId, "B+").orElse(0))
//                        )
//                );
//
//                list.add(response);
//            }
//        }
//
//        return list;
//    }

//    public Optional<courseStatsReponse> test(String code) {
//        Optional<Integer> id = subjectRepository.findIdByCodeIgnoreCase(code);
//        if(id.isEmpty()) {
//            return Optional.empty();
//        }
//        List<Course> courses = courseRepository.findAllBySubjectId(id.get());
//        for(Course course : courses) {
//            List<Offering> offerings = offeringRepository.findAllByCourse(course);
//
//            for(Offering offering : offerings) {
//
//            }
//        }
//    }



}


