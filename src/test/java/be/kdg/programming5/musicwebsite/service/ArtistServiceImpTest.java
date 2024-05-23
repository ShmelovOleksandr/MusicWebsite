package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.repository.ArtistJpaRepository;
import be.kdg.programming5.musicwebsite.repository.SongParticipationJpaRepository;
import be.kdg.programming5.musicwebsite.repository.TourJpaRepository;
import be.kdg.programming5.musicwebsite.util.id.SongParticipationId;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class ArtistServiceImpTest {
    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Autowired
    private SongParticipationJpaRepository songParticipationJpaRepository;

    @Autowired
    private TourJpaRepository tourJpaRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updatingFieldsOfActorShouldUpdateEveryChangedField() {
        // Arrange
        var createdArtist = artistService.save(new Artist("Name", LocalDate.of(2000,1,1), 1));
        var changedArist = new Artist();
        changedArist.setName("New_name");
        changedArist.setBirthDate(LocalDate.of(2000,1,2));
        changedArist.setListeners(2);

        // Act
        var updatedArtist = artistService.update(createdArtist.getId(), changedArist);

        // Assert
        assertNotNull(updatedArtist);
        assertEquals("New_name", updatedArtist.getName());
        assertEquals(LocalDate.of(2000,1,2), updatedArtist.getBirthDate());
        assertEquals(2, updatedArtist.getListeners());

    }

    @Test
    void updatingNonExistingActorShouldThrowEntityNotFoundException() {
        // Arrange
        var createdArtist = artistService.save(new Artist("Name", LocalDate.of(2000,1,1), 1));
        var changedArist = new Artist();
        changedArist.setName("New_name");
        changedArist.setBirthDate(LocalDate.of(2000,1,2));
        changedArist.setListeners(2);

        // Act
        Executable executable = () -> artistService.update(9999999, changedArist);

        // Assert
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void deleteShouldDeleteArtistAndAllRelatedEntities() {
        // Arrange
        final int ID = 1;
        Artist artist = artistJpaRepository.findByIdFetched(ID).orElseThrow();

        // Act
        artistService.delete(ID);

        // Assert
        assertThrows(EntityNotFoundException.class, () -> artistService.getOne(ID));
        artist.getSongParticipations().forEach(
                songParticipation -> assertFalse(
                        songParticipationJpaRepository.existsById(
                                new SongParticipationId(
                                        artist,
                                        new Song(songParticipation.getSong().getId())
                                )
                        )
                )
        );
        artist.getTours().forEach(
                tour -> assertFalse(
                        tourJpaRepository.existsById(tour.getId())
                )
        );
    }
}