package service;

import model.course.Course;
import model.topic.Topic;
import repository.CourseRepository;
import repository.TopicRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CourseService {
    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;

    public CourseService(CourseRepository courseRepository, TopicRepository topicRepository) {
        this.courseRepository = courseRepository;
        this.topicRepository = topicRepository;
    }

    public Course createCourse(String name) {
        Course newCourse = new Course(name);

        return courseRepository.create(newCourse);
    }
    
    public boolean deleteCourseWithTopics(UUID courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        
        if (course == null) return false;

        for (Topic topic : course.getTopics()) {
            topicRepository.delete(topic.getId()); // композиция
        }

        return courseRepository.delete(courseId);
    }

    public Topic addTopicToCourse(UUID courseId, UUID topicId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));

        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Тема не найдена"));

        
        return course.addTopic(topic);
    }

    public Topic removeTopicFromCourse(UUID courseId, UUID topicId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));

        Topic topic = topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Тема не найдена"));

        topicRepository.delete(topicId); // композиция (топик не существует без своего год курса)

        return course.removeTopic(topic); // но я его верну но уже без годкурса
    }

    public List<Course> getAllCourses() {
        return Collections.unmodifiableList(courseRepository.findAll());
    }

    public void updateCourseName(UUID courseId, String name) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя курса некорректное");
        }
        
        course.setName(name);

        courseRepository.update(courseId, course);
    }
    
}