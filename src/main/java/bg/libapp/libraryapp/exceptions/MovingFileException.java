package bg.libapp.libraryapp.exceptions;

public class MovingFileException extends RuntimeException {
    public MovingFileException(String fileName, String message) {
        super(String.format("Error moving file: %s, with message: %s", fileName, message));
    }
}