package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.author.AuthorDTO;
import bg.libapp.libraryapp.model.dto.genre.GenreDTO;

import java.util.Set;

public class BookExtendedDTO extends BookDTO {
    private String dateAdded;
    private Set<AuthorDTO> authors;

    public BookExtendedDTO() {
    }

    public BookExtendedDTO(String isbn, String title, int year, boolean isActive, int availableQuantity, int totalQuantity, String deactivateReason, String publisher, Set<GenreDTO> genres, String dateAdded, Set<AuthorDTO> authors) {
        super(isbn, title, year, isActive, availableQuantity, totalQuantity, deactivateReason, publisher, genres);
        this.dateAdded = dateAdded;
        this.authors = authors;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public BookExtendedDTO setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }


    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    @Override
    public BookExtendedDTO setIsbn(String isbn) {
        super.setIsbn(isbn);
        return this;
    }


    @Override
    public BookExtendedDTO setTitle(String title) {
        super.setTitle(title);
        return this;
    }


    @Override
    public BookExtendedDTO setYear(int year) {
        super.setYear(year);
        return this;
    }


    @Override
    public BookExtendedDTO setPublisher(String publisher) {
        super.setPublisher(publisher);
        return this;
    }


    @Override
    public BookExtendedDTO setGenres(Set<GenreDTO> genres) {
        super.setGenres(genres);
        return this;
    }

    public BookExtendedDTO setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
        return this;
    }

    @Override
    public BookExtendedDTO setAvailableQuantity(int availableQuantity) {
        super.setAvailableQuantity(availableQuantity);
        return this;
    }

    @Override
    public BookExtendedDTO setTotalQuantity(int totalQuantity) {
        super.setTotalQuantity(totalQuantity);
        return this;
    }

    @Override
    public BookExtendedDTO setActive(boolean active) {
        super.setActive(active);
        return this;
    }

    @Override
    public BookExtendedDTO setDeactivateReason(String deactivateReason) {
        super.setDeactivateReason(deactivateReason);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "dateAdded=" + dateAdded +
                ", authors=" + authors +
                "} " + super.toString();
    }
}