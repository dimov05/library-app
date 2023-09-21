package bg.libapp.libraryapp.exceptions;

public class BookNotActiveException extends RuntimeException {
    public BookNotActiveException(String isbn) {
        super("Book with this isbn: '" + isbn + "' is not with active status in library!");
    }
}