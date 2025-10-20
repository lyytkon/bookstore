package backend.bookstore.controller;

import backend.bookstore.model.Book;
import backend.bookstore.model.BookRepository;
import backend.bookstore.model.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookRepository repo;
    private final CategoryRepository categoryRepo;

    public BookController(BookRepository repo, CategoryRepository categoryRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", repo.findAll());
        return "booklist";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepo.findAll());
        return "addbook";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        if (book.getCategory() != null && book.getCategory().getId() != null) {
            book.setCategory(categoryRepo.findById(book.getCategory().getId()).orElse(null));
        } else {
            book.setCategory(null);
        }
        repo.save(book);
        return "redirect:/books";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Book book = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepo.findAll());
        return "editbook";
    }

    // Login-sivu
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}