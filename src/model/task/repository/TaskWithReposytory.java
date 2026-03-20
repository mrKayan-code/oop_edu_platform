package model.task.repository;

import model.task.Task;
import model.topic.Topic;

public class TaskWithReposytory extends Task {
    private String repositoryLink;

    public TaskWithReposytory(Topic godTopic) {
        super(godTopic);
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

}
