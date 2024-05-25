package be.kdg.programming5.musicwebsite.controller;

import be.kdg.programming5.musicwebsite.domain.Genre;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.service.SongService;
import be.kdg.programming5.musicwebsite.view_model.SimpleSongViewModel;
import be.kdg.programming5.musicwebsite.view_model.SongViewModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SongsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SongService songService;

    @Test
    void getSongsPageShouldReturnSongsPageAndAllSongsObjectsInTheModel() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/songs")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("view/songs/songs"))
                .andReturn();

        List<SongViewModel> songs = (List<SongViewModel>) result.getModelAndView().getModel().get("songs");
        assertNotNull(songs);
        assertEquals(4, songs.size());
    }

    @Test
    void getSongsPageWithNameShouldReturnSongsPageAndFoundSongsObjectsInTheModel() throws Exception {
        final String SONG_NAME = "Halo";
        MvcResult result = mockMvc.perform(
                get("/songs").param("songName", SONG_NAME)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("view/songs/songs"))
                .andReturn();

        List<SimpleSongViewModel> songs = (List<SimpleSongViewModel>) result.getModelAndView().getModel().get("songs");
        assertNotNull(songs);
        assertEquals(1, songs.size());
        assertEquals(SONG_NAME, songs.get(0).getName());
    }

    @Test
    void getSongsPageWithNamePartShouldReturnSongsPageAndFoundSongsObjectsInTheModel() throws Exception {
        final String SONG_NAME_PART = "n";
        MvcResult result = mockMvc.perform(
                        get("/songs")
                                .param("songName", SONG_NAME_PART)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("view/songs/songs"))
                .andReturn();

        List<SimpleSongViewModel> songs = (List<SimpleSongViewModel>) result.getModelAndView().getModel().get("songs");
        assertNotNull(songs);
        assertEquals(2, songs.size());
        assertTrue(songs.get(0).getName().contains(SONG_NAME_PART));
        assertTrue(songs.get(1).getName().contains(SONG_NAME_PART));
    }

    @Test
    void createSongForUnauthorizedUserShouldReturnRedirectionToLoginPage() throws Exception {
        mockMvc.perform(
                        post("/songs")
                                .param("id", "0")
                                .param("name", "song_name")
                                .param("length", String.valueOf(100))
                                .param("genre", String.valueOf(Genre.POP))
                                .param("artistsId", new String[]{"1"})
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    @WithUserDetails("ed")
    void createSongForArtistUserShouldReturnSongViewModelIfSuccessfullyCreated() throws Exception {
        mockMvc.perform(
                post("/songs")
                        .param("id", "0")
                        .param("name", "song_name")
                        .param("length", String.valueOf(100))
                        .param("genre", String.valueOf(Genre.POP))
                        .param("artistsId", new String[]{"1"})
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/songs"));

        MvcResult result = mockMvc.perform(
                get("/songs")
                .param("songName", "song_name")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("view/songs/songs"))
                .andReturn();

        List<SimpleSongViewModel> songs = (List<SimpleSongViewModel>) result.getModelAndView().getModel().get("songs");
        assertNotNull(songs);
        Optional<SimpleSongViewModel> createdSongOptional = songs.stream().filter(simpleSongViewModel -> simpleSongViewModel.getName().equals("song_name")).findFirst();
        assertTrue(createdSongOptional.isPresent());

        SimpleSongViewModel createdSong = createdSongOptional.get();
        songService.delete(createdSong.getId());
    }

    @Test
    @WithUserDetails("ed")
    void createSongForArtistUserAndWithInvalidDataShouldNotCreateSongAndReturnSongCreatorView() throws Exception {
        mockMvc.perform(
                post("/songs")
                        .param("id", "0")
                        .param("name", "sn")
                        .param("length", String.valueOf(-1))
                        .param("genre", String.valueOf(Genre.POP))
                        .param("artistsId", new String[]{"1"})
                        .with(csrf())
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("view/songs/songCreator"));

        MvcResult result = mockMvc.perform(
                get("/songs")
                .param("songName", "sn")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("view/songs/songs"))
                .andReturn();

        List<SimpleSongViewModel> songs = (List<SimpleSongViewModel>) result.getModelAndView().getModel().get("songs");
        assertTrue(songs.isEmpty());
    }
}

