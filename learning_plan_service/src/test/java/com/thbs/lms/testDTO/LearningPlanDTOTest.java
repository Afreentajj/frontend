package com.thbs.lms.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.LearningPlanDTO;
import com.thbs.lms.dto.PathDTO;

@SpringBootTest
class LearningPlanDTOTest {

    @Test
    void testGettersAndSetters() {
        // Sample data
        Long batchId = 123L;
        Long learningPlanId = 456L;
        List<PathDTO> path = Arrays.asList(
                new PathDTO(1L, "type1", "trainer1", new Date(), new Date(), new CourseDTO()),
                new PathDTO(2L, "type2", "trainer2", new Date(), new Date(), new CourseDTO()));

        // Create a LearningPlanDTO object
        LearningPlanDTO learningPlanDTO = new LearningPlanDTO();

        // Test setters
        learningPlanDTO.setBatchId(batchId);
        learningPlanDTO.setLearningPlanId(learningPlanId);
        learningPlanDTO.setPath(path);

        // Test getters
        assertEquals(batchId, learningPlanDTO.getBatchId());
        assertEquals(learningPlanId, learningPlanDTO.getLearningPlanId());
        assertEquals(path, learningPlanDTO.getPath());
    }

    @Test
    void testAllArgsConstructor() {
        // Sample data for constructor
        Long batchId = 123L;
        Long learningPlanId = 456L;
        List<PathDTO> path = Arrays.asList(new PathDTO(), new PathDTO());

        // Create a LearningPlanDTO object using AllArgsConstructor
        LearningPlanDTO learningPlanDTO = new LearningPlanDTO(batchId, learningPlanId, path);

        // Test getters to verify the data set by AllArgsConstructor
        assertEquals(batchId, learningPlanDTO.getBatchId());
        assertEquals(learningPlanId, learningPlanDTO.getLearningPlanId());
        assertEquals(path, learningPlanDTO.getPath());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a LearningPlanDTO object using NoArgsConstructor
        LearningPlanDTO learningPlanDTO = new LearningPlanDTO();

        // Test getters to verify the default values set by NoArgsConstructor
        assertNull(learningPlanDTO.getBatchId());
        assertNull(learningPlanDTO.getLearningPlanId());
        assertNull(learningPlanDTO.getPath());
    }
}
