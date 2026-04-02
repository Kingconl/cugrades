package com.connor.cu_grades.repository;


import com.connor.cu_grades.model.Course;
import com.connor.cu_grades.model.GradeDistribution;
import com.connor.cu_grades.model.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeDistributionRepository extends JpaRepository<GradeDistribution, Integer> {
    Optional<GradeDistribution> findByOffering(Offering offering);

    @Query("""
    SELECT g.count
    FROM GradeDistribution g
    WHERE LOWER(g.gradeLabel) = LOWER(:label)
      AND g.offering.id = :id
""")
    Optional<Integer> findCountByOfferingIdAndGradeLabelIgnoreCase(
            @Param("id") int id,
            @Param("label") String label
    );

    List<GradeDistribution> findAllByOfferingIdIn(List<Integer> offeringIds);

}
