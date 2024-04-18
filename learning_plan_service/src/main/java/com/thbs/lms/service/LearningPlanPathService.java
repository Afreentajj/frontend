package com.thbs.lms.service;

import com.thbs.lms.exception.*;
import com.thbs.lms.model.Course;
import com.thbs.lms.model.LearningPlanPath;
import com.thbs.lms.repository.LearningPlanPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanPathService {

    private static final String NOT_FOUND_MSG = "Learning Plan Path not found for ID: ";
    private LearningPlanPathRepository learningPlanPathRepository;

    @Autowired
    public LearningPlanPathService(LearningPlanPathRepository learningPlanPathRepository) {
        this.learningPlanPathRepository = learningPlanPathRepository;
    }

    // Saves a new learning plan path with validation
    public LearningPlanPath saveLearningPlanPath(LearningPlanPath learningPlanPath) {
        // Validates data and checks for duplicates before saving
        if (learningPlanPath.getStartDate() == null || learningPlanPath.getEndDate() == null
                || learningPlanPath.getTrainer() == null || learningPlanPath.getType() == null
                || learningPlanPath.getTrainer().isEmpty() || learningPlanPath.getType().isEmpty()
                || learningPlanPath.getCourse() == null) {
            // Throws exceptions if path data is invalid or duplicate
            throw new InvalidLearningPlanPathDataException(
                    "Invalid or incomplete data provided for creating Learning Plan Path");
        }
        Long learningPlanId = learningPlanPath.getLearningPlan().getLearningPlanID();
        Course course = learningPlanPath.getCourse();
        String type = learningPlanPath.getType();

        Optional<LearningPlanPath> existingEntry = learningPlanPathRepository
                .findByLearningPlanLearningPlanIDAndCourseAndType(learningPlanId, course, type);
        if (existingEntry.isPresent()) {
            throw new DuplicateLearningPlanPathException(
                    "A learning plan path with the same course, learning plan ID, and type already exists.");
        }

        return learningPlanPathRepository.save(learningPlanPath);
    }

    // Saves a list of learning plan paths with validation
    public List<LearningPlanPath> saveAllLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        List<LearningPlanPath> savedPaths = new ArrayList<>();
        for (LearningPlanPath learningPlanPath : learningPlanPaths) {
            savedPaths.add(saveLearningPlanPath(learningPlanPath));
        }
        return savedPaths;
    }

    // Retrieves all learning plan paths
    public List<LearningPlanPath> getAllLearningPlanPaths() {
        return learningPlanPathRepository.findAll();
    }

    // Retrieves learning plan paths by learning plan ID
    public List<LearningPlanPath> getAllLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        return learningPlanPathRepository.findByLearningPlanLearningPlanID(learningPlanId);
    }

    // Retrieves learning plan paths by type
    public List<LearningPlanPath> getAllLearningPlanPathsByType(String type) {
        if (type == null || type.isEmpty()) {
            // Throws exceptions if type is invalid or null
            throw new InvalidTypeException("Type cannot be null or empty.");
        }

        return learningPlanPathRepository.findByType(type);
    }

    // Retrieves learning plan paths by trainer
    public List<LearningPlanPath> getAllLearningPlanPathsByTrainer(String trainer) {
        // Validates trainer and retrieves paths by trainer
        if (trainer == null || trainer.isEmpty()) {
            // Throws exceptions if trainer is invalid or null
            throw new InvalidTrainerException("trainer cannot be null or empty.");
        }
        return learningPlanPathRepository.findByTrainer(trainer);
    }

    // Update the trainer of a learning plan path by LearningPlanPathID
    public LearningPlanPath updateLearningPlanPathTrainer(Long pathId, String newTrainer) {
        // Validates and updates the trainer of the path
        if (newTrainer == null || newTrainer.isEmpty()) {
            // Throws exceptions if trainer is invalid or null
            throw new InvalidTrainerException("Invalid or Incomplete trainer value provided");
        }
        LearningPlanPath learningPlanPath = learningPlanPathRepository.findById(pathId)
                .orElseThrow(() -> new LearningPlanPathNotFoundException(
                        NOT_FOUND_MSG + pathId));
        learningPlanPath.setTrainer(newTrainer);
        return learningPlanPathRepository.save(learningPlanPath);
    }

    // Update the dates of a learning plan path
    public Optional<LearningPlanPath> updateLearningPlanPathDates(Long learningPlanPathID, Date startDate,
            Date endDate) {
        if (startDate == null || endDate == null) {
            // Throws exceptions if learning plan path date format is invalid or null
            throw new InvalidLearningPlanPathDataException(
                    "Invalid or incomplete date provided for updating Learning Plan Path");
        }

        if (endDate.before(startDate)) {
            throw new InvalidLearningPlanPathDataException("End date must be after start date");
        }

        Optional<LearningPlanPath> optionalLearningPlanPath = learningPlanPathRepository
                .findById(learningPlanPathID);
        if (optionalLearningPlanPath.isPresent()) {
            LearningPlanPath learningPlanPath = optionalLearningPlanPath.get();
            learningPlanPath.setStartDate(startDate);
            learningPlanPath.setEndDate(endDate);
            return Optional.of(learningPlanPathRepository.save(learningPlanPath));
        } else {
            throw new LearningPlanPathNotFoundException(
                    NOT_FOUND_MSG + learningPlanPathID);
        }
    }

    // Deletes all learning plan paths associated with a learning plan
    public void deleteLearningPlanPathsByLearningPlanId(Long learningPlanId) {
        List<LearningPlanPath> learningPlanPaths = learningPlanPathRepository
                .findByLearningPlanLearningPlanID(learningPlanId);
        for (LearningPlanPath path : learningPlanPaths) {
            deleteLearningPlanPath(path.getPathID());
        }
    }

    // Deletes a list of learning plan paths
    public void deleteLearningPlanPaths(List<LearningPlanPath> learningPlanPaths) {
        learningPlanPathRepository.deleteAll(learningPlanPaths);
    }

    // Deletes a learning plan path by its ID
    public void deleteLearningPlanPath(Long learningPlanPathId) {

        Optional<LearningPlanPath> learningPlanPath = learningPlanPathRepository.findById(learningPlanPathId);
        if (learningPlanPath.isPresent()) {
            learningPlanPathRepository.delete(learningPlanPath.get());
        } else {
            throw new LearningPlanPathNotFoundException(
                    NOT_FOUND_MSG + learningPlanPathId);
        }
    }
}
