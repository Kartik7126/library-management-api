package com.library.library_api.repository;

import com.library.library_api.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.author.name = :author")
    List<Book> findByAuthor(@Param("author") String author);

    //List<Book> findByAuthor(String author);
    List<Book> findByTitleContaining(String keyword);
    List<Book> findByAvailableCopiesGreaterThan(int copies);

    @Query("Select b from Book b Join Fetch b.author where b.availableCopies > 0")
    List<Book> findAvailableBooks();

    @Query("Select b from Book b where b.author.nationality = :nationality")
    List<Book> findBooksByAuthorNationality(
            @Param("nationality") String nationality
    );

    @Query("Select b from Book b where b.availableCopies > :minCopies AND b.author.nationality = :nationality")
    List<Book> findBooksByNationalityAndAvailability(
            @Param("minCopies") int minCopies,
            @Param("nationality") String nationality
    );

    @Override
    Page<Book> findAll(Pageable pageable);
}
