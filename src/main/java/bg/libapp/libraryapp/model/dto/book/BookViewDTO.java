package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.author.AuthorBookViewDTO;
import bg.libapp.libraryapp.model.dto.genre.GenreBookViewDTO;

import java.time.LocalDateTime;
import java.util.Set;

public class BookViewDTO {
    private String isbn;
    private String title;
    private int year;
    private String publisher;
    private LocalDateTime dateAdded;
    private Set<GenreBookViewDTO> genres;
    private Set<AuthorBookViewDTO> authors;

    public BookViewDTO() {
    }

    public BookViewDTO(String isbn, String title, int year, String publisher, LocalDateTime dateAdded, Set<GenreBookViewDTO> genres, Set<AuthorBookViewDTO> authors) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.dateAdded = dateAdded;
        this.genres = genres;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookViewDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookViewDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookViewDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookViewDTO setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public BookViewDTO setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public Set<GenreBookViewDTO> getGenres() {
        return genres;
    }

    public BookViewDTO setGenres(Set<GenreBookViewDTO> genres) {
        this.genres = genres;
        return this;
    }

    public Set<AuthorBookViewDTO> getAuthors() {
        return authors;
    }

    public BookViewDTO setAuthors(Set<AuthorBookViewDTO> authors) {
        this.authors = authors;
        return this;
    }
}
