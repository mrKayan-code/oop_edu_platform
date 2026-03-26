package repository;

import java.util.Map;
import java.util.UUID;

import interfaces.Identifiable;
import interfaces.Named;

import java.util.List;

public abstract class Repository<T extends Named & Identifiable> {
    Map<UUID, T> database;

    public abstract List<T> getAll();

    public abstract T getById(UUID id);

    public abstract UUID create(T instance);

    public abstract void delete(UUID id);
}
