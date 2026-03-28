package service;

import model.Course.Course;
import model.topic.Topic;
import model.topic.TopicImpl;
import repository.CourseRepository;
import repository.TopicRepository;
import java.util.UUID;

public class CourseService {
    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;

    public CourseService(CourseRepository courseRepo, TopicRepository topicRepo) {
        this.courseRepository = courseRepo;
        this.topicRepository = topicRepo;
    }

    public Topic createTopicInCourse(UUID courseId, String topicName) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Курс не найден: " + courseId));

        Topic newTopic = new TopicImpl(topicName);

        topicRepository.create(newTopic);

        course.addTopic(newTopic);

        // courseRepository.update(courseId, course);

        return newTopic;
    }

    public void removeTopicFromCourse(UUID courseId, UUID topicId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));

        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Тема не найдена"));

        course.removeTopic(topic);

        courseRepository.update(courseId, course);

        topicRepository.delete(topicId); // композиция
    }

    public boolean deleteCourseWithTopics(UUID courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        
        if (course == null) return false;

        for (Topic topic : course.getTopics()) {
            topicRepository.delete(topic.getId());
        }

        return courseRepository.delete(courseId);
    }
}