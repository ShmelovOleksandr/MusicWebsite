package be.kdg.programming5.musicwebsite.view_model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TourViewModel {
    private Integer id;
    @Size(min = 5, max = 30, message = "Location of the tour should be between 5 and 30 characters.")
    private String location;
    @NotNull(message = "Date should not be left empty.")
    private LocalDate date;
    @Positive(message = "Price can't be negative.")
    private Double price;
    @NotNull
    private Integer artistId;

    public TourViewModel(int id, String location, LocalDate date, Double price, Integer artistId) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.price = price;
        this.artistId = artistId;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public int getId() {
        return id;
    }
}
