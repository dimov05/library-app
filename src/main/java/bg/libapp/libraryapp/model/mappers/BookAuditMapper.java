package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.event.SaveBookAuditEvent;
import bg.libapp.libraryapp.event.UpdatePublisherBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateYearBookAuditEvent;
import bg.libapp.libraryapp.model.entity.BookAudit;

import java.time.LocalDateTime;

public class BookAuditMapper {

    public static BookAudit mapToBookAudit(UpdateYearBookAuditEvent event) {
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }

    public static BookAudit mapToBookAudit(UpdatePublisherBookAuditEvent event) {
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }
    public static BookAudit mapToBookAudit(SaveBookAuditEvent event) {
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }
}
