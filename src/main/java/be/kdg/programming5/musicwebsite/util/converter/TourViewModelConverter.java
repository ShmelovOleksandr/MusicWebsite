package be.kdg.programming5.musicwebsite.util.converter;

import be.kdg.programming5.musicwebsite.domain.Tour;
import be.kdg.programming5.musicwebsite.service.ArtistService;
import be.kdg.programming5.musicwebsite.view_model.TourViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourViewModelConverter implements ViewModelConverter<TourViewModel, Tour> {
    private final ArtistService artistService;

    @Autowired
    public TourViewModelConverter(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public Tour convertToModel(TourViewModel tourViewModel){
        return new Tour(
                tourViewModel.getId(),
                tourViewModel.getLocation(),
                tourViewModel.getDate(),
                tourViewModel.getPrice(),
                artistService.getOne(tourViewModel.getArtistId())
        );
    }

    @Override
    public TourViewModel convertToView(Tour tour) {
        return new TourViewModel(
                tour.getId(),
                tour.getLocation(),
                tour.getDate(),
                tour.getPrice(),
                tour.getArtist().getId()
        );
    }
}
