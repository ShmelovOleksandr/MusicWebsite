package be.kdg.programming5.musicwebsite.security.permission_service;

public interface MvcArtistAccessPermissionService {
    boolean allowToSeeCreatorPage(String username);
    boolean allowToSeeEditorPage(int artistId, String username);
}
