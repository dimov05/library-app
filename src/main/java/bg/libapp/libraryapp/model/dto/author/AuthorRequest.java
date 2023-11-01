package bg.libapp.libraryapp.model.dto.author;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.AUTHOR_FIRST_NAME_LENGTH_VALIDATION;
import static bg.libapp.libraryapp.model.constants.ApplicationConstants.AUTHOR_FIRST_NAME_NOT_EMPTY;
import static bg.libapp.libraryapp.model.constants.ApplicationConstants.AUTHOR_LAST_NAME_LENGTH_VALIDATION;
import static bg.libapp.libraryapp.model.constants.ApplicationConstants.AUTHOR_LAST_NAME_NOT_EMPTY;

public class AuthorRequest {
    @NotEmpty(message = AUTHOR_FIRST_NAME_NOT_EMPTY)
    @Length(min = 2, message = AUTHOR_FIRST_NAME_LENGTH_VALIDATION)
    private String firstName;

    @NotEmpty(message = AUTHOR_LAST_NAME_NOT_EMPTY)
    @Length(min = 2, message = AUTHOR_LAST_NAME_LENGTH_VALIDATION)
    private String lastName;

    public AuthorRequest() {
    }

    public AuthorRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}