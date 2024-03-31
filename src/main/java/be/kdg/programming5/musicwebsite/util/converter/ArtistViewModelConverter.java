package be.kdg.programming5.musicwebsite.util.converter;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.view_model.ArtistViewModel;
import org.springframework.stereotype.Component;

@Component
public class ArtistViewModelConverter implements ViewModelConverter<ArtistViewModel, Artist> {

    @Override
    public Artist convertToModel(ArtistViewModel artistViewModel){
        return new Artist(
                artistViewModel.getId(),
                artistViewModel.getName(),
                artistViewModel.getBirthDate(),
                artistViewModel.getListeners()
        );
    }

    @Override
    public ArtistViewModel convertToView(Artist artist) {
        return new ArtistViewModel(
                artist.getArtistId(),
                artist.getName(),
                artist.getBirthDate(),
                artist.getListeners()
        );
    }
}
