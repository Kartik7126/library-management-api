package com.library.library_api.service;

import com.library.library_api.dto.BookRequestDTO;
import com.library.library_api.dto.BookResponseDTO;
import com.library.library_api.exception.InvalidBookDataException;
import com.library.library_api.model.Author;
import com.library.library_api.model.Book;
import com.library.library_api.exception.BookNotFoundException;
import com.library.library_api.repository.AuthorRepository;
import com.library.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository){
            this.authorRepository = authorRepository;
            this.bookRepository = bookRepository;
    }

    private BookResponseDTO toResponseDTO(Book book){
        String authorName = book.getAuthor() != null ? book.getAuthor().getName() : null;
        String authorNationality = book.getAuthor() != null ? book.getAuthor().getNationality() : null;

        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAvailableCopies(),
                authorName,
                authorNationality
        );
    }

    public List<BookResponseDTO> getBooks(){
        return bookRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookbyid(long id){

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        return toResponseDTO(book);
    }

    public BookResponseDTO addBook(BookRequestDTO request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new InvalidBookDataException("Book title cannot be empty");
        }
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author with id " + request.getAuthorId() + " not found"));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAvailableCopies(request.getAvailableCopies());
        book.setAuthor(author);

        Book saved = bookRepository.save(book);
        return toResponseDTO(saved);
    }

    @Transactional
    public BookResponseDTO updateBook(long id, BookRequestDTO request){

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book wit id " + id + " not found" ));

        Author author = authorRepository.findById(request.getAuthorId())
                            .orElseThrow(() -> new RuntimeException("Author with id " + request.getAuthorId() +" not found"));
        book.setTitle(request.getTitle());
        book.setAuthor(author);
        book.setIsbn(request.getIsbn());
        book.setAvailableCopies(request.getAvailableCopies());

        return toResponseDTO(bookRepository.save(book));
    }

    public void deleteBook(long id){

        if(!bookRepository.existsById(id)){
            throw new BookNotFoundException("Book with id " + id + " Not Found");
        }

        bookRepository.deleteById(id);
    }

    public List<BookResponseDTO> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getBookWithAvailableCopies(Integer minCopies) {
        return bookRepository.findByAvailableCopiesGreaterThan(minCopies)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getAvailableBooks() {
        return bookRepository.findAvailableBooks()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getBooksByAuthorNationality(String nationality) {
        return bookRepository.findBooksByAuthorNationality(nationality)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getBooksByNationalityAndAvailability(int minCopies, String nationality) {
        return bookRepository.findBooksByNationalityAndAvailability(minCopies, nationality)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<BookResponseDTO> getBooksPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookRepository.findAll(pageable).map(this::toResponseDTO);
    }

}
