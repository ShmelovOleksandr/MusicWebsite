package be.kdg.programming5.musicwebsite.api.controller;

import be.kdg.programming5.musicwebsite.api.dto.patch.ArtistPatchDTO;
import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Genre;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.domain.SongParticipation;
import be.kdg.programming5.musicwebsite.service.ArtistService;
import be.kdg.programming5.musicwebsite.service.SongParticipationService;
import be.kdg.programming5.musicwebsite.service.SongService;
import be.kdg.programming5.musicwebsite.util.id.SongParticipationId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RestArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @Autowired
    private SongParticipationService songParticipationService;

    private int artistId;
    private int songId;
    private SongParticipationId songParticipationId;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Artist savedArist = artistService.save(new Artist("Name", LocalDate.of(2000, 1, 1), 1));
        artistId = savedArist.getId();

        Song savedSong = songService.save(new Song("Title", 1, Genre.POP));
        songId = savedSong.getId();

        SongParticipation songParticipation = new SongParticipation(savedArist, savedSong);
        savedArist.setSongParticipations(Set.of(songParticipation));
        savedSong.setSongParticipations(Set.of(songParticipation));
        songParticipationId = new SongParticipationId(savedArist, savedSong);
    }

    @AfterEach
    void tearDown() {
        artistService.delete(artistId);
        songService.delete(songId);
        songParticipationService.delete(songParticipationId);

        artistId = 0;
        songId = 0;
        songParticipationId = null;
    }


    @Test
    @WithUserDetails("admin")
    public void patchArtistShouldReturnNotFoundForNonExistingArtist() throws Exception {
        mockMvc.perform(
                patch("/api/artists/{artistId}", 999999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(
                                objectMapper.writeValueAsString(
                                        new ArtistPatchDTO(
                                                "patch_name",
                                                LocalDate.of(2024, 1, 1),
                                                123
                                        )
                                )
                        )
        ).andExpect(status().isNotFound());
    }
}