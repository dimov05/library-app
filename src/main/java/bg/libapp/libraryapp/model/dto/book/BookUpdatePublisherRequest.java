package bg.libapp.libraryapp.model.dto.book;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class BookUpdatePublisherRequest {
    @NotBlank(message = "Publisher should not be blank")
    @Length(min = 1, max = 100, message = "Publisher name should be between 1 and 100 symbols")
    private String publisher;

    public BookUpdatePublisherRequest() {
    }

    public BookUpdatePublisherRequest(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookUpdatePublisherRequest setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "publisher='" + publisher + '\'' +
                '}';
    }
}