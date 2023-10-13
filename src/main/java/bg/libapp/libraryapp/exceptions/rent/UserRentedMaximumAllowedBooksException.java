package bg.libapp.libraryapp.exceptions.rent;

public class UserRentedMaximumAllowedBooksException extends RuntimeException {
    public UserRentedMaximumAllowedBooksException(long id) {
        super("User with this id :'" + id + "' has rented maximum allowed books!");
    }
}
