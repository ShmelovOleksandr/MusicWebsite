package be.kdg.programming5.musicwebsite.api.dto.post;

import jakarta.validation.constraints.Size;

public class WebsiteUserPostDTO {
    @Size(min = 2, max = 100)
    private String username;
    @Size(min = 4, max = 254)
    private String password;

    public WebsiteUserPostDTO() {
    }

    public WebsiteUserPostDTO(String username, String password) {
        this.username = username;
        this.password = password;
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
}
