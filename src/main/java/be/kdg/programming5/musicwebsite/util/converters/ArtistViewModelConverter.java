package be.kdg.programming5.musicwebsite.util.converters;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.view_model.ArtistViewModel;
import org.springframework.stereotype.Component;

@Component
public class ArtistViewModelConverter implements ViewModelConverter<ArtistViewModel, Artist> {
    @Override
    public Artist convertToModel(ArtistViewModel artistViewModel){
        Artist artist = new Artist();
        artist.setId(artistViewModel.getId());
        artist.setName(artistViewModel.getName());
        artist.setBirthDate(artistViewModel.getBirthDate());
        artist.setListeners(artistViewModel.getListeners());
        return artist;
    }

    @Override
    public ArtistViewModel convertToView(Artist artist) {
        return new ArtistViewModel(
                artist.getId(),
                artist.getName(),
                artist.getBirthDate(),
                artist.getListeners()
        );
    }
}
