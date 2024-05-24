package be.kdg.programming5.musicwebsite.controller;

import be.kdg.programming5.musicwebsite.domain.Song;
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

import java.util.List;

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

    @Test
    void getSongsPageShouldReturnSongsPageAndSongsObjectsInTheModel() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/songs")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("view/songs/songs"))
                .andReturn();

        List<Song> songs = (List<Song>) result.getModelAndView().getModel().get("songs");
        assertEquals(4, songs.size());
    }

    @Test
    @WithUserDetails("ed")
    void createSong() {

    }
}

