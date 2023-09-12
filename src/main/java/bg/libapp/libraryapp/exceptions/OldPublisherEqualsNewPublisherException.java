package bg.libapp.libraryapp.exceptions;

public class OldPublisherEqualsNewPublisherException extends RuntimeException {
    public OldPublisherEqualsNewPublisherException(String publisher) {
        super("Old and new publisher are equals(" + publisher + ")");
    }
}