package be.kdg.programming5.musicwebsite.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auths -> auths
//                                .requestMatchers(regexMatcher("^/artists\\?.+"))
//                                .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/js/**"),
                                        antMatcher(HttpMethod.GET, "/webjars/**"),
                                        antMatcher(HttpMethod.GET, "/pictures/**"),
                                        antMatcher(HttpMethod.GET, "/style/**")
                                ).permitAll()

                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/"),
                                        antMatcher(HttpMethod.GET, "/artists"),
                                        antMatcher(HttpMethod.GET, "/songs"),
                                        antMatcher(HttpMethod.GET, "/tours")
                                ).permitAll()

                                .requestMatchers(
                                        regexMatcher(HttpMethod.GET, "^/artists/[0-9]*"),
                                        regexMatcher(HttpMethod.GET, "^/songs/[0-9]*"),
                                        regexMatcher(HttpMethod.GET, "^/tours/[0-9]*")
                                ).permitAll()

                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/api/**")
                                ).permitAll()

                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/registration"),
                                        antMatcher(HttpMethod.POST, "/register")
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(
                        logoutConfigurer -> logoutConfigurer
                                .logoutUrl("/logout")
                                .permitAll()
                                .logoutSuccessUrl("/")
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(
                                (request, response, exception) -> {
                                    if (request.getRequestURI().startsWith("/api")) {
                                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    } else {
                                        response.sendRedirect(request.getContextPath() + "/login");
                                    }
                                })
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

}
