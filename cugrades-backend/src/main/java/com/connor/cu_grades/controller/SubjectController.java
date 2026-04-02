package com.connor.cu_grades.controller;



import com.connor.cu_grades.dto.CourseResponse;
import com.connor.cu_grades.dto.SubjectResponse;
import com.connor.cu_grades.model.Course;
import com.connor.cu_grades.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<SubjectResponse> getAllSubjects(@RequestParam(required = false) String query) {
        if (query == null || query.isBlank()) {
            return subjectService.getAllSubjects();
        }

        return subjectService.getSubjectByCode(query);
    }

    @GetMapping("{code}/courses")
    public List<Course> getCoursesBySubject(@PathVariable String code) {
        return subjectService.getCoursesBySubject(code);
    }

//    @GetMapping("{code}/courses")
//    public List<CourseResponse> getCoursesBySubject(@PathVariable String code) {
//        System.out.println("getCoursesBySubject");
//        return cou.getDetailedCourseList(code);
//    }


}
