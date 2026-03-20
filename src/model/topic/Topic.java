package model.topic;

import java.util.List;
import model.task.Task;
import model.Course.Course;


public abstract class Topic {
    private List<Task> tasks;
    private boolean visibility;

    private Course godCourse;    
    private Module godModule;    

    public Topic(Course godCourse, Module godModule) {
        this.godCourse = godCourse;
        this.godModule = godModule;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTasks(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }


    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
    
}
