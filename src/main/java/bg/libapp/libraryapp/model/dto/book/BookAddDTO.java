package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.author.AuthorBookViewDTO;
import bg.libapp.libraryapp.model.dto.genre.GenreBookViewDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

public class BookAddDTO {
    @NotBlank(message = "isbn should not be blank")
    @Length(min = 13, max = 17, message = "Size of isbn should be between 13 and 17 symbols")
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
    private Set<GenreBookViewDTO> genres;
    @NotEmpty
    private Set<AuthorBookViewDTO> authors;

    public BookAddDTO() {
    }

    public BookAddDTO(String isbn, String title, int year, String publisher, Set<GenreBookViewDTO> genres, Set<AuthorBookViewDTO> authors) {
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

    public BookAddDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookAddDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookAddDTO setYear(int year) {
        this.year = year;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookAddDTO setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Set<GenreBookViewDTO> getGenres() {
        return genres;
    }

    public BookAddDTO setGenres(Set<GenreBookViewDTO> genres) {
        this.genres = genres;
        return this;
    }

    public Set<AuthorBookViewDTO> getAuthors() {
        return authors;
    }

    public BookAddDTO setAuthors(Set<AuthorBookViewDTO> authors) {
        this.authors = authors;
        return this;
    }
}
