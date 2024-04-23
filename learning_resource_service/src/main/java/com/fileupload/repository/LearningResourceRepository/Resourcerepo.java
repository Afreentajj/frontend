package com.fileupload.repository.LearningResourceRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fileupload.model.LearningResourceModel.Resource;

public interface Resourcerepo extends JpaRepository<Resource, Long> {

}
