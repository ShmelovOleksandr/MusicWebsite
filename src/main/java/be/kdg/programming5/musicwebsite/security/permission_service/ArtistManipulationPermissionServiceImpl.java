package be.kdg.programming5.musicwebsite.security.permission_service;

import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;
import org.springframework.stereotype.Component;

@Component
public class ArtistManipulationPermissionServiceImpl implements ArtistManipulationPermissionService {

    @Override
    public boolean allowedArtistCreation(WebsiteUserDetails websiteUser) {
        if (websiteUser == null)
            return false;

        return websiteUser.isAdmin();
    }

    @Override
    public boolean allowedArtistEdit(WebsiteUserDetails websiteUser, int artistId) {
        if (websiteUser == null)
            return false;

        return websiteUser.isAdmin() || websiteUser.getArtistId() == artistId;
    }

    @Override
    public boolean allowedArtistDelete(WebsiteUserDetails websiteUser, int artistId) {
        if (websiteUser == null)
            return false;

        return websiteUser.isAdmin() || websiteUser.getArtistId() == artistId;
    }
}
