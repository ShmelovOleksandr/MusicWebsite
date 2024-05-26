package be.kdg.programming5.musicwebsite.api.controller;

import be.kdg.programming5.musicwebsite.api.dto.ArtistDTO;
import be.kdg.programming5.musicwebsite.api.dto.SongDTO;
import be.kdg.programming5.musicwebsite.api.dto.TourDTO;
import be.kdg.programming5.musicwebsite.api.dto.patch.ArtistPatchDTO;
import be.kdg.programming5.musicwebsite.api.dto.post.ArtistPostDTO;
import be.kdg.programming5.musicwebsite.security.permission_service.ArtistManipulationPermissionService;
import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.domain.Tour;
import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;
import be.kdg.programming5.musicwebsite.service.ArtistService;
import be.kdg.programming5.musicwebsite.service.SongService;
import be.kdg.programming5.musicwebsite.service.TourService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class RestArtistController {
    private final ArtistService artistService;
    private final SongService songService;
    private final TourService tourService;
    private final ArtistManipulationPermissionService artistManipulationPermissionService;
    private final ModelMapper mapper;
    private final Logger logger;

    @Autowired
    public RestArtistController(ArtistService artistService, SongService songService, TourService tourService, ArtistManipulationPermissionService artistManipulationPermissionService, ModelMapper mapper, Logger logger) {
        this.artistService = artistService;
        this.songService = songService;
        this.tourService = tourService;
        this.artistManipulationPermissionService = artistManipulationPermissionService;
        this.mapper = mapper;
        this.logger = logger;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists(
            @RequestParam(value = "namePart", required = false) String artistNamePart,
            @RequestParam(value = "listeners", required = false) Long listeners) {

        List<Artist> artists;
        if ((artistNamePart != null && !artistNamePart.isEmpty()) && (listeners != null && listeners > 0))
            artists = artistService.getAllByNamePartAndMinListeners(artistNamePart, listeners);
        else if (artistNamePart != null && !artistNamePart.isBlank())
            artists = artistService.getAllByNamePart(artistNamePart);
        else if (listeners != null && listeners > 0)
            artists = artistService.getAllByMinListeners(listeners);
        else
            artists = artistService.getAll();

        if (artists.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(artists.stream().map(artist -> mapper.map(artist, ArtistDTO.class)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable int id) {
        ArtistDTO artistDTO = mapper.map(artistService.getOne(id), ArtistDTO.class);
        return ResponseEntity.ok(artistDTO);
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongDTO>> getAllSongsByArtist(@PathVariable int id) {
        List<Song> songs = songService.getAllByArtistId(id);
        List<SongDTO> songDTOS = songs.stream().map(song -> mapper.map(song, SongDTO.class)).toList();

        if (songDTOS.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(songDTOS);
    }

    @GetMapping("/{id}/tours")
    public ResponseEntity<List<TourDTO>> getAllToursByArtist(@PathVariable int id) {
        List<Tour> tours = tourService.getAllByArtistId(id);
        List<TourDTO> tourDTOS = tours.stream().map(song -> mapper.map(song, TourDTO.class)).toList();

        if (tourDTOS.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(tourDTOS);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CacheEvict(value = {"all_artists_search", "name_part_artists_search", "min_listeners_artists_search", "name_part_and_min_listeners_artists_search"}, allEntries = true)
    public ResponseEntity<ArtistDTO> postArtist(@RequestBody @Valid ArtistPostDTO artistDTO, @AuthenticationPrincipal WebsiteUserDetails websiteUserDetails) {
        if(!artistManipulationPermissionService.allowedArtistCreation(websiteUserDetails.getUsername()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Artist artist = mapper.map(artistDTO, Artist.class);
        Artist savedArtist = artistService.save(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(savedArtist, ArtistDTO.class));
    }

    @PatchMapping({"/{id}"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ARTIST')")
    @CacheEvict(value = {"all_artists_search", "name_part_artists_search", "min_listeners_artists_search", "name_part_and_min_listeners_artists_search"}, allEntries = true)
    public ResponseEntity<ArtistDTO> patchArtist(@PathVariable int id, @RequestBody @Valid ArtistPatchDTO artistDTO, @AuthenticationPrincipal WebsiteUserDetails websiteUserDetails) {
        if(!artistManipulationPermissionService.allowedArtistEdit(websiteUserDetails.getUsername(), id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Artist artist = mapper.map(artistDTO, Artist.class);
        Artist updatedArtist = artistService.update(id, artist);
        return ResponseEntity.ok(mapper.map(updatedArtist, ArtistDTO.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ARTIST')")
    @CacheEvict(value = {"all_artists_search", "name_part_artists_search", "min_listeners_artists_search", "name_part_and_min_listeners_artists_search"}, allEntries = true)
    public ResponseEntity<Void> deleteArtist(@PathVariable int id, @AuthenticationPrincipal WebsiteUserDetails websiteUserDetails) {
        if (!artistManipulationPermissionService.allowedArtistDelete(websiteUserDetails.getUsername(), id))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        artistService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
