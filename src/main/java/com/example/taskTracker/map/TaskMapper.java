package com.example.taskTracker.map;

import com.example.taskTracker.dto.TaskDto;
import com.example.taskTracker.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "observers", ignore = true)
    Task toTask(TaskDto taskDto);

    TaskDto toTaskDto(Task task);
}
