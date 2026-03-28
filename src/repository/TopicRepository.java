package repository;

import model.topic.Topic;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class TopicRepository {
    private final Map<UUID, Topic> database = new HashMap<>();

    public Topic create(Topic topic) {
        if (topic == null || topic.getName() == null || topic.getName().isBlank()) {
            throw new IllegalArgumentException("Имя темы некорректное");
        }
        database.put(topic.getId(), topic);
        return topic;
    }

    public Optional<Topic> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    public List<Topic> findAll() {
        return new ArrayList<>(database.values());
    }

    public boolean update(UUID id, Topic newTopic) {
        if (!database.containsKey(id) || newTopic == null) {
            return false;
        }

        Topic existing = database.get(id);
        
        existing.mergeFrom(newTopic);
        
        return true;
    }

    public boolean delete(UUID id) {
        return database.remove(id) != null;
    }
}