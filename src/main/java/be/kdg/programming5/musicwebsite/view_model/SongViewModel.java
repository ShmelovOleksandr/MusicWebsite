package be.kdg.programming5.musicwebsite.view_model;

import be.kdg.programming5.musicwebsite.domain.Genre;
import jakarta.validation.constraints.*;

public class SongViewModel {
    private Integer id;
    @Size(min = 3, max = 30, message = "Name of the song should be between 3 and 30 characters.")
    private String name;
    @Min(value = 1, message = "Length should be at least 1.")
    @Max(value = Integer.MAX_VALUE, message = "Too big number, unable to process.")
    private Integer length;
    @NotNull(message = "You shoud specify the genre.")
    private Genre genre;

//    @NotEmpty(message = "Song should have at least one artist.")
//    private Integer[] artistsId;

    public SongViewModel(Integer id, String name, int length, Genre genre, Integer... artistsId) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.genre = genre;
//        this.artistsId = artistsId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public Genre getGenre() {
        return genre;
    }

//    public Integer[] getArtistsId() {
//        return artistsId;
//    }
}
