package be.kdg.programming5.musicwebsite.security.permission_service;

import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;

public interface ArtistManipulationPermissionService {
    boolean allowedArtistCreation(WebsiteUserDetails websiteUser);
    boolean allowedArtistEdit(WebsiteUserDetails websiteUser, int artistId);
    boolean allowedArtistDelete(WebsiteUserDetails websiteUser, int artistId);
}
