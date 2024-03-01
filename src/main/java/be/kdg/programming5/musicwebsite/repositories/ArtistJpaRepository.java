package be.kdg.programming5.musicwebsite.repositories;

import be.kdg.programming5.musicwebsite.domain.Artist;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistJpaRepository extends JpaRepository<Artist, Integer> {

    @Query("select a from Artist a " +
            "inner join fetch a.songParticipations " +
            "join fetch a.tours " +
            "")
    List<Artist> getAllFetched();

    // For some reason doesn't delete records from the DB without a custom Query
    @Override
    @Modifying
    @Query("DELETE FROM Artist WHERE id=?1")
    void deleteById(Integer integer);

    Optional<Artist> findByName(String name);

    List<Artist> findAllByNameContainingIgnoreCase(String namePart);
    List<Artist> findAllByListenersAfter(long minListeners);
    List<Artist> findAllByListenersAfterAndNameContainingIgnoreCase(long minListeners, String namePart);
}
