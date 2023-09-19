package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.service.BookAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookAuditListener {
    private final BookAuditService bookAuditService;

    @Autowired
    public BookAuditListener(BookAuditService bookAuditService) {
        this.bookAuditService = bookAuditService;
    }

    @EventListener
    public void handleUpdateYearOfBook(UpdateYearBookAuditEvent event) {
        bookAuditService.updateYearOfBook(event);

    }

    @EventListener
    public void handleUpdatePublisherOfBook(UpdatePublisherBookAuditEvent event) {
        bookAuditService.updatePublisherOfBook(event);

    }

    @EventListener
    public void handleSaveBook(SaveBookAuditEvent event) {
        bookAuditService.saveBook(event);
    }
}
