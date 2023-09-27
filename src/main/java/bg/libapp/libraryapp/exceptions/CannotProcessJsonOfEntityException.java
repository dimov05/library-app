package bg.libapp.libraryapp.exceptions;

public class CannotProcessJsonOfEntityException extends RuntimeException {
    public CannotProcessJsonOfEntityException(Object entity) {
        super("Cannot process JSON of '" + entity.getClass().getSimpleName() + "'");
    }
}
