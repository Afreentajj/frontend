package com.fileupload.DTO;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProgressOfUsersDTO {
    private long batchId;
    private List<Long> userIds;
}
