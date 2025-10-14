package backend.bookstore;

import backend.bookstore.model.Book;
import backend.bookstore.model.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(BookRepository bookRepository) {
        return args -> {
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", "9780132350884", 2008, 37.90));
            bookRepository.save(new Book("Effective Java", "Joshua Bloch", "9780134685991", 2018, 42.50));
            bookRepository.save(new Book("Refactoring", "Martin Fowler", "9780201485677", 1999, 39.00));
        };
    }
}