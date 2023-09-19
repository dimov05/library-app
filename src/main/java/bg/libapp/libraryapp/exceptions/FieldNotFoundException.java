package bg.libapp.libraryapp.exceptions;

public class FieldNotFoundException extends RuntimeException {
    public FieldNotFoundException(String fieldName, String className) {
        super("Field '" + fieldName + "' in '" + className + "' is not found!");
    }
}
