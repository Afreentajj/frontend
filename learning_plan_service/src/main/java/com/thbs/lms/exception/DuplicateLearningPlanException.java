package com.thbs.lms.exception;

public class DuplicateLearningPlanException extends RuntimeException {
    public DuplicateLearningPlanException(String message) {
        super(message);
    }
}