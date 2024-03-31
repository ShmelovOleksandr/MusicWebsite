package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ArtistService extends GeneralService<Artist, Integer>, UserDetailsService {
    Artist getArtistByName(String name);
    List<Artist> getAll(String name);
    List<Artist> getAll(Long minListeners);
    List<Artist> getAll(String namePart, Long minListeners);
    List<Artist> getAllFetched(String namePart, Long minListeners);
}
