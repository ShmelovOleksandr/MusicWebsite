package be.kdg.programming5.musicwebsite.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "listeners")
    private long listeners;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.EAGER)
    @Cascade(CascadeType.PERSIST)
    private Set<Song> songs;


    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    @Cascade(CascadeType.PERSIST)
    private Set<Tour> tours;

    public Artist() {
        this.songs = new HashSet<>();
        this.tours = new HashSet<>();
    }

    public Artist(int id, String name, LocalDate birthDate, long listeners) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.listeners = listeners;
        this.songs = new HashSet<>();
        this.tours = new HashSet<>();
    }

    public void addSong(Song song){
        if(this.songs == null)
            this.songs = new HashSet<>();

        this.songs.add(song);
        song.getArtists().add(this);
    }

    public void removeSong(Song song){
        if(this.songs == null)
            return;

        this.songs.remove(song);
        song.getArtists().remove(this);
    }

    public void addTour(Tour tour){
        if(this.tours == null)
            this.tours = new HashSet<>();

        this.tours.add(tour);
        tour.setArtist(this);
    }

    public void removeTour(Tour tour){
        if(this.tours == null)
            return;

        this.tours.remove(tour);
        tour.setArtist(null);
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

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id == artist.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
