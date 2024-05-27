package be.kdg.programming5.musicwebsite.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auths -> auths
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/js/**"),
                                        antMatcher(HttpMethod.GET, "/css/**"),
                                        antMatcher(HttpMethod.GET, "/pictures/**")
                                ).permitAll()

                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/registration"),
                                        antMatcher(HttpMethod.POST, "/register")
                                ).permitAll()

                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/"),
                                        antMatcher(HttpMethod.GET, "/artists"),
                                        antMatcher(HttpMethod.GET, "/songs"),
                                        antMatcher(HttpMethod.GET, "/tours")
                                ).permitAll()

                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/api/**")
                                ).permitAll()

                                // Required for week9 Client POST
                                .requestMatchers(
                                        antMatcher(HttpMethod.POST, "/api/artists")
                                ).permitAll()

                                .requestMatchers(
                                        regexMatcher(HttpMethod.GET, "^/artists/[0-9]*"),
                                        regexMatcher(HttpMethod.GET, "^/songs/[0-9]*"),
                                        regexMatcher(HttpMethod.GET, "^/tours/[0-9]*")
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

                // Required for week9 Client POST
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        antMatcher(HttpMethod.POST, "/api/artists")
                ))

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
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:9000")
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name());
            }
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
