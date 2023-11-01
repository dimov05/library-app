package bg.libapp.libraryapp.event.cronJobs;

import bg.libapp.libraryapp.service.BookService;
import bg.libapp.libraryapp.service.UserService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class ImportXmlZipsWithBooks {
    private final BookService bookService;

    public ImportXmlZipsWithBooks(BookService bookService) {
        this.bookService = bookService;
    }

    // Everyday of month at 03:00 am
    @Scheduled(cron = "0 0 3 * * *")
    public void importBooksFromZips() {
        bookService.importXmlBooks();
    }
}
