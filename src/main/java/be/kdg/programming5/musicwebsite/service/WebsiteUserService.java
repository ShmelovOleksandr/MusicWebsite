package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface WebsiteUserService extends UserDetailsService {
    WebsiteUser registerUser(String username, String password);
}
