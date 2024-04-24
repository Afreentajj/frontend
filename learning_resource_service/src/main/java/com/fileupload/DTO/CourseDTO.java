package com.fileupload.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDTO {
    private long courseId;
    private double courseProgress;
    private List<TopicDTO> topics;
}
