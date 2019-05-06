package de.softinva.multitimer.database;

public interface AppDao<T> {

    void insert(T entity);

    void update(T entity);

    void delete(T entity);
}
