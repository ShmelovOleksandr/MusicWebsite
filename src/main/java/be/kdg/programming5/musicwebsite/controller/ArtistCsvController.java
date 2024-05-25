package be.kdg.programming5.musicwebsite.controller;

import be.kdg.programming5.musicwebsite.service.ArtistCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;



@Controller
@RequestMapping("/artists_csv")
public class ArtistCsvController {
    private final ArtistCsvService artistCsvService;

    @Autowired
    public ArtistCsvController(ArtistCsvService artistCsvService) {
        this.artistCsvService = artistCsvService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView uploadCsvPage() {
        var mav = new ModelAndView("view/artists/aristCsvUploader");
        mav.getModel().put("inProgress", false);
        return mav;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CacheEvict(value = {"all_artists_search", "name_part_artists_search", "min_listeners_artists_search", "name_part_and_min_listeners_artists_search"}, allEntries = true)
    public ModelAndView uploadCsv(
            @RequestParam("artists_csv") MultipartFile file)
            throws IOException {
        artistCsvService.handleArtistsCsvUpload(file.getInputStream());
        var mav = new ModelAndView("view/artists/aristCsvUploader");
        mav.getModel().put("inProgress", true);
        return mav;
    }
}
