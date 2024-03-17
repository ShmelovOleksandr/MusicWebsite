package be.kdg.programming5.musicwebsite.api.dto;

import be.kdg.programming5.musicwebsite.domain.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SongDTO {
    private int id;

    @Size(min = 3, max = 30, message = "Name of the song should be between 3 and 30 characters.")
    private String name;

    @Min(value = 1, message = "Length should be at least 1.")
    @Max(value = Integer.MAX_VALUE, message = "Too big number, unable to process.")
    private Integer length;

    @NotNull(message = "You should specify the genre.")
    private Genre genre;
    private String formattedLength;

    public SongDTO() {
    }

    public SongDTO(int id, String name, Integer length, Genre genre) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getFormattedLength() {
        return formattedLength;
    }

    public void setFormattedLength(String formattedLength) {
        this.formattedLength = formattedLength;
    }
}
