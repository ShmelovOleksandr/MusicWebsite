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
    public List<Artist> getAllByNamePart(String namePart) {
        return artistJpaRepository.findAllByNameContainingIgnoreCase(namePart);
    }

    @Override
    public List<Artist> getAllByMinListeners(Long minListeners) {
        return artistJpaRepository.findAllByListenersAfter(minListeners);
    }

    @Override
    public List<Artist> getAllByNamePartAndMinListeners(String namePart, Long minListeners) {
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
    public List<Artist> saveAll(Iterable<Artist> artists) {
        return artistJpaRepository.saveAll(artists);
    }

    @Override
    @Transactional
    public Artist update(Integer id, Artist newArtist) {
        Artist savedArtist = artistJpaRepository.findById(id).orElseThrow(
                () -> new ArtistNotFoundException("Cannot update. Artist with given id does not exist.")
        );

        savedArtist.setName(newArtist.getName());
        savedArtist.setBirthDate(newArtist.getBirthDate());
        savedArtist.setListeners(newArtist.getListeners());
        return savedArtist;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        songParticipationService.deleteByArtistId(id);
        tourService.deleteAllByArtistId(id);
        artistJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        artistJpaRepository.deleteAll();
    }
}
