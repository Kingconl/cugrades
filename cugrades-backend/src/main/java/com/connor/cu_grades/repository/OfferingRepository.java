package com.connor.cu_grades.repository;


import com.connor.cu_grades.model.Course;
import com.connor.cu_grades.model.Offering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferingRepository extends JpaRepository<Offering, Integer> {
    List<Offering> findAllByCourse(Course course);
    List<Offering> findAllByCourseIdIn(List<Integer> courseIds);
    List<Offering> findAllByCourseId(Integer courseId);
}
