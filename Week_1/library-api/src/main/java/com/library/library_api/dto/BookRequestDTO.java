package com.library.library_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookRequestDTO {

    @NotBlank(message = "Title Cannot be Empty")
    private String title;

    @NotBlank(message = "ISBN cannot be Empty")
    @Size(min = 3, max = 20, message = "ISBN must be between 3 to 20 characters")
    private String isbn;

    @Min(value = 0, message = "Available copies cannot be negative")
    private int availableCopies;

    @NotNull(message = "Author ID cannot be Null")
    private Long authorId;

    public BookRequestDTO() {}

    public String getTitle(){return title;}
    public String getIsbn(){return isbn;}
    public int getAvailableCopies(){return availableCopies;}
    public Long getAuthorId(){return authorId;}

    public void setTitle(String title){this.title = title;}
    public void setIsbn(String isbn){this.isbn = isbn;}
    public void setAvailableCopies(int availableCopies){this.availableCopies = availableCopies;}
    public void setAuthorId(Long authorId){this.authorId = authorId;}
}
