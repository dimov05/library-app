package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.enums.AuditEnum;

public class BaseBookAuditEvent {
    private AuditEnum operationType;
    private User user;
    private Book book;

    public BaseBookAuditEvent(Book book) {
        this.book = book;
    }

    public BaseBookAuditEvent() {
    }

    public BaseBookAuditEvent(AuditEnum operationType, Book book) {
        this.operationType = operationType;
        this.book = book;
    }

    public AuditEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(AuditEnum operationType) {
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

    @Override
    public String toString() {
        return "{" +
                "operationType='" + operationType + '\'' +
                '}';
    }
}
