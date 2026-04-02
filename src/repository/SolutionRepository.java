package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import model.solution.Solution;

public class SolutionRepository {
    private final Map<UUID, Solution> database = new HashMap<>();

    public Solution create(Solution solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Решение некорректно");
        }

        database.put(solution.getId(), solution);

        return solution;
    }

    public Optional<Solution> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    public List<Solution> findAll() {
        return new ArrayList<>(database.values());
    }

    public boolean delete(UUID id) {
        return database.remove(id) != null;
    }
}
