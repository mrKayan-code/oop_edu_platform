package repository;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import model.course.Course;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class CourseRepository {
    private final Map<UUID, Course> database = new HashMap<>();

    public Course create(Course course) {        
        if (course == null || course.getName() == null || course.getName().isBlank()) {
            throw new IllegalArgumentException("Имя темы некорректное");
        }

        database.put(course.getId(), course);
        
        return course;
    }

    public Optional<Course> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    public List<Course> findAll() {
        return new ArrayList<>(database.values());
    }

    public boolean update(UUID id, Course newCourse) {
        if (!database.containsKey(id) || newCourse == null) {
            return false;
        }
        Course existing = database.get(id);

        existing.mergeFrom(newCourse);

        return true;
    }

    public boolean delete(UUID id) {
        return database.remove(id) != null;
    }

}