package service;

import java.util.UUID;

import model.task.Task;
import model.task.algorithmic.AlgorithmicTask;
import model.task.quiz.QuizTask;
import model.task.with_repository.TaskWithRepository;
import model.topic.Topic;
import repository.TaskRepository;
import repository.TopicRepository;

public class TaskService {
    private final TaskRepository taskRepo;
    private final TopicRepository topicRepo;

    public TaskService(TaskRepository taskRepo, TopicRepository topicRepo) {
        this.taskRepo = taskRepo;
        this.topicRepo = topicRepo;
    }

    public TaskWithRepository createTaskWithRepository() {}

    public AlgorithmicTask createAlgorithmicTask() {}

    public QuizTask createQuizTask() {}
}
