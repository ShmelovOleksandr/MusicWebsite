package be.kdg.programming5.musicwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MusicWebsiteApplication {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
        SpringApplication.run(MusicWebsiteApplication.class, args);
    }

}
