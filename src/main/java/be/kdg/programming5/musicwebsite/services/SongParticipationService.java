package be.kdg.programming5.musicwebsite.services;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.SongParticipation;
import be.kdg.programming5.musicwebsite.util.ids.SongParticipationId;

import java.util.Set;

public interface SongParticipationService extends GeneralService<SongParticipation, SongParticipationId>{
    Set<SongParticipation> getAllByArtistName(String name);
    Set<SongParticipation> getAllByArtist(Artist artist);
}
