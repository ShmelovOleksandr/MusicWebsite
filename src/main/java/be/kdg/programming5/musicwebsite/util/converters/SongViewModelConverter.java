package be.kdg.programming5.musicwebsite.util.converters;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.domain.SongParticipation;
import be.kdg.programming5.musicwebsite.services.ArtistService;
import be.kdg.programming5.musicwebsite.services.SongParticipationService;
import be.kdg.programming5.musicwebsite.util.ids.SongParticipationId;
import be.kdg.programming5.musicwebsite.view_model.SongViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        Song song = new Song(
                songViewModel.getId(),
                songViewModel.getName(),
                songViewModel.getLength(),
                songViewModel.getGenre()
        );
//        Arrays.stream(songViewModel.getArtistsId()).map(artistService::getOne).forEach(song::addArtist);
        song.setSongParticipations(songViewModel.getArtists().stream().map(artistViewModel -> songParticipationService.getOne(new SongParticipationId(artistService.getArtistByName(artistViewModel.getName()), song))).collect(Collectors.toSet()));

        return song;
    }

    @Override
    public SongViewModel convertToView(Song song) {
//        Integer[] artistsIds = song.getSongParticipations().stream().map(songParticipation -> songParticipation.getArtist().getId()).toArray(Integer[]::new);
        return new SongViewModel(
                song.getId(),
                song.getName(),
                song.getLength(),
                song.getGenre(),
null
//                TODO
//                song.getArtists().stream().map(artist -> )
//                artistsIds
        );
    }
}
