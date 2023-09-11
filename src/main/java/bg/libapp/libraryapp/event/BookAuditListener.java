package bg.libapp.libraryapp.event;

import bg.libapp.libraryapp.model.entity.BookAudit;
import bg.libapp.libraryapp.repository.BookAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookAuditListener {
    private final BookAuditRepository bookAuditRepository;

    @Autowired
    public BookAuditListener(BookAuditRepository bookAuditRepository) {
        this.bookAuditRepository = bookAuditRepository;
    }

    @EventListener
    public void handleBookEvent(BookAuditEvent event) {
        BookAudit audit = new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
        bookAuditRepository.saveAndFlush(audit);
    }
}
