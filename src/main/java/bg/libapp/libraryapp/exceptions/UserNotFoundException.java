package bg.libapp.libraryapp.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String username) {
        super("User with this username :'" + username + "' is not found!");
    }
}
