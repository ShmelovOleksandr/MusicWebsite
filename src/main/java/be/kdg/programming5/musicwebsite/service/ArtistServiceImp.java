package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.repository.ArtistJpaRepository;
import be.kdg.programming5.musicwebsite.util.exception.ArtistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ArtistServiceImp implements ArtistService {
    private final ArtistJpaRepository artistJpaRepository;
    private final SongParticipationService songParticipationService;
    private final TourService tourService;

    @Autowired
    public ArtistServiceImp(ArtistJpaRepository artistJpaRepository, SongParticipationService songParticipationService, TourService tourService) {
        this.artistJpaRepository = artistJpaRepository;
        this.songParticipationService = songParticipationService;
        this.tourService = tourService;
    }

    @Override
    public List<Artist> getAll() {
        return artistJpaRepository.findAll();
    }

    @Override
    public List<Artist> getAll(String namePart) {
        if(namePart == null || namePart.isBlank())
            return getAll();

        return artistJpaRepository.findAllByNameContainingIgnoreCase(namePart);
    }

    @Override
    public List<Artist> getAll(Long minListeners) {
        return artistJpaRepository.findAllByListenersAfter(minListeners != null ? minListeners : 0L);
    }

    @Override
    public List<Artist> getAll(String namePart, Long minListeners) {
        if(namePart == null || namePart.isBlank())
            return getAll(minListeners);

        if(minListeners == null || minListeners == 0L)
            return getAll(namePart);

        return artistJpaRepository.findAllByListenersAfterAndNameContainingIgnoreCase(minListeners, namePart);
    }

    @Override
    public Artist getOne(Integer id) {
        return artistJpaRepository.findById(id).orElseThrow(
                () -> new ArtistNotFoundException("No artists with given id have been found.")
        );
    }

    @Override
    public Artist getArtistByName(String name) {
        return artistJpaRepository.findByName(name).orElseThrow(
                () -> new ArtistNotFoundException("No artists with given name have been found.")
        );
    }

    @Override
    @Transactional
    public Artist save(Artist artist) {
        return artistJpaRepository.save(artist);
    }

    @Override
    @Transactional
    public Artist update(Integer id, Artist artist) {
        if(!artistJpaRepository.existsById(id))
            throw new ArtistNotFoundException("Cannot update. Artist with given id does not exist.");

        artist.setId(id);
        return artistJpaRepository.save(artist);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        songParticipationService.deleteByArtistId(id);
        tourService.deleteAllByArtistId(id);
        artistJpaRepository.deleteById(id);
    }
}
