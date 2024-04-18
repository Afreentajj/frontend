package com.thbs.lms.testService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.service.CourseService;
import com.thbs.lms.service.TopicService;
import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testSaveCourse_InvalidCourseDataException() {
        Course course = new Course();
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_DuplicateCourseException() {
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setLevel("Test type");

        when(courseRepository.findByCourseNameIgnoreCase("Java Programming")).thenReturn(Optional.of(course));

        assertThrows(DuplicateCourseException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourse_SuccessfulSaving() {
        Course course = new Course();
        course.setCourseName("Python Programming");
        course.setLevel("Test type");
        when(courseRepository.findByCourseNameIgnoreCase("Python Programming")).thenReturn(Optional.empty());
        when(courseRepository.save(course)).thenReturn(course);
        assertEquals(course, courseService.saveCourse(course));
    }

    @ParameterizedTest
    @CsvSource({
            ", Intermediate", // null course name
            "empty, Intermediate", // empty course name
            "Java, ", // null level
            "Java,empty" // empty level
    })
    void testSaveCourse_InvalidCourseDataException(String courseName, String level) {
        Course course = new Course();
        course.setCourseName("empty".equals(courseName) ? "" : courseName);
        course.setLevel("empty".equals(level) ? "" : level);
        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourse(course));
    }

    @Test
    void testSaveCourses_InvalidCourseDataException() {
        List<Course> courses = new ArrayList<>();
        Course invalidCourse = new Course();
        courses.add(invalidCourse);

        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testSaveCourses_DuplicateCourseException() {
        List<Course> courses = new ArrayList<>();
        Course existingCourse = new Course();
        existingCourse.setCourseName("Java Programming");
        existingCourse.setLevel("Intermediate");
        courses.add(existingCourse);

        when(courseRepository.findByCourseNameIgnoreCase("Java Programming")).thenReturn(Optional.of(existingCourse));

        assertThrows(DuplicateCourseException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testSaveCourses_SuccessfulSaving() {
        List<Course> courses = new ArrayList<>();
        Course newCourse = new Course();
        newCourse.setCourseName("Python Programming");
        newCourse.setLevel("Intermediate");
        courses.add(newCourse);

        when(courseRepository.findByCourseNameIgnoreCase("Python Programming")).thenReturn(Optional.empty());
        when(courseRepository.save(newCourse)).thenReturn(newCourse);

        List<Course> savedCourses = courseService.saveCourses(courses);

        assertEquals(1, savedCourses.size());
        assertEquals(newCourse, savedCourses.get(0));
    }

    @ParameterizedTest
    @CsvSource({
            ", Intermediate", // Null course name
            "empty, Intermediate", // Empty course name
            "Python Programming, ", // Null level
            "Python Programming, empty" // Empty level
    })
    void testSaveCourses_InvalidCourseDataException(String courseName, String level) {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("empty".equals(courseName) ? "" : courseName);
        course1.setLevel("empty".equals(level) ? "" : level);

        Course course2 = new Course();
        course2.setCourseName("Java Programming");
        course2.setLevel("Intermediate");

        courses.add(course1);
        courses.add(course2);

        assertThrows(InvalidCourseDataException.class, () -> courseService.saveCourses(courses));
    }

    @Test
    void testGetAllCourses_SuccessfulRetrieval() {
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCourseID(1L);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        Course course2 = new Course();
        course2.setCourseID(2L);
        course2.setCourseName("Python");
        course2.setLevel("Beginner");

        courses.add(course);
        courses.add(course2);

        when(courseRepository.findAll()).thenReturn(courses);

        assertEquals(courses, courseService.getAllCourses());
    }

    @Test
    void testGetCoursesByLevel_InvalidLevelException() {

        assertThrows(InvalidLevelException.class, () -> courseService.getCoursesByLevel(null));

        assertThrows(InvalidLevelException.class, () -> courseService.getCoursesByLevel(""));
    }

    @Test
    void testGetCoursesByLevel_SuccessfulRetrieval() {
        String level = "Intermediate";
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCourseID(1L);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        Course course2 = new Course();
        course2.setCourseID(2L);
        course2.setCourseName("Python");
        course2.setLevel("Intermediate");

        courses.add(course);
        courses.add(course2);
        when(courseRepository.findByLevel(level)).thenReturn(courses);

        assertEquals(courses, courseService.getCoursesByLevel(level));
    }

    @Test
    void testGetCourseById_CourseFound() {
        Long courseId = 1L;
        Course course = new Course();
        course.setCourseID(courseId);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        assertEquals(course, courseService.getCourseById(courseId));
    }

    @Test
    void testGetCourseById_CourseNotFoundException() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
    }

    @Test
    void testUpdateCourseName_Success() {
        Long courseId = 1L;
        Course course = new Course();
        course.setCourseID(courseId);
        course.setCourseName("Java");
        course.setLevel("Intermediate");

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course updatedCourse = courseService.updateCourseName(1L, "New Course Name");

        assertEquals("New Course Name", updatedCourse.getCourseName());

    }

    @Test
    void testGetAllCourseDTOs_SuccessfulRetrieval() {
        Course course1 = new Course();
        course1.setCourseID(1L);
        course1.setCourseName("Java");
        course1.setLevel("Intermediate");

        Course course2 = new Course();
        course2.setCourseID(2L);
        course2.setCourseName("Python");
        course2.setLevel("Beginner");

        List<Course> courses = Arrays.asList(course1, course2);

        when(courseRepository.findAll()).thenReturn(courses);
        when(topicService.getTopicsByCourse(any(Course.class))).thenReturn(Collections.emptyList());

        List<CourseDTO> courseDTOs = courseService.getAllCourseDTOs();

        assertEquals(courses.size(), courseDTOs.size());
        for (int i = 0; i < courses.size(); i++) {
            assertEquals(courses.get(i).getCourseID(), courseDTOs.get(i).getCourseId());
            assertEquals(courses.get(i).getCourseName(), courseDTOs.get(i).getCourseName());
            assertTrue(courseDTOs.get(i).getTopics().isEmpty());
        }
    }

    @Test
    void testUpdateCourseName_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> {
            courseService.updateCourseName(1L, "New Course Name");
        });

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testDeleteCourse_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).delete(any(Course.class));
    }

    @Test
    void testDeleteCourse_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> {
            courseService.deleteCourse(1L);
        });

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, never()).delete(any(Course.class));
    }

    @Test
    void testDeleteCourses_Success() {
        List<Course> coursesToDelete = new ArrayList<>();
        coursesToDelete.add(new Course(1L, "Course1", "Beginner"));
        coursesToDelete.add(new Course(2L, "Course2", "Intermediate"));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(new Course()));
        doNothing().when(courseRepository).delete(any());

        assertDoesNotThrow(() -> courseService.deleteCourses(coursesToDelete));

        verify(courseRepository, times(2)).delete(any());
    }

    @Test
    void testDeleteCourses_MixOfExistingAndNonExisting() {
        List<Course> coursesToDelete = new ArrayList<>();
        coursesToDelete.add(new Course(1L, "Course1", "Beginner"));
        coursesToDelete.add(new Course(2L, "Course2", "Intermediate"));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(courseRepository.findById(2L)).thenReturn(Optional.empty());

        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class,
                () -> courseService.deleteCourses(coursesToDelete));

        assertEquals("Course not found for ID: 2", exception.getMessage());

        verify(courseRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteCourses_EmptyList() {
        List<Course> coursesToDelete = new ArrayList<>();

        assertDoesNotThrow(() -> courseService.deleteCourses(coursesToDelete));
    }

    @Test
    void testDeleteCourses_WhenIdNotFound() {
        List<Course> coursesToDelete = new ArrayList<>();
        coursesToDelete.add(new Course(1L, "Course1", "Beginner"));

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class,
                () -> courseService.deleteCourses(coursesToDelete));

        assertEquals("Course not found for ID: 1", exception.getMessage());

        verify(courseRepository, never()).delete(any());
    }
}
