package be.kdg.programming5.musicwebsite.util.id;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;

import java.io.Serializable;
import java.util.Objects;

public class SongParticipationId implements Serializable {
    private Artist artist;
    private Song song;

    public SongParticipationId() {

    }

    public SongParticipationId(Artist artist, Song song) {
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
        SongParticipationId that = (SongParticipationId) o;
        return Objects.equals(artist, that.artist) && Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, song);
    }
}