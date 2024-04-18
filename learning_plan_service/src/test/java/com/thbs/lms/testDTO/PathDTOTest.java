package com.thbs.lms.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.PathDTO;

@SpringBootTest
class PathDTOTest {

    @Test
    void testGettersAndSetters() {
        // Sample data
        Long learningPlanPathId = 1L;
        String type = "type";
        String trainer = "trainer";
        Date startDate = new Date();
        Date endDate = new Date();
        CourseDTO course = new CourseDTO();

        // Create a PathDTO object
        PathDTO pathDTO = new PathDTO();

        // Test setters
        pathDTO.setLearningPlanPathId(learningPlanPathId);
        pathDTO.setType(type);
        pathDTO.setTrainer(trainer);
        pathDTO.setStartDate(startDate);
        pathDTO.setEndDate(endDate);
        pathDTO.setCourse(course);

        // Test getters
        assertEquals(learningPlanPathId, pathDTO.getLearningPlanPathId());
        assertEquals(type, pathDTO.getType());
        assertEquals(trainer, pathDTO.getTrainer());
        assertEquals(startDate, pathDTO.getStartDate());
        assertEquals(endDate, pathDTO.getEndDate());
        assertEquals(course, pathDTO.getCourse());
    }

    @Test
    void testAllArgsConstructor() {
        // Sample data
        Long learningPlanPathId = 1L;
        String type = "type";
        String trainer = "trainer";
        Date startDate = new Date();
        Date endDate = new Date();
        CourseDTO course = new CourseDTO();

        // Create a PathDTO object using AllArgsConstructor
        PathDTO pathDTO = new PathDTO(learningPlanPathId, type, trainer, startDate, endDate, course);

        // Test getters to verify the data set by AllArgsConstructor
        assertEquals(learningPlanPathId, pathDTO.getLearningPlanPathId());
        assertEquals(type, pathDTO.getType());
        assertEquals(trainer, pathDTO.getTrainer());
        assertEquals(startDate, pathDTO.getStartDate());
        assertEquals(endDate, pathDTO.getEndDate());
        assertEquals(course, pathDTO.getCourse());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a PathDTO object using NoArgsConstructor
        PathDTO pathDTO = new PathDTO();

        // Test getters to verify the default values set by NoArgsConstructor
        assertNull(pathDTO.getLearningPlanPathId());
        assertNull(pathDTO.getType());
        assertNull(pathDTO.getTrainer());
        assertNull(pathDTO.getStartDate());
        assertNull(pathDTO.getEndDate());
        assertNull(pathDTO.getCourse());
    }
}
