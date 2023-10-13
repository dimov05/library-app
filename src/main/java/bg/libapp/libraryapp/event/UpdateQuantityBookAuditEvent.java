package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class UpdateQuantityBookAuditEvent extends BaseUpdateBookAuditEvent {
    public UpdateQuantityBookAuditEvent(Book book, int oldValue, String fieldName) {
        super(book, fieldName, String.valueOf(oldValue));
    }
}
