package be.kdg.programming5.musicwebsite.repository;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.SongParticipation;
import be.kdg.programming5.musicwebsite.util.id.SongParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongParticipationJpaRepository extends JpaRepository<SongParticipation, SongParticipationId> {
    List<SongParticipation> findSongParticipationByArtist_Name(String artistName);

    List<SongParticipation> findSongParticipationByArtist(Artist artist);

    @Modifying
    @Query(value = "delete from SongParticipation where artist.artistId = :artistId")
    void deleteAllByArtist_Id(int artistId);
}
