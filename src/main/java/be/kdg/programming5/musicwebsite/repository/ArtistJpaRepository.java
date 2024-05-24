package be.kdg.programming5.musicwebsite.repository;

import be.kdg.programming5.musicwebsite.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistJpaRepository extends JpaRepository<Artist, Integer> {
    // For some reason doesn't delete records from the DB without a custom Query
    @Override
    @Modifying
    @Query("DELETE FROM Artist WHERE id=?1")
    void deleteById(Integer integer);

    @Query("""
    select a from Artist a
    join fetch a.songParticipations
    join fetch a.tours
    join fetch a.websiteUser
    where a.id = :id
    """)
    Optional<Artist> findByIdFetched(Integer id);
    Optional<Artist> findByName(String name);

    List<Artist> findAllByNameContainingIgnoreCase(String namePart);
    List<Artist> findAllByListenersAfter(long minListeners);
    List<Artist> findAllByListenersAfterAndNameContainingIgnoreCase(long minListeners, String namePart);
}
