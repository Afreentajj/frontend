package com.fileupload.DTO;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserTopicRequestDTO {
    private Long userId;
    private Long batchId;
    private List<Long> topicIds;

    
}
