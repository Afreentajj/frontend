package com.thbs.lms.repository;

import com.thbs.lms.model.LearningPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningPlanRepository extends JpaRepository<LearningPlan, Long> {

    List<LearningPlan> findByType(String type);

    List<LearningPlan> findByBatchID(Long batchID);
}
