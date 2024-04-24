package com.fileupload.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceProgressDTO {

    private Long topicId;
    private Long batchId;
    private Map<Long, Double> ResourceMap = new HashMap<>(); // Changed field name to ResourceMap

    public void addProgress(Long resourceId, Double progress) {
        ResourceMap.put(resourceId, progress);
    }
}