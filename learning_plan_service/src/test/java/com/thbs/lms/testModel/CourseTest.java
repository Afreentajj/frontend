package com.thbs.lms.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.thbs.lms.model.Course;

class CourseTest {
    @Test
    void testGettersAndSetters() {
        // Initialize a Course object
        Course course = new Course();

        // Set values using setters
        Long courseID = 1L;
        String courseName = "Test Course";
        String level = "Intermediate";

        course.setCourseID(courseID);
        course.setCourseName(courseName);
        course.setLevel(level);

        // Test getters
        assertEquals(courseID, course.getCourseID());
        assertEquals(courseName, course.getCourseName());
        assertEquals(level, course.getLevel());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialize a Course object using all-args constructor
        Long courseID = 1L;
        String courseName = "Test Course";
        String level = "Intermediate";

        Course course = new Course(courseID, courseName, level);

        // Test getters
        assertEquals(courseID, course.getCourseID());
        assertEquals(courseName, course.getCourseName());
        assertEquals(level, course.getLevel());
    }

    @Test
    void testNoArgsConstructor() {
        // Initialize a Course object using no-args constructor
        Course course = new Course();

        // Test getters
        assertNull(course.getCourseID());
        assertNull(course.getCourseName());
        assertNull(course.getLevel());
    }
}
