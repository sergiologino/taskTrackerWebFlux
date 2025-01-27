package com.example.taskTracker.dto;

import com.example.taskTracker.model.TaskStatus;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class TaskDto {
    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;
    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;
}

