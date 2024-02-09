package be.kdg.programming5.musicwebsite.controllers;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Tour;
import be.kdg.programming5.musicwebsite.services.ArtistService;
import be.kdg.programming5.musicwebsite.services.TourService;
import be.kdg.programming5.musicwebsite.util.converters.TourViewModelConverter;
import be.kdg.programming5.musicwebsite.view_model.TourViewModel;
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
@RequestMapping("/tours")
public class TourController extends DownloadController {
    @Value("${download.json-file.name.tours}")
    private String TOUR_JSON_FILE_NAME;
    private final Logger logger;
    private final TourService tourService;
    private final ArtistService artistService;
    private final TourViewModelConverter tourViewModelConverter;

    @Autowired
    public TourController(Logger logger, TourService tourService, ArtistService artistService, TourViewModelConverter tourViewModelConverter) {
        this.logger = logger;
        this.tourService = tourService;
        this.artistService = artistService;
        this.tourViewModelConverter = tourViewModelConverter;

        this.fileName = TOUR_JSON_FILE_NAME;
    }

    @GetMapping
    public String getToursPage(Model model,
                               HttpSession session,
                               @RequestParam(value = "artistName", required = false) String artistName) {

        List<Tour> tours = tourService.getAll(artistName);
        model.addAttribute("tours", tours);
        logger.trace("Added list of tours({}) to the model", tours);

        addJsonSessionAttribute(session, tours.stream().map(tourViewModelConverter::convertToView).toList());
        logger.trace("Added tours.json to the session");
        return "view/tours/tours";
    }

    @GetMapping("/{id}")
    public String getArtistsDetailsPage(Model model, @PathVariable int id){
        Tour tour = tourService.getOne(id);
        model.addAttribute("tour", tour);
        logger.trace("Added {} to the model", tour);
        return "view/tours/tourDetails";
    }

    @GetMapping("/{id}/editor")
    public String getTourEditorPage(Model model, HttpSession session, @PathVariable int id){
        Tour tour = tourService.getOne(id);
        model.addAttribute("tourViewModel", tourViewModelConverter.convertToView(tour));

        List<Artist> artists = artistService.getAll();
        session.setAttribute("sessionArtists", artists);
        return "view/tours/tourEditor";
    }

    @GetMapping("/new")
    public String getTourCreator(Model model, HttpSession session){
        model.addAttribute("tourViewModel", new TourViewModel(0, null, null, 0d, null));

        List<Artist> artists = artistService.getAll();
        session.setAttribute("sessionArtists", artists);

        logger.trace("Added Artists({}) to the session", artists);
        return "view/tours/tourCreator";
    }

    @PostMapping
    public String createTour(@ModelAttribute @Valid TourViewModel tourViewModel, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            logger.debug("Errors in the bindingResult (ToursController): {}", bindingResult.getAllErrors());
            return "view/tours/tourCreator";
        }

        tourService.save(tourViewModelConverter.convertToModel(tourViewModel));
        return "redirect:/tours";
    }

    @PatchMapping("/{id}")
    public String editTour(@PathVariable int id,
                           @ModelAttribute("tourViewModel") @Valid TourViewModel tourViewModel,
                           BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            logger.debug("Errors in the bindingResult (ToursController::editTour): {}", bindingResult.getAllErrors());
            return "view/tours/tourEditor";
        }

        tourService.update(id, tourViewModelConverter.convertToModel(tourViewModel));
        return "redirect:/tours/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteTour(@PathVariable int id){
        tourService.delete(id);
        return "redirect:/tours";
    }
}
