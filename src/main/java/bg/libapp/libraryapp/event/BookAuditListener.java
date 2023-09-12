package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.mappers.BookAuditMapper;
import bg.libapp.libraryapp.repository.BookAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookAuditListener {
    private final BookAuditRepository bookAuditRepository;

    @Autowired
    public BookAuditListener(BookAuditRepository bookAuditRepository) {
        this.bookAuditRepository = bookAuditRepository;
    }

    @EventListener
    public void handleBookEvent(BookAuditEvent event) {
        bookAuditRepository.saveAndFlush(BookAuditMapper.mapToBookAudit(event));
    }
}
