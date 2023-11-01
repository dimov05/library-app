package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.event.BookImportAuditEvent;
import bg.libapp.libraryapp.model.mappers.BookImportAuditMapper;
import bg.libapp.libraryapp.repository.BookImportAuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.CREATING_EVENT_FOR_IMPORTING_XML_FILE_LOGGER;

@Service
public class BookImportAuditService {
    private final Logger logger = LoggerFactory.getLogger(BookImportAuditService.class);
    private final BookImportAuditRepository bookImportAuditRepository;

    @Autowired
    public BookImportAuditService(BookImportAuditRepository bookImportAuditRepository) {
        this.bookImportAuditRepository = bookImportAuditRepository;
    }

    public void importXmlFileWithBook(BookImportAuditEvent event) {
        event.setImported(event.getImported());
        event.setFailedFile(event.getFailedFile());
        event.setFailedZip(event.getFailedZip());
        event.setFailReason(event.getFailReason());
        logger.info(CREATING_EVENT_FOR_IMPORTING_XML_FILE_LOGGER);
        bookImportAuditRepository.saveAndFlush(BookImportAuditMapper.mapToBookImportAudit(event));
    }
}
