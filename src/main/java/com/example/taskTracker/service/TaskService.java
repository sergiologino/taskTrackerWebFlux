package com.example.taskTracker.service;

import com.example.taskTracker.model.Task;
import com.example.taskTracker.model.User;
import com.example.taskTracker.repository.TaskRepository;
import com.example.taskTracker.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import reactor.core.publisher.Mono;
import java.util.HashSet;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public Flux<Task> findAllTasks() {
        return taskRepository.findAll()
                .flatMap(this::enrichTask);
    }

    public Mono<Task> findTaskById(String id) {
        return taskRepository.findById(id)
                .flatMap(this::enrichTask);
    }

    public Mono<Task> createTask(Task task) {
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task);
    }

    public Mono<Task> updateTask(String id, Task task) {
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setName(task.getName());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setStatus(task.getStatus());
                    existingTask.setUpdatedAt(Instant.now());
                    return taskRepository.save(existingTask);
                });
    }

    public Mono<Void> deleteTask(String id) {
        return taskRepository.deleteById(id);
    }

    private Mono<Task> enrichTask(Task task) {
        Mono<User> author = userRepository.findById(task.getAuthorId());
        Mono<User> assignee = userRepository.findById(task.getAssigneeId());
        Flux<User> observers = Flux.fromIterable(task.getObserverIds())
                .flatMap(userRepository::findById);

        return Mono.zip(author, assignee, observers.collectList())
                .map(tuple -> {
                    task.setAuthor(tuple.getT1());
                    task.setAssignee(tuple.getT2());
                    task.setObservers(Set.copyOf(tuple.getT3()));
                    return task;
                });
    }
    public Mono<Task> addObserver(String taskId, String observerId) {
        return taskRepository.findById(taskId)
                .flatMap(task -> {
                    if (task.getObserverIds() == null) {
                        task.setObserverIds(new HashSet<>());
                    }
                    task.getObserverIds().add(observerId);
                    return taskRepository.save(task);
                })
                .flatMap(this::enrichTask); // Обогащаем задачу с вложенными данными
    }
}
