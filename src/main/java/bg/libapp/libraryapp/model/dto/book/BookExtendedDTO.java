package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.author.AuthorDTO;
import bg.libapp.libraryapp.model.dto.genre.GenreDTO;

import java.time.LocalDateTime;
import java.util.Set;

public class BookExtendedDTO extends BookDTO {
    private LocalDateTime dateAdded;
    private Set<AuthorDTO> authors;

    public BookExtendedDTO() {
    }

    public BookExtendedDTO(String isbn, String title, int year, String publisher, Set<GenreDTO> genres, LocalDateTime dateAdded, Set<AuthorDTO> authors) {
        super(isbn, title, year, publisher, genres);
        this.dateAdded = dateAdded;
        this.authors = authors;
    }


    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public BookExtendedDTO setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }


    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public BookExtendedDTO setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
        return this;
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
}
