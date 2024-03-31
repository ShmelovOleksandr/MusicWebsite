package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import be.kdg.programming5.musicwebsite.repository.WebsiteUserJpaRepository;
import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class WebsiteUserServiceImp implements WebsiteUserService {
    private final WebsiteUserJpaRepository websiteUserJpaRepository;

    @Autowired
    public WebsiteUserServiceImp(WebsiteUserJpaRepository websiteUserJpaRepository) {
        this.websiteUserJpaRepository = websiteUserJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebsiteUser user = websiteUserJpaRepository.findByUsernameFetched(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found.")
        );

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.isAdmin())
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (user.getArtist() != null)
            authorities.add(new SimpleGrantedAuthority("ROLE_ARTIST"));

        return new WebsiteUserDetails(user.getUsername(), user.getPassword(), authorities);
    }
}
