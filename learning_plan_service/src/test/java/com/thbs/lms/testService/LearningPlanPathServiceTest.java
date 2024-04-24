package com.thbs.lms.testService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.controller.LearningPlanPathController;
import com.thbs.lms.exception.DuplicateLearningPlanPathException;
import com.thbs.lms.exception.InvalidLearningPlanPathDataException;
import com.thbs.lms.exception.InvalidTrainerException;
import com.thbs.lms.exception.InvalidTypeException;
import com.thbs.lms.exception.LearningPlanPathNotFoundException;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlan;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import com.thbs.lms.service.LearningPlanPathService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class LearningPlanPathServiceTest {

    @Mock
    private LearningPlanPathRepository learningPlanPathRepository;

    @Mock
    private LearningPlanPathController learningPlanPathController;

    @InjectMocks
    private LearningPlanPathService learningPlanPathService;

    private LearningPlanPath learningPlanPath, learningPlanPath2;
    private Course course;
    private LearningPlan learningPlan;

    @BeforeEach
    void setUp() {
        // Mocking a LearningPlanPath object
        course = new Course();
        course.setCourseID(1L);
        course.setCourseName("Test Course");

        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanID(1L);

        learningPlanPath = new LearningPlanPath();
        learningPlanPath.setPathID(1L);
        learningPlanPath.setStartDate(new Date());
        learningPlanPath.setEndDate(new Date());
        learningPlanPath.setTrainer("Test Trainer");
        learningPlanPath.setType("Test Type");
        learningPlanPath.setCourse(course);
        learningPlanPath.setLearningPlan(learningPlan);

        learningPlanPath2 = new LearningPlanPath();
        learningPlanPath2.setPathID(2L);
        learningPlanPath2.setStartDate(new Date());
        learningPlanPath2.setEndDate(new Date());
        learningPlanPath2.setTrainer("Test Trainer2");
        learningPlanPath2.setType("Test Type2");
        learningPlanPath2.setCourse(course);
        learningPlanPath2.setLearningPlan(learningPlan);
    }

    @Test
    void testSaveLearningPlanPath_Success() {
        when(learningPlanPathRepository.save(any(LearningPlanPath.class))).thenReturn(learningPlanPath);

        LearningPlanPath createdPath = learningPlanPathService.saveLearningPlanPath(learningPlanPath);

        assertNotNull(createdPath);
        assertEquals(learningPlanPath, createdPath);
    }

    @Test
    void testSaveLearningPlanPath_NullStartDate() {
        learningPlanPath.setStartDate(null);

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveLearningPlanPath_NullEndDate() {
        learningPlanPath.setEndDate(null);

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveLearningPlanPath_EmptyTrainer() {
        learningPlanPath.setTrainer("");

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveLearningPlanPath_NullTrainer() {
        learningPlanPath.setTrainer(null);

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveLearningPlanPath_EmptyType() {
        learningPlanPath.setType("");

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveLearningPlanPath_NullCourse() {
        learningPlanPath.setCourse(null);

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveLearningPlanPath_NullType() {
        learningPlanPath.setType(null);

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveLearningPlanPath_DuplicateEntry() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanIDAndCourseAndType(anyLong(), any(Course.class),
                anyString())).thenReturn(Optional.of(learningPlanPath));

        assertThrows(DuplicateLearningPlanPathException.class, () -> {
            learningPlanPathService.saveLearningPlanPath(learningPlanPath);
        });
    }

    @Test
    void testSaveAllLearningPlanPaths_Success() {
        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        learningPlanPaths.add(learningPlanPath);
        learningPlanPaths.add(learningPlanPath2);

        when(learningPlanPathRepository.saveAll(learningPlanPaths))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<LearningPlanPath> savedPaths = learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths);

        assertEquals(2, savedPaths.size());
        verify(learningPlanPathRepository, times(2)).save(any(LearningPlanPath.class));
    }

    @Test
    void testSaveAllLearningPlanPaths_EmptyList() {
        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        List<LearningPlanPath> savedPaths = learningPlanPathService.saveAllLearningPlanPaths(learningPlanPaths);

        assertEquals(0, savedPaths.size());
        verify(learningPlanPathRepository, never()).save(any(LearningPlanPath.class));
    }

    @Test
    void testGetAllLearningPlanPaths_Success() {
        List<LearningPlanPath> expectedPaths = new ArrayList<>();

        expectedPaths.add(new LearningPlanPath());

        when(learningPlanPathRepository.findAll()).thenReturn(expectedPaths);

        List<LearningPlanPath> actualPaths = learningPlanPathService.getAllLearningPlanPaths();

        assertEquals(expectedPaths.size(), actualPaths.size());
    }

    @Test
    void testGetAllLearningPlanPathsByLearningPlanId_Success() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong()))
                .thenReturn(Arrays.asList(learningPlanPath));

        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByLearningPlanId(1L);

        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(learningPlanPath.getPathID(), paths.get(0).getPathID());
    }

    @Test
    void testGetAllLearningPlanPathsByType_Success() {
        when(learningPlanPathRepository.findByType(anyString())).thenReturn(Arrays.asList(learningPlanPath));

        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByType("Test Type");

        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(learningPlanPath.getPathID(), paths.get(0).getPathID());
    }

    @Test
    void testGetAllLearningPlanPathsByType_NullType() {
        assertThrows(InvalidTypeException.class, () -> {
            learningPlanPathService.getAllLearningPlanPathsByType(null);
        });
    }

    @Test
    void testGetAllLearningPlanPathsByType_EmptyType() {
        assertThrows(InvalidTypeException.class, () -> {
            learningPlanPathService.getAllLearningPlanPathsByType("");
        });
    }

    @Test
    void testGetAllLearningPlanPathsByTrainer_Success() {
        when(learningPlanPathRepository.findByTrainer(anyString())).thenReturn(Arrays.asList(learningPlanPath));

        List<LearningPlanPath> paths = learningPlanPathService.getAllLearningPlanPathsByTrainer("Test Trainer");

        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(learningPlanPath.getPathID(), paths.get(0).getPathID());
    }

    @Test
    void testGetAllLearningPlanPathsByTrainer_NullTrainer() {
        assertThrows(InvalidTrainerException.class, () -> {
            learningPlanPathService.getAllLearningPlanPathsByTrainer(null);
        });
    }

    @Test
    void testGetAllLearningPlanPathsByTrainer_EmptyTrainer() {
        assertThrows(InvalidTrainerException.class, () -> {
            learningPlanPathService.getAllLearningPlanPathsByTrainer("");
        });
    }

    @Test
    void testUpdateLearningPlanPathTrainer_Success() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.of(learningPlanPath));
        when(learningPlanPathRepository.save(any(LearningPlanPath.class))).thenReturn(learningPlanPath);

        String newTrainer = "New Trainer";
        learningPlanPathService.updateLearningPlanPathTrainer(1L, newTrainer);

        assertEquals(newTrainer, learningPlanPath.getTrainer());
    }

    @Test
    void testUpdateLearningPlanPathTrainer_NullTrainer() {
        assertThrows(InvalidTrainerException.class, () -> {
            learningPlanPathService.updateLearningPlanPathTrainer(1L, null);
        });
    }

    @Test
    void testUpdateLearningPlanPathTrainer_EmptyTrainer() {
        assertThrows(InvalidTrainerException.class, () -> {
            learningPlanPathService.updateLearningPlanPathTrainer(1L, "");
        });
    }

    @Test
    void testUpdateLearningPlanPathTrainer_LearningPlanPathNotFound() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearningPlanPathNotFoundException.class, () -> {
            learningPlanPathService.updateLearningPlanPathTrainer(2L, "New Trainer");
        });
    }

    @Test
    void testUpdateLearningPlanPathDates_Success() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.of(learningPlanPath));
        when(learningPlanPathRepository.save(any(LearningPlanPath.class))).thenReturn(learningPlanPath);

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 1000 * 60 * 60 * 24); // Adding one day

        Optional<LearningPlanPath> updatedPath = learningPlanPathService.updateLearningPlanPathDates(1L, startDate,
                endDate);

        assertTrue(updatedPath.isPresent());
        assertEquals(startDate, updatedPath.get().getStartDate());
        assertEquals(endDate, updatedPath.get().getEndDate());
    }

    @Test
    void testUpdateLearningPlanPathDates_NullStartDate() {
        Date date = new Date();
        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.updateLearningPlanPathDates(1L, null, date);
        });
    }

    @Test
    void testUpdateLearningPlanPathDates_NullEndDate() {
        Date date = new Date();
        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.updateLearningPlanPathDates(1L, date, null);
        });
    }

    @Test
    void testUpdateLearningPlanPathDates_EndDateBeforeStartDate() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() - 1000 * 60 * 60 * 24); // Subtracting one day

        assertThrows(InvalidLearningPlanPathDataException.class, () -> {
            learningPlanPathService.updateLearningPlanPathDates(1L, startDate, endDate);
        });
    }

    @Test
    void testUpdateLearningPlanPathDates_LearningPlanPathNotFound() {
        Date date = new Date();
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearningPlanPathNotFoundException.class, () -> {
            learningPlanPathService.updateLearningPlanPathDates(2L, date, date);
        });
    }

    @Test
    void testDeleteLearningPlanPathsByLearningPlanId_Success() {
        List<LearningPlanPath> learningPlanPaths = new ArrayList<>();

        learningPlanPaths.add(learningPlanPath);
        learningPlanPaths.add(learningPlanPath2);

        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong())).thenReturn(learningPlanPaths);

        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.of(learningPlanPath));
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.of(learningPlanPath2));

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(1L);
        });
    }

    @Test
    void testDeleteLearningPlanPathsByLearningPlanId_EmptyList() {
        when(learningPlanPathRepository.findByLearningPlanLearningPlanID(anyLong())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPathsByLearningPlanId(2L);
        });
    }

    @Test
    void testDeleteLearningPlanPaths_Success() {
        List<LearningPlanPath> paths = Arrays.asList(learningPlanPath);

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPaths(paths);
        });
    }

    @Test
    void testDeleteLearningPlanPaths_EmptyList() {
        List<LearningPlanPath> paths = new ArrayList<>();

        assertDoesNotThrow(() -> {
            learningPlanPathService.deleteLearningPlanPaths(paths);
        });
    }

    @Test
    void testDeleteLearningPlanPath_Success() {
        when(learningPlanPathRepository.findById(1L)).thenReturn(Optional.of(learningPlanPath));

        learningPlanPathService.deleteLearningPlanPath(1L);

        verify(learningPlanPathRepository, times(1)).delete(learningPlanPath);
    }

    @Test
    void testDeleteLearningPlanPath_LearningPlanPathNotFound() {
        when(learningPlanPathRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearningPlanPathNotFoundException.class, () -> {
            learningPlanPathService.deleteLearningPlanPath(2L);
        });
    }
}
