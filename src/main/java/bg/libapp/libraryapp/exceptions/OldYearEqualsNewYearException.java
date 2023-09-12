package bg.libapp.libraryapp.exceptions;

public class OldYearEqualsNewYearException extends RuntimeException {
    public OldYearEqualsNewYearException(String year) {
        super("Old and new year are equals(" + year + ")");
    }
}