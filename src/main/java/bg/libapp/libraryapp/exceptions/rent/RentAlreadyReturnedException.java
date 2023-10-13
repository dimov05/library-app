package bg.libapp.libraryapp.exceptions.rent;

public class RentAlreadyReturnedException extends RuntimeException {
    public RentAlreadyReturnedException(long id) {
        super("Rent with this id :'" + id + "' is already returned!");
    }
}
