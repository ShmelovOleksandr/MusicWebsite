package be.kdg.programming5.musicwebsite.security.detail;

import be.kdg.programming5.musicwebsite.domain.Artist;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class ArtistDetails implements UserDetails {
    private final Artist artist;

    public ArtistDetails(Artist artist) {
        this.artist = artist;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO
        return null;
    }

    @Override
    public String getPassword() {
        return this.artist.getWebsiteUser().getPassword();
    }

    @Override
    public String getUsername() {
        return this.artist.getWebsiteUser().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        //TODO
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //TODO
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //TODO
        return true;
    }

    @Override
    public boolean isEnabled() {
        //TODO
        return true;
    }

    public Artist getArtist() {
        return artist;
    }
}
