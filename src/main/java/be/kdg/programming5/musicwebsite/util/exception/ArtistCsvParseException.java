package be.kdg.programming5.musicwebsite.util.exception;

import java.io.IOException;

public class ArtistCsvParseException extends RuntimeException {
    public ArtistCsvParseException(String message) {
        super(message);
    }
}
