package com.thbs.lms.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.model.LearningPlanPath;

@SpringBootTest
class LearningPlanPathTest {
    @Test
    void testGettersAndSetters() {
        // Initialize a LearningPlanPath object
        LearningPlanPath learningPlanPath = new LearningPlanPath();

        // Set values using setters
        Long pathID = 1L;
        LearningPlan learningPlan = new LearningPlan();
        Course course = new Course();
        String type = "Test Type";
        String trainer = "Test Trainer";
        Date startDate = new Date();
        Date endDate = new Date();

        learningPlanPath.setPathID(pathID);
        learningPlanPath.setLearningPlan(learningPlan);
        learningPlanPath.setCourse(course);
        learningPlanPath.setType(type);
        learningPlanPath.setTrainer(trainer);
        learningPlanPath.setStartDate(startDate);
        learningPlanPath.setEndDate(endDate);

        // Test getters
        assertEquals(pathID, learningPlanPath.getPathID());
        assertEquals(learningPlan, learningPlanPath.getLearningPlan());
        assertEquals(course, learningPlanPath.getCourse());
        assertEquals(type, learningPlanPath.getType());
        assertEquals(trainer, learningPlanPath.getTrainer());
        assertEquals(startDate, learningPlanPath.getStartDate());
        assertEquals(endDate, learningPlanPath.getEndDate());
    }

    @Test
    void testNoArgsConstructor() {
        LearningPlanPath learningPlanPath = new LearningPlanPath();
        assertNotNull(learningPlanPath);
        assertNull(learningPlanPath.getPathID());
        assertNull(learningPlanPath.getLearningPlan());
        assertNull(learningPlanPath.getCourse());
        assertNull(learningPlanPath.getType());
        assertNull(learningPlanPath.getTrainer());
        assertNull(learningPlanPath.getStartDate());
        assertNull(learningPlanPath.getEndDate());
    }

    @Test
    void testAllArgsConstructor() {
        Long pathID = 1L;
        LearningPlan learningPlan = new LearningPlan();
        Course course = new Course();
        String type = "Test Type";
        String trainer = "Test Trainer";
        Date startDate = new Date();
        Date endDate = new Date();

        LearningPlanPath learningPlanPath = new LearningPlanPath(pathID, learningPlan, course, type, trainer, startDate,
                endDate);
        assertNotNull(learningPlanPath);
        assertEquals(pathID, learningPlanPath.getPathID());
        assertEquals(learningPlan, learningPlanPath.getLearningPlan());
        assertEquals(course, learningPlanPath.getCourse());
        assertEquals(type, learningPlanPath.getType());
        assertEquals(trainer, learningPlanPath.getTrainer());
        assertEquals(startDate, learningPlanPath.getStartDate());
        assertEquals(endDate, learningPlanPath.getEndDate());
    }
}
