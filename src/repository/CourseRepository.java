package repository;

import model.Course.Course;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CourseRepository {
    private final Map<UUID, Course> database = new ConcurrentHashMap<>();

    public Course create(String name) {        
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя курса некорректное");
        }
        
        Course course = new Course(name);
        
        database.put(course.getId(), course);
        
        return course;
    }

    public Optional<Course> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    public List<Course> findAll() {
        return new ArrayList<>(database.values());
    }

    public boolean update(UUID id, String newName) {
        Course course = database.get(id);
        if (course != null && newName != null && !newName.isBlank()) {
            course.setName(newName);
            return true;
        }
        return false;
    }

    public boolean delete(UUID id) {
        return database.remove(id) != null;
    }

}