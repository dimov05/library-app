package bg.libapp.libraryapp.exceptions.rent;

public class UserHasProlongedRentsException extends RuntimeException {
    public UserHasProlongedRentsException(long userId, long rentId) {
        super("User with this id: '" + userId + "' has prolonged rent with id '" + rentId + "'!");
    }
}