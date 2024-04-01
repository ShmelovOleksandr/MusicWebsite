package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@ActiveProfiles("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtistServiceImpTest {

    @Autowired
    private ArtistServiceImp artistServiceImp;

    @BeforeAll
    void beforeAll() {

    }

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> artistServiceImp.deleteAll());

        List<Artist> savedArtists = artistServiceImp.saveAll(List.of(
                new Artist("test name 1", LocalDate.of(2000, 1, 1), 1),
                new Artist("test name 2", LocalDate.of(2000, 1, 1), 1),
                new Artist("test name 3", LocalDate.of(2000, 1, 1), 1),
        ));

        assertEquals(3, savedArtists.size());
    }

    void savingArtistShouldCreateNewRecordInDB() {
        // Arrange


        // Act

        // Assert
    }

    @Test
    void updatingArtistsNameShouldChangeTheirNameInDB() {
        String testArtistName = "test name update";
        LocalDate testArtistBirthDate = LocalDate.of(2000, 1, 1);
        int testArtistListeners = 1;

        // Arrange
        Artist testArtist = new Artist(testArtistName, testArtistBirthDate, testArtistListeners);
        Artist savedArtist = artistServiceImp.save(testArtist);

        // Act
//        Artist changedArtist =
//        Artist updatedArtist = artistServiceImp.update(savedArtist.getId(), )

//         Assert
//        assertNotNull(savedArtist);
//        assertEquals(testArtistName, savedArtist.getName());
//        assertEquals(testArtistBirthDate, savedArtist.getBirthDate());
//        assertEquals(testArtistListeners, savedArtist.getListeners());


    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> artistServiceImp.deleteAll());
    }
}