package com.thbs.lms.testController;

import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.service.BulkUploadService;
import com.thbs.lms.service.LearningPlanService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.thbs.lms.controller.LearningPlanController;
import com.thbs.lms.dto.LearningPlanDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningPlanControllerTest {

    @Mock
    private LearningPlanService learningPlanService;

    @Mock
    private BulkUploadService bulkUploadService;

    @InjectMocks
    private LearningPlanController learningPlanController;

    private LearningPlan learningPlan;

    @BeforeEach
    void setUp() {
        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanID(1L);
        learningPlan.setType("Test Type");
        learningPlan.setBatchID(1L);
    }

    @Test
    void testSaveLearningPlan() {
        when(learningPlanService.saveLearningPlan(learningPlan)).thenReturn(learningPlan);

        ResponseEntity<?> responseEntity = learningPlanController.saveLearningPlan(learningPlan);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlan, responseEntity.getBody());
    }

    @Test
    void testUploadFile() throws IOException {
        MultipartFile file = null;

        doNothing().when(bulkUploadService).uploadFile(file);

        ResponseEntity<?> responseEntity = learningPlanController.uploadFile(file);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("File uploaded successfully.", responseEntity.getBody());
    }

    @Test
    void testGetAllLearningPlans() {
        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Test Type 2");

        learningPlans.add(learningPlan);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getAllLearningPlans()).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getAllLearningPlans();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    @Test
    void testGetAllLearningPlanPathDTOs() {
        // Sample data
        List<LearningPlanDTO> expectedDTOs = Arrays.asList(
                new LearningPlanDTO(),
                new LearningPlanDTO());

        // Mock service method to return sample data
        when(learningPlanService.getAllLearningPlanPathDTOs()).thenReturn(expectedDTOs);

        // Call the controller method
        ResponseEntity<List<LearningPlanDTO>> response = learningPlanController.getAllLearningPlanPathDTOs();

        // Verify response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTOs, response.getBody());
    }

    @Test
    void testGetAllLearningPlanPathDTOsByBatchId() {
        // Mock data
        Long batchId = 123L;
        List<LearningPlanDTO> expectedDtos = new ArrayList<>();
        expectedDtos.add(new LearningPlanDTO());

        // Mock service method
        when(learningPlanService.getAllLearningPlanPathDTOsByBatchId(batchId)).thenReturn(expectedDtos);

        // Call controller method
        ResponseEntity<List<LearningPlanDTO>> response = learningPlanController
                .getAllLearningPlanPathDTOsByBatchId(batchId);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDtos, response.getBody());
    }

    @Test
    void testFindByType() {
        String type = "Type";

        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan1 = new LearningPlan();
        learningPlan1.setType("Type");

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Type");

        learningPlans.add(learningPlan1);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getLearningPlansByType(type)).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getLearningPlansByType(type);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    @Test
    void testFindByBatchID() {
        Long batchID = 123L;

        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan1 = new LearningPlan();
        learningPlan1.setType("Type 1");

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Type 2");

        learningPlans.add(learningPlan1);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getLearningPlansByBatchID(batchID)).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getLearningPlansByBatchID(batchID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    @Test
    void testDeleteLearningPlan() {
        Long idToDelete = 1L;

        ResponseEntity<?> responseEntity = learningPlanController.deleteLearningPlan(idToDelete);

        verify(learningPlanService).deleteLearningPlan(idToDelete);
        assertEquals("LearningPlan deleted successfully.", responseEntity.getBody());
    }
}
