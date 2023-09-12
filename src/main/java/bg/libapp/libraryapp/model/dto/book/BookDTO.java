package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.genre.GenreDTO;

import java.util.Set;

public class BookDTO {
    private String isbn;
    private String title;
    private int year;
    private String publisher;
    private Set<GenreDTO> genres;

    public BookDTO() {
    }

    public BookDTO(String isbn, String title, int year, String publisher, Set<GenreDTO> genres) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.genres = genres;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookDTO setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public BookDTO setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
        return this;
    }
}