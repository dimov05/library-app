package bg.libapp.libraryapp.model.dto.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class BookUpdateYearRequest {
    @Min(value = 1000, message = "Year should be between 1000 and now")
    @Max(value = 2100, message = "Year should be between 1000 and now")
    private int year;

    public BookUpdateYearRequest() {
    }

    public BookUpdateYearRequest(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public BookUpdateYearRequest setYear(int year) {
        this.year = year;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "year=" + year +
                '}';
    }
}