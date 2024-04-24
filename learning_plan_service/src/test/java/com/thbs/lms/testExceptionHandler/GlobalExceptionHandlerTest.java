package com.thbs.lms.testExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thbs.lms.exception.CourseNotFoundException;
import com.thbs.lms.exception.DuplicateCourseException;
import com.thbs.lms.exception.DuplicateLearningPlanException;
import com.thbs.lms.exception.DuplicateLearningPlanPathException;
import com.thbs.lms.exception.DuplicateTopicException;
import com.thbs.lms.exception.ErrorResponse;
import com.thbs.lms.exception.GlobalExceptionHandler;
import com.thbs.lms.exception.InvalidCourseDataException;
import com.thbs.lms.exception.InvalidDescriptionException;
import com.thbs.lms.exception.InvalidLearningPlanException;
import com.thbs.lms.exception.InvalidLearningPlanPathDataException;
import com.thbs.lms.exception.InvalidLevelException;
import com.thbs.lms.exception.InvalidSheetFormatException;
import com.thbs.lms.exception.InvalidTopicDataException;
import com.thbs.lms.exception.InvalidTrainerException;
import com.thbs.lms.exception.LearningPlanNotFoundException;
import com.thbs.lms.exception.LearningPlanPathNotFoundException;
import com.thbs.lms.exception.TopicNotFoundException;

@ExtendWith(SpringExtension.class)

@SpringBootTest
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleLearningPlanNotFoundException() {
        // Create a mock LearningPlanNotFoundException with a custom message
        LearningPlanNotFoundException mockException = mock(LearningPlanNotFoundException.class);
        String errorMessage = "Learning plan not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        // Call the exception handler method
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ErrorResponse response = handler.handleLearningPlanNotFoundException(mockException);

        // Verify the HTTP status and error message
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidLearningPlanException() {
        InvalidLearningPlanException mockException = mock(InvalidLearningPlanException.class);
        String errorMessage = "Invalid learning plan data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidLearningPlanException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleDuplicateLearningPlanException() {
        DuplicateLearningPlanException mockException = mock(DuplicateLearningPlanException.class);
        String errorMessage = "Duplicate learning plan detected";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateLearningPlanException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleDuplicateLearningPlanPathException() {
        DuplicateLearningPlanPathException mockException = mock(DuplicateLearningPlanPathException.class);
        String errorMessage = "Duplicate learning plan path detected";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateLearningPlanPathException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleCourseNotFoundException() {
        CourseNotFoundException mockException = mock(CourseNotFoundException.class);
        String errorMessage = "Course not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleCourseNotFoundException(mockException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidCourseDataException() {
        InvalidCourseDataException mockException = mock(InvalidCourseDataException.class);
        String errorMessage = "Invalid course data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidCourseDataException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidLevelException() {
        InvalidLevelException mockException = mock(InvalidLevelException.class);
        String errorMessage = "Invalid level";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidLevelException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleDuplicateCourseException() {
        DuplicateCourseException mockException = mock(DuplicateCourseException.class);
        String errorMessage = "Duplicate course found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateCourseException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleTopicNotFoundException() {
        TopicNotFoundException mockException = mock(TopicNotFoundException.class);
        String errorMessage = "Topic not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleTopicNotFoundException(mockException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidTopicDataException() {
        InvalidTopicDataException mockException = mock(InvalidTopicDataException.class);
        String errorMessage = "Invalid topic data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidTopicDataException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidDescriptionException() {
        InvalidDescriptionException mockException = mock(InvalidDescriptionException.class);
        String errorMessage = "Invalid description";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidDescriptionException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleDuplicateTopicException() {
        DuplicateTopicException mockException = mock(DuplicateTopicException.class);
        String errorMessage = "Duplicate topic found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateTopicException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleLearningPlanPathNotFoundException() {
        LearningPlanPathNotFoundException mockException = mock(LearningPlanPathNotFoundException.class);
        String errorMessage = "Learning plan path not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleLearningPlanPathNotFoundException(mockException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidLearningPlanPathDataException() {
        InvalidLearningPlanPathDataException mockException = mock(InvalidLearningPlanPathDataException.class);
        String errorMessage = "Invalid learning plan path data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidLearningPlanPathDataException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidSheetFormatException() {
        InvalidSheetFormatException mockException = mock(InvalidSheetFormatException.class);
        String errorMessage = "Invalid Sheet Format";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidSheetFormatException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleFileUploadException() {
        FileUploadException mockException = mock(FileUploadException.class);
        String errorMessage = "Issue with File Upload";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleFileUploadException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidTrainerException() {
        InvalidTrainerException mockException = mock(InvalidTrainerException.class);
        String errorMessage = "Invalid trainer";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidTrainerException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

}
