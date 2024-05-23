package be.kdg.programming5.musicwebsite.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "length")
    private Integer length;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "song")
    private Set<SongParticipation> songParticipations;

    public Song() {
        this.songParticipations = new HashSet<>();
    }

    public Song(int id) {
        this.id = id;
    }

    public Song(int id, String name, Integer length, Genre genre) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.genre = genre;
        this.songParticipations = new HashSet<>();
    }

    public Song(int id, String name, int length, Genre genre, Set<SongParticipation> songParticipations) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.genre = genre;
        this.songParticipations = songParticipations;
    }


    public void addArtist(Artist artist){
        if(this.songParticipations == null)
            this.songParticipations = new HashSet<>();

        this.songParticipations.add(new SongParticipation(artist, this));
        artist.getSongParticipations().add(new SongParticipation(artist, this));
    }

    public void removeArtist(Artist artist){
        if(this.songParticipations == null)
            return;

        this.songParticipations.removeIf(songParticipation -> songParticipation.getArtist().equals(artist));
        artist.getSongParticipations().removeIf(songParticipation -> songParticipation.getArtist().equals(artist));
    }

    public Set<Artist> getArtists() {
        return getSongParticipations().stream()
                .map(SongParticipation::getArtist)
                .collect(Collectors.toSet());
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

    public Set<SongParticipation> getSongParticipations() {
        if(songParticipations == null)
            songParticipations = new HashSet<>();

        return songParticipations;
    }

    public void setSongParticipations(Set<SongParticipation> artists) {
        this.songParticipations = artists;
    }

    public String getArtistsString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (SongParticipation songParticipation : songParticipations)
            stringBuilder.append(songParticipation.getArtist().getName()).append(", ");

        int lastComaIndex = stringBuilder.length()-2;
        return stringBuilder.substring(0, Math.max(lastComaIndex, 0));
    }

    public String getFormattedLength() {
        return String.format("%2d:%2d", length / 60, length % 60).replaceAll("\\s", "0");
    }

    @Override
    public String toString() {
        return String.format("\"%s\" by %s (%s)",  name, getArtistsString(), getFormattedLength());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
