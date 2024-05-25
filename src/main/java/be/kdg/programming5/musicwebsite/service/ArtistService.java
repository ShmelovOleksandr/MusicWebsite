package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;

import java.util.List;

public interface ArtistService extends GeneralService<Artist, Integer> {
    Artist getArtistByName(String name);
    List<Artist> getAllByNamePart(String namePart);
    List<Artist> getAllByMinListeners(Long minListeners);
    List<Artist> getAllByNamePartAndMinListeners(String namePart, Long minListeners);
}
