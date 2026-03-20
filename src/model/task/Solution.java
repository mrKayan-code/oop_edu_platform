package model.task;

public class Solution {
    private Task task;
    private String solutionText;
    
    public Solution(Task task) {
        this.task = task;
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
}
