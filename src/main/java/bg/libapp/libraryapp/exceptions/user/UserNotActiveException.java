package bg.libapp.libraryapp.exceptions.user;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String username) {
        super("User with this username :'" + username + "' is not active!");
    }

    public UserNotActiveException(Long id) {
        super("User with this id :'" + id + "' is not active!");
    }
}
