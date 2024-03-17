package be.kdg.programming5.musicwebsite.controller;

import be.kdg.programming5.musicwebsite.service.ArtistService;
import be.kdg.programming5.musicwebsite.util.converter.ArtistViewModelConverter;
import be.kdg.programming5.musicwebsite.view_model.ArtistViewModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artists")
public class ArtistController extends DownloadController {
    @Value("${download.json-file.name.artists}")
    private String ARTIST_JSON_FILE_NAME;
    private final ArtistService artistService;
    private final ArtistViewModelConverter artistViewModelConverter;
    private final Logger logger;

    @Autowired
    public ArtistController(ArtistService artistService, ArtistViewModelConverter artistViewModelConverter, Logger logger) {
        this.artistService = artistService;
        this.artistViewModelConverter = artistViewModelConverter;
        this.logger = logger;

        this.fileName = ARTIST_JSON_FILE_NAME;
    }

    @GetMapping
    public String getArtistPage(@RequestParam(value = "namePart", required = false) String artistNamePat,
                                @RequestParam(value = "listeners", required = false) Long listeners) {

//        List<ResponseArtistDTO> artists = artistService.getAll(artistNamePat, listeners).stream()
//                .map(artistViewModelConverter::convertToView).
//                toList();
//        logger.trace("Request on /api/artists has been handled. ({})", artists);
//        addJsonSessionAttribute(session, artists);
//        logger.trace("Added artists.json to the session");
        return "view/artists/artists";
    }

    @GetMapping("/{id}")
    public String getArtistsDetailsPage(Model model, @PathVariable int id){
//        ResponseArtistDTO artist = artistViewModelConverter.convertToView(artistService.getOne(id));
//        model.addAttribute("artist", artist);
//
//        logger.trace("Added {} to the model", artist);
        return "view/artists/artistDetails";
    }

    @GetMapping("/new")
    public String getArtistCreatorPage(Model model){
//        model.addAttribute("artistViewModel", new ArtistViewModel(0, null, null, 0));
        return "view/artists/artistCreator";
    }

    @GetMapping("/{id}/editor")
    public String getArtistEditorPage(Model model, @PathVariable int id){
//        model.addAttribute("artistViewModel", artistViewModelConverter.convertToView(artistService.getOne(id)));
        return "view/artists/artistEditor";
    }

    @PostMapping
    public String createArtist(@ModelAttribute("artistViewModel") @Valid ArtistViewModel artistViewModel,
                               BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            logger.debug("Errors in the bindingResult (ArtistsController::createArtist): {}", bindingResult.getAllErrors());
//            return "view/artists/artistCreator";
//        }
//        artistService.save(artistViewModelConverter.convertToModel(artistViewModel));
        return "redirect:/artists";
    }

    @PatchMapping("/{id}")
    public String editArtist(@PathVariable int id,
                             @ModelAttribute("artistViewModel") @Valid ArtistViewModel artistViewModel,
                             BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            logger.debug("Errors in the bindingResult (ArtistsController::editArtist): {}", bindingResult.getAllErrors());
//            return "view/artists/artistEditor";
//        }
//
//        artistService.update(id, artistViewModelConverter.convertToModel(artistViewModel));
        return "redirect:/artists/" + id;
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteArtist(@PathVariable int id){
//        artistService.delete(id);
//        return ResponseEntity.status(204).build();
//    }
}
