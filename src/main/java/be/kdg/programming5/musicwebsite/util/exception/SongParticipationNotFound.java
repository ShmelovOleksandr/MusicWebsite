package be.kdg.programming5.musicwebsite.util.exception;

import jakarta.persistence.EntityNotFoundException;

public class SongParticipationNotFound extends EntityNotFoundException {
    public SongParticipationNotFound(String message) {
        super(message);
    }
}
