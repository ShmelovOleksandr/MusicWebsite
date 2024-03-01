package be.kdg.programming5.musicwebsite.repositories;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.SongParticipation;
import be.kdg.programming5.musicwebsite.util.ids.SongParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongParticipationJpaRepository extends JpaRepository<SongParticipation, SongParticipationId> {
    List<SongParticipation> findSongParticipationByArtist_Name(String artistName);

    List<SongParticipation> findSongParticipationByArtist(Artist artist);
}
