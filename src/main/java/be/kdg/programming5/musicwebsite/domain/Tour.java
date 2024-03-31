package be.kdg.programming5.musicwebsite.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    @Cascade(CascadeType.PERSIST)
    private Artist artist;

    public Tour() {
    }

    public Tour(int id, String location, LocalDate date, double price) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.price = price;
    }

    public Tour(int id, String location, LocalDate date, double price, Artist artist) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.price = price;
        this.artist = artist;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        if(!artist.getTours().contains(this))
            artist.getTours().add(this);

        this.artist = artist;
    }

    @Override
    public String toString() {
        return String.format("Tour to %s with %s. (%.1f$, %s)",
                location,
                artist.getName(),
                price,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
