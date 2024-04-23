package com.fileupload.service.ProgressService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fileupload.DTO.CourseDTO;
import com.fileupload.DTO.ProgressDTO;
import com.fileupload.DTO.ResourceProgressDTO;
import com.fileupload.DTO.TopicDTO;
import com.fileupload.DTO.UserCourseProgressDTO;
import com.fileupload.DTO.UserProgressDTO;
import com.fileupload.DTO.UserResourceProgressDTO;
import com.fileupload.DTO.UserTopicProgressDTO;
import com.fileupload.Exception.ProgressExceptions.CourseNotFoundException;
import com.fileupload.Exception.ProgressExceptions.ResourceIdNotFoundException;
import com.fileupload.Exception.ProgressExceptions.TopicIdNotFoundException;
import com.fileupload.Exception.ProgressExceptions.UserNotFoundException;
import com.fileupload.model.ProgressModel.Progress;
import com.fileupload.repository.ProgressRespository.ProgressRepository;

@Service
public class UserProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public UserProgressDTO calculateOverallProgressForUser(Long userId, Long batchId) throws UserNotFoundException {
        List<Object[]> results = progressRepository.findOverallProgressForUser(userId, batchId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[0] != null && result[1] != null) {
                long userIdFromQuery = (long) result[0];
                double overallProgress = Math.round((double) result[1] * 100.0) / 100.0;
                return new UserProgressDTO(userIdFromQuery, overallProgress);
            }
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return null;
    }

    public UserCourseProgressDTO calculateCourseProgressForUser(Long userId, long batchId, long courseId)
            throws CourseNotFoundException {
        List<Object[]> results = progressRepository.findCourseProgressByUserAndCourse(userId, batchId, courseId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[1] != null && result[2] != null) {
                Number userIdFromQuery = (Number) result[1];
                Number overallProgress = (Number) result[2];
                return new UserCourseProgressDTO(userIdFromQuery.longValue(), overallProgress.doubleValue());
            }
        } else {
            throw new CourseNotFoundException("course with ID " + courseId + " not found.");
        }
        return null;
    }

    public UserTopicProgressDTO calculateUserTopicProgress(Long userId, long batchId, long topicId)
            throws TopicIdNotFoundException {
        List<Object[]> results = progressRepository.findTopicProgressByTopicAndUserId(userId, batchId, topicId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[1] != null && result[2] != null) {
                long userIdFromQuery = (long) result[0];
                double topicProgress = (double) result[2];
                return new UserTopicProgressDTO(userIdFromQuery, topicProgress);
            }
        }
        throw new TopicIdNotFoundException("Topic with ID " + topicId + " not found.");
    }

    public UserResourceProgressDTO calculateResourceProgressForUser(long userId, long resourceId)
            throws ResourceIdNotFoundException {
        Progress progress = progressRepository.findByUserIdAndResourceId(userId, resourceId);
        if (progress != null) {
            return new UserResourceProgressDTO(userId, progress.getCompletionPercentage());
        }
        throw new ResourceIdNotFoundException("Resource with ID " + resourceId + " not found for user " + userId);
    }

    public ProgressDTO getUserProgress(Long userId, Long batchId, List<Long> courseIds) {
        List<Object[]> results = progressRepository.getUserProgress(userId, batchId, courseIds);
        List<Progress> user = progressRepository.findByUserIdAndBatchId(userId, batchId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("user not found in batch:" + batchId);
        }
        ProgressDTO response = new ProgressDTO();
        response.setUserId(userId);
        response.setBatchId(batchId);
        List<CourseDTO> courses = new ArrayList<>();

        for (Long courseId : courseIds) {
            CourseDTO courseDTO = new CourseDTO();
            List<Object[]> progress = progressRepository.findCourseProgressByUserAndCourse(userId, batchId, courseId);
            if (progress.isEmpty()) {
                throw new CourseNotFoundException("course with ID " + courseId + " not found");
            }
            Object[] result = progress.get(0);
            if (result[0] != null && result[1] != null) {
                double overallProgress = (double) result[2];
                courseDTO.setCourseProgress(overallProgress);
            }
            courseDTO.setCourseId(courseId);
            courseDTO.setTopics(new ArrayList<>());
            courses.add(courseDTO);
        }

        for (Object[] result : results) {
            long courseId = (long) result[0];
            long topicId = (long) result[1];
            double progressDouble = (double) result[3];

            // Find the corresponding courseDTO for the current courseId
            CourseDTO courseDTO = findCourse(courses, courseId);
            if (courseDTO == null) {
                throw new CourseNotFoundException("course with ID " + courseId + " not found");
            }

            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setTopicId(topicId);
            topicDTO.setProgress(progressDouble);
            courseDTO.getTopics().add(topicDTO);
        }

        response.setCourses(courses);
        return response;
    }

    // Helper method to find a courseDTO by courseId
    private CourseDTO findCourse(List<CourseDTO> courses, long courseId) {
        for (CourseDTO courseDTO : courses) {
            if (courseDTO.getCourseId() == courseId) {
                return courseDTO;
            }
        }
        return null;
    }

    public void updateProgress(long userId, double resourceProgress, long resourceId) {
        Progress progress = progressRepository.findByUserIdAndResourceId(userId, resourceId);
        if (progress == null) {
            throw new ResourceIdNotFoundException("Resource with ID " + resourceId + " not found for user " + userId);
        }
        progress.setCompletionPercentage(resourceProgress);
        progressRepository.save(progress);
    }
    

    public List<ResourceProgressDTO> findProgressByUserIdAndTopics(Long userId, Long batchId, List<Long> topicIds) {
        Map<Long, ResourceProgressDTO> resourceProgressMap = new HashMap<>();
        List<Progress> user = progressRepository.findByUserIdAndBatchId(userId, batchId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("user not found in batch:" + batchId);
        }
        List<Object[]> results = progressRepository.findProgressByUserIdAndTopics(userId, batchId, topicIds);
        if (results.isEmpty()) {
            throw new TopicIdNotFoundException("Topic not found");
        }
        for (Object[] result : results) {
            Long resourceId = (Long) result[0];
            Double progressPercentage = (Double) result[1];
            Long topicId = (Long) result[2];

            ResourceProgressDTO progressDTO = resourceProgressMap.getOrDefault(topicId, new ResourceProgressDTO());
            progressDTO.setTopicId(topicId);
            progressDTO.setBatchId(batchId);
            progressDTO.addProgress(resourceId, progressPercentage);
            resourceProgressMap.put(topicId, progressDTO);
        }

        return new ArrayList<>(resourceProgressMap.values());
    }

    public void setProgressForNewUsers(List<Long> userIds,long batchId){
        List<Long> resourceIds=progressRepository.findResourceIdsByBatchId(batchId);
		List<Long> existingUsers=progressRepository.findAllUsers(batchId);
        userIds.removeAll(existingUsers);    
        if(!resourceIds.isEmpty()){
            for(long user:userIds){
                for(long resource:resourceIds){
                    Progress progress=new Progress();
                    progress.setBatchId(batchId);
                    progress.setCompletionPercentage(0);
                    progress.setResourceId(resource);
                    progress.setUserId(user);
                    progressRepository.save(progress);
                }
            }
        }
        
    }
    @Transactional
    public void deleteProgressOfUsers(List<Long> userIds, long batchId) {
        for (Long userId : userIds) {
            progressRepository.deleteByUserIdAndBatchId(userId, batchId);
        }
    }

}
