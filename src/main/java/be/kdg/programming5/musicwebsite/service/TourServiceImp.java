package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Tour;
import be.kdg.programming5.musicwebsite.repository.TourJpaRepository;
import be.kdg.programming5.musicwebsite.util.exception.TourNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
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
    public List<Tour> getAllWithArtistFetched() {
        return tourJpaRepository.findAllWithArtistFetched();
    }

    @Override
    public List<Tour> getAllWithArtistFetched(String artistNamePart) {
        if(artistNamePart == null || artistNamePart.isBlank())
            return getAllWithArtistFetched();

        return tourJpaRepository.findAllByArtist_NameContainingIgnoreCase(artistNamePart);
    }

    @Override
    @Transactional
    public void deleteAllByArtistId(int artistId) {
        tourJpaRepository.deleteAllByArtist_Id(artistId);
    }

    @Override
    public List<Tour> getAllByArtistId(int id) {
        return tourJpaRepository.findAllByArtist_Id(id);
    }

    @Override
    public Tour getOne(Integer id) {
        return tourJpaRepository.findByIdWithArtistFetched(id).orElseThrow(
                () -> new TourNotFoundException("No tours with given id have been found.")
        );
    }

    @Override
    @Transactional
    public Tour save(Tour tour) {
        return tourJpaRepository.save(tour);
    }

    @Override
    public List<Tour> saveAll(Iterable<Tour> tours) {
        return tourJpaRepository.saveAll(tours);
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

    @Override
    public void deleteAll() {
        tourJpaRepository.deleteAll();
    }
}
