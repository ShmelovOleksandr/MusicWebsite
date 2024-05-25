package be.kdg.programming5.musicwebsite.domain;


import be.kdg.programming5.musicwebsite.util.id.SongParticipationId;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "artist_song")
@IdClass(SongParticipationId.class)
public class SongParticipation {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private Artist artist;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private Song song;

    public SongParticipation() {
    }

    public SongParticipation(Artist artist, Song song) {
        this.artist = artist;
        this.song = song;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongParticipation that = (SongParticipation) o;
        return Objects.equals(artist, that.artist) && Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, song);
    }
}
