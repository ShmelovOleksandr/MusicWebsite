package be.kdg.programming5.musicwebsite.api.controller;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.service.ArtistService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RestArtistControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistService artistService;

    @Test
    public void deleteArtistForUnauthorizedUserShouldReturnStatusUnauthorized() throws Exception {
        final int ID = 1;
        mockMvc.perform(
                delete("/api/artists/{id}", ID)
                        .with(csrf())
        )
                .andExpect(status().isUnauthorized());

        verify(artistService, never()).delete(ID);
    }

    @Test
    @WithUserDetails("admin")
    public void deleteArtistForAdminUserShouldReturnStatusNoContent() throws Exception {
        final int ID = 1;
        mockMvc.perform(
                delete("/api/artists/{id}", ID)
                        .with(csrf())
        )
                .andExpect(status().isNoContent());

        verify(artistService).delete(ID);
    }

    @Test
    public void getAllArtistsForAnyUserWithoutAnyFiltersShouldReturnListOfAllArtists() throws Exception {
        List<Artist> artists = new ArrayList<>(List.of(
                new Artist(1, "name_1", LocalDate.of(2000,1,1),1),
                new Artist(2, "name_2", LocalDate.of(2000,1,1),2),
                new Artist(3, "name_3", LocalDate.of(2000,1,1),3)
        ));
        given(artistService.getAll()).willReturn(artists);

        mockMvc.perform(get("/api/artists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(artists.size()))
                .andExpect(
                        jsonPath("$[*].name",
                        Matchers.containsInAnyOrder("name_1", "name_2", "name_3"))
                );


        verify(artistService).getAll();
        verify(artistService, never()).getAllByNamePart(anyString());
        verify(artistService, never()).getAllByMinListeners(anyLong());
        verify(artistService, never()).getAllByNamePartAndMinListeners(anyString(), anyLong());
    }

    @Test
    public void getAllArtistsForAnyUserWithFilterByNamePartShouldReturnListOfAllFilteredArtists() throws Exception {
        final String NAME_PART = "Ben";
        List<Artist> artists = new ArrayList<>(List.of(
                new Artist(1, "Ben", LocalDate.of(2000,1,1),1),
                new Artist(2, "Benjamin", LocalDate.of(2000,1,1),2)
        ));
        given(artistService.getAllByNamePart(NAME_PART)).willReturn(artists);

        mockMvc.perform(
                get("/api/artists")
                        .param("namePart", NAME_PART)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(artists.size()))
                .andExpect(
                        jsonPath("$[*].name",
                        Matchers.containsInAnyOrder("Ben", "Benjamin"))
                );


        verify(artistService).getAllByNamePart(NAME_PART);
        verify(artistService, never()).getAll();
        verify(artistService, never()).getAllByMinListeners(anyLong());
        verify(artistService, never()).getAllByNamePartAndMinListeners(anyString(), anyLong());
    }

    @Test
    public void getAllArtistsForAnyUserWithFilterByMinListenersShouldReturnListOfAllFilteredArtists() throws Exception {
        final long MIN_LISTENERS = 2;
        List<Artist> artists = new ArrayList<>(List.of(
                new Artist(2, "name_2", LocalDate.of(2000,1,1),2),
                new Artist(3, "name_3", LocalDate.of(2000,1,1),3)
        ));
        given(artistService.getAllByMinListeners(MIN_LISTENERS)).willReturn(artists);

        mockMvc.perform(
                get("/api/artists")
                        .param("listeners", String.valueOf(MIN_LISTENERS))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(artists.size()))
                .andExpect(
                        jsonPath("$[*].name",
                        Matchers.containsInAnyOrder("name_2", "name_3"))
                );


        verify(artistService).getAllByMinListeners(MIN_LISTENERS);
        verify(artistService, never()).getAll();
        verify(artistService, never()).getAllByNamePart(anyString());
        verify(artistService, never()).getAllByNamePartAndMinListeners(anyString(), anyLong());
    }

    @Test
    public void getAllArtistsForAnyUserWithFilterByNamePartAndMinListenersShouldReturnListOfAllFilteredArtists() throws Exception {
        final String NAME_PART = "Ben";
        final long MIN_LISTENERS = 2;
        List<Artist> artists = new ArrayList<>(List.of(
                new Artist(1, "Ben", LocalDate.of(2000,1,1),3)
        ));
        given(artistService.getAllByNamePartAndMinListeners(NAME_PART, MIN_LISTENERS)).willReturn(artists);

        mockMvc.perform(
                get("/api/artists")
                        .param("namePart", NAME_PART)
                        .param("listeners", String.valueOf(MIN_LISTENERS))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(artists.size()))
                .andExpect(
                        jsonPath("$[*].name",
                        Matchers.containsInAnyOrder("Ben"))
                );


        verify(artistService).getAllByNamePartAndMinListeners(NAME_PART, MIN_LISTENERS);
        verify(artistService, never()).getAll();
        verify(artistService, never()).getAllByNamePart(anyString());
        verify(artistService, never()).getAllByMinListeners(anyLong());
    }
}