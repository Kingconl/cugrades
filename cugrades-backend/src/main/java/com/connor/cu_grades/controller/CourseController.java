package com.connor.cu_grades.controller;


import com.connor.cu_grades.dto.CourseResponse;
import com.connor.cu_grades.dto.DetailedCourseResponse;
import com.connor.cu_grades.dto.SubjectResponse;
import com.connor.cu_grades.model.Course;
import com.connor.cu_grades.service.CourseService;
import com.connor.cu_grades.service.SubjectService;
import jakarta.validation.constraints.Null;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

//    @GetMapping
//    public List<SubjectResponse> getAllSubjects(@RequestParam(required = false) String query) {
//        if (query == null || query.isBlank()) {
//            return subjectService.getAllSubjects();
//        }
//
//        return subjectService.getSubjectByCode(query);
//    }

//    @GetMapping("{code}/courses")
//    public List<Course> getCoursesBySubject(@PathVariable String code) {
//        System.out.println("getCoursesBySubject");
//        return subjectService.getCoursesBySubject(code);
//    }

    @GetMapping("{code}/courses")
    public List<CourseResponse> getCoursesBySubject(@PathVariable String code, @RequestParam(required = false) String level) {
        return courseService.getCourseListPooled(code, level);
    }

    @GetMapping("{code}/{course}/details")
    public DetailedCourseResponse getDetailedCoursesBySubject(@PathVariable String code, @PathVariable String course) {
        return courseService.getDetailedCourseBySubject(code, course);
    }


}
