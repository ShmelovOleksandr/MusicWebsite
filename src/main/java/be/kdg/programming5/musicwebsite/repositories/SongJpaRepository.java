package be.kdg.programming5.musicwebsite.repositories;


import be.kdg.programming5.musicwebsite.domain.Song;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongJpaRepository extends JpaRepository<Song, Integer> {
    // For some reason doesn't delete records from the DB without a custom Query
    @Override
    @Modifying
    @Query("DELETE FROM Song WHERE id=?1")
    void deleteById(Integer integer);
    List<Song> findAllByNameContainingIgnoreCase(String name);
}
