package bg.libapp.libraryapp.exceptions.rent;

public class InsufficientTotalQuantityException extends RuntimeException {
    public InsufficientTotalQuantityException(String isbn, int totalQuantity) {
        super("Total quantity '" + totalQuantity + "' is not enough for book with isbn '" + isbn + "'! There are more rented books");
    }
}
