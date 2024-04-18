package com.thbs.lms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearningPlanDTO {
    private Long batchId;
    private Long learningPlanId;
    private List<PathDTO> path;
}
