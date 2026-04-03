package com.connor.cu_grades.repository;


import com.connor.cu_grades.model.Course;
import com.connor.cu_grades.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllBySubjectId(int id);
    List<Course> findAllBySubjectIdOrderByCourseNumberAsc(int id);
//    @Query(value = """
//    SELECT * FROM courses
//    WHERE subject_id = :id
//    ORDER BY course_number ASC
//    LIMIT :limit OFFSET :offset
//""", nativeQuery = true)
//    List<Course> findCoursesWithPagination(
//            @Param("id") int id,
//            @Param("limit") int limit,
//            @Param("offset") int offset
//    );

    @Query(value = """
    SELECT * FROM courses 
    WHERE subject_id = :id 
    AND (:prefix IS NULL OR course_number LIKE CONCAT(:prefix, '%'))
    ORDER BY course_number ASC 
    LIMIT :limit OFFSET :offset
""", nativeQuery = true)
    List<Course> findCoursesWithPagination(
            @Param("id") int id,
            @Param("prefix") String prefix,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
    Optional<Course> findBySubjectCodeIgnoreCaseAndCourseNumberIgnoreCase(String subjectCode, String courseNumber);
    List<Course> findAllBySubjectIdAndCourseNumberStartingWithOrderByCourseNumberAsc(int id, String prefix);

    List<Course> findAllByCourseNumberContainingIgnoreCaseOrTitleContainingIgnoreCase(String number, String title);



    List<Course> id(Integer id);
}
