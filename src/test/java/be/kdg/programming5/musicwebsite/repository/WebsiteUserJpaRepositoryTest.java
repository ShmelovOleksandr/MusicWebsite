package be.kdg.programming5.musicwebsite.repository;

import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class WebsiteUserJpaRepositoryTest {
    @Autowired
    private WebsiteUserJpaRepository websiteUserJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void deletionOfWebsiteUserWithReferenceToArtistShouldThrowDataIntegrityViolationException() {
        // Arrange

        // Act
        Executable executable = () -> websiteUserJpaRepository.deleteById(1);

        // Assert
        assertThrows(DataIntegrityViolationException.class, executable);

    }

    @Test
    void deletionOfWebsiteUserWithNoReferenceToArtistShouldNotThrowDataIntegrityViolationException() {
        // Arrange
        var user = websiteUserJpaRepository.save(new WebsiteUser("test_name", passwordEncoder.encode("1234")));

        // Act
        Executable executable = () -> websiteUserJpaRepository.deleteById(user.getId());

        // Assert
        assertDoesNotThrow(executable);


        // Tear down
        websiteUserJpaRepository.delete(user);
    }

    @Test
    void existsByUsernameShouldReturnTrueIfThereIsAnUserWithTheSpecifiedName() {
        // Arrange
        final String NAME = "test_name";
        var user = websiteUserJpaRepository.save(new WebsiteUser(NAME, passwordEncoder.encode("1234")));

        // Act
        boolean exists = websiteUserJpaRepository.existsByUsername(NAME);

        // Assert
        assertTrue(exists);


        // Tear down
        websiteUserJpaRepository.delete(user);
    }

    @Test
    void existsByUsernameShouldReturnFalseIfThereIsNoUserWithTheSpecifiedName() {
        // Arrange
        final String NAME = "test_name";
        var user = websiteUserJpaRepository.save(new WebsiteUser(NAME, passwordEncoder.encode("1234")));

        // Act
        boolean exists = websiteUserJpaRepository.existsByUsername(NAME + "_not_the_same");

        // Assert
        assertFalse(exists);


        // Tear down
        websiteUserJpaRepository.delete(user);
    }


    @Test
    void findByUsernameFetchedShouldReturnAnUserWithAllDataFetched() {
        // Arrange
        // Act
        var user = websiteUserJpaRepository.findByUsernameFetched("ed");

        // Assert
        assertTrue(user.isPresent());
        assertNotNull(user.get().getArtist());
    }
}