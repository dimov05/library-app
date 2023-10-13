package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class UpdateYearBookAuditEvent extends BaseUpdateBookAuditEvent {
    public UpdateYearBookAuditEvent(Book book, String oldValue) {
        super(book, oldValue);
    }
}
