package be.kdg.programming5.musicwebsite.repository;


import be.kdg.programming5.musicwebsite.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SongJpaRepository extends JpaRepository<Song, Integer> {
    @Query("select s from Song s " +
            "join fetch s.songParticipations sp " +
            "join fetch sp.artist " +
            "where s.id=:id ")
    Optional<Song> findSongByIdFetched(int id);

    // For some reason doesn't delete records from the DB without a custom Query
    @Override
    @Modifying
    @Query("DELETE FROM Song WHERE id=?1")
    void deleteById(Integer id);
    List<Song> findAllByNameContainingIgnoreCase(String name);

    @Query("select s from Song s " +
            "join SongParticipation sp on s.id = sp.song.id " +
            "join Artist a on sp.artist.id = a.id " +
            "where a.id = :artistId")
    List<Song> findAllByArtistId(int artistId);
}
