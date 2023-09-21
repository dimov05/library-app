package bg.libapp.libraryapp.exceptions;

public class NoSuchDeactivateReasonException extends RuntimeException {
    public NoSuchDeactivateReasonException(String reason) {
        super("This reason '" + reason + "' is not a valid reason to deactivate a book!");
    }
}
