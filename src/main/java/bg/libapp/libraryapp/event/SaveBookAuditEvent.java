package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class SaveBookAuditEvent extends BaseBookAuditEvent {
    private String newValue;

    public SaveBookAuditEvent(Book book) {
        super(book);
    }

    public SaveBookAuditEvent(Book book, String newValue) {
        super(book);
        this.newValue = newValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return "{" + "newValue='" + newValue + '\'' +
                ", operationType='" + super.getOperationType() + '\'' +
                "}";
    }
}
