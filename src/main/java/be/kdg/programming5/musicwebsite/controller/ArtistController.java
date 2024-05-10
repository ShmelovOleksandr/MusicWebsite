package be.kdg.programming5.musicwebsite.controller;

import be.kdg.programming5.musicwebsite.service.ArtistService;
import be.kdg.programming5.musicwebsite.util.converter.ArtistViewModelConverter;
import be.kdg.programming5.musicwebsite.view_model.ArtistViewModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artists")
public class ArtistController extends DownloadController {
    @Value("${download.json-file.name.artists}")
    private String ARTIST_JSON_FILE_NAME;


    public ArtistController() {
        this.fileName = ARTIST_JSON_FILE_NAME;
    }

    @GetMapping
    public String getArtistPage() {
        return "view/artists/artists";
    }

    @GetMapping("/{id}")
    public String getArtistsDetailsPage(){
        return "view/artists/artistDetails";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getArtistCreatorPage(){
        return "view/artists/artistCreator";
    }

    @GetMapping("/{id}/editor")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ARTIST')")
    public String getArtistEditorPage(){
        return "view/artists/artistEditor";
    }

}
