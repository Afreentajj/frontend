package com.thbs.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlanPath;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearningPlanPathRepository extends JpaRepository<LearningPlanPath, Long> {

    List<LearningPlanPath> findByLearningPlanLearningPlanID(Long learningPlanID);

    List<LearningPlanPath> findByType(String type);

    List<LearningPlanPath> findByTrainer(String trainer);

    Optional<LearningPlanPath> findByLearningPlanLearningPlanIDAndCourseAndType(Long learningPlanId, Course course,
            String type);
}
