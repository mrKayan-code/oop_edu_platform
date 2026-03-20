package model.topic;

import java.util.List;

import model.Course.Course;

public class Module extends Topic {
    private List<Topic> topics;

    public Module(Course godCourse, Module godModule) {
        super(godCourse, godModule);
    }

    public List<Topic> getTopics() {
        return topics;
    } 

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
    }

}
