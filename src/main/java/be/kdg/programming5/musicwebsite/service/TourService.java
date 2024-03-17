package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Tour;

import java.util.List;

public interface TourService extends GeneralService<Tour, Integer>{
    List<Tour> getAll(String artistName);

    void deleteAllByArtistId(int artistId);

    List<Tour> getAllByArtistId(int id);
}
