package backend.bookstore;

import backend.bookstore.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return args -> {
            Category classics = categoryRepository.save(new Category("Classics"));
            Category programming = categoryRepository.save(new Category("Programming"));
            Category scifi = categoryRepository.save(new Category("Sci-Fi"));

            bookRepository.save(new Book("Clean Code", "Robert C. Martin", "9780132350884", 2008, 37.90, programming));
            bookRepository.save(new Book("Effective Java", "Joshua Bloch", "9780134685991", 2018, 42.50, programming));
            bookRepository.save(new Book("Animal Farm", "George Orwell", "9780451526342", 1945, 9.90, classics));
            bookRepository.save(new Book("Dune", "Frank Herbert", "9780441013593", 1965, 14.50, scifi));
        };
    }

    // Osa II: lisää käyttäjiä tietokantaan, salasanat BCryptillä
    @Bean
    CommandLineRunner initUsers(UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (users.findByUsername("user").isEmpty()) {
                users.save(new AppUser("user", encoder.encode("user"), "user@example.com", "USER"));
            }
            if (users.findByUsername("admin").isEmpty()) {
                users.save(new AppUser("admin", encoder.encode("admin"), "admin@example.com", "ADMIN"));
            }
        };
    }
}