package bg.libapp.libraryapp.model.dto.book;

import bg.libapp.libraryapp.model.dto.author.AuthorRequest;
import bg.libapp.libraryapp.model.dto.genre.GenreRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.*;

@JacksonXmlRootElement
public class BookAddRequest {
    @JacksonXmlProperty(localName = "isbn")
    @NotBlank(message = ISBN_NOT_BLANK)
    @ISBN(type = ISBN.Type.ANY, message = ISBN_INCORRECT_VALUE)
    private String isbn;
    @JacksonXmlProperty(localName = "title")
    @NotBlank(message = TITLE_NOT_BLANK)
    @Length(min = 1, max = 150, message = TITLE_LENGTH_VALIDATION)
    private String title;
    @JacksonXmlProperty(localName = "year")
    @Min(value = 1000, message = YEAR_RANGE_EXCEPTION)
    @Max(value = 2100, message = YEAR_RANGE_EXCEPTION)
    private int year;
    @JacksonXmlProperty(localName = "publisher")
    @NotBlank(message = PUBLISHER_NOT_BLANK_EXCEPTION)
    @Length(min = 1, max = 100, message = PUBLISHER_SIZE_EXCEPTION)
    private String publisher;
    @JacksonXmlProperty(localName = "totalQuantity")
    @Min(value = 0, message = BOOK_QUANTITY_LESS_THAN_0)
    private int totalQuantity;
    @JacksonXmlProperty(localName = "genres")
    @NotEmpty
    private Set<@Valid GenreRequest> genres;
    @JacksonXmlProperty(localName = "genres")
    @NotEmpty
    private Set<@Valid AuthorRequest> authors;

    public BookAddRequest() {
    }

    public BookAddRequest(String isbn, String title, int year, int totalQuantity, String publisher, Set<@Valid GenreRequest> genres, Set<@Valid AuthorRequest> authors) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.totalQuantity = totalQuantity;
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BookAddRequest setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", publisher='" + publisher + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", genres=" + genres +
                ", authors=" + authors +
                '}';
    }
}