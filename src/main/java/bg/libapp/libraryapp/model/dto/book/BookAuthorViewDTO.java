package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.genre.GenreBookViewDTO;

import java.time.LocalDateTime;
import java.util.Set;

public class BookAuthorViewDTO {
    private String isbn;
    private String title;
    private int year;
    private String publisher;
    private Set<GenreBookViewDTO> genres;

    public BookAuthorViewDTO() {
    }

    public BookAuthorViewDTO(String isbn, String title, int year, String publisher, Set<GenreBookViewDTO> genres) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.genres = genres;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookAuthorViewDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookAuthorViewDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookAuthorViewDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookAuthorViewDTO setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Set<GenreBookViewDTO> getGenres() {
        return genres;
    }

    public BookAuthorViewDTO setGenres(Set<GenreBookViewDTO> genres) {
        this.genres = genres;
        return this;
    }
}
