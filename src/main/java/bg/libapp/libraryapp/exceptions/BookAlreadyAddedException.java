package bg.libapp.libraryapp.exceptions;

public class BookAlreadyAddedException extends RuntimeException{
    public BookAlreadyAddedException(String message) {
        super(message);
    }
}
