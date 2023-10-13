package bg.libapp.libraryapp.model.dto.book;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Arrays;

public class BookFilterRequest {
    @Length(min = 1, max = 150, message = "Title should be between 1 and 150 symbols")
    private String title;
    @Length(min = 1, max = 100, message = "Publisher name should be between 1 and 100 symbols")
    private String publisher;
    private Integer yearFrom;
    private Integer yearTo;
    @Pattern(regexp = "^([<>]=?|!=|=):(\\d{1,9})(,[<>]=?|!=|=):(\\d{1,9})*$")
    private String year;
    private Boolean isActive;
    private Integer[] genres;
    private String[] authorsFirstName;
    private String[] authorsLastName;
    @Pattern(regexp = "^([<>]=?|!=|=):(\\d{1,9})(,[<>]=?|!=|=):(\\d{1,9})*$")
    private String availableQuantity;
    @Pattern(regexp = "^([<>]=?|!=|=):(\\d{1,9})(,[<>]=?|!=|=):(\\d{1,9})*$")
    private String totalQuantity;

    public BookFilterRequest() {

    }

    public BookFilterRequest(String title, String publisher, Integer yearFrom, Integer yearTo, Boolean isActive, Integer[] genres, String[] authorsFirstName, String[] authorsLastName, String availableQuantity, String totalQuantity) {
        this.title = title;
        this.publisher = publisher;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.isActive = isActive;
        this.genres = genres;
        this.authorsFirstName = authorsFirstName;
        this.authorsLastName = authorsLastName;
        this.availableQuantity = availableQuantity;
        this.totalQuantity = totalQuantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(int yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    public Integer[] getGenres() {
        return genres;
    }

    public void setGenres(Integer[] genres) {
        this.genres = genres;
    }

    public String[] getAuthorsFirstName() {
        return authorsFirstName;
    }

    public void setAuthorsFirstName(String[] authorsFirstName) {
        this.authorsFirstName = authorsFirstName;
    }

    public String[] getAuthorsLastName() {
        return authorsLastName;
    }

    public void setAuthorsLastName(String[] authorsLastName) {
        this.authorsLastName = authorsLastName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = Boolean.parseBoolean(String.valueOf(isActive).toLowerCase());
    }

    public BookFilterRequest setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
        return this;
    }

    public BookFilterRequest setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public BookFilterRequest setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public BookFilterRequest setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
        return this;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public BookFilterRequest setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public String getYear() {
        return year;
    }

    public BookFilterRequest setYear(String year) {
        this.year = year;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", yearFrom=" + yearFrom +
                ", yearTo=" + yearTo +
                ", isActive=" + isActive +
                ", genres=" + Arrays.toString(genres) +
                ", authorsFirstName=" + Arrays.toString(authorsFirstName) +
                ", authorsLastName=" + Arrays.toString(authorsLastName) +
                ", availableQuantity='" + availableQuantity + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                '}';
    }
}
