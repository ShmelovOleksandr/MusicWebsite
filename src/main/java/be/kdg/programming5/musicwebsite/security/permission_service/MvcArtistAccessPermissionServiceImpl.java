package be.kdg.programming5.musicwebsite.security.permission_service;

import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;
import org.springframework.stereotype.Component;

@Component
public class MvcArtistAccessPermissionServiceImpl implements MvcArtistAccessPermissionService {
    @Override
    public boolean allowedToSeeCreatorPage(WebsiteUserDetails websiteUser) {
        if (websiteUser == null)
            return false;

        return websiteUser.isAdmin();
    }

    @Override
    public boolean allowedToSeeEditorPage(WebsiteUserDetails websiteUser, int artistId) {
        if (websiteUser == null)
            return false;

        return websiteUser.isAdmin() || websiteUser.getArtistId() == artistId;
    }
}
