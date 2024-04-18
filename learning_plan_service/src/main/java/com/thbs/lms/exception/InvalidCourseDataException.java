package com.thbs.lms.exception;

public class InvalidCourseDataException extends RuntimeException {
    public InvalidCourseDataException(String message) {
        super(message);
    }
}