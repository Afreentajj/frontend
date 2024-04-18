package com.thbs.lms.controller;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
@CrossOrigin("172.18.4.186:5173, 172.18.4.113:5173, 172.18.4.195:5173")
public class TopicController {

    private final TopicService topicService;
    private final CourseService courseService;

    @Autowired
    public TopicController(TopicService topicService, CourseService courseService) {
        this.topicService = topicService;
        this.courseService = courseService;
    }

    // Add a single topic
    @PostMapping
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic) {
        Topic addedTopic = topicService.addTopicWithValidation(topic.getTopicName(), topic.getDescription(),
                topic.getCourse());
        return ResponseEntity.ok().body(addedTopic);
    }

    // Add a multiple topics
    @PostMapping("/multiple")
    public ResponseEntity<List<Topic>> addTopics(@RequestBody List<Topic> topics) {
        List<Topic> addedTopics = topicService.addTopicsWithValidation(topics);
        return ResponseEntity.ok().body(addedTopics);
    }

    // Get all topics
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/id/{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        return ResponseEntity.ok().body(topic);
    }

    // Get all topics related to a course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Topic>> getTopicsByCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);

        List<Topic> topics = topicService.getTopicsByCourse(course);
        return ResponseEntity.ok().body(topics);
    }

    // Update topicName given topicId
    @PutMapping("/name/{topicId}")
    public ResponseEntity<String> updateName(@PathVariable Long topicId, @RequestBody String newName) {
        String result = topicService.updateTopicNameWithValidation(topicId, newName);
        return ResponseEntity.ok().body(result);
    }

    // Update description for a given topicID
    @PutMapping("/description/{topicId}")
    public ResponseEntity<String> updateDescription(@PathVariable Long topicId, @RequestBody String newDescription) {
        String result = topicService.updateTopicDescriptionWithValidation(topicId, newDescription);
        return ResponseEntity.ok().body(result);
    }

    // Delete a particular topic
    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopicById(topicId);
        return ResponseEntity.ok().body("Topic deleted successfully.");
    }

    // Delete multiple topics
    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteTopics(@RequestBody List<Topic> topics) {
        topicService.deleteTopics(topics);
        return ResponseEntity.ok().body("Topics deleted successfully.");
    }
}
