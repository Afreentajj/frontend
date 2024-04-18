package com.thbs.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.lms.model.Course;
import com.thbs.lms.repository.CourseRepository;
import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.TopicDTO;
import com.thbs.lms.exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private static final String NOT_FOUND_MSG = "Course not found for ID: ";
    private CourseRepository courseRepository;
    private TopicService topicService;

    @Autowired
    public CourseService(CourseRepository courseRepository, TopicService topicService) {
        this.courseRepository = courseRepository;
        this.topicService = topicService;
    }

    // Saves a single course
    public Course saveCourse(Course course) {
        // Validation checks
        if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                course.getLevel() == null || course.getLevel().isEmpty()) {
            // Throws exceptions for invalid data or duplicate course
            throw new InvalidCourseDataException("Course name, and level cannot be null or empty.");
        }

        Optional<Course> existingCourse = courseRepository.findByCourseNameIgnoreCase(course.getCourseName());
        if (existingCourse.isPresent()) {
            throw new DuplicateCourseException("Course with name '" + course.getCourseName() + "' already exists.");
        }

        return courseRepository.save(course);
    }

    // Saves multiple courses
    public List<Course> saveCourses(List<Course> courses) {
        List<Course> savedCourses = new ArrayList<>();
        // Validation checks
        for (Course course : courses) {
            if (course.getCourseName() == null || course.getCourseName().isEmpty() ||
                    course.getLevel() == null || course.getLevel().isEmpty()) {
                // Throws exceptions for invalid data or duplicate course
                throw new InvalidCourseDataException("Course name, and level cannot be null or empty.");
            }

            Optional<Course> existingCourse = courseRepository.findByCourseNameIgnoreCase(course.getCourseName());
            if (existingCourse.isPresent()) {
                throw new DuplicateCourseException(
                        "Course with name '" + course.getCourseName() + "' already exists.");
            }
            savedCourses.add(courseRepository.save(course));
        }
        return savedCourses;

    }

    // Retrieves all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Retrieves courses by level
    public List<Course> getCoursesByLevel(String level) {
        // Validation checks for level

        if (level == null || level.isEmpty()) {
            throw new InvalidLevelException("Level cannot be null or empty.");
        }
        return courseRepository.findByLevel(level);
    }

    // Retrieves a course by its ID
    public Course getCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        // Retrieves a course by ID or throws exception if not found
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else {
            throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
        }
    }

    // Retrieves all courses as DTOs
    public List<CourseDTO> getAllCourseDTOs() {
        // Converts each course to DTO and returns a list of DTOs
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Converts course entity to a DTO
    public CourseDTO convertToDTO(Course course) {
        // Converts course entity to DTO format with associated topics
        List<TopicDTO> topicDTOs = topicService.getTopicsByCourse(course)
                .stream()
                .map(topic -> new TopicDTO(topic.getTopicID(), topic.getTopicName()))
                .collect(Collectors.toList());

        return new CourseDTO(course.getCourseID(), course.getCourseName(), topicDTOs);
    }

    // Updates the name of the course with the given ID
    public Course updateCourseName(Long courseId, String newCourseName) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setCourseName(newCourseName);
            return courseRepository.save(course);
        } else {
            throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
        }
    }

    // Deletes a course by its ID along with its associated Topics
    public void deleteCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            topicService.deleteTopicsByCourse(course);
            courseRepository.delete(course);
        } else {
            throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
        }
    }

    // Deletes multiple courses by its ID's along with its associated Topics
    public void deleteCourses(List<Course> courses) {
        for (Course course : courses) {
            Long courseId = course.getCourseID();
            Optional<Course> optionalCourse = courseRepository.findById(courseId);
            if (optionalCourse.isPresent()) {
                topicService.deleteTopicsByCourse(course);
                courseRepository.delete(course);
            } else {
                throw new CourseNotFoundException(NOT_FOUND_MSG + courseId);
            }
        }
    }
}
