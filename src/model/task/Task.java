package model.task;

import model.topic.Topic;
import java.util.UUID;

import interfaces.Identifiable;
import interfaces.Named;

public abstract class Task implements Named, Identifiable {
    private final UUID id;
    private String name;
    private String taskText;
    private String example;
    private Topic godTopic;
    

    public Task() {
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getTaskText() {
        return taskText;
    }

    public String getExample() {
        return example;
    }

    public Topic getGodTopic() {
        return godTopic;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setGodTopic(Topic godTopic) {
        this.godTopic = godTopic;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}