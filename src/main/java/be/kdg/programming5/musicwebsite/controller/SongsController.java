package be.kdg.programming5.musicwebsite.controller;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.service.ArtistService;
import be.kdg.programming5.musicwebsite.service.SongService;
import be.kdg.programming5.musicwebsite.util.converter.ArtistViewModelConverter;
import be.kdg.programming5.musicwebsite.util.converter.SimpleSongViewModelConverter;
import be.kdg.programming5.musicwebsite.util.converter.SongViewModelConverter;
import be.kdg.programming5.musicwebsite.view_model.SimpleSongViewModel;
import be.kdg.programming5.musicwebsite.view_model.SongViewModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/songs")
public class SongsController extends DownloadController {
    @Value("${download.json-file.name.songs}")
    private String SONG_JSON_FILE_NAME;
    private final Logger logger;
    private final SongService songService;
    private final ArtistService artistService;
    private final SongViewModelConverter songViewModelConverter;
    private final SimpleSongViewModelConverter simpleSongViewModelConverter;
    private final ArtistViewModelConverter artistViewModelConverter;

    @Autowired
    public SongsController(Logger logger, SongService songService, ArtistService artistService, SongViewModelConverter songViewModelConverter, SimpleSongViewModelConverter simpleSongViewModelConverter, ArtistViewModelConverter artistViewModelConverter) {
        this.logger = logger;
        this.songService = songService;
        this.artistService = artistService;
        this.songViewModelConverter = songViewModelConverter;
        this.simpleSongViewModelConverter = simpleSongViewModelConverter;
        this.artistViewModelConverter = artistViewModelConverter;

        this.fileName = SONG_JSON_FILE_NAME;
    }

    @GetMapping
    public String getSongsPage(Model model,
                               HttpSession session,
                               @RequestParam(value = "songName", required = false) String songName){

        List<SimpleSongViewModel> songs = songService.getAllByName(songName).stream().map(simpleSongViewModelConverter::convertToView).toList();
        model.addAttribute("songs", songs);
        logger.trace("Added list of Songs({}) to the model", songs);

        addJsonSessionAttribute(session, songs);
        logger.trace("Added songs.json to the session");
        return "view/songs/songs";
    }

    @GetMapping("/{id}")
    public String getSongsDetailsPage(Model model, @PathVariable int id){
        Song song = songService.getOne(id);
        Set<Artist> relatedArtists = song.getArtists();
        model.addAttribute("song", songViewModelConverter.convertToView(song));
        model.addAttribute("relatedArtists", relatedArtists.stream().map(artistViewModelConverter::convertToView).toList());
        logger.trace("Added {} to the model", song);
        return "view/songs/songDetails";
    }

    @GetMapping("/{id}/editor")
    public String getSongsEditorPage(Model model, @PathVariable int id){
        Song song = songService.getOne(id);
        SongViewModel songViewModel = songViewModelConverter.convertToView(song);
        model.addAttribute("songViewModel", songViewModel);
        model.addAttribute("artists", artistService.getAll());
        logger.trace("Added {} to the model", song);
        return "view/songs/songEditor";
    }

    @GetMapping("/new")
    public String getSongCreationPage(Model model){
        model.addAttribute("songViewModel", new SongViewModel(0, null, 0, null));
        model.addAttribute("artists", artistService.getAll());

//        logger.trace("Added Genres({}) and Artists({}) to the model", genres, artists);
        return "view/songs/songCreator";
    }

    @PostMapping
    public String createSong(@ModelAttribute @Valid SongViewModel songViewModel,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            logger.debug("Errors in the bindingResult (SongsController::createSong): {}", bindingResult.getAllErrors());
            return "view/songs/songCreator";
        }

        songService.save(songViewModelConverter.convertToModel(songViewModel));
        return "redirect:/songs";
    }

    @PatchMapping("/{id}")
    public String editSong(@PathVariable int id,
                           Model model,
                           @ModelAttribute("songViewModel") @Valid SongViewModel songViewModel,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            logger.debug("Errors in the bindingResult (SongsController::editSong): {}", bindingResult.getAllErrors());
            return getSongsEditorPage(model, id);
        }

        songService.update(id, songViewModelConverter.convertToModel(songViewModel));
        return "redirect:/songs/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteSong(@PathVariable int id){
        songService.delete(id);
        return "redirect:/songs";
    }
}
