package com.thbs.lms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathDTO {
    private Long learningPlanPathId;
    private String type;
    private String trainer;
    private Date startDate;
    private Date endDate;
    private CourseDTO course;
}
