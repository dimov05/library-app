package bg.libapp.libraryapp.exceptions.book;

public class BookIsActiveOnDeleteException extends RuntimeException {
    public BookIsActiveOnDeleteException(String isbn) {
        super("Book with this isbn: '" + isbn + "' is with active status and can not be deleted from library!");
    }
}