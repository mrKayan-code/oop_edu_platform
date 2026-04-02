package model.solution;

import java.util.UUID;

import interfaces.Identifiable;
import model.task.Task;

public class Solution implements Identifiable {
    private final UUID id;
    private final Task task;
    private String solutionText;
    
    public Solution(Task task) {
        this.task = task;
        this.id = UUID.randomUUID();
    }
    
    public Task getTask() {
        return task;
    }

    public String getSolutionText() {
        return solutionText;
    }
    
    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }

    @Override
    public UUID getId() {
        return id;
    }

    
}
