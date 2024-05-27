package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.SongParticipation;
import be.kdg.programming5.musicwebsite.repository.SongParticipationJpaRepository;
import be.kdg.programming5.musicwebsite.util.exception.SongNotFoundException;
import be.kdg.programming5.musicwebsite.util.exception.SongParticipationNotFound;
import be.kdg.programming5.musicwebsite.util.id.SongParticipationId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SongParticipationServiceImp implements SongParticipationService {
    private final SongParticipationJpaRepository songParticipationRepository;
    private final SongService songService;

    @Autowired
    public SongParticipationServiceImp(SongParticipationJpaRepository songParticipationRepository, SongService songService) {
        this.songParticipationRepository = songParticipationRepository;
        this.songService = songService;
    }

    @Override
    public List<SongParticipation> getAll() {
        return songParticipationRepository.findAll();
    }

    @Override
    public SongParticipation getOne(SongParticipationId songParticipationId) {
        return songParticipationRepository.findById(songParticipationId).orElseThrow(
                () -> new SongParticipationNotFound("No SongParticipation with given id have been found.")
        );
    }

    @Override
    @Transactional
    public SongParticipation save(SongParticipation songParticipation) {
        return songParticipationRepository.save(songParticipation);
    }

    @Override
    public List<SongParticipation> saveAll(Iterable<SongParticipation> songParticipations) {
        return songParticipationRepository.saveAll(songParticipations);
    }

    @Override
    @Transactional
    public SongParticipation update(SongParticipationId songParticipationId, SongParticipation songParticipation) {
        if(!songParticipationRepository.existsById(songParticipationId))
            throw new SongNotFoundException("Cannot update. SongParticipation with given id does not exist.");

        songParticipation.setArtist(songParticipationId.getArtist());
        songParticipation.setSong(songParticipationId.getSong());
        return songParticipationRepository.save(songParticipation);
    }

    @Override
    @Transactional
    public void delete(SongParticipationId songParticipationId) {
        songParticipationRepository.deleteById(songParticipationId);
    }

    @Override
    public void deleteAll() {
        songParticipationRepository.deleteAll();
    }

    @Override
    public Set<SongParticipation> getAllByArtistName(String name) {
        return new HashSet<>(songParticipationRepository.findSongParticipationByArtist_Name(name));
    }

    @Override
    public Set<SongParticipation> getAllByArtist(Artist artist) {
        return new HashSet<>(songParticipationRepository.findSongParticipationByArtist(artist));
    }

    @Override
    @Transactional
    public void deleteByArtistId(int artistId) {
        songParticipationRepository.deleteAllByArtist_Id(artistId);
        songService.deleteAllWithoutArtists();
    }

    @Override
    public void deleteBySongId(int songId) {
        songParticipationRepository.deleteAllBySong_Id(songId);
    }
}
