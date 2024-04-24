package com.fileupload.repository.ProgressRespository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fileupload.model.ProgressModel.Progress;

@Repository
public interface BatchProgressRepository extends JpaRepository<Progress, Long> {

        // Fetches the overall_progress of all the users in a batch
        @Query(value = "SELECT user_id, AVG(course_progress) AS overall_progress" +
                        " FROM (" +
                        "    SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress" +
                        "    FROM progress p" +
                        "    JOIN resource r ON p.resource_id = r.resource_id" +
                        "    JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id" +
                        "    WHERE p.batch_id = :batchId" +
                        "    GROUP BY lr.course_id, p.user_id" +
                        ") AS cp" +
                        "GROUP BY user_id;", nativeQuery = true)
        List<Object[]> findOvreallProgressOfUsersInABatch(int batchId);

        // Fetches the overall progress of a batch
        @Query(value = "SELECT AVG(overall_progress) AS average_overall_progress " +
                        " FROM ( " +
                        " SELECT user_id, AVG(course_progress) AS overall_progress  " +
                        " FROM ( " +
                        "     SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress  " +
                        "     FROM progress p  " +
                        "     JOIN resource r ON p.resource_id = r.resource_id  " +
                        "     JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id  " +
                        "     WHERE p.batch_id = :batchId " +
                        "     GROUP BY lr.course_id, p.user_id " +
                        " ) AS cp  " +
                        " GROUP BY user_id " +
                        " ) AS user_progress", nativeQuery = true)
        List<Object[]> findOverallBatchProgress(long batchId);

        // Fetches all the batches and their overall progress
        @Query(value = "SELECT batch_id, AVG(overall_progress) AS batch_completion_progress " +
                        "FROM (" +
                        "    SELECT p.batch_id, p.user_id, AVG(p.completion_percentage) AS overall_progress " +
                        "    FROM progress p " +
                        "    JOIN resource r ON p.resource_id = r.resource_id " +
                        "    JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
                        "    GROUP BY p.batch_id, p.user_id " +
                        ") AS batch_progress " +
                        "GROUP BY batch_id", nativeQuery = true)
        List<Object[]> findBatchwiseProgress();

        // Fetches the overall progress of all the users in a batch
        @Query(value = "SELECT user_id, AVG(course_progress) AS overall_progress  " +
                        "FROM ( " +
                        " SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress  " +
                        " FROM progress p  " +
                        " JOIN resource r ON p.resource_id = r.resource_id  " +
                        " JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id  " +
                        "  GROUP BY lr.course_id, p.user_id " +
                        " ) AS cp  " +
                        " WHERE cp.course_id IN ( " +
                        "     SELECT lr.course_id  " +
                        "     FROM progress p  " +
                        "     JOIN resource r ON p.resource_id = r.resource_id  " +
                        "     JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id  " +
                        "     WHERE p.batch_id = :batchId " +
                        " ) " +
                        " GROUP BY user_id", nativeQuery = true)
        List<Object[]> findOverallBatchProgressAllUsers(Long batchId);

        @Query(value = "SELECT user_id, AVG(course_progress) AS overall_progress " +
                        "FROM (" +
                        "   SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress " +
                        "   FROM progress p " +
                        "   JOIN resource r ON p.resource_id = r.resource_id " +
                        "   JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
                        "   WHERE p.user_id in :userIds " +
                        "   GROUP BY lr.course_id, p.user_id " +
                        ") AS cp " +
                        "GROUP BY user_id", nativeQuery = true)
        List<Object[]> findUserProgressInBu(List<Long> userIds);

        @Query(value = "SELECT AVG(course_progress) AS overall_progress " +
                        "FROM ( " +
                        "    SELECT p.user_id, (SUM(p.completion_percentage) / COUNT(p.user_id)) AS course_progress " +
                        "    FROM progress p " +
                        "    JOIN resource r ON p.resource_id = r.resource_id " +
                        "    JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
                        "    WHERE p.user_id IN :userIds " +
                        "    GROUP BY p.user_id " +
                        ") AS cp;", nativeQuery = true)
        List<Object[]> findOverallBUProgress(List<Long> userIds);

        @Query(value = " SELECT  " +
                        " tp.course_id,  " +
                        " tp.user_id,  " +
                        "  AVG(tp.topic_progress) AS course_progress  " +
                        " FROM ( " +
                        "  SELECT  " +
                        "     lr.course_id,  " +
                        "     lr.topic_id,  " +
                        "      p.user_id,  " +
                        "      AVG(p.completion_percentage) AS topic_progress  " +
                        "  FROM  " +
                        "      progress p " +
                        "  JOIN  " +
                        "      resource r ON p.resource_id = r.resource_id  " +
                        "  JOIN  " +
                        "      learning_resource lr ON r.learning_resource_id = lr.learning_resource_id  " +
                        "  WHERE  " +
                        "      p.user_id IN :userIds  " +
                        "      AND lr.course_id = :courseId  " +
                        "      AND lr.batch_id = :batchId   " +
                        "  GROUP BY  " +
                        "      lr.course_id,  " +
                        "      lr.topic_id,  " +
                        "      p.user_id " +
                        " ) AS tp  " +
                        " GROUP BY  " +
                        "  tp.course_id,  " +
                        "  tp.user_id;", nativeQuery = true)
        List<Object[]> findCourseProgressByUserAndCourseInBatch(List<Long> userIds, long batchId, long courseId);

        // Fetches all the batch id's from the progress entity
        @Query(value = "SELECT DISTINCT batch_id FROM progress", nativeQuery = true)
        List<Object[]> findAllBatches();

        // Fetches all the user id's from the progress entity
        @Query(value = "SELECT DISTINCT user_id FROM progress where batch_id= :batchId ", nativeQuery = true)
        List<Object[]> findAllUsers(long batchId);

        List<Progress> findAllByUserId(Long userId);

}
