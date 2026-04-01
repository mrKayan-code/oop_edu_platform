package model.topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import interfaces.ContainerOfTopics;

public class Module extends Topic implements ContainerOfTopics {
    private List<Topic> topics;

    public Module(String name) {
        super("Модуль "  + name);
        this.topics = new ArrayList<>();
    }

    @Override
    public void setName(String name) {
        super.setName("Модуль " + name);
    }
    
    public List<Topic> getTopics() {
        return Collections.unmodifiableList(topics);
    } 

    public void addTopic(Topic topic) {
        if (topic != null && !topics.contains(topic)) {
            topics.add(topic);
            
            if (topic.getGodCourse() != null && topic.getGodCourse() != this.getGodCourse()) {
                topic.getGodCourse().removeTopic(topic);
            }
            topic.setGodModule(this);
        }
    }

    public Topic removeTopic(Topic topic) {
        if (topic != null) {
            topics.remove(topic);

            topic.setGodModule(null);

            return topic;
        }

        return null;
    }

}
