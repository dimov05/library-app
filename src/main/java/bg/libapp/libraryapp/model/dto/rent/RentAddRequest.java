package bg.libapp.libraryapp.model.dto.rent;

import jakarta.validation.constraints.Future;

import java.time.LocalDate;

public class RentAddRequest {
    @Future(message = "Return date should be in future.(Format -> 2023-10-22")
    private LocalDate expectedReturnDate;
    private Long userId;

    public RentAddRequest() {
    }

    public RentAddRequest(LocalDate expectedReturnDate, Long userId) {
        this.expectedReturnDate = expectedReturnDate;
        this.userId = userId;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public RentAddRequest setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public RentAddRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "expectedReturnDate=" + expectedReturnDate +
                ", userId=" + userId +
                '}';
    }
}
