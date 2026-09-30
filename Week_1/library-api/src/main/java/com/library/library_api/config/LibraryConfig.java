package com.library.library_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("library")
public class LibraryConfig {

    private String name;
    private int maxBooksPerUser;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getMaxBooksPerUser(){
        return maxBooksPerUser;
    }

    public void setMaxBooksPerUser(int maxBooksPerUser){
        this.maxBooksPerUser = maxBooksPerUser;
    }

}
