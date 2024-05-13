package be.kdg.programming5.musicwebsite.security.permission_service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import be.kdg.programming5.musicwebsite.service.WebsiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MvcArtistAccessPermissionServiceImpl implements MvcArtistAccessPermissionService {
    private final WebsiteUserService websiteUserService;

    @Autowired
    public MvcArtistAccessPermissionServiceImpl(WebsiteUserService websiteUserService) {
        this.websiteUserService = websiteUserService;
    }

    @Override
    public boolean allowToSeeCreatorPage(String username) {
        WebsiteUser websiteUser = websiteUserService.getOneByNameFetched(username);
        return websiteUser.isAdmin();
    }

    @Override
    public boolean allowToSeeEditorPage(int artistId, String username) {
        WebsiteUser websiteUser = websiteUserService.getOneByNameFetched(username);
        if (websiteUser.isAdmin())
            return true;

        Artist artist = websiteUser.getArtist();
        if (artist != null)
            return artist.getId() == artistId;
        return false;
    }
}
