package com.thbs.lms.testService;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.LearningPlanDTO;
import com.thbs.lms.dto.PathDTO;
import com.thbs.lms.exception.DuplicateLearningPlanException;
import com.thbs.lms.exception.InvalidBatchException;
import com.thbs.lms.exception.InvalidLearningPlanException;
import com.thbs.lms.exception.InvalidTypeException;
import com.thbs.lms.exception.LearningPlanNotFoundException;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import com.thbs.lms.repository.LearningPlanRepository;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.service.LearningPlanPathService;
import com.thbs.lms.service.LearningPlanService;
import com.thbs.lms.service.TopicService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class LearningPlanServiceTest {

    @Mock
    private LearningPlanRepository learningPlanRepository;

    @Mock
    private LearningPlanPathService learningPlanPathService;

    @Mock
    private LearningPlanPathRepository learningPlanPathRepository;

    @Mock
    private TopicService topicService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private LearningPlanService learningPlanService;

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
        when(learningPlanRepository.save(learningPlan)).thenReturn(learningPlan);

        LearningPlan savedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);

        verify(learningPlanRepository, times(1)).save(learningPlan);
        assertEquals(learningPlan, savedLearningPlan);
    }

    @Test
    void testSaveLearningPlan_NullBatchID() {
        learningPlan.setBatchID(null);
        assertThrows(InvalidLearningPlanException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });
    }

    @Test
    void testSaveLearningPlan_NullType() {
        learningPlan.setType(null);

        assertThrows(InvalidLearningPlanException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });
    }

    @Test
    void testSaveLearningPlan_EmptyType() {
        learningPlan.setType("");

        assertThrows(InvalidLearningPlanException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });
    }

    @Test
    void testSaveLearningPlan_DuplicateLearningPlanException() {
        LearningPlan existingLearningPlan = new LearningPlan();
        existingLearningPlan.setBatchID(1L);

        when(learningPlanRepository.findByBatchID(existingLearningPlan.getBatchID()))
                .thenReturn(new ArrayList<>(List.of(existingLearningPlan)));

        learningPlan.setBatchID(existingLearningPlan.getBatchID());

        assertThrows(DuplicateLearningPlanException.class,
                () -> learningPlanService.saveLearningPlan(learningPlan));

        verify(learningPlanRepository, never()).save(any());
    }

    @Test
    void testGetAllLearningPlans() {
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);

        when(learningPlanRepository.findAll()).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getAllLearningPlans();

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    @Test
    void testGetAllLearningPlanPathDTOs() {
        // Sample data
        LearningPlan learningPlan1 = new LearningPlan();
        learningPlan1.setLearningPlanID(1L);

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setLearningPlanID(2L);

        List<LearningPlan> learningPlans = List.of(learningPlan1, learningPlan2);

        // Mocking repository method
        when(learningPlanPathRepository.findAll()).thenReturn(List.of(new LearningPlanPath(), new LearningPlanPath()));

        // Calling the method to test
        List<LearningPlanDTO> learningPlanDTOs = learningPlanService.getAllLearningPlanPathDTOs();
        assertNotNull(learningPlans);
        assertNotNull(learningPlanDTOs);

    }

    @Test
    void testGetAllLearningPlanPathDTOsByBatchId() {
        // Mock data
        Long batchId = 123L;
        List<LearningPlanDTO> allDTO = new ArrayList<>();
        // Add mock data to allDTO
        when(learningPlanService.getAllLearningPlanPathDTOs()).thenReturn(allDTO);

        // Call getAllLearningPlanPathDTOsByBatchId method
        List<LearningPlanDTO> dtoByBatch = learningPlanService.getAllLearningPlanPathDTOsByBatchId(batchId);

        // Verify the correctness of dtoByBatch
        // Add assertions to verify that only DTOs with the correct batchId are returned
        for (LearningPlanDTO dto : dtoByBatch) {
            assertEquals(batchId, dto.getBatchId());
        }
    }

    @Test
    void testGetLearningPlanById_Success() {
        Long learningPlanId = 1L;

        when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.of(learningPlan));

        LearningPlan actualLearningPlan = learningPlanService.getLearningPlanById(learningPlanId);

        assertEquals(learningPlan, actualLearningPlan);
    }

    @Test
    void testGetLearningPlanById_NotFound() {
        Long learningPlanId = 1L;

        when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.empty());

        assertThrows(LearningPlanNotFoundException.class, () -> {
            learningPlanService.getLearningPlanById(learningPlanId);
        });
    }

    @Test
    void testGetLearningPlansByType_Success() {
        String type = "Test Type";

        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);

        when(learningPlanRepository.findByType(type)).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getLearningPlansByType(type);

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    @Test
    void testGetLearningPlansByType_NotFound() {
        String type = "Nonexistent Type";

        when(learningPlanRepository.findByType(type)).thenReturn(new ArrayList<>());

        assertThrows(LearningPlanNotFoundException.class, () -> {
            learningPlanService.getLearningPlansByType(type);
        });
    }

    @Test
    void testGetLearningPlansByType_NullType() {
        assertThrows(InvalidTypeException.class, () -> {
            learningPlanService.getLearningPlansByType(null);
        });
    }

    @Test
    void testGetLearningPlansByType_EmptyType() {
        assertThrows(InvalidTypeException.class, () -> {
            learningPlanService.getLearningPlansByType("");
        });
    }

    @Test
    void testGetLearningPlansByBatchID_Success() {
        Long batchID = 1L;

        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);

        when(learningPlanRepository.findByBatchID(batchID)).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getLearningPlansByBatchID(batchID);

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    @Test
    void testGetLearningPlansByBatchID_NotFound() {
        Long batchID = 2L;

        when(learningPlanRepository.findByBatchID(batchID)).thenReturn(new ArrayList<>());

        assertThrows(LearningPlanNotFoundException.class, () -> {
            learningPlanService.getLearningPlansByBatchID(batchID);
        });
    }

    @Test
    void testGetLearningPlansByBatchID_NullBatchID() {
        assertThrows(InvalidBatchException.class, () -> {
            learningPlanService.getLearningPlansByBatchID(null);
        });
    }

    @Test
    void testDeleteLearningPlanById() {
        when(learningPlanRepository.findById(1L)).thenReturn(Optional.of(learningPlan));

        assertDoesNotThrow(() -> {
            learningPlanService.deleteLearningPlan(1L);
        });

        verify(learningPlanRepository, times(1)).delete(learningPlan);
    }

    @Test
    void testDeleteLearningPlanById_LearningPlanNotFound() {
        Long id = 1L;

        when(learningPlanRepository.findById(id)).thenReturn(Optional.empty());

        LearningPlanNotFoundException exception = assertThrows(LearningPlanNotFoundException.class, () -> {
            learningPlanService.deleteLearningPlan(id);
        });

        assertEquals("Learning plan with ID 1 not found.", exception.getMessage());
    }
}
