package be.kdg.programming5.musicwebsite.controllers;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.domain.Genre;
import be.kdg.programming5.musicwebsite.services.ArtistService;
import be.kdg.programming5.musicwebsite.services.SongService;
import be.kdg.programming5.musicwebsite.util.converters.SongViewModelConverter;
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

@Controller
@RequestMapping("/songs")
public class SongsController extends DownloadController {
    @Value("${download.json-file.name.songs}")
    private String SONG_JSON_FILE_NAME;
    private final Logger logger;
    private final SongService songService;
    private final ArtistService artistService;
    private final SongViewModelConverter songViewModelConverter;

    @Autowired
    public SongsController(Logger logger, SongService songService, ArtistService artistService, SongViewModelConverter songViewModelConverter) {
        this.logger = logger;
        this.songService = songService;
        this.artistService = artistService;
        this.songViewModelConverter = songViewModelConverter;

        this.fileName = SONG_JSON_FILE_NAME;
    }

    @GetMapping
    public String getSongsPage(Model model,
                               HttpSession session,
                               @RequestParam(value = "songName", required = false) String songName){

        List<Song> songs = songService.getAll(songName);
        model.addAttribute("songs", songs);
        logger.trace("Added list of Songs({}) to the model", songs);

        addJsonSessionAttribute(session, songs.stream().map(songViewModelConverter::convertToView).toList());
        logger.trace("Added songs.json to the session");
        return "view/songs/songs";
    }

    @GetMapping("/{id}")
    public String getSongsDetailsPage(Model model, @PathVariable int id){
        Song song = songService.getOne(id);
        model.addAttribute("song", song);
        logger.trace("Added {} to the model", song);
        return "view/songs/songDetails";
    }

    @GetMapping("/{id}/editor")
    public String getSongsEditorPage(Model model, HttpSession session, @PathVariable int id){
        Song song = songService.getOne(id);
        SongViewModel songViewModel = songViewModelConverter.convertToView(song);
        model.addAttribute("songViewModel", songViewModel);
        session.setAttribute("sessionGenres", Genre.values());
        session.setAttribute("sessionArtists", artistService.getAll());
        logger.trace("Added {} to the model", song);
        return "view/songs/songEditor";
    }

    @GetMapping("/new")
    public String getSongCreationPage(Model model, HttpSession session){
        model.addAttribute("songViewModel", new SongViewModel(0, null, 0,null, 0));

        Genre[] genres = Genre.values();
        List<Artist> artists = artistService.getAll();
        session.setAttribute("sessionGenres", genres);
        session.setAttribute("sessionArtists", artists);

        logger.trace("Added Genres({}) and Artists({}) to the model", genres, artists);
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
                           @ModelAttribute("songViewModel") @Valid SongViewModel songViewModel,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            logger.debug("Errors in the bindingResult (SongsController::editSong): {}", bindingResult.getAllErrors());
            return "view/songs/songEditor";
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
