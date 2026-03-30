package service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import model.course.Course;
import model.task.Task;
import model.topic.Module;
import model.topic.Section;
import model.topic.Topic;
import repository.TaskRepository;
import repository.TopicRepository;

public class TopicService {
    private final TopicRepository topicRepository;
    private final TaskRepository taskRepository;

    public TopicService(TopicRepository topicRepository, TaskRepository taskRepository) {
        this.topicRepository = topicRepository;
        this.taskRepository = taskRepository;
    }

    public Module createModule(String name) {
        Module topic = new Module(name);

        return (Module) topicRepository.create(topic);
    }

    public Section createSection(String name) {
        Section topic = new Section(name);

        return (Section) topicRepository.create(topic);
    }

    public boolean deleteTopic(UUID topicId) {
        return topicRepository.delete(topicId);
    }

    public Task addTaskToTopic(UUID topicId, UUID taskId) {
        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Тема не найден"));

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Таска не найдена"));

        return topic.addTask(task);
    }

    public Task removeTaskFromTopic(UUID topicId, UUID taskId) {
        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Тема не найден"));

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Таска не найдена"));

        return topic.removeTask(task);
    }

    public List<Topic> getAllTopics() {
        return Collections.unmodifiableList(topicRepository.findAll());
    }

    public void updateTopicName(UUID topicId, String name) {
        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя курса некорректное");
        }
        
        topic.setName(name);

        topicRepository.update(topicId, topic);
    }
}
