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

    public QuizTask createQuizTask(String name, String taskText, String example) {
        QuizTask task = new QuizTask(name);

        task.setTaskText(taskText);
        task.setExample(example);

        return (QuizTask) taskRepository.create(task);
    }

    public boolean deleteTask(UUID taskId) {
        // Task task = taskRepository.findById(taskId).orElse(null);

        // if (task == null) return false;

        return taskRepository.delete(taskId);
    }
}
