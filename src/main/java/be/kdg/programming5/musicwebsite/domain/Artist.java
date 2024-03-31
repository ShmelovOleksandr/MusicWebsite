package be.kdg.programming5.musicwebsite.domain;

import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artist")
public class Artist implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "listeners")
    private long listeners;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private WebsiteUser websiteUser;


    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private Set<SongParticipation> songParticipations;


    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private Set<Tour> tours;

    public Artist() {
        this.songParticipations = new HashSet<>();
        this.tours = new HashSet<>();
    }

    public Artist(int id, String name, LocalDate birthDate, long listeners) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.listeners = listeners;
    }

    public void addSong(Song song){
        if(this.songParticipations == null)
            this.songParticipations = new HashSet<>();

        SongParticipation songParticipation = new SongParticipation(this, song);
        this.songParticipations.add(songParticipation);
        song.getSongParticipations().add(songParticipation);
    }

    public void removeSong(Song song){
        if(this.songParticipations == null)
            return;

        this.songParticipations.removeIf(songParticipation -> songParticipation.getSong().equals(song));
        song.getSongParticipations().removeIf(songParticipation -> songParticipation.getSong().equals(song));
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

    public void setId(int artistId) {
        this.id = artistId;
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

    public WebsiteUser getWebsiteUser() {
        return websiteUser;
    }

    public void setWebsiteUser(WebsiteUser websiteUser) {
        this.websiteUser = websiteUser;
    }

    public Set<SongParticipation> getSongParticipations() {
        return songParticipations;
    }

    public void setSongParticipations(Set<SongParticipation> songParticipations) {
        this.songParticipations = songParticipations;
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
}
