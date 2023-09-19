package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class UpdateYearBookAuditEvent extends BaseBookAuditEvent {
    private String fieldName;
    private String oldValue;

    private String newValue;

    public UpdateYearBookAuditEvent(Book book, String oldValue) {
        super(book);
        this.oldValue = oldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return "{" +
                "fieldName='" + fieldName + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", operationType='" + this.getOperationType() + '\'' +
                "}";
    }
}
