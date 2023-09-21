package bg.libapp.libraryapp.model.dto.book;

import org.hibernate.validator.constraints.Length;

import java.util.Arrays;

public class BookFilterRequest {
    //    @NotBlank(message = "Title should not be blank")
    @Length(min = 1, max = 150, message = "Title should be between 1 and 150 symbols")
    private String title;
    //    @NotBlank(message = "Publisher should not be blank")
    @Length(min = 1, max = 100, message = "Publisher name should be between 1 and 100 symbols")
    private String publisher;
    //    @Min(value = 1000, message = "Year should be between 1000 and now")
//    @Max(value = 2100, message = "Year should be between 1000 and now")
    private Integer yearFrom;
    //    @Min(value = 1000, message = "Year should be between 1000 and now")
//    @Max(value = 2100, message = "Year should be between 1000 and now")
    private Integer yearTo;
    private Boolean isActive;
    private Integer[] genres;
    private String[] authorsFirstName;
    private String[] authorsLastName;

    public BookFilterRequest() {

    }

    public BookFilterRequest(String title, String publisher, Integer yearFrom, Integer yearTo, Boolean isActive, Integer[] genres, String[] authorsFirstName, String[] authorsLastName) {
        this.title = title;
        this.publisher = publisher;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.isActive = Boolean.parseBoolean(String.valueOf(isActive).toLowerCase());
        this.genres = genres;
        this.authorsFirstName = authorsFirstName;
        this.authorsLastName = authorsLastName;
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
                '}';
    }
}
