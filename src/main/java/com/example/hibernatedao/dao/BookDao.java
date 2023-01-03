package com.example.hibernatedao.dao;


import com.example.hibernatedao.domain.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {
    
    
    
    Book findByISBN(String isbn);
    
    Book getById(Long id);
    
    Book findByTitle(String title);
    
    Book savedNewBook(Book book);
    
    Book updateBook(Book book);
    
    void deleteBookById(Long id);
    
    List<Book> findAll();
    
    List<Book>  findAllBooks(Pageable pageable);
    
    List<Book>  findAllBooksByTitle(Pageable pageable);
}