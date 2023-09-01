package bg.libapp.libraryapp.exceptions;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(String message) {
        super(message);
    }
}
