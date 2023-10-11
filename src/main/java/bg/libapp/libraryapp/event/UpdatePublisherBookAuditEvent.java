package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class UpdatePublisherBookAuditEvent extends BaseUpdateBookAuditEvent {
    public UpdatePublisherBookAuditEvent(Book book, String oldValue) {
        super(book, oldValue);
    }
}
