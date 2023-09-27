package bg.libapp.libraryapp.exceptions.rent;

public class CannotRentBookTwiceException extends RuntimeException {
    public CannotRentBookTwiceException(String isbn) {
        super("Cannot rent book with this isbn: '" + isbn + "' twice!");
    }
}