package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.event.*;
import bg.libapp.libraryapp.model.entity.BookAudit;
import bg.libapp.libraryapp.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class BookAuditMapper {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public static BookAudit mapToBookAudit(UpdateYearBookAuditEvent event) {
        logger.info("mapToBookAudit mapper method called with params " + event);
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType().name())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }

    public static BookAudit mapToBookAudit(UpdatePublisherBookAuditEvent event) {
        logger.info("mapToBookAudit mapper method called with params " + event);

        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType().name())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }
    public static BookAudit mapToBookAudit(UpdateQuantityBookAuditEvent event) {
        logger.info("mapToBookAudit mapper method called with params " + event);
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType().name())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }
    public static BookAudit mapToBookAudit(UpdateDeactivateReasonBookAuditEvent event) {
        logger.info("mapToBookAudit mapper method called with params " + event);
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType().name())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }
    public static BookAudit mapToBookAudit(UpdateActiveStatusBookAuditEvent event) {
        logger.info("mapToBookAudit mapper method called with params " + event);
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType().name())
                .setFieldName(event.getFieldName())
                .setOldValue(event.getOldValue())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }

    public static BookAudit mapToBookAudit(SaveBookAuditEvent event) {
        logger.info("mapToBookAudit mapper method called with params " + event);
        return new BookAudit()
                .setEventDate(LocalDateTime.now())
                .setOperationType(event.getOperationType().name())
                .setNewValue(event.getNewValue())
                .setUser(event.getUser())
                .setBook(event.getBook());
    }
}
