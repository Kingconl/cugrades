package com.connor.cu_grades.repository;


import com.connor.cu_grades.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String code, String name);

    @Query("SELECT s.id FROM Subject s WHERE LOWER(s.code) = LOWER(:code)")
    Optional<Integer> findIdByCodeIgnoreCase(@Param("code") String code);

}
