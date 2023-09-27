package bg.libapp.libraryapp.model.dto.book;

import jakarta.validation.constraints.Min;

public class BookUpdateTotalQuantityRequest {
    @Min(value = 0, message = "Book's total quantity should be equal or more than 0")
    private int totalQuantity;

    public BookUpdateTotalQuantityRequest() {
    }

    public BookUpdateTotalQuantityRequest(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BookUpdateTotalQuantityRequest setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "totalQuantity=" + totalQuantity +
                '}';
    }
}