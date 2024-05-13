package be.kdg.programming5.musicwebsite.security.permission_service;

public interface ArtistManipulationPermissionService {
    boolean allowedArtistCreation(String username);
    boolean allowedArtistEdit(String username, int artistId);
    boolean allowedArtistDelete(String username, int artistId);
}
