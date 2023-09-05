package bg.libapp.libraryapp.exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String firstName,String lastName) {
        super("Author with this first and last name: '"+firstName+ " " + lastName + "' is not present in library!");
    }
}
