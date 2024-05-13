package be.kdg.programming5.musicwebsite.api.dto.patch;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ArtistPatchDTO {
    @Size(min = 3, max = 18, message = "Name of the artist should be between 3 and 18 characters.")
    private String name;

    @NotNull(message = "Date should not be left empty.")
    @Past(message = "Date should be in the past.")
    private LocalDate birthDate;

    @Min(value = 0, message = "Number of listeners should be greater than 0.")
    @Max(value = Long.MAX_VALUE, message = "Too big number, unable to process.")
    private long listeners;

    public ArtistPatchDTO() {
    }

    public ArtistPatchDTO(String name, LocalDate birthDate, long listeners) {
        this.name = name;
        this.birthDate = birthDate;
        this.listeners = listeners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public long getListeners() {
        return listeners;
    }

    public void setListeners(long listeners) {
        this.listeners = listeners;
    }
}
