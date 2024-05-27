package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import be.kdg.programming5.musicwebsite.repository.WebsiteUserJpaRepository;
import be.kdg.programming5.musicwebsite.security.detail.WebsiteUserDetails;
import be.kdg.programming5.musicwebsite.util.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class WebsiteUserServiceImp implements WebsiteUserService {
    private final PasswordEncoder passwordEncoder;
    private final WebsiteUserJpaRepository websiteUserJpaRepository;

    @Autowired
    public WebsiteUserServiceImp(PasswordEncoder passwordEncoder, WebsiteUserJpaRepository websiteUserJpaRepository) {
        this.passwordEncoder = passwordEncoder;
        this.websiteUserJpaRepository = websiteUserJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebsiteUser user = websiteUserJpaRepository.findByUsernameFetched(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found.")
        );

        boolean isAdmin = false;
        int artistId = 0;
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            isAdmin = true;
        }

        if (user.getArtist() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ARTIST"));
            artistId = user.getArtist().getId();
        }

        return new WebsiteUserDetails(user.getUsername(), user.getPassword(), authorities, artistId, isAdmin);
    }

    @Override
    public WebsiteUser getOneByNameFetched(String username) {
        return websiteUserJpaRepository.findByUsernameFetched(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found.")
        );
    }

    @Override
    @Transactional
    public WebsiteUser registerUser(String username, String password) {
        if (websiteUserJpaRepository.existsByUsername(username))
            throw new UserAlreadyExistsException();

        return websiteUserJpaRepository.save(new WebsiteUser(username, passwordEncoder.encode(password)));
    }
}
