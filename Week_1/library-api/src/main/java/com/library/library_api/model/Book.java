package com.library.library_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @NotNull(message = "author cannot be null")
    private Author author;

    @NotBlank(message = "ISBN cannot be Empty")
    @Size(min = 3,max = 20,message = "ISBN must be between 3 to 20 characters")
    private String isbn;

    @Min(value = 0,message = "Available copies cannot be Empty")
    private int availableCopies;

    public Book(){
    }

    public Book(Long id,String title,Author author,String isbn,int availableCopies){
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public Author getAuthor(){
        return author;
    }

    public String getIsbn(){
        return isbn;
    }

    public int getAvailableCopies(){
        return availableCopies;
    }

    public void setId(long nid){
        id = nid;
    }

    public void setTitle(String ntitle){
        title = ntitle;
    }

    public void setAuthor(Author nauthor){
        this.author = nauthor;
    }

    public void setIsbn(String nisbn){
        isbn = nisbn;
    }

    public void setAvailableCopies(int navailableCopies){
        availableCopies = navailableCopies;
    }
}
