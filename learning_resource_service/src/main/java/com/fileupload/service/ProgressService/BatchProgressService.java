package com.fileupload.service.ProgressService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fileupload.DTO.BUProgressDTO;
import com.fileupload.DTO.BatchProgressDTO;
import com.fileupload.DTO.BatchWiseProgressDTO;
import com.fileupload.DTO.UserBatchProgressDTO;
import com.fileupload.DTO.UserCourseProgressDTO;
import com.fileupload.DTO.UserProgressDTO;
import com.fileupload.Exception.ProgressExceptions.BatchIdNotFoundException;
import com.fileupload.Exception.ProgressExceptions.CourseNotFoundException;
import com.fileupload.Exception.ProgressExceptions.UserNotFoundException;
import com.fileupload.model.ProgressModel.Progress;
import com.fileupload.repository.ProgressRespository.BatchProgressRepository;




@Service
public class BatchProgressService {
    
    @Autowired
    private BatchProgressRepository batchProgressRepository;

    @Autowired
    private UserProgressService userProgressService;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${userModule.uri}")
    String userModuleUri;


    public List<BatchWiseProgressDTO> findBatchwiseProgress() {
        List<Object[]> batches = batchProgressRepository.findAllBatches();
        List<BatchWiseProgressDTO> batchProgressList = new ArrayList<>();
    
        for (Object[] result : batches) {
            long batchId = (long) result[0];
            try {
                BatchProgressDTO progress = calculateBatchProgress(batchId);
                batchProgressList.add(new BatchWiseProgressDTO(batchId, progress.getBatchProgress()));
            } catch (Exception e) {
                System.err.println("Error calculating progress for batchId " + batchId + ": " + e.getMessage());
                continue;
            }
        }
    
        return batchProgressList;
    }
    

    public List<UserBatchProgressDTO> calculateOverallBatchProgressAllUsers(Long batchId) throws BatchIdNotFoundException {
        List<Object[]> users=batchProgressRepository.findAllUsers(batchId);
        if (users != null && !users.isEmpty()) {
            List<UserBatchProgressDTO> progressList = new ArrayList<>();
            for (Object[] user : users) {
                if (user[0] != null) {
                    Long userId = (Long) user[0];
                    UserProgressDTO progress=userProgressService.calculateOverallProgressForUser(userId,batchId);
                    progressList.add(new UserBatchProgressDTO(userId, progress.getOverallProgress()));
                }
            }
            return progressList;
        } else {
            throw new BatchIdNotFoundException("Batch with ID " + batchId + " not found.");
        }
    }

   

    public BatchProgressDTO calculateBatchProgress(long batchId) throws BatchIdNotFoundException {
        List<Object[]> results = batchProgressRepository.findOverallBatchProgress(batchId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result != null && result.length > 0) {
                double batchProgress = (double) result[0];
                batchProgress = Math.round(batchProgress * 100.0) / 100.0;
                return new BatchProgressDTO(batchId, batchProgress);
            }
        }
        throw new BatchIdNotFoundException("Batch with ID " + batchId + " not found.");
    }

    public List<UserBatchProgressDTO> calculateBuProgress(String buName) {
        String uri = userModuleUri;
        ResponseEntity<List<Long>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {}, buName);
        List<Long> userIds = response.getBody();
        List<Progress> users=new ArrayList<>();
        for(Long id:userIds){
            List<Progress> temp=batchProgressRepository.findAllByUserId(id);
            users.addAll(temp);
        }
        List<Long> resId=new ArrayList<>();
        for(Progress user:users){
            resId.add(user.getUserId());
        }
        if (resId!=null && !resId.isEmpty()) {
            List<Object[]> results = batchProgressRepository.findUserProgressInBu(resId);
            if (!results.isEmpty()) {
                List<UserBatchProgressDTO> userProgressList = new ArrayList<>();
                for (Object[] result : results) {
                    Long userId = (Long) result[0];
                    Double overallProgress = (Double) result[1];
                    userProgressList.add(new UserBatchProgressDTO(userId, overallProgress));
                }
                return userProgressList;
            } else {
                throw new BatchIdNotFoundException("No progress found for users in batch with ID " + buName);
            }
        } else {
            throw new BatchIdNotFoundException("No users found for batch with ID " + buName);
        }
    }
    
    public BUProgressDTO findOverallBUProgress(String buName) {
        String uri = userModuleUri;
        ResponseEntity<List<Long>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {}, buName);
        List<Long> userIds = response.getBody();
        List<Progress> users=new ArrayList<>();
        for(Long id:userIds){
            List<Progress> temp=batchProgressRepository.findAllByUserId(id);
            users.addAll(temp);
        }
        List<Long> resId=new ArrayList<>();
        for(Progress user:users){
            resId.add(user.getUserId());
        }
        if (resId!=null && !resId.isEmpty()) {
            List<Object[]> result = batchProgressRepository.findOverallBUProgress(resId);
            if (!result.isEmpty()) {
                Object[] res = result.get(0);
                double progress=(double) res[0];
                return new BUProgressDTO(buName, progress);
            } else {
                throw new BatchIdNotFoundException("No progress found for users in batch with ID " + buName);
            }
        } else {
            throw new BatchIdNotFoundException("No users found for batch with ID " + buName);
        }
    }

    public List<UserCourseProgressDTO> calculateCourseProgressOfUsersInBatch(long batchId, long courseId) {
        try {
            List<Object[]> userFromQuery = batchProgressRepository.findAllUsers(batchId);
            if (userFromQuery.isEmpty()) {
                throw new UserNotFoundException("No users found for the batch: " + batchId);
            }

            List<Long> users = new ArrayList<>();
            for (Object[] id : userFromQuery) {
                long userId = (long) id[0];
                users.add(userId);
            }

            List<Object[]> results = batchProgressRepository.findCourseProgressByUserAndCourseInBatch(users,batchId, courseId);
            if (results.isEmpty()) {
                throw new CourseNotFoundException("Course not found with ID: " + courseId);
            }

            List<UserCourseProgressDTO> progress = new ArrayList<>();
            for (Object[] res : results) {
                long userId = (long) res[1];
                double courseProgress = (double) res[2];
                progress.add(new UserCourseProgressDTO(userId, courseProgress));
            }
            return progress;
        } catch (BatchIdNotFoundException | CourseNotFoundException e) {
            throw e; 
        } catch (Exception e) {
            e.printStackTrace(); // Log other unexpected exceptions
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
}
