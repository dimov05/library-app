package bg.libapp.libraryapp.exceptions.user;

public class UserIsAlreadyActivatedException extends RuntimeException {
    public UserIsAlreadyActivatedException(long id) {
        super("User with this id :'" + id + "' is already deactivated!");
    }
}
