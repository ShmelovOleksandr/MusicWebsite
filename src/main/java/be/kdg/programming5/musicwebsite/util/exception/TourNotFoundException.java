package be.kdg.programming5.musicwebsite.util.exception;

import jakarta.persistence.EntityNotFoundException;

public class TourNotFoundException extends EntityNotFoundException {
    public TourNotFoundException(String message) {
        super(message);
    }
}
