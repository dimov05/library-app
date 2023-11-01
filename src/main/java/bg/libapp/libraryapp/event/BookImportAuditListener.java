package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.service.BookImportAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookImportAuditListener {
    private final BookImportAuditService bookImportAuditService;

    @Autowired
    public BookImportAuditListener(BookImportAuditService bookImportAuditService) {
        this.bookImportAuditService = bookImportAuditService;
    }

    @EventListener
    public void handleImportingXmlWithBooks(BookImportAuditEvent event) {
        bookImportAuditService.importXmlFileWithBook(event);
    }
}
