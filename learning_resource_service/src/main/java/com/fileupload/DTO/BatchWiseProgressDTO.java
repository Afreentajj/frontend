package com.fileupload.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class BatchWiseProgressDTO {
    private long batchId;
    private double batchProgress;

}

