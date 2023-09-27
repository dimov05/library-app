package bg.libapp.libraryapp.exceptions.user;

public class UserWithThisUsernameAlreadyExistsException extends RuntimeException {
    public UserWithThisUsernameAlreadyExistsException(String username) {
        super("User with this username :'" + username + "' already exists!");
    }
}
