package be.kdg.programming5.musicwebsite.service;

import be.kdg.programming5.musicwebsite.domain.Artist;
import be.kdg.programming5.musicwebsite.util.exception.ArtistCsvParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Service
public class ArtistCsvServiceImp implements ArtistCsvService {
    private final ArtistService artistService;

    @Autowired
    public ArtistCsvServiceImp(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    @Async
    public void handleArtistsCsvUpload(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String artistName = data[0];
                LocalDate birthDate = LocalDate.parse(data[1]);
                long listeners = Long.parseLong(data[2]);
                Artist artist = new Artist(artistName, birthDate, listeners);
                artistService.save(artist);
            }
        } catch (IOException e) {
            throw new ArtistCsvParseException("Invalid CSV file.");
        }

    }
}
