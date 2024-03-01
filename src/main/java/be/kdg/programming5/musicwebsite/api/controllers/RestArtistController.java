package be.kdg.programming5.musicwebsite.api.controllers;

import be.kdg.programming5.musicwebsite.services.ArtistService;
import be.kdg.programming5.musicwebsite.util.converters.ArtistViewModelConverter;
import be.kdg.programming5.musicwebsite.view_model.ArtistViewModel;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class RestArtistController {
    private final ArtistService artistService;
    private final ArtistViewModelConverter artistViewModelConverter;
    private final Logger logger;

    public RestArtistController(ArtistService artistService, ArtistViewModelConverter artistViewModelConverter, Logger logger) {
        this.artistService = artistService;
        this.artistViewModelConverter = artistViewModelConverter;
        this.logger = logger;
    }

    @GetMapping
    public ResponseEntity<List<ArtistViewModel>> getAllArtists(
            @RequestParam(value = "namePart", required = false) String artistNamePat,
            @RequestParam(value = "listeners", required = false) Long listeners) {

        List<ArtistViewModel> artists = artistService.getAll(artistNamePat, listeners).stream()
                .map(artistViewModelConverter::convertToView).
                toList();
        logger.trace("Request on /api/artists has been handled. ({})", artists);
//        addJsonSessionAttribute(session, artists);
//        logger.trace("Added artists.json to the session");
        return ResponseEntity.ok(artists);
    }
}
