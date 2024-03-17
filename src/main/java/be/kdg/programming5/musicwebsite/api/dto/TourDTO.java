package be.kdg.programming5.musicwebsite.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TourDTO {
    private int id;
    @Size(min = 5, max = 30, message = "Location of the tour should be between 5 and 30 characters.")
    private String location;
    @NotNull(message = "Date should not be left empty.")
    private LocalDate date;
    @Positive(message = "Price can't be negative.")
    private double price;

    public TourDTO() {
    }

    public TourDTO(int id, String location, LocalDate date, double price) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
