package model.task.with_repository;

import model.task.Task;

public class TaskWithRepository extends Task {
    private String repositoryLink;

    public TaskWithRepository(String name) {
        super(name);
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

    @Override
    public void mergeFrom(Task other) {
        super.mergeFrom(other);
        
        if (!(other instanceof TaskWithRepository)) {
            return;
        }

        TaskWithRepository repoTask = (TaskWithRepository) other;

        this.repositoryLink = repoTask.repositoryLink;
    }

}
