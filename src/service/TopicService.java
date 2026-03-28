package service;

import model.topic.Section;
import model.topic.Topic;
import repository.CourseRepository;
import repository.TopicRepository;

public class TopicService {
    private final TopicRepository topicRepo;
    private final CourseRepository courseRepo;

    public TopicService(TopicRepository topicRepo, CourseRepository courseRepo) {
        this.topicRepo = topicRepo;
        this.courseRepo = courseRepo;
    }

    public Module createModule() {}

    public Section createSection() {}
}
