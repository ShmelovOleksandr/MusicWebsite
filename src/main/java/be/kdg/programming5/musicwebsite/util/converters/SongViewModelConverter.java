package be.kdg.programming5.musicwebsite.util.converters;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.services.ArtistService;
import be.kdg.programming5.musicwebsite.view_model.SongViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SongViewModelConverter implements ViewModelConverter<SongViewModel, Song> {
    private final ArtistService artistService;

    @Autowired
    public SongViewModelConverter(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public Song convertToModel(SongViewModel songViewModel){
        Set<Artist> artists = new HashSet<>();
        for (int id : songViewModel.getArtistsId())
            artists.add(artistService.getOne(id));

        return new Song(songViewModel.getId(), songViewModel.getName(), songViewModel.getLength(), songViewModel.getGenre(), artists);
    }

    @Override
    public SongViewModel convertToView(Song song) {
        Integer[] artistsIds = song.getArtists().stream().map(Artist::getId).toArray(Integer[]::new);

        return new SongViewModel(song.getId(), song.getName(), song.getLength(), song.getGenre(), artistsIds);
    }
}
