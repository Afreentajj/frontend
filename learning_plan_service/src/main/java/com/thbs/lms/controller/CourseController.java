package com.thbs.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.model.Course;
import com.thbs.lms.service.CourseService;

@RestController
@RequestMapping("/course")
@CrossOrigin("172.18.4.186:5173, 172.18.4.113:5173, 172.18.4.30:5173, 172.18.4.195:5173")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // add a single course
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course addedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok().body(addedCourse);
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }

    // Get all courseDTOs
    @GetMapping("/dto")
    public ResponseEntity<List<CourseDTO>> getAllCourseDTOs() {
        List<CourseDTO> courseDTOs = courseService.getAllCourseDTOs();
        return ResponseEntity.ok().body(courseDTOs);
    }

    // Get courses at a particular level (Beginner, Intermediate, Difficult)
    @GetMapping("/level/{level}")
    public ResponseEntity<List<Course>> getCoursesByLevel(@PathVariable String level) {
        List<Course> courses = courseService.getCoursesByLevel(level);
        return ResponseEntity.ok().body(courses);
    }

    // Get course by courseId
    @GetMapping("/id/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok().body(course);
    }

    // Update courseName for a particular courseId
    @PatchMapping("/name/{courseId}")
    public ResponseEntity<Course> updateCourseName(@PathVariable Long courseId, @RequestBody String newCourseName) {
        Course updatedCourse = courseService.updateCourseName(courseId, newCourseName);
        return ResponseEntity.ok().body(updatedCourse);
    }

    // Delete a course by courseId
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().body("Course deleted successfully");
    }

    // Delete multiple courses
    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteCourses(@RequestBody List<Course> courses) {
        courseService.deleteCourses(courses);
        return ResponseEntity.ok().body("Courses deleted successfully");
    }
}
