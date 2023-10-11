package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class UpdateActiveStatusBookAuditEvent extends BaseUpdateBookAuditEvent {
    public UpdateActiveStatusBookAuditEvent(Book book, String oldValue) {
        super(book, oldValue);
    }
}
