package be.kdg.programming5.musicwebsite.services;

import java.util.List;

public interface GeneralService<T, ID> {
    List<T> getAll();
    T getOne(ID key);
    T save(T object);
    T update(ID key, T object);
    void delete(ID key);
}
