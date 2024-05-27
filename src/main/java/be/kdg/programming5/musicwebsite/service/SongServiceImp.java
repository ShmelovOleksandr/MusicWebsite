package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.repository.SongJpaRepository;
import be.kdg.programming5.musicwebsite.repository.SongParticipationJpaRepository;
import be.kdg.programming5.musicwebsite.util.exception.ArtistNotFoundException;
import be.kdg.programming5.musicwebsite.util.exception.SongNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SongServiceImp implements SongService {
    private final SongJpaRepository songJpaRepository;
    private final SongParticipationJpaRepository songParticipationJpaRepository;

    public SongServiceImp(SongJpaRepository songJpaRepository, SongParticipationJpaRepository songParticipationJpaRepository) {
        this.songJpaRepository = songJpaRepository;
        this.songParticipationJpaRepository = songParticipationJpaRepository;
    }

    @Override
    public List<Song> getAll() {
        return songJpaRepository.findAll();
    }

    @Override
    public List<Song> getAllByName(String name) {
        return songJpaRepository.findAllByNameContainingIgnoreCase(name);
    }

    public List<Song> getAllByArtistId(int artistId) {
        return songJpaRepository.findAllByArtistId(artistId);
    }

    @Override
    public void deleteAllWithoutArtists() {
        songJpaRepository.deleteAllBySongParticipationsIsEmpty();
    }

    @Override
    public Song getOne(Integer id) {
        return songJpaRepository.findSongByIdFetched(id).orElseThrow(
                () -> new SongNotFoundException("No songs with given id have been found.")
        );
    }

    @Override
    @Transactional
    public Song save(Song song) {
        return songJpaRepository.save(song);
    }

    @Override
    public List<Song> saveAll(Iterable<Song> songs) {
        return songJpaRepository.saveAll(songs);
    }

    @Override
    @Transactional
    public Song update(Integer id, Song newSong) {
        Song savedSong = songJpaRepository.findById(id).orElseThrow(
                () -> new SongNotFoundException("Cannot update. Song with given id does not exist.")
        );

        savedSong.setName(newSong.getName());
        savedSong.setGenre(newSong.getGenre());
        savedSong.setLength(newSong.getLength());
        return savedSong;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        songParticipationJpaRepository.deleteAllBySong_Id(id);
        songJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        songJpaRepository.deleteAll();
    }
}
