package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Song;

import java.util.List;

public interface SongService extends GeneralService<Song, Integer> {
    List<Song> getAllByName(String name);

    List<Song> getAllByArtistId(int artistId);

    void deleteAllWithoutArtists();
}
