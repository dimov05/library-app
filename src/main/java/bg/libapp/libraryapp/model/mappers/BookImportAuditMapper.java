package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.event.BookImportAuditEvent;
import bg.libapp.libraryapp.model.entity.BookImportAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class BookImportAuditMapper {
    private static final Logger logger = LoggerFactory.getLogger(BookImportAuditMapper.class);

    public static BookImportAudit mapToBookImportAudit(BookImportAuditEvent event) {
        logger.info("mapToBookImportAudit accessed");
        return new BookImportAudit()
                .setImported(event.getImported())
                .setEventDate(LocalDateTime.now())
                .setFileName(event.getFailedFile())
                .setZipName(event.getFailedZip())
                .setMessage(event.getFailReason());

    }

}
