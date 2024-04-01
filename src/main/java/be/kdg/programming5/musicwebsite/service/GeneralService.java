package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;

import java.util.List;

public interface GeneralService<T, ID> {
    List<T> getAll();
    T getOne(ID key);
    T save(T object);
    List<T> saveAll(Iterable<T> objects);
    T update(ID key, T object);
    void delete(ID key);

    void deleteAll();
}
