package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private static final String NOT_FOUND_MSG = "Topic not found for ID: ";
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    // Adds a topic with validation
    public Topic addTopicWithValidation(String topicName, String description,
            Course course) {

        // Checks for duplicate topic and validates data
        if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
            // Throws exceptions if topic already exists or data is invalid
            throw new DuplicateTopicException("Topic '" + topicName + "' already exists for this course.");
        }

        if (topicName == null || topicName.isEmpty() || description == null ||
                description.isEmpty()) {
            throw new InvalidTopicDataException("Topic name and description cannot be null or empty.");
        }

        Topic newTopic = new Topic();
        newTopic.setTopicName(topicName);
        newTopic.setDescription(description);
        newTopic.setCourse(course);
        return topicRepository.save(newTopic);
    }

    // Adds multiple topics with validation
    public List<Topic> addTopicsWithValidation(List<Topic> topics) {
        List<Topic> addedTopics = new ArrayList<>();
        for (Topic topic : topics) {
            String topicName = topic.getTopicName();
            String description = topic.getDescription();
            Course course = topic.getCourse();

            // Checks for duplicate topic and validates data
            if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
                // Throws exceptions if topic already exists or data is invalid
                throw new DuplicateTopicException("Topic '" + topicName + "' already exists for this course.");
            }

            if (topicName == null || topicName.isEmpty() || description == null ||
                    description.isEmpty()) {
                throw new InvalidTopicDataException("Topic name and description cannot be null or empty.");
            }

            Topic newTopic = new Topic();
            newTopic.setTopicName(topicName);
            newTopic.setDescription(description);
            newTopic.setCourse(course);

            addedTopics.add(topicRepository.save(newTopic));
        }
        return addedTopics;
    }

    // Retrieves all topics
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long topicId) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            return optionalTopic.get();
        } else {
            throw new TopicNotFoundException(NOT_FOUND_MSG + topicId);
        }
    }

    // Retrieves topics by course
    public List<Topic> getTopicsByCourse(Course course) {
        return topicRepository.findByCourse(course);
    }

    // Update topic description with validation
    public String updateTopicDescriptionWithValidation(Long topicId, String newDescription) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            // Updates topic description if exists and validates the new description
            if (newDescription == null || newDescription.isEmpty()) {
                // Throws exceptions if topic not found or description is invalid
                throw new InvalidDescriptionException("Description cannot be null or empty.");
            }

            topic.setDescription(newDescription);
            topicRepository.save(topic);
            return "Description updated successfully";
        } else {
            throw new TopicNotFoundException(NOT_FOUND_MSG + topicId);
        }
    }

    // Delete a topic by its ID
    public void deleteTopicById(Long topicId) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            topicRepository.delete(optionalTopic.get());
        } else {
            // Throws exception if topic not found
            throw new TopicNotFoundException(NOT_FOUND_MSG + topicId);
        }
    }

    // Delete multiple topics by ID's
    public void deleteTopics(List<Topic> topics) {
        for (Topic topic : topics) {
            Long topicId = topic.getTopicID();
            Optional<Topic> optionalTopic = topicRepository.findById(topicId);
            if (optionalTopic.isPresent()) {
                topicRepository.delete(topic);
            } else {
                // Throws exception if topic not found
                throw new TopicNotFoundException(NOT_FOUND_MSG + topicId);
            }
        }
    }

    // Delete all topics associated with a course
    public void deleteTopicsByCourse(Course course) {
        List<Topic> topics = topicRepository.findByCourse(course);
        for (Topic topic : topics) {
            topicRepository.delete(topic);
        }
    }

    // Update topic name with validation
    public String updateTopicNameWithValidation(Long topicId, String newName) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        // Updates topic name if exists and validates the new name
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();

            if (newName == null || newName.isEmpty()) {
                // Throws exceptions if topic not found or name is invalid
                throw new InvalidTopicDataException("Topic Name cannot be null or empty.");
            }

            topic.setTopicName(newName);
            topicRepository.save(topic);
            return "Topic name updated successfully";
        } else {
            throw new TopicNotFoundException(NOT_FOUND_MSG + topicId);
        }
    }

}
