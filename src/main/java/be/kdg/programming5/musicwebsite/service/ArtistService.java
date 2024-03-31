package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;

import java.util.List;

public interface ArtistService extends GeneralService<Artist, Integer> {
    Artist getArtistByName(String name);
    List<Artist> getAll(String name);
    List<Artist> getAll(Long minListeners);
    List<Artist> getAll(String namePart, Long minListeners);
}
