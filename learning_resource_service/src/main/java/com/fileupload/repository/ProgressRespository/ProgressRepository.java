package com.fileupload.repository.ProgressRespository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fileupload.model.ProgressModel.Progress;
import com.fileupload.model.LearningResourceModel.Resource;



@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

     // find overall progress of user
     @Query(value = " SELECT  " +
               " cp.user_id,  " +
               " AVG(cp.course_progress) AS overall_progress " +
               " FROM ( " +
               "  SELECT  " +
               "     lr.course_id,  " +
               "     p.user_id,  " +
               "     AVG(p.completion_percentage) AS course_progress " +
               "  FROM  " +
               "     progress p  " +
               "  JOIN  " +
               "     resource r ON p.resource_id = r.resource_id  " +
               "  JOIN  " +
               "     learning_resource lr ON r.learning_resource_id = lr.learning_resource_id  " +
               "  WHERE  " +
               "     p.user_id = :userId " +
               "     AND lr.batch_id = :batchId " +
               " GROUP BY  " +
               "    lr.course_id,  " +
               "     p.user_id " +
               "  ) AS cp " +
               " GROUP BY  " +
               " cp.user_id;", nativeQuery = true)

     List<Object[]> findOverallProgressForUser(Long userId, Long batchId);

     // ------------------------------------------changed---------------------------------------
     // Fetches the overall course progress of a user in a course
     @Query(value = "SELECT tp.course_id, tp.user_id, AVG(tp.topic_progress) AS course_progress " +
               " FROM ( " +
               "    SELECT lr.course_id, lr.topic_id, p.user_id, AVG(p.completion_percentage) AS topic_progress " +
               "    FROM progress p " +
               "    JOIN resource r ON p.resource_id = r.resource_id " +
               "    JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
               "    WHERE lr.batch_id = :batchId " +
               "      AND p.user_id = :userId " +
               "      AND lr.course_id = :courseId " +
               "    GROUP BY lr.course_id, lr.topic_id, p.user_id " +
               " ) AS tp " +
               " GROUP BY tp.course_id;", nativeQuery = true)
     List<Object[]> findCourseProgressByUserAndCourse(Long userId, long batchId, long courseId);

     // ---------------------------------------changed----------------------------------------------------
     // Fetches the overall topic progress of a user in a particular topic
     @Query(value = " SELECT " +
               " p.user_id, " +
               " lr.topic_id, " +
               " AVG(p.completion_percentage) AS progress " +
               " FROM " +
               " progress p " +
               " JOIN " +
               " resource r ON p.resource_id = r.resource_id " +
               " JOIN " +
               " learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
               " WHERE " +
               " p.user_id = :userId " +
               " AND lr.topic_id = :topicId " +
               " AND lr.batch_id = :batchId " +
               " GROUP BY " +
               " p.user_id, " +
               " lr.topic_id;", nativeQuery = true)
     List<Object[]> findTopicProgressByTopicAndUserId(Long userId, long batchId, long topicId);

     // -----------------------------changed----------------------------------------------------
     @Query(value = "SELECT tp.course_id, tp.topic_id, tp.user_id, AVG(tp.topic_progress) AS topic_progress " +
               " FROM ( " +
               "    SELECT lr.course_id, lr.topic_id, p.user_id, AVG(p.completion_percentage) AS topic_progress " +
               "    FROM progress p " +
               "    JOIN resource r ON p.resource_id = r.resource_id " +
               "    JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
               "    WHERE lr.batch_id = :batchId " +
               "      AND p.user_id = :userId " +
               "      AND lr.course_id IN :courseIds " +
               "    GROUP BY lr.course_id, lr.topic_id, p.user_id " +
               " ) AS tp " +
               " GROUP BY tp.course_id, tp.topic_id, tp.user_id", nativeQuery = true)
     List<Object[]> getUserProgress(Long userId, Long batchId, List<Long> courseIds);

     // -----------------------------changed---------------------------------------------------
     // Fetches the progress of a user in a particular resource
     Progress findByUserIdAndResourceId(long userId, long resourceId);

     @Query(value = " SELECT " +
               "  p.resource_id,  " +
               "  p.completion_percentage,  " +
               "  lr.topic_id  " +
               "  FROM " +
               "   progress p " +
               "  JOIN  " +
               "  resource " +
               " r ON p.resource_id= " +
               "  r.resource_id  " +
               "  JOIN " +
               " learning_resource lr " +
               " ON r.learning_resource_id= " +
               " lr.learning_resource_id  " +
               "  WHERE " +

               "  lr.topic_id IN :topicIds  " +
               "  AND p.user_id = :userId  " +
               "  AND lr.batch_id = :batchId  " +
               "  ORDER BY  " +
               "  lr.topic_id ASC;", nativeQuery = true)
     List<Object[]> findProgressByUserIdAndTopics(Long userId, Long batchId, List<Long> topicIds);
// -----------------------------changed-----------------------------------------
     List<Resource> findByBatchId(Long batchId);

     List<Progress> findByUserIdAndBatchId(long userId, long batchId);

     @Query(value=" SELECT r.resource_id "+
     " FROM learning_resource lr "+
     " JOIN resource r ON lr.learning_resource_id = r.learning_resource_id "+
     " WHERE lr.batch_id = :batchId",nativeQuery=true)
     List<Long> findResourceIdsByBatchId(Long batchId);

     @Query(value = "select user_id from progress where batch_id = :batchId",nativeQuery = true)
     List<Long> findAllUsers(Long batchId);

     void deleteByUserIdAndBatchId(Long userId, Long batchId);

     @Transactional
     @Modifying
     @Query(value = "DELETE FROM progress p WHERE p.resource_id = :resourceId",nativeQuery = true)
     void deleteByResourceId(long resourceId);
}