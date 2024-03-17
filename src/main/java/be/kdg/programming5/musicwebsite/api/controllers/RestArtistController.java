package be.kdg.programming5.musicwebsite.api.controllers;

import be.kdg.programming5.musicwebsite.api.dto.ArtistDTO;
import be.kdg.programming5.musicwebsite.api.dto.SongDTO;
import be.kdg.programming5.musicwebsite.api.dto.TourDTO;
import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.domain.Tour;
import be.kdg.programming5.musicwebsite.services.ArtistService;
import be.kdg.programming5.musicwebsite.services.SongService;
import be.kdg.programming5.musicwebsite.services.TourService;
import be.kdg.programming5.musicwebsite.util.converters.ArtistViewModelConverter;
import be.kdg.programming5.musicwebsite.view_model.ArtistViewModel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class RestArtistController {
    private final ArtistService artistService;
    private final SongService songService;
    private final TourService tourService;
    private final ModelMapper mapper;
    private final Logger logger;

    @Autowired
    public RestArtistController(ArtistService artistService, SongService songService, TourService tourService, ModelMapper mapper, Logger logger) {
        this.artistService = artistService;
        this.songService = songService;
        this.tourService = tourService;
        this.mapper = mapper;
        this.logger = logger;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists(
            @RequestParam(value = "namePart", required = false) String artistNamePat,
            @RequestParam(value = "listeners", required = false) Long listeners) {

        List<ArtistDTO> artists = artistService.getAll(artistNamePat, listeners).stream().map(
                artist -> mapper.map(artist, ArtistDTO.class)
                ).toList();
        logger.trace("Request on /api/artists has been handled. ({})", artists);
//        addJsonSessionAttribute(session, artists);
//        logger.trace("Added artists.json to the session");
        return ResponseEntity.ok(artists);
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
        return ResponseEntity.ok(songDTOS);
    }

    @GetMapping("/{id}/tours")
    public ResponseEntity<List<TourDTO>> getAllToursByArtist(@PathVariable int id) {
        List<Tour> tours = tourService.getAllByArtistId(id);
        List<TourDTO> tourDTOS = tours.stream().map(song -> mapper.map(song, TourDTO.class)).toList();
        return ResponseEntity.ok(tourDTOS);
    }

    @PostMapping
    public ResponseEntity<ArtistDTO> postArtist(@RequestBody @Valid ArtistDTO artistDTO) {
        Artist artist = mapper.map(artistDTO, Artist.class);
        artistService.save(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(artistDTO);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<ArtistDTO> patchArtist(@PathVariable int id, @RequestBody ArtistDTO artistDTO) {
        Artist artist = mapper.map(artistDTO, Artist.class);
        Artist updatedArtist = artistService.update(id, artist);
        return ResponseEntity.ok(mapper.map(updatedArtist, ArtistDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable int id) {
        artistService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
