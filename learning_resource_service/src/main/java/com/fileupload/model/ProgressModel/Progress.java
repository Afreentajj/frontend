package com.fileupload.model.ProgressModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long progressId;
    private long userId;
    private long batchId;
    private long resourceId;
    private double completionPercentage;
    public Progress() {
    }

    public Progress(long userId, long resourceId, double completionPercentage) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.completionPercentage = completionPercentage;
    }
    
}