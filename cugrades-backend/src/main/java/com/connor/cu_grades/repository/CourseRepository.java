package com.connor.cu_grades.repository;


import com.connor.cu_grades.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllBySubjectId(int id);
    List<Course> findAllBySubjectIdOrderByCourseNumberAsc(int id);
    Optional<Course> findBySubjectCodeIgnoreCaseAndCourseNumberIgnoreCase(String subjectCode, String courseNumber);
    List<Course> findAllBySubjectIdAndCourseNumberStartingWithOrderByCourseNumberAsc(int id, String prefix);


    List<Course> id(Integer id);
}
