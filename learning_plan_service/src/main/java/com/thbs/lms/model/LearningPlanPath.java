package com.thbs.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LearningPlanPath")
public class LearningPlanPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pathID;

    @ManyToOne
    @JoinColumn(name = "learningPlanID")
    private LearningPlan learningPlan;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    private String type;

    private String trainer;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;
}