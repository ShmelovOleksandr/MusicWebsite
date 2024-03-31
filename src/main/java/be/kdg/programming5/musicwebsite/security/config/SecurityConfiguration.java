package be.kdg.programming5.musicwebsite.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auths -> auths
                                .requestMatchers(regexMatcher("^/artists\\?.+"))
                                .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/js/**"),
                                        antMatcher(HttpMethod.GET, "/webjars/**"),
                                        antMatcher(HttpMethod.GET, "/pictures/**"),
                                        antMatcher(HttpMethod.GET, "/style/**"))
                                .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/"),
                                        antMatcher(HttpMethod.DELETE, "/api/**"),
                                        antMatcher(HttpMethod.GET, "/")
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(formLogin -> formLogin.permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}
