package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.event.BookAuditEvent;
import bg.libapp.libraryapp.model.entity.BookAudit;

import java.time.LocalDateTime;

public class BookAuditMapper {

    public static BookAudit mapToBookAudit(BookAuditEvent event){
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }
}
