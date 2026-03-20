package model.task;

import model.topic.Topic;

public abstract class Task {    
    private String taskText;    
    private String example;
    private Topic godTopic;

    public Task(Topic godTopic) {
        this.godTopic = godTopic;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


    
}
