package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.repository.ArtistJpaRepository;
import be.kdg.programming5.musicwebsite.util.exception.ArtistNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")

class ArtistServiceImpUnitTest {
    @Autowired
    private ArtistService artistService;

    @MockBean
    private ArtistJpaRepository artistJpaRepository;

    @Test
    void updateShouldThrowArtistNotFoundExceptionForNonExistentArtist() {
        // Arrange
        final int ID = 1;
        given(artistJpaRepository.findById(ID)).willReturn(Optional.empty());

        Artist artist = new Artist("updated_name", LocalDate.of(2000,1, 1), 1);

        // Act
        Executable executable = () -> artistService.update(ID, artist);

        // Assert
        assertThrows(ArtistNotFoundException.class, executable);
    }

    @Test
    void updateShouldSucceedForExistingArtist() {
        // Arrange
        final int ID = 1;
        Artist artist = new Artist(ID, "name", LocalDate.of(2000,1, 1), 1);
        given(artistJpaRepository.findById(ID)).willReturn(Optional.of(artist));

        Artist futureArtist = new Artist("updated_name", LocalDate.of(2222,1, 1), 123);

        // Act
        Artist updatedArtist = artistService.update(ID, futureArtist);

        // Assert
        assertNotNull(updatedArtist);
        assertEquals(futureArtist.getName(), updatedArtist.getName());
        assertEquals(futureArtist.getBirthDate(), updatedArtist.getBirthDate());
        assertEquals(futureArtist.getListeners(), updatedArtist.getListeners());
    }
}