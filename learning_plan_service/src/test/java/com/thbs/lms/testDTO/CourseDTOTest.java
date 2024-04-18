package com.thbs.lms.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.lms.dto.CourseDTO;
import com.thbs.lms.dto.TopicDTO;

@SpringBootTest
class CourseDTOTest {

    @Test
    void testGetterAndSetter() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(1L);
        courseDTO.setCourseName("Java");
        List<TopicDTO> topics = new ArrayList<>();
        courseDTO.setTopics(topics);

        assertEquals(1L, courseDTO.getCourseId());
        assertEquals("Java", courseDTO.getCourseName());
        assertEquals(topics, courseDTO.getTopics());
    }

    @Test
    void testAllArgsConstructor() {
        List<TopicDTO> topics = new ArrayList<>();
        TopicDTO topic1 = new TopicDTO(1L, "Topic 1");
        TopicDTO topic2 = new TopicDTO(2L, "Topic 2");
        topics.add(topic1);
        topics.add(topic2);

        CourseDTO courseDTO = new CourseDTO(1L, "Java", topics);

        assertEquals(1L, courseDTO.getCourseId());
        assertEquals("Java", courseDTO.getCourseName());
        assertEquals(topics, courseDTO.getTopics());
    }

    @Test
    void testNoArgsConstructor() {
        CourseDTO courseDTO = new CourseDTO();

        assertNull(courseDTO.getCourseId());
        assertNull(courseDTO.getCourseName());
        assertNull(courseDTO.getTopics());
    }
}
