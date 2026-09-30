package com.library.library_api.service;

import com.library.library_api.dto.BookRequestDTO;
import com.library.library_api.dto.BookResponseDTO;
import com.library.library_api.exception.BookNotFoundException;
import com.library.library_api.exception.InvalidBookDataException;
import com.library.library_api.model.Author;
import com.library.library_api.model.Book;
import com.library.library_api.repository.AuthorRepository;
import com.library.library_api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository, authorRepository);
    }

    @Test
    void getBookbyid_shouldReturnCorrectValue_whenBookExists() {
        Book mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setTitle("Zero to One");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));

        BookResponseDTO result = bookService.getBookbyid(1);
        assertEquals("Zero to One", result.getTitle());
    }

    @Test
    void getBookbyid_shouldThrowException_whenBookDoesNotExist() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookbyid(999);
        });
    }

    @Test
    void addBook_shouldThrowException_whenTitleIsBlank() {
        BookRequestDTO invalidRequest = new BookRequestDTO();
        invalidRequest.setTitle("");
        invalidRequest.setIsbn("123");
        invalidRequest.setAvailableCopies(5);
        invalidRequest.setAuthorId(1L);

        assertThrows(InvalidBookDataException.class, () -> {
            bookService.addBook(invalidRequest);
        });
    }
}