package com.example.taskTracker.controller;

import com.example.taskTracker.model.Task;
import com.example.taskTracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Controller", description = "API для управления задачами")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping
    @Operation(summary = "Получить все задачи", description = "Возвращает список всех задач с вложенными сущностями автора, исполнителя и наблюдателей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public Flux<Task> getAllTasks() {
        return taskService.findAllTasks();
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping("/{id}")
    @Operation(summary = "Получить задачу по ID", description = "Возвращает задачу с вложенными сущностями автора, исполнителя и наблюдателей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public Mono<Task> getTaskById(
            @Parameter(description = "ID задачи", required = true, example = "64fca776e9a3a7")
            @PathVariable String id) {
        return taskService.findTaskById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @PostMapping
    @Operation(summary = "Создать задачу", description = "Создает новую задачу")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public Mono<Task> createTask(
            @RequestBody(
                    description = "Данные для создания задачи",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                    {
                      "name": "Implement Swagger",
                      "description": "Add detailed Swagger documentation",
                      "status": "TODO",
                      "authorId": "12345",
                      "assigneeId": "67890",
                      "observerIds": ["11111", "22222"]
                    }
                    """
                            )
                    )
            )
             Task task) {
        return taskService.createTask(task);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @PutMapping("/{id}")
    @Operation(summary = "Обновить задачу", description = "Обновляет информацию о задаче на основе ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public Mono<Task> updateTask(
            @Parameter(description = "ID задачи", required = true, example = "64fca776e9a3a7")
            @PathVariable String id,
            @RequestBody(
                    description = "Обновленные данные задачи",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                    {
                      "name": "Update Swagger",
                      "description": "Update Swagger documentation examples",
                      "status": "IN_PROGRESS",
                      "authorId": "12345",
                      "assigneeId": "67890",
                      "observerIds": ["11111", "22222"]
                    }
                    """
                            )
                    )
            )
             Task task) {
        return taskService.updateTask(id, task);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить задачу", description = "Удаляет задачу на основе ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public Mono<Void> deleteTask(
            @Parameter(description = "ID задачи", required = true, example = "64fca776e9a3a7")
            @PathVariable String id) {
        return taskService.deleteTask(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @PatchMapping("/{id}/add-observer/{observerId}")
    @Operation(summary = "Добавить наблюдателя в задачу", description = "Добавляет наблюдателя в список наблюдателей задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Наблюдатель успешно добавлен"),
            @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public Mono<Task> addObserver(
            @Parameter(description = "ID задачи", required = true, example = "64fca776e9a3a7")
            @PathVariable String id,
            @Parameter(description = "ID наблюдателя", required = true, example = "11111")
            @PathVariable String observerId) {
        return taskService.addObserver(id, observerId);
    }
}
