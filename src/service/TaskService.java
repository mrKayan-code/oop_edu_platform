package service;

import java.util.UUID;

import model.task.Task;
import model.task.algorithmic.AlgorithmicTask;
import model.task.algorithmic.ProgrammingLang;
import model.task.quiz.QuizTask;
import model.task.with_repository.TaskWithRepository;
import model.topic.Topic;
import repository.TaskRepository;
import repository.TopicRepository;

import java.util.Collections;
import java.util.List;

public class TaskService {
    private final TaskRepository taskRepository;
    private final TopicRepository topicRepository;

    public TaskService(TaskRepository taskRepository, TopicRepository topicRepository) {
        this.taskRepository = taskRepository;
        this.topicRepository = topicRepository;
    }

    public TaskWithRepository createTaskWithRepository(String name, String taskText, String example, String repositoryLink) {
        TaskWithRepository task = new TaskWithRepository(name);

        task.setTaskText(taskText);
        task.setExample(example);
        task.setRepositoryLink(repositoryLink);
        
        return (TaskWithRepository) taskRepository.create(task);
    }

    public AlgorithmicTask createAlgorithmicTask(String name, String taskText, String example, List<ProgrammingLang> ProgrammingLangs) {
        AlgorithmicTask task = new AlgorithmicTask(name);

        task.setTaskText(taskText);
        task.setExample(example);

        for (ProgrammingLang programmingLang : ProgrammingLangs) {
            task.addProgrammingLang(programmingLang);
        }

        return (AlgorithmicTask) taskRepository.create(task);
    }

    public QuizTask createQuizTask(String name) {
        QuizTask task = new QuizTask(name);

        return (QuizTask) taskRepository.create(task);
    }

    public boolean deleteTask(UUID taskId) {
        // Task task = taskRepository.findById(taskId).orElse(null);

        // if (task == null) return false;

        return taskRepository.delete(taskId);
    }

    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(taskRepository.findAll());
    }

    public void updateTaskName(UUID taskId, String newName) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));

        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Имя задачи некорректное");
        }
        
        task.setName(newName);

        taskRepository.update(taskId, task);
    }

    public void updateTaskText(UUID taskId, String newTaskText) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));

        
        task.setTaskText(newTaskText);

        taskRepository.update(taskId, task);
    }

    public void updateTaskExample(UUID taskId, String newExample) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));

        
        task.setExample(newExample);

        taskRepository.update(taskId, task);
    }
}
