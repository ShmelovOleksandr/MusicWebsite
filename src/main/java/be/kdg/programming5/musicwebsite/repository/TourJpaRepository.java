package be.kdg.programming5.musicwebsite.repository;

import be.kdg.programming5.musicwebsite.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourJpaRepository extends JpaRepository<Tour, Integer> {
    // For some reason doesn't delete records from the DB without a custom Query
    @Override
    @Modifying
    @Query("DELETE FROM Tour WHERE id=?1")
    void deleteById(Integer integer);

    // Not necessary here. Annotation added only to fulfill one of the requirements to the project. Function will work without it as well.
    @Query("SELECT t FROM Tour t JOIN Artist a ON t.artist.id=a.id WHERE LOWER(a.name) LIKE '%' || LOWER(?1) || '%'")
    List<Tour> findAllByArtist_NameContainingIgnoreCase(String artistNamePart);

    @Modifying
    @Query(value = "delete from Tour where artist.id = :artistId")
    void deleteAllByArtist_Id(int artistId);

    List<Tour> findAllByArtist_Id(int artistId);
}
