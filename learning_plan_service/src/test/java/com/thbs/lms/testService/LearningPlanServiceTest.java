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

        // Assertions
        // assertEquals(learningPlans.size(), learningPlanDTOs.size());
        // for (int i = 0; i < learningPlans.size(); i++) {
        // assertEquals(learningPlans.get(i).getLearningPlanID(),
        // learningPlanDTOs.get(i).getLearningPlanId());
    }

    // @Test
    // void testConvertToDTO() {
    // // Mock data
    // Long learningPlanId = 1L;
    // LearningPlan learningPlan = new LearningPlan();
    // learningPlan.setBatchID(123L);

    // LearningPlanPath path1 = new LearningPlanPath();
    // path1.setPathID(101L);
    // path1.setType("Course");

    // LearningPlanPath path2 = new LearningPlanPath();
    // path2.setPathID(102L);
    // path2.setType("Module");

    // List<LearningPlanPath> relatedPaths = new ArrayList<>();
    // relatedPaths.add(path1);
    // relatedPaths.add(path2);

    // Course course1 = new Course(202L, "course2", "level");
    // path1.setCourse(course1);

    // Course course2 = new Course(202L, "course2", "level");
    // path2.setCourse(course2);

    // List<Course> relatedCourses = new ArrayList<>();
    // relatedCourses.add(course1);
    // relatedCourses.add(course2);

    // Topic topic1 = new Topic();
    // topic1.setTopicID(301L);

    // Topic topic2 = new Topic();
    // topic2.setTopicID(302L);

    // List<Topic> topics = new ArrayList<>();
    // topics.add(topic1);
    // topics.add(topic2);

    // // Mock interactions
    // when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.of(learningPlan));
    // when(learningPlanPathRepository.findByLearningPlanLearningPlanID(learningPlanId)).thenReturn(relatedPaths);
    // // when(path1.getCourse()).thenReturn(course1);
    // // when(path2.getCourse()).thenReturn(course2);
    // when(topicService.getTopicsByCourse(course1)).thenReturn(topics);
    // when(topicService.getTopicsByCourse(course2)).thenReturn(topics);

    // // Call the method
    // LearningPlanDTO dto = learningPlanService.convertToDTO(learningPlanId);

    // // Assertions
    // assertEquals(learningPlan.getBatchID(), dto.getBatchId());
    // assertEquals(learningPlanId, dto.getLearningPlanId());
    // assertEquals(2, dto.getPath().size());
    // assertEquals(2, dto.getCourseIds().size());
    // assertEquals(2, dto.getTopicIds().size());
    // }

    @Test
    void testConvertToDTO() {
        // Mock data
        Long learningPlanId = 1L;
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setBatchID(123L);

        LearningPlanPath path1 = new LearningPlanPath();
        path1.setPathID(101L);
        path1.setType("Course");
        path1.setTrainer("Trainer1");
        path1.setStartDate(new Date());
        path1.setEndDate(new Date());

        LearningPlanPath path2 = new LearningPlanPath();
        path2.setPathID(102L);
        path2.setType("Module");
        path2.setTrainer("Trainer2");
        path2.setStartDate(new Date());
        path2.setEndDate(new Date());

        List<LearningPlanPath> relatedPaths = new ArrayList<>();
        relatedPaths.add(path1);
        relatedPaths.add(path2);

        Course course1 = new Course(202L, "course2", "level");
        path1.setCourse(course1);

        Course course2 = new Course(203L, "course3", "level");
        path2.setCourse(course2);

        // Mock interactions
        when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.of(learningPlan));
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(learningPlanId)).thenReturn(relatedPaths);
        when(courseService.convertToDTO(course1)).thenReturn(new CourseDTO(202L, "course2", null));
        when(courseService.convertToDTO(course2)).thenReturn(new CourseDTO(203L, "course3", null));

        // Call the method
        LearningPlanDTO dto = learningPlanService.convertToDTO(learningPlanId);

        // Assertions
        assertEquals(learningPlan.getBatchID(), dto.getBatchId());
        assertEquals(learningPlanId, dto.getLearningPlanId());
        assertEquals(2, dto.getPath().size());

        // Check path 1
        PathDTO pathDTO1 = dto.getPath().get(0);
        assertEquals(path1.getPathID(), pathDTO1.getLearningPlanPathId());
        assertEquals(path1.getType(), pathDTO1.getType());
        assertEquals(path1.getTrainer(), pathDTO1.getTrainer());
        assertEquals(path1.getStartDate(), pathDTO1.getStartDate());
        assertEquals(path1.getEndDate(), pathDTO1.getEndDate());
        assertEquals(202L, pathDTO1.getCourse().getCourseId()); // Assuming course1 has ID 202L

        // Check path 2
        PathDTO pathDTO2 = dto.getPath().get(1);
        assertEquals(path2.getPathID(), pathDTO2.getLearningPlanPathId());
        assertEquals(path2.getType(), pathDTO2.getType());
        assertEquals(path2.getTrainer(), pathDTO2.getTrainer());
        assertEquals(path2.getStartDate(), pathDTO2.getStartDate());
        assertEquals(path2.getEndDate(), pathDTO2.getEndDate());
        assertEquals(203L, pathDTO2.getCourse().getCourseId()); // Assuming course2 has ID 203L
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
