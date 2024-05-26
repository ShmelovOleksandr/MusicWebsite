package be.kdg.programming5.musicwebsite.controller;

import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;
import be.kdg.programming5.musicwebsite.security.permission_service.MvcArtistAccessPermissionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artists")
public class ArtistController extends DownloadController {
    @Value("${download.json-file.name.artists}")
    private String ARTIST_JSON_FILE_NAME;

    private final MvcArtistAccessPermissionService mvcArtistAccessPermissionService;

    public ArtistController(MvcArtistAccessPermissionService mvcArtistAccessPermissionService) {
        this.mvcArtistAccessPermissionService = mvcArtistAccessPermissionService;
        this.fileName = ARTIST_JSON_FILE_NAME;
    }

    @GetMapping
    public String getArtistsPage(Model model,
                                 @AuthenticationPrincipal WebsiteUserDetails userDetails) {
        model.addAttribute("artistAddAllowed", userDetails != null && userDetails.isAdmin());
        return "view/artists/artists";
    }

    @GetMapping("/{id}")
    public String getArtistsDetailsPage(@PathVariable int id,
                                        Model model,
                                        @AuthenticationPrincipal WebsiteUserDetails userDetails){
        model.addAttribute("modificationAllowed", (userDetails != null && userDetails.getArtistId() == id) || (userDetails != null && userDetails.isAdmin()) );
        return "view/artists/artistDetails";
    }

    @GetMapping("/new")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getArtistCreatorPage(@AuthenticationPrincipal WebsiteUserDetails websiteUserDetails){
        if(!mvcArtistAccessPermissionService.allowedToSeeCreatorPage(websiteUserDetails))
            return "redirect:/artists";
        return "view/artists/artistCreator";
    }

    @GetMapping("/{id}/editor")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ARTIST')")
    public String getArtistEditorPage(@PathVariable int id, @AuthenticationPrincipal WebsiteUserDetails websiteUserDetails){
        if(!mvcArtistAccessPermissionService.allowedToSeeEditorPage(websiteUserDetails, id))
            return "redirect:/artists/" + id;
        return "view/artists/artistEditor";
    }

}
