package be.kdg.programming5.musicwebsite.security.detail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class WebsiteUserDetails extends User {
    private int artistId;
    private boolean isAdmin;


    public WebsiteUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, int artistId, boolean isAdmin) {
        super(username, password, authorities);
        this.artistId = artistId;
        this.isAdmin = isAdmin;
    }

    public WebsiteUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, int artistId, boolean isAdmin) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.artistId = artistId;
        this.isAdmin = isAdmin;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "WebsiteUserDetails{" +
                "artistId=" + artistId +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
