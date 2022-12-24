package com.example.hibernatedao.repositories;

import com.example.hibernatedao.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}