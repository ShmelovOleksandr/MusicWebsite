package be.kdg.programming5.musicwebsite.view_model;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ArtistViewModel {
    private Integer id;

    @Size(min = 3, max = 18, message = "Name of the artist should be between 3 and 18 characters.")
    private String name;

    @NotNull(message = "Date should not be left empty.")
    @Past(message = "Date should be in the past.")
    private LocalDate birthDate;

    @Min(value = 0, message = "Number of listeners should be greater than 0.")
    @Max(value = Long.MAX_VALUE, message = "Too big number, unable to process.")
    private long listeners;

    public ArtistViewModel(Integer id, String name, LocalDate birthDate, long listeners) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.listeners = listeners;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public long getListeners() {
        return listeners;
    }
}
