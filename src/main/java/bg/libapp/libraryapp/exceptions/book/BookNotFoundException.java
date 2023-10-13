package bg.libapp.libraryapp.exceptions.book;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super("Book with this isbn: '" + isbn + "' is not present in library!");
    }
}