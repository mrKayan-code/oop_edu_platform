package model.task.with_repository;

import model.task.Task;
import model.topic.Topic;

public class TaskWithRepository extends Task {
    private String repositoryLink;

    public TaskWithRepository(Topic godTopic) {
        super(godTopic);
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

}
