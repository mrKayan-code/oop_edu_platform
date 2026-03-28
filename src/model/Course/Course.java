package model.Course;

import model.topic.Topic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import interfaces.Named;
import interfaces.Updatable;
import interfaces.Identifiable;

public class Course implements Named, Identifiable, Updatable<Course>{
    private final UUID id;
    private String name;
    private final List<Topic> topics;

    public Course(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.topics = new ArrayList<>();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Topic> getTopics() {
        return Collections.unmodifiableList(topics);
    }

    @Override
    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    public Topic addTopic(Topic topic) {
        if (topic != null && !topics.contains(topic)) {
            topics.add(topic);

            topic.setGodCourse(this);

            return topic;
        }

        return null;
    }

    public Topic removeTopic(Topic topic) {
        if (topic != null) {
            topics.remove(topic);

            topic.setGodCourse(null);

            return topic;
        }

        return null;
    }

    @Override
    public void mergeFrom(Course other) {
        this.name = other.name;

        // this.topics = other.topics; при апдейте буду обновлять только аттрибуты
    }
}