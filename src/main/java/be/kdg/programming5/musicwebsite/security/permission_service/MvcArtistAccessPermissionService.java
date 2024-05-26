package be.kdg.programming5.musicwebsite.security.permission_service;

import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;

public interface MvcArtistAccessPermissionService {
    boolean allowedToSeeCreatorPage(WebsiteUserDetails websiteUser);
    boolean allowedToSeeEditorPage(WebsiteUserDetails websiteUser, int artistId);
}
