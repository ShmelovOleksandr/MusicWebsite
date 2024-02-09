package be.kdg.programming5.musicwebsite.util.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ArtistNotFoundException extends EntityNotFoundException {
    public ArtistNotFoundException(String message) {
        super(message);
    }
}
