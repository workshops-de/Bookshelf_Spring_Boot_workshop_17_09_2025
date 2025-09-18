package de.workshops.bookshelf.book;

import jakarta.validation.constraints.Size;

record BookSearchRequest(@Size(max = 14) String isbn, String title) {
}
