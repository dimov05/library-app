package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.Book;

public class UpdateDeactivateReasonBookAuditEvent extends BaseUpdateBookAuditEvent {
    public UpdateDeactivateReasonBookAuditEvent(Book book, String oldValue) {
        super(book, oldValue);
    }
}
