# Task Tracker (Реактивный трекер задач)

Приложение **Task Tracker** — это REST API для управления задачами и пользователями, реализованное с использованием Spring Boot, MongoDB и реактивного программирования. Оно поддерживает создание, обновление и управление задачами, а также позволяет назначать исполнителей и наблюдателей.

## Функциональность

- Управление пользователями:
  - Создание, обновление, удаление.
  - Получение списка пользователей или конкретного пользователя по ID.

- Управление задачами:
  - Создание, обновление, удаление.
  - Получение списка задач с информацией о создателе, исполнителе и наблюдателях.
  - Добавление наблюдателей к задаче.

## Технологии

- **Java 17**
- **Spring Boot 3**
- **Spring WebFlux**
- **MongoDB** (реактивный драйвер)
- **Lombok**
- **MapStruct**
- **Swagger/OpenAPI**

## Требования для запуска

- Установленная **Java 17** или выше.
- Локально установленный **MongoDB** (или подключение к удаленному серверу MongoDB).
- **Gradle** (с настройкой Kotlin DSL).

## Настройка и запуск

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/<ваш-аккаунт>/task-tracker.git
cd task-tracker