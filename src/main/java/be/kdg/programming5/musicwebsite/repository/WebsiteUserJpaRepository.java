package be.kdg.programming5.musicwebsite.repository;

import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WebsiteUserJpaRepository extends JpaRepository<WebsiteUser, Integer> {
    @Query("select wu from WebsiteUser wu " +
            "left join fetch wu.artist " +
            "where wu.username = :username ")
    Optional<WebsiteUser> findByUsernameFetched(String username);

    boolean existsByUsername(String username);
}
