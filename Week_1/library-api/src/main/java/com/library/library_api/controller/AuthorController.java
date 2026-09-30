package com.library.library_api.controller;

import com.library.library_api.model.Author;
import com.library.library_api.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors(){
        return ResponseEntity.ok(authorService.getAuthors());
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        return ResponseEntity.status(201).body(authorService.addAuthor(author));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

}









