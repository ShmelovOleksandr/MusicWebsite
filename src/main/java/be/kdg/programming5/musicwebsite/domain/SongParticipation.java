package be.kdg.programming5.musicwebsite.domain;


import be.kdg.programming5.musicwebsite.util.id.SongParticipationId;
import jakarta.persistence.*;

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

    public SongParticipation(Artist artist, be.kdg.programming5.musicwebsite.domain.Song song) {
        this.artist = artist;
        this.song = song;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public be.kdg.programming5.musicwebsite.domain.Song getSong() {
        return song;
    }

    public void setSong(be.kdg.programming5.musicwebsite.domain.Song song) {
        this.song = song;
    }
}
