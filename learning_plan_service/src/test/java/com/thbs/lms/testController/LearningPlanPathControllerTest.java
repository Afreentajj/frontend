package com.thbs.lms.testController;

import com.thbs.lms.controller.LearningPlanPathController;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.service.LearningPlanPathService;
import com.thbs.lms.utility.DateRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningPlanPathControllerTest {

    @Mock
    private LearningPlanPathService learningPlanPathService;

    @InjectMocks
    private LearningPlanPathController learningPlanPathController;

    @Test
    void testCreateLearningPlanPath() {
        LearningPlanPath learningPlanPath = new LearningPlanPath();

        when(learningPlanPathService.saveLearningPlanPath(any())).thenReturn(learningPlanPath);

        ResponseEntity<?> responseEntity = learningPlanPathController.createLearningPlanPath(learningPlanPath);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPath, responseEntity.getBody());

        verify(learningPlanPathService, times(1)).saveLearningPlanPath(any());
    }

    @Test
    void testCreateLearningPlanPaths() {
        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        LearningPlanPath learningPlanPath1 = new LearningPlanPath();
        learningPlanPath1.setPathID(1L); // Set an ID for testing

        LearningPlanPath learningPlanPath2 = new LearningPlanPath();
        learningPlanPath2.setPathID(2L); // Set an ID for testing

        learningPlanPaths.add(learningPlanPath1);
        learningPlanPaths.add(learningPlanPath2);

        when(learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths)).thenReturn(learningPlanPaths);

        ResponseEntity<?> responseEntity = learningPlanPathController.createLearningPlanPaths(learningPlanPaths);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPaths, responseEntity.getBody());
    }

    @Test
    void testGetAllLearningPlanPaths() {
        List<LearningPlanPath> expectedPaths = new ArrayList<>();

        expectedPaths.add(new LearningPlanPath());

        when(learningPlanPathService.getAllLearningPlanPaths()).thenReturn(expectedPaths);

        ResponseEntity<?> responseEntity = learningPlanPathController.getAllLearningPlanPaths();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPaths, responseEntity.getBody());
    }

    @Test
    void testGetAllLearningPlanPathsByLearningPlanId() {
        Long learningPlanId = 1L;

        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        when(learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(learningPlanId))
                .thenReturn(learningPlanPaths);

        ResponseEntity<?> responseEntity = learningPlanPathController
                .getAllLearningPlanPathsByLearningPlanId(learningPlanId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPaths, responseEntity.getBody());

        verify(learningPlanPathService, times(1)).getAllLearningPlanPathsByLearningPlanId(learningPlanId);
    }

    @Test
    void testGetAllLearningPlansByType() {
        String type = "Test Type";

        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        when(learningPlanPathService.getAllLearningPlanPathsByType(type)).thenReturn(learningPlanPaths);

        ResponseEntity<?> responseEntity = learningPlanPathController.getAllLearningPlanPathsByType(type);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPaths, responseEntity.getBody());

        verify(learningPlanPathService, times(1)).getAllLearningPlanPathsByType(type);
    }

    @Test
    void testGetAllLearningPlansByTrainer() {
        String trainer = "Test Trainer";

        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        when(learningPlanPathService.getAllLearningPlanPathsByTrainer(trainer)).thenReturn(learningPlanPaths);

        ResponseEntity<?> responseEntity = learningPlanPathController.getAllLearningPlanPathsByTrainer(trainer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlanPaths, responseEntity.getBody());

        verify(learningPlanPathService, times(1)).getAllLearningPlanPathsByTrainer(trainer);
    }

    @Test
    void testUpdateTrainer() {
        Long pathId = 1L;
        String newTrainer = "New Trainer";

        ResponseEntity<?> responseEntity = learningPlanPathController.updateTrainer(pathId, newTrainer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Trainer updated successfully", responseEntity.getBody());

        verify(learningPlanPathService, times(1)).updateLearningPlanPathTrainer(pathId, newTrainer);
    }

    @Test
    void testUpdateLearningPlanDates() {
        Long learningPlanPathID = 1L;

        DateRange dateRange = new DateRange();
        dateRange.setStartDate(new Date());
        dateRange.setEndDate(new Date());

        when(learningPlanPathService.updateLearningPlanPathDates(learningPlanPathID, dateRange.getStartDate(),
                dateRange.getEndDate()))
                .thenReturn(Optional.of(new LearningPlanPath()));

        ResponseEntity<?> responseEntity = learningPlanPathController.updateDates(learningPlanPathID,
                dateRange);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Date Range updated successfully.", responseEntity.getBody());
        verify(learningPlanPathService, times(1)).updateLearningPlanPathDates(learningPlanPathID,
                dateRange.getStartDate(),
                dateRange.getEndDate());
    }

    @Test
    void testDeleteLearningPlanPath() {
        doNothing().when(learningPlanPathService).deleteLearningPlanPath(1L);

        ResponseEntity<?> responseEntity = learningPlanPathController.deleteLearningPlanPath(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Learning plan path deleted successfully", responseEntity.getBody());
    }
}
