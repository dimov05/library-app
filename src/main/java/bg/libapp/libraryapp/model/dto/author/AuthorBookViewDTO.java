package bg.libapp.libraryapp.model.dto.author;

public class AuthorBookViewDTO {
    private String firstName;

    private String lastName;

    public AuthorBookViewDTO() {
    }

    public AuthorBookViewDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorBookViewDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorBookViewDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
