package com.library.library_api.dto;

public class BookResponseDTO{

    private Long id ;
    private String title ;
    private String isbn ;
    private int availableCopies;
    private String authorName;
    private String authorNationality;

    public BookResponseDTO(){}

    public BookResponseDTO(Long id,String title,String isbn,
                           int availableCopies,String authorName,
                           String authorNationality){
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
        this.authorName = authorName;
        this.authorNationality = authorNationality;
    }

    public Long getId() {return id;}
    public String getTitle(){return title;}
    public String getIsbn() {return isbn;}
    public int getAvailableCopies(){return availableCopies;}
    public String getAuthorName() {return authorName;}
    public String getAuthorNationality() {return authorNationality;}

    public void setId(Long id) {this.id = id;}
    public void setTitle(String title){this.title = title;}
    public void setIsbn(String isbn) {this.isbn = isbn;}
    public void setAvailableCopies(int availableCopies){this.availableCopies = availableCopies;}
    public void setAuthorName(String authorName) {this.authorName = authorName;}
    public void setAuthorNationality(String authorNationality) {this.authorNationality = authorNationality;}
}
