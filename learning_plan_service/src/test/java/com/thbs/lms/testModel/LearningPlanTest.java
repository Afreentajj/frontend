package com.thbs.lms.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.model.LearningPlan;

@SpringBootTest
class LearningPlanTest {

    @Test
    void testGettersAndSetters() {
        // Initialize a LearningPlan object
        LearningPlan learningPlan = new LearningPlan();

        // Set values using setters
        Long learningPlanID = 1L;
        String type = "Test Type";
        Long batchID = 123L;

        learningPlan.setLearningPlanID(learningPlanID);
        learningPlan.setType(type);
        learningPlan.setBatchID(batchID);

        // Test getters
        assertEquals(learningPlanID, learningPlan.getLearningPlanID());
        assertEquals(type, learningPlan.getType());
        assertEquals(batchID, learningPlan.getBatchID());
    }

    @Test
    void testNoArgsConstructor() {
        LearningPlan learningPlan = new LearningPlan();
        assertNotNull(learningPlan);
        assertNull(learningPlan.getLearningPlanID());
        assertNull(learningPlan.getType());
        assertNull(learningPlan.getBatchID());
    }

    @Test
    void testAllArgsConstructor() {
        Long learningPlanID = 1L;
        String type = "Test Type";
        Long batchID = 123L;

        LearningPlan learningPlan = new LearningPlan(learningPlanID, type, batchID);
        assertNotNull(learningPlan);
        assertEquals(learningPlanID, learningPlan.getLearningPlanID());
        assertEquals(type, learningPlan.getType());
        assertEquals(batchID, learningPlan.getBatchID());
    }
}
