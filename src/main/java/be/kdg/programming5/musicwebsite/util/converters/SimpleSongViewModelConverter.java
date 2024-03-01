package be.kdg.programming5.musicwebsite.util.converters;

import be.kdg.programming5.musicwebsite.domain.Song;
import be.kdg.programming5.musicwebsite.view_model.SimpleSongViewModel;
import org.springframework.stereotype.Component;

@Component
public class SimpleSongViewModelConverter implements ViewModelConverter<SimpleSongViewModel, Song> {

    @Override
    public Song convertToModel(SimpleSongViewModel simpleSongViewModel) {
        return new Song(
                simpleSongViewModel.getId(),
                simpleSongViewModel.getName(),
                simpleSongViewModel.getLength(),
                simpleSongViewModel.getGenre()
        );
    }

    @Override
    public SimpleSongViewModel convertToView(Song song) {
        return new SimpleSongViewModel(
                song.getId(),
                song.getName(),
                song.getLength(),
                song.getGenre()
        );
    }
}
