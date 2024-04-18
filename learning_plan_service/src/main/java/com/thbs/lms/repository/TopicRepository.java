package com.thbs.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTopicNameAndCourse(String topicName, Course course);

    Object existsByTopicName(String anyString);

    boolean existsByCourseAndTopicNameAndDescription(Course course, String topicName, String description);

    List<Topic> findByCourse(Course course);

}
