package com.example.hibernatedao.repositories;

import com.example.hibernatedao.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}