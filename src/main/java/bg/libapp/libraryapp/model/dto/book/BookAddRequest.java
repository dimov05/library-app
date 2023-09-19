package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.author.AuthorRequest;
import bg.libapp.libraryapp.model.dto.genre.GenreRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public class BookAddRequest {
    @NotBlank(message = "isbn should not be blank")
    @ISBN(type = ISBN.Type.ANY)
    private String isbn;
    @NotBlank(message = "Title should not be blank")
    @Length(min = 1, max = 150, message = "Title should be between 1 and 150 symbols")
    private String title;
    @Min(value = 1000, message = "Year should be between 1000 and now")
    @Max(value = 2100, message = "Year should be between 1000 and now")
    private int year;
    @NotBlank(message = "Publisher should not be blank")
    @Length(min = 1, max = 100, message = "Publisher name should be between 1 and 100 symbols")
    private String publisher;
    @NotEmpty
    private Set<@Valid GenreRequest> genres;
    @NotEmpty
    private Set<@Valid AuthorRequest> authors;

    public BookAddRequest() {
    }

    public BookAddRequest(String isbn, String title, int year, String publisher, Set<@Valid GenreRequest> genres, Set<@Valid AuthorRequest> authors) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.genres = genres;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookAddRequest setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookAddRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookAddRequest setYear(int year) {
        this.year = year;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookAddRequest setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Set<GenreRequest> getGenres() {
        return genres;
    }

    public BookAddRequest setGenres(Set<GenreRequest> genres) {
        this.genres = genres;
        return this;
    }

    public Set<AuthorRequest> getAuthors() {
        return authors;
    }

    public BookAddRequest setAuthors(Set<AuthorRequest> authors) {
        this.authors = authors;
        return this;
    }
}