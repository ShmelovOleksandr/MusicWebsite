package be.kdg.programming5.musicwebsite.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "website_user")
public class WebsiteUser {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "username", nullable = false)
    protected String username;
    @Column(name = "password", nullable = false)
    private String password;

    public WebsiteUser() {
    }

    public WebsiteUser(int userId) {
        this.userId = userId;
    }

    public WebsiteUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public WebsiteUser(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebsiteUser that = (WebsiteUser) o;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
