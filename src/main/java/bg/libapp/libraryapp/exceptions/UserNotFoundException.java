package bg.libapp.libraryapp.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with this id :'" + id + "' is not found!");
    }
}
