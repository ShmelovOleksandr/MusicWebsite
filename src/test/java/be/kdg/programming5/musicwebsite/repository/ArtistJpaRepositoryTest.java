package be.kdg.programming5.musicwebsite.repository;

import be.kdg.programming5.musicwebsite.domain.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ArtistJpaRepositoryTest {
    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Test
    void findByIdFetchedShouldReturnArtistWithAllFieldsFetched() {
        // Arrange

        // Act
        final int ID = 1;
        Artist artist = artistJpaRepository.findByIdFetched(ID).orElse(null);

        // Assert

        assertNotNull(artist);
        assertNotNull(artist.getSongParticipations());
        assertNotNull(artist.getWebsiteUser());
        assertNotNull(artist.getTours());
    }

    @Test
    void findByIdFetchedWithNonExistingIdShouldReturnEmptyOptional() {
        // Arrange

        // Act
        final int ID = 9999999;
        Optional<Artist> artist = artistJpaRepository.findByIdFetched(ID);

        // Assert
        assertTrue(artist.isEmpty());
    }

    @Test
    void savingArtistWithoutNameShouldThrowDataIntegrityViolationException() {
        // Arrange
        Artist artist = new Artist(null, LocalDate.of(2000, 1, 1), 1);

        // Act
        Executable executable = () -> artistJpaRepository.save(artist);

        // Assert
        assertThrows(DataIntegrityViolationException.class, executable);
    }

    @Test
    void savingArtistWithNameShouldNotThrowDataIntegrityViolationException() {
        // Arrange
        Artist artist = new Artist("name", LocalDate.of(2000, 1, 1), 1);

        // Act
        Executable executable = () -> artistJpaRepository.save(artist);

        // Assert
        assertDoesNotThrow(executable);
    }
}