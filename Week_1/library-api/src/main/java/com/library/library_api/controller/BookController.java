package com.library.library_api.controller;

import com.library.library_api.config.LibraryConfig;
import com.library.library_api.dto.BookRequestDTO;
import com.library.library_api.dto.BookResponseDTO;
import com.library.library_api.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import com.library.library_api.model.Book;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookservice;
    private final LibraryConfig libraryConfig;
    public BookController(BookService bookservice,LibraryConfig libraryConfig){
        this.bookservice = bookservice;
        this.libraryConfig = libraryConfig;
    }
//    @Value("${library.name}")
//    private String libraryname;

    @GetMapping("/library-info")
    public String getLibraryinfo(){
        return "Welcome to " + libraryConfig.getName() +" Max Books Per User " + libraryConfig.getMaxBooksPerUser();
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getBooks(){
        return ResponseEntity.status(200).body(bookservice.getBooks());
    }

//    @PostMapping
//    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
//        return ResponseEntity.status(201).body(bookservice.addBook(book));
//    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO request){
        return ResponseEntity.status(201).body(bookservice.addBook(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookbyid(@PathVariable long id){
        return ResponseEntity.ok(bookservice.getBookbyid(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id){
        bookservice.deleteBook(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable long id,@Valid @RequestBody BookRequestDTO request){
        return ResponseEntity.ok(bookservice.updateBook(id,request));
    }
/*
    @GetMapping("/books/author/{author}")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable String author){
        return ResponseEntity.ok(bookservice.getBooksByAuthor(author));
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<Book>> getBookByTitle(@RequestParam String keyword){
        return ResponseEntity.ok(bookservice.getBooksByTitle(keyword));
    }

    @GetMapping("/books/available")
    public ResponseEntity<List<Book>> getAvailableBooks(@RequestParam int mincopies){
        return ResponseEntity.ok(bookservice.getBookWithAvailableCopies(mincopies));
    } */

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minCopies
    ){
        if(author != null){
            return ResponseEntity.ok(bookservice.getBooksByAuthor(author));
        }
        else if(keyword != null){
            return ResponseEntity.ok(bookservice.getBooksByTitle(keyword));
        }
        else if(minCopies != null){
            return ResponseEntity.ok(bookservice.getBookWithAvailableCopies(minCopies));
        }
        else{
            return ResponseEntity.ok(bookservice.getBooks());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponseDTO>> getAvailableBooks(){
        return ResponseEntity.ok(bookservice.getAvailableBooks());
    }

    @GetMapping("/nationality/{nationality}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByNationality(@PathVariable String nationality){
        return ResponseEntity.ok(bookservice.getBooksByAuthorNationality(nationality));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BookResponseDTO>> filterBooks(
            @RequestParam int minCopies,
            @RequestParam String nationality
    ){
        return ResponseEntity.ok(bookservice.getBooksByNationalityAndAvailability(minCopies,nationality));
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<BookResponseDTO>> getBooksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortBy){
        return ResponseEntity.ok(bookservice.getBooksPaginated(page,size,sortBy));
    }

}
