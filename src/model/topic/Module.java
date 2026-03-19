package model.topic;

import java.util.List;

public class Module extends Topic {
    private List<Topic> topics;

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
