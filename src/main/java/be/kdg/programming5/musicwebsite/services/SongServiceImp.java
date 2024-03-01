package be.kdg.programming5.musicwebsite.services;

import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.repositories.SongJpaRepository;
import be.kdg.programming5.musicwebsite.util.exceptions.SongNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SongServiceImp implements SongService {
    private final SongJpaRepository songJpaRepository;

    public SongServiceImp(SongJpaRepository songJpaRepository) {
        this.songJpaRepository = songJpaRepository;
    }

    @Override
    public List<Song> getAll() {
        return songJpaRepository.findAll();
    }

    @Override
    public List<Song> getAll(String name) {
        if(name == null || name.isBlank())
            return getAll();

        return songJpaRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public Song getOne(Integer id) {
        return songJpaRepository.findById(id).orElseThrow(
                () -> new SongNotFoundException("No songs with given id have been found.")
        );
    }

    @Override
    @Transactional
    public Song save(Song song) {
        return songJpaRepository.save(song);
    }

    @Override
    @Transactional
    public Song update(Integer id, Song song) {
        if(!songJpaRepository.existsById(id))
            throw new SongNotFoundException("Cannot update. Song with given id does not exist.");

        song.setId(id);
        return songJpaRepository.save(song);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        songJpaRepository.deleteById(id);
    }
}
