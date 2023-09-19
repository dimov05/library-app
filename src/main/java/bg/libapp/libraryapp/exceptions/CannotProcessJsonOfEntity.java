package bg.libapp.libraryapp.exceptions;

public class CannotProcessJsonOfEntity extends RuntimeException {
    public CannotProcessJsonOfEntity(Object entity) {
        super("Cannot process JSON of '" + entity.getClass().getSimpleName() + "'");
    }
}
