package be.kdg.programming5.musicwebsite.util.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class SongNotFoundException extends EntityNotFoundException {
    public SongNotFoundException(String message) {
        super(message);
    }
}
