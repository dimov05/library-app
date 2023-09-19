package bg.libapp.libraryapp.model.dto.author;

import bg.libapp.libraryapp.model.dto.book.BookDTO;

import java.util.Set;

public class AuthorExtendedDTO extends AuthorDTO {
    private Set<BookDTO> books;

    public AuthorExtendedDTO() {
    }

    public AuthorExtendedDTO(String firstName, String lastName, Set<BookDTO> books) {
        super(firstName, lastName);
        this.books = books;
    }

    public Set<BookDTO> getBooks() {
        return books;
    }

    public AuthorExtendedDTO setBooks(Set<BookDTO> books) {
        this.books = books;
        return this;
    }

    @Override
    public AuthorExtendedDTO setFirstName(String firstName) {
        super.setFirstName(firstName);
        return this;
    }

    @Override
    public AuthorExtendedDTO setLastName(String lastName) {
        super.setLastName(lastName);
        return this;
    }
}