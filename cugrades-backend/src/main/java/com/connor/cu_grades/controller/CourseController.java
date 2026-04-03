package com.connor.cu_grades.controller;


import com.connor.cu_grades.dto.BasicCourseReponse;
import com.connor.cu_grades.dto.CourseResponse;
import com.connor.cu_grades.dto.DetailedCourseResponse;
import com.connor.cu_grades.service.CourseService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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


    @Cacheable(value = "coursesBySubject", key = "#code + '-' + #level + '-' + #limit + '-' + #offset")
    @GetMapping("{code}/courses")
    public List<CourseResponse> getCoursesBySubject(@PathVariable String code, @RequestParam(required = false) String level,
    @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset
    ) {
        return courseService.getCourseListPooled(code, level, limit, offset);
    }

    @GetMapping
    public List<BasicCourseReponse> getAllSubjects(@RequestParam(required = false) String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }

        return courseService.getCoursesByQuery(query);
    }

    @Cacheable(value = "courseDetail", key = "#subject + '-' + #courseNumber")
    @GetMapping("{code}/{course}/details")
    public DetailedCourseResponse getDetailedCoursesBySubject(@PathVariable String code, @PathVariable String course) {
        return courseService.getDetailedCourseBySubject(code, course);
    }




}
