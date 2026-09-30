package com.library.library_api.service;

import com.library.library_api.model.Author;
import com.library.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id){
        return authorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Author with id " + id + " not found"));
    }


}
