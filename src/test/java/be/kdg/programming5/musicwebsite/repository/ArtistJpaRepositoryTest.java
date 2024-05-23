package be.kdg.programming5.musicwebsite.repository;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.domain.Tour;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ArtistJpaRepositoryTest {
    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Autowired
    private SongJpaRepository songJpaRepository;

    @Autowired
    private TourJpaRepository tourJpaRepository;

    @BeforeEach
    void setUp() {
//        artistJpaRepository.save(new Artist(1, "test_name1", LocalDate.of(2000, 1, 1), 1));
//        artistJpaRepository.save(new Artist(2, "test_name2", LocalDate.of(2000, 1, 1), 2));
//        artistJpaRepository.save(new Artist(3, "test_name3", LocalDate.of(2000, 1, 1), 3));
    }

    @AfterEach
    void tearDown() {
        artistJpaRepository.deleteAll();
    }

    @Test
    void deleteByIdShouldThrowDataIntegrityViolationException() {
        try {
            artistJpaRepository.deleteById(1);
        } catch (RuntimeException ignored) {

        }

        // Arrange
        var artist = artistJpaRepository.save(new Artist(3, "test_name3", LocalDate.of(2000, 1, 1), 3));


        // Act
//        Executable executable = () -> developerRepository.save(new Developer(
//                "harry@kdg.be", "Harry2", "harry2", "password123",
//                DeveloperRole.USER
//        ));

        // Assert
//        assertThrows(DataIntegrityViolationException.class, executable);
        //assertThrows(DataIntegrityViolationException.class,
        // Act
        //() -> developerRepository.save(new Developer(
        //        "harry@kdg.be", "Harry2", "harry2", "password123",
        //        DeveloperRole.USER
        //)));
    }

    @Test
    void findByName() {
    }
}