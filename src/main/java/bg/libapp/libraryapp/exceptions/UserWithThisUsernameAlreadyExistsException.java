package bg.libapp.libraryapp.exceptions;

public class UserWithThisUsernameAlreadyExistsException extends RuntimeException {
    public UserWithThisUsernameAlreadyExistsException(String username) {
        super("User with this username :'" + username + "' already exists!");
    }
}
