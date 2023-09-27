package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class UpdateQuantityBookAuditEvent extends BaseBookAuditEvent {
    private String fieldName;
    private String oldValue;

    private String newValue;

    public UpdateQuantityBookAuditEvent(Book book, int oldValue, String fieldName) {
        super(book);
        this.oldValue = String.valueOf(oldValue);
        this.fieldName = fieldName;
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

    public void setOldValue(int oldValue) {
        this.oldValue = String.valueOf(oldValue);
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
