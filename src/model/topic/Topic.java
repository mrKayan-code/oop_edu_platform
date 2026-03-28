package model.topic;

import model.Course.Course;
import model.task.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import interfaces.Identifiable;
import interfaces.Named;
import interfaces.Updatable;

public abstract class Topic implements Named, Identifiable, Updatable<Topic> {
    private final UUID id;
    private String name;
    private final List<Task> tasks;
    private boolean visibility;
    

    private Course godCourse;
    private Module godModule;

    public Topic(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.tasks = new ArrayList<>();
        this.visibility = true;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public boolean getVisibility() {
        return visibility;
    }

    public Course getGodCourse() {
        return godCourse;
    }

    public Module getGodModule() {
        return godModule;
    }

    @Override
    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setGodCourse(Course godCourse) {
        this.godCourse = godCourse;
    }

    public void setGodModule(Module godModule) {
        this.godModule = godModule;
    }

    public void addTask(Task task) {
        if (task != null && !tasks.contains(task)) {
            tasks.add(task);

            task.setGodTopic(this);
        }
    }

    public void removeTask(Task task) {
        if (task != null) {
            tasks.remove(task);
            
            task.setGodTopic(null);
        }
    }

    @Override
    public void mergeFrom(Topic other) {
        this.name = other.name;
        this.visibility = other.visibility;
    }
}