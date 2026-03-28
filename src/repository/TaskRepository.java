package repository;

import model.task.Task;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class TaskRepository {
    private final Map<UUID, Task> database = new HashMap<>();

    public Task create(Task task) {
        if (task == null || task.getName() == null || task.getName().isBlank()) {
            throw new IllegalArgumentException("Имя темы некорректное");
        }
        database.put(task.getId(), task);
        return task;
    }

    public Optional<Task> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(database.values());
    }

    public boolean update(UUID id, Task newTask) {
        if (!database.containsKey(id) || newTask == null) {
            return false;
        }

        Task existing = database.get(id);
        
        existing.mergeFrom(newTask);
        
        return true;
    }

    public boolean delete(UUID id) {
        return database.remove(id) != null;
    }
}