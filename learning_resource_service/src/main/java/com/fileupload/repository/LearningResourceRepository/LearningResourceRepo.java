package com.fileupload.repository.LearningResourceRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fileupload.model.LearningResourceModel.LearningResource;

public interface LearningResourceRepo extends JpaRepository<LearningResource, Long> {

	LearningResource findByBatchIdAndCourseIdAndTopicId(Long batchId, Long courseId, Long topicId);

	boolean existsByBatchIdAndCourseIdAndTopicId(Long batchId, Long courseId, Long topicId);

}
