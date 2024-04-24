package com.thbs.lms.testService;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.Topic;
import com.thbs.lms.repository.TopicRepository;
import com.thbs.lms.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    private Topic topic;

    @BeforeEach
    void setUp() {
        topic = new Topic();
        topic.setTopicName("Test Topic");
        topic.setDescription("Test Description");
    }

    @Test
    void testAddTopicWithValidation_Success() {
        when(topicRepository.existsByTopicNameAndCourse(anyString(), any(Course.class))).thenReturn(false);

        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        Topic addedTopic = topicService.addTopicWithValidation("New Topic", "Description", new Course());

        assertNotNull(addedTopic);
        assertEquals(topic, addedTopic);
    }

    @Test
    void testAddTopicWithValidation_EmptyTopicName() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation("", "Description", new Course()));
    }

    @Test
    void testAddTopicWithValidation_NullTopicName() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation(null, "Description", new Course()));
    }

    @Test
    void testAddTopicWithValidation_EmptyDescription() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation("New Topic", "", new Course()));
    }

    @Test
    void testAddTopicWithValidation_NullDescription() {
        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicWithValidation("New Topic", null, new Course()));
    }

    @Test
    void testAddTopicWithValidation_DuplicateTopic() {
        when(topicRepository.existsByTopicNameAndCourse(anyString(), any(Course.class))).thenReturn(true);

        assertThrows(DuplicateTopicException.class,
                () -> topicService.addTopicWithValidation("Existing Topic", "Description", new Course()));
    }

    @Test
    void testAddTopicsWithValidation_Success() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic validTopic2 = new Topic();
        validTopic2.setTopicName("Topic 2");
        validTopic2.setDescription("Description 2");
        validTopic2.setCourse(course);
        topics.add(validTopic1);
        topics.add(validTopic2);

        when(topicRepository.existsByTopicNameAndCourse(any(), any())).thenReturn(false);

        List<Topic> addedTopics = assertDoesNotThrow(() -> {
            return topicService.addTopicsWithValidation(topics);
        });

        assertEquals(topics.size(), addedTopics.size());
    }

    @Test
    void testAddTopicsWithValidation_DuplicateTopics() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic duplicateTopic = new Topic();
        duplicateTopic.setTopicName("Topic 1");
        duplicateTopic.setDescription("Description 1");
        duplicateTopic.setCourse(course);

        topics.add(validTopic1);
        topics.add(duplicateTopic);

        when(topicRepository.existsByTopicNameAndCourse(any(), any())).thenReturn(true);

        assertThrows(DuplicateTopicException.class, () -> {
            topicService.addTopicsWithValidation(topics);
        });
    }

    @Test
    void testAddTopicsWithValidation_EmptyTopicName() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("");
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic validTopic2 = new Topic();
        validTopic2.setTopicName("");
        validTopic2.setDescription("Description 2");
        Course course2 = new Course();
        validTopic2.setCourse(course2);

        topics.add(validTopic1);
        topics.add(validTopic2);

        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicsWithValidation(topics));
    }

    @Test
    void testAddTopicsWithValidation_NullTopicName() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName(null);
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic validTopic2 = new Topic();
        validTopic2.setTopicName(null);
        validTopic2.setDescription("Description 2");
        Course course2 = new Course();
        validTopic2.setCourse(course2);

        topics.add(validTopic1);
        topics.add(validTopic2);

        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicsWithValidation(topics));
    }

    @Test
    void testAddTopicsWithValidation_EmptyDescription() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription("");
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic validTopic2 = new Topic();
        validTopic2.setTopicName("Topic 2");
        validTopic2.setDescription("");
        Course course2 = new Course();
        validTopic2.setCourse(course2);

        topics.add(validTopic1);
        topics.add(validTopic2);

        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicsWithValidation(topics));
    }

    @Test
    void testAddTopicsWithValidation_NullDescription() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription(null);
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic validTopic2 = new Topic();
        validTopic2.setTopicName("Topic 2");
        validTopic2.setDescription(null);
        Course course2 = new Course();
        validTopic2.setCourse(course2);

        topics.add(validTopic1);
        topics.add(validTopic2);

        assertThrows(InvalidTopicDataException.class,
                () -> topicService.addTopicsWithValidation(topics));
    }

    @Test
    void testGetAllTopics_Success() {
        List<Topic> expectedTopics = new ArrayList<>();

        expectedTopics.add(new Topic());

        when(topicRepository.findAll()).thenReturn(expectedTopics);

        List<Topic> actualTopics = topicService.getAllTopics();

        assertEquals(expectedTopics.size(), actualTopics.size());
    }

    @Test
    void testGetTopicById_Success() {
        // Mock data
        Long topicId = 123L;
        Topic expectedTopic = new Topic(/* Add constructor parameters here */);
        Optional<Topic> optionalTopic = Optional.of(expectedTopic);

        // Mock repository method
        when(topicRepository.findById(topicId)).thenReturn(optionalTopic);

        // Call service method
        Topic topic = topicService.getTopicById(topicId);

        // Verify that repository method was called with correct parameter
        verify(topicRepository).findById(topicId);

        // Verify returned topic
        assertEquals(expectedTopic, topic);
    }

    @Test
    void testGetTopicById_TopicNotFound() {
        // Mock data
        Long topicId = 123L;
        Optional<Topic> optionalTopic = Optional.empty();

        // Mock repository method
        when(topicRepository.findById(topicId)).thenReturn(optionalTopic);

        // Call service method - should throw TopicNotFoundException
        TopicNotFoundException exception = assertThrows(TopicNotFoundException.class, () -> {
            topicService.getTopicById(topicId);
        });

        // Optionally, assert the exception message or properties
        assertEquals("Topic not found for ID: " + topicId, exception.getMessage());
    }

    @Test
    void testGetTopicsByCourse_Success() {
        Course course = new Course();
        course.setCourseID(1L);

        Topic topic1 = new Topic();
        topic1.setCourse(course);

        Topic topic2 = new Topic();
        topic2.setCourse(course);

        List<Topic> topics = new ArrayList<>();
        topics.add(topic1);
        topics.add(topic2);

        when(topicRepository.findByCourse(course)).thenReturn(topics);

        List<Topic> result = topicService.getTopicsByCourse(course);

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_Success() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        String updatedTopic = topicService.updateTopicDescriptionWithValidation(1L, "New Description");

        assertEquals("Description updated successfully", updatedTopic);
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_InvalidDescription() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        assertThrows(InvalidDescriptionException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, ""));
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_NullDescription() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        assertThrows(InvalidDescriptionException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, null));
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_TopicNotFoundException() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TopicNotFoundException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, "New Description"));
    }

    @Test
    void testUpdateTopicNameWithValidation_Success() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        String updatedTopic = topicService.updateTopicNameWithValidation(1L, "New Name");

        assertEquals("Topic name updated successfully", updatedTopic);
    }

    @Test
    void testUpdateTopicNameWithValidation_InvalidName() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        assertThrows(InvalidTopicDataException.class,
                () -> topicService.updateTopicNameWithValidation(1L, ""));
    }

    @Test
    void testUpdateTopicNameWithValidation_NullName() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        assertThrows(InvalidTopicDataException.class,
                () -> topicService.updateTopicNameWithValidation(1L, null));
    }

    @Test
    void testUpdateTopicNameWithValidation_TopicNotFoundException() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TopicNotFoundException.class,
                () -> topicService.updateTopicNameWithValidation(1L, "New Name"));
    }

    @Test
    void testDeleteTopicById_Success() {
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        assertDoesNotThrow(() -> {
            topicService.deleteTopicById(1L);
        });

        verify(topicRepository, times(1)).delete(topic);
    }

    @Test
    void testDeleteTopicById_TopicNotFoundException() {
        when(topicRepository.findById(eq(1L))).thenReturn(Optional.empty());

        TopicNotFoundException exception = assertThrows(TopicNotFoundException.class, () -> {
            topicService.deleteTopicById(1L);
        });

        assertEquals("Topic not found for ID: 1", exception.getMessage());

        verify(topicRepository).findById(eq(1L));

        verify(topicRepository, never()).delete(any());
    }

    @Test
    void testDeleteTopics_Success() {
        List<Topic> topics = new ArrayList<>();

        Topic topic1 = new Topic();
        topic1.setTopicID(1L);

        Topic topic2 = new Topic();
        topic2.setTopicID(2L);

        topics.add(topic1);
        topics.add(topic2);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic1));

        when(topicRepository.findById(2L)).thenReturn(Optional.of(topic2));

        assertDoesNotThrow(() -> {
            topicService.deleteTopics(topics);
        });

        verify(topicRepository, times(1)).delete(topic1);
        verify(topicRepository, times(1)).delete(topic2);
    }

    @Test
    void testDeleteTopics_TopicNotFoundException() {
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        List<Topic> topicsToDelete = new ArrayList<Topic>();
        Topic topic1 = new Topic();
        topic1.setTopicID(1L);
        topicsToDelete.add(topic1);

        Topic topic2 = new Topic();
        topic2.setTopicID(1L);
        topicsToDelete.add(topic2);

        TopicNotFoundException exception = assertThrows(TopicNotFoundException.class, () -> {
            topicService.deleteTopics(topicsToDelete);
        });

        assertEquals("Topic not found for ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteTopicsByCourse_Success() {
        List<Topic> topics = new ArrayList<>();

        topics.add(new Topic());
        topics.add(new Topic());

        when(topicRepository.findByCourse(any(Course.class))).thenReturn(topics);

        assertDoesNotThrow(() -> topicService.deleteTopicsByCourse(new Course()));

        verify(topicRepository, times(2)).delete(any(Topic.class));
    }

    @Test
    void testDeleteTopicsByCourse_NoTopicsFound() {
        when(topicRepository.findByCourse(any(Course.class))).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> topicService.deleteTopicsByCourse(new Course()));

        verify(topicRepository, never()).delete(any(Topic.class));
    }
}
