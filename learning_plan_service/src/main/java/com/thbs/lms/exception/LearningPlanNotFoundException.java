package com.thbs.lms.exception;

public class LearningPlanNotFoundException extends RuntimeException {
    public LearningPlanNotFoundException(String message) {
        super(message);
    }
}