package bg.libapp.libraryapp.model.dto.author;

import bg.libapp.libraryapp.model.dto.book.BookAuthorViewDTO;

import java.util.Set;

public class AuthorViewDTO {

    private String firstName;

    private String lastName;

    private Set<BookAuthorViewDTO> books;

    public AuthorViewDTO() {
    }

    public AuthorViewDTO(String firstName, String lastName, Set<BookAuthorViewDTO> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorViewDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorViewDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<BookAuthorViewDTO> getBooks() {
        return books;
    }

    public AuthorViewDTO setBooks(Set<BookAuthorViewDTO> books) {
        this.books = books;
        return this;
    }
}
