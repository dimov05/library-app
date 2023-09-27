package bg.libapp.libraryapp.exceptions.rent;

public class RentNotFoundException extends RuntimeException {
    public RentNotFoundException(long id) {
        super("Rent with this id: '" + id + "' is not present in library!");
    }
}