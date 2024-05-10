package be.kdg.programming5.musicwebsite.domain.user;

import be.kdg.programming5.musicwebsite.domain.Artist;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "website_user")
public class WebsiteUser {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    protected String username;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @OneToOne(mappedBy = "websiteUser", fetch = FetchType.LAZY)
    private Artist artist;

    public WebsiteUser() {
    }

    public WebsiteUser(int id) {
        this.id = id;
    }

    public WebsiteUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int userId) {
        this.id = userId;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebsiteUser that = (WebsiteUser) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
