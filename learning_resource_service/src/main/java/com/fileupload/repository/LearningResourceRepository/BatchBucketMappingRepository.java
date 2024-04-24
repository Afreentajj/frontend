package com.fileupload.repository.LearningResourceRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fileupload.model.LearningResourceModel.BatchBucketMapping;

public interface BatchBucketMappingRepository extends JpaRepository<BatchBucketMapping, Long> {
	BatchBucketMapping findByBatchId(Long batchId);

	void deleteByBatchId(Long batchId);
}
