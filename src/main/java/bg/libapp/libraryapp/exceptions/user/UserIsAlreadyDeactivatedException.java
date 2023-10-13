package bg.libapp.libraryapp.exceptions.user;

public class UserIsAlreadyDeactivatedException extends RuntimeException {
    public UserIsAlreadyDeactivatedException(long id) {
        super("User with this id :'" + id + "' is already deactivated!");
    }
}
