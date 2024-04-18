package com.thbs.lms.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;

@SpringBootTest
class TopicTest {
    @Test
    void testGettersAndSetters() {
        // Initialize a Topic object
        Topic topic = new Topic();

        // Set values using setters
        Long topicID = 1L;
        String topicName = "Test Topic";
        String description = "Test Description";
        Course course = new Course();

        topic.setTopicID(topicID);
        topic.setTopicName(topicName);
        topic.setDescription(description);
        topic.setCourse(course);

        // Test getters
        assertEquals(topicID, topic.getTopicID());
        assertEquals(topicName, topic.getTopicName());
        assertEquals(description, topic.getDescription());
        assertEquals(course, topic.getCourse());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialize a Topic object using all-args constructor
        Long topicID = 1L;
        String topicName = "Test Topic";
        String description = "Test Description";
        Course course = new Course();

        Topic topic = new Topic(topicID, topicName, description, course);

        // Test getters
        assertEquals(topicID, topic.getTopicID());
        assertEquals(topicName, topic.getTopicName());
        assertEquals(description, topic.getDescription());
        assertEquals(course, topic.getCourse());
    }

    @Test
    void testNoArgsConstructor() {
        // Initialize a Topic object using no-args constructor
        Topic topic = new Topic();

        // Test getters
        assertNull(topic.getTopicID());
        assertNull(topic.getTopicName());
        assertNull(topic.getDescription());
        assertNull(topic.getCourse());
    }
}
