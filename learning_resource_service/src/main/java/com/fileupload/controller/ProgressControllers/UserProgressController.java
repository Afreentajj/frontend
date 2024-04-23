package com.fileupload.controller.ProgressControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fileupload.DTO.DeleteProgressOfUsersDTO;
import com.fileupload.DTO.ProgressDTO;
import com.fileupload.DTO.ProgressRequest;
import com.fileupload.DTO.ResourceProgressDTO;
import com.fileupload.DTO.UpdateNewUsersFromBatchDTO;
import com.fileupload.DTO.UserCourseProgressDTO;
import com.fileupload.DTO.UserProgressDTO;
import com.fileupload.DTO.UserResourceProgressDTO;
import com.fileupload.DTO.UserTopicProgressDTO;
import com.fileupload.DTO.UserTopicRequestDTO;
import com.fileupload.Exception.ProgressExceptions.ResourceIdNotFoundException;
import com.fileupload.service.ProgressService.UserProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user-progress")
@CrossOrigin("*")
@Tag(name = "user Progress", description = "Operations related to user progress")

public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;

    // gives the overall progress of the user
    @Operation(summary = "gives the overall progress of the user")
    @GetMapping("/{userId}/batch/{batchId}")
    public ResponseEntity<UserProgressDTO> calculateOverallProgress(@PathVariable long userId,
            @PathVariable long batchId) {
        UserProgressDTO progress = userProgressService.calculateOverallProgressForUser(userId, batchId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // gives the course progress of the user in a particular course
    @Operation(summary = "gives the course progress of the user in a particular course")
    @GetMapping("/{userId}/batch/{batchId}/course/{courseId}")
    public ResponseEntity<UserCourseProgressDTO> calculateOverallCourseProgress(@PathVariable long userId,
            @PathVariable long courseId, @PathVariable long batchId) {
        UserCourseProgressDTO progress = userProgressService.calculateCourseProgressForUser(userId, batchId, courseId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // gives the progress of the user in a particular topic
    @Operation(summary = "gives the progress of the user in a particular topic")
    @GetMapping("/{userId}/batch/{batchId}/topic/{topicId}")
    public ResponseEntity<UserTopicProgressDTO> calculateOverallTopicProgress(@PathVariable long userId,
            @PathVariable long topicId, @PathVariable long batchId) {
        UserTopicProgressDTO progress = userProgressService.calculateUserTopicProgress(userId, batchId, topicId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // gives the progress of a user in a particular resourse
    @Operation(summary = "gives the progress of a user in a particular resourse")
    @GetMapping("/{userId}/resource/{resourceId}")
    public ResponseEntity<UserResourceProgressDTO> calculateResourceProgress(@PathVariable int resourceId,
            @PathVariable long userId) {
        UserResourceProgressDTO progress = userProgressService.calculateResourceProgressForUser(userId, resourceId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // gives the progress of a user in the courses and the topic progress within
    // that course
    @Operation(summary = "gives the progress of a user in the courses and the topic progress within that course")
    @PostMapping("/course-progress")
    public ProgressDTO getUserProgress(@RequestBody ProgressRequest request) {
        return userProgressService.getUserProgress(request.getUserId(), request.getBatchId(),
                request.getCourseIds());
    }

    // to update the resource completion percentage of the user
    @Operation(summary = "Update the resource completion percentage of the user")
    @PatchMapping("/{userId}/resource/{resourceId}/update/{progress}")
    public ResponseEntity<?> updateProgress(@PathVariable long userId, @PathVariable long resourceId,
            @PathVariable double progress) {
        try {
            userProgressService.updateProgress(userId, progress, resourceId);
            return ResponseEntity.ok().build();
        } catch (ResourceIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Resource with ID " + resourceId + " not found.");
        }
    }

    @Operation(summary = "gives list of resource progress for given list of topic ids")
    @PostMapping("/resource-progress")
    public List<ResourceProgressDTO> findProgressByUserIdAndTopics(@RequestBody UserTopicRequestDTO userTopicRequest) {
        Long userId = userTopicRequest.getUserId();
        List<Long> topicIds = userTopicRequest.getTopicIds();
        return userProgressService.findProgressByUserIdAndTopics(userId, userTopicRequest.getBatchId(), topicIds);
    }

    @PostMapping("/update-progress")
    public ResponseEntity<?> updateProgressForNewUsers(@RequestBody UpdateNewUsersFromBatchDTO newUsers) {
        userProgressService.setProgressForNewUsers(newUsers.getUserIds(), newUsers.getBatchId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-progress")
    public ResponseEntity<?> deleteProgressForUsers(@RequestBody DeleteProgressOfUsersDTO deleteUsers){
        userProgressService.deleteProgressOfUsers(deleteUsers.getUserIds(),deleteUsers.getBatchId());
        return ResponseEntity.ok().build();
    }

}
