package be.kdg.programming5.musicwebsite.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "artist_song",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    @Cascade(CascadeType.PERSIST)
    private Set<Artist> artists;

    public Song() {
        this.artists = new HashSet<>();
    }

    public Song(int id, String name, Integer length, Genre genre) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.genre = genre;
        this.artists = new HashSet<>();
    }

    public Song(int id, String name, int length, Genre genre, Set<Artist> artists) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.genre = genre;
        this.artists = artists;
    }


    public void addArtist(Artist artist){
        if(this.artists == null)
            this.artists = new HashSet<>();

        this.artists.add(artist);
        artist.getSongs().add(this);
    }

    public void removeArtist(Artist artist){
        if(this.artists == null)
            return;

        this.artists.remove(artist);
        artist.getSongs().remove(this);
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

    public Set<Artist> getArtists() {
        if(artists == null)
            artists = new HashSet<>();

        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public String getArtistsString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Artist artist : artists)
            stringBuilder.append(artist.getName()).append(", ");

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
