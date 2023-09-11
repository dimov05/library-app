package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.User;
import org.springframework.context.ApplicationEvent;

public class BookAuditEvent extends ApplicationEvent {
    private String operationType;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private User user;
    private Book book;

    public BookAuditEvent(Object source, String operationType, String fieldName, String oldValue, String newValue, User user, Book book) {
        super(source);
        this.operationType = operationType;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.user = user;
        this.book = book;
    }

    public String getOperationType() {
        return operationType;
    }

    public BookAuditEvent setOperationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public BookAuditEvent setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public BookAuditEvent setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public BookAuditEvent setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BookAuditEvent setUser(User user) {
        this.user = user;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public BookAuditEvent setBook(Book book) {
        this.book = book;
        return this;
    }
}
