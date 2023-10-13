package bg.libapp.libraryapp.exceptions.book;

public class IsbnNotValidException extends RuntimeException {
    public IsbnNotValidException(String isbn) {
        super("This isbn: '" + isbn + "' is not a valid isbn!");
    }
}