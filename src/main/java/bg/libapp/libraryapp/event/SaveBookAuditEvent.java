package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class SaveBookAuditEvent extends BaseBookAuditEvent {
    private String operationType;
    private String newValue;

    public SaveBookAuditEvent(Book book, String operationType, String newValue) {
        super(book);
        this.operationType = operationType;
        this.newValue = newValue;
    }

    public SaveBookAuditEvent(Book book, String newValue) {
        super(book);
        this.newValue = newValue;
    }

    @Override
    public String getOperationType() {
        return operationType;
    }

    @Override
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
