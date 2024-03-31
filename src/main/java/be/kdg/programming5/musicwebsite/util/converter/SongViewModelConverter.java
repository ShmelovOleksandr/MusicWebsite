package be.kdg.programming5.musicwebsite.util.converter;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.domain.SongParticipation;
import be.kdg.programming5.musicwebsite.service.ArtistService;
import be.kdg.programming5.musicwebsite.service.SongParticipationService;
import be.kdg.programming5.musicwebsite.util.id.SongParticipationId;
import be.kdg.programming5.musicwebsite.view_model.SongViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SongViewModelConverter implements ViewModelConverter<SongViewModel, Song> {
    private final ArtistService artistService;
    private final SongParticipationService songParticipationService;

    @Autowired
    public SongViewModelConverter(ArtistService artistService, SongParticipationService songParticipationService) {
        this.artistService = artistService;
        this.songParticipationService = songParticipationService;
    }

    @Override
    public Song convertToModel(SongViewModel songViewModel){
        Song song = new Song(songViewModel.getId(), songViewModel.getName(), songViewModel.getLength(), songViewModel.getGenre());
        Set<SongParticipation> songParticipations = new HashSet<>();
        for (int id : songViewModel.getArtistsId())
            songParticipations.add(songParticipationService.getOne(new SongParticipationId(artistService.getOne(id), song)));

        song.setSongParticipations(songParticipations);
        return song;
    }

    @Override
    public SongViewModel convertToView(Song song) {
        Integer[] artistsIds = song.getArtists().stream().map(Artist::getArtistId).toArray(Integer[]::new);

        return new SongViewModel(song.getId(), song.getName(), song.getLength(), song.getGenre(), artistsIds);
    }
}
