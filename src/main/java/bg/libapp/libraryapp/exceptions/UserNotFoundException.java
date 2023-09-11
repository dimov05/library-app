package bg.libapp.libraryapp.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with this id :'" + id + "' is not found!");
    }
    public UserNotFoundException(String username) {
        super("User with this username :'" + username + "' is not found!");
    }
}
