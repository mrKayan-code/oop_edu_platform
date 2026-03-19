package model.task.repository;

import model.task.Task;

public class TaskWithReposytory extends Task {
    private String repositoryLink;

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

}
