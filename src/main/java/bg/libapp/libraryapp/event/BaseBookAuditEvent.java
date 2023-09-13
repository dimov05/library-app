package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.User;

public class BaseBookAuditEvent{
    private String operationType;
    private User user;
    private Book book;

    public BaseBookAuditEvent(Book book) {
        this.book = book;
    }

    public BaseBookAuditEvent(String operationType, User user, Book book) {
        this.operationType = operationType;
        this.user = user;
        this.book = book;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
