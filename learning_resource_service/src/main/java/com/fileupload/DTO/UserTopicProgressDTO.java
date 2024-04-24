package com.fileupload.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTopicProgressDTO {
    private long userId;
    private double topicProgress;
    
}