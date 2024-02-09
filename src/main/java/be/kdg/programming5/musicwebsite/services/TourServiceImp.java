package be.kdg.programming5.musicwebsite.services;

import be.kdg.programming5.musicwebsite.domain.Tour;
import be.kdg.programming5.musicwebsite.repositories.TourJpaRepository;
import be.kdg.programming5.musicwebsite.util.exceptions.TourNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Profile("jpa")
public class TourServiceImp implements TourService {
    private final TourJpaRepository tourJpaRepository;

    @Autowired
    public TourServiceImp(TourJpaRepository tourJpaRepository) {
        this.tourJpaRepository = tourJpaRepository;
    }

    @Override
    public List<Tour> getAll() {
        return tourJpaRepository.findAll();
    }

    @Override
    public List<Tour> getAll(String artistNamePart) {
        if(artistNamePart == null || artistNamePart.isBlank())
            return getAll();

        return tourJpaRepository.findAllByArtist_NameContainingIgnoreCase(artistNamePart);
    }

    @Override
    public Tour getOne(Integer id) {
        return tourJpaRepository.findById(id).orElseThrow(
                () -> new TourNotFoundException("No tours with given id have been found.")
        );
    }

    @Override
    @Transactional
    public Tour save(Tour tour) {
        return tourJpaRepository.save(tour);
    }

    @Override
    @Transactional
    public Tour update(Integer id, Tour tour) {
        if(!tourJpaRepository.existsById(id))
            throw new TourNotFoundException("Cannot update. Tour with given id does not exist.");

        tour.setId(id);
        return tourJpaRepository.save(tour);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        tourJpaRepository.deleteById(id);
    }
}
