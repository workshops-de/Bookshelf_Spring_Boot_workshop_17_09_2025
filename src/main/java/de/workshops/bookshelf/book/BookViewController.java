package de.workshops.bookshelf.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
class BookViewController {

  private final BookService bookService;

  BookViewController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  String getAllBooks(Model model) {
    model.addAttribute("books", bookService.getAllBooks());
    return "booksTemplate";
  }
}
