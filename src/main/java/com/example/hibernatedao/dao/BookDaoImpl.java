package com.example.hibernatedao.dao;

import com.example.hibernatedao.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {
    
    private final EntityManagerFactory emf;
    
    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    
    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager ();
        
        try {
            TypedQuery<Book> query = em.createQuery ("select b from  Book  b where  b.isbn = : isbn", Book.class);
            query.setParameter ("isbn", isbn);
            
            Book book = query.getSingleResult ();
            
            return book;
        } finally {
            em.close ();
        }
        
        
    }
    
    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager ();
        Book book = getEntityManager ().find (Book.class, id);
        em.close ();
        return book;
    }
    
    @Override
    public Book findByTitle(String title) {
        
        EntityManager em = getEntityManager ();
        TypedQuery<Book> query = em.createQuery ("SELECT b from Book  b where b.title = :title", Book.class);
        query.setParameter ("title", title);
        Book book = query.getSingleResult ();
        em.close ();
        return book;
    }
    
    @Override
    public Book savedNewBook(Book book) {
        EntityManager em = getEntityManager ();
        em.getTransaction ().begin ();
        em.persist (book);
        em.flush ();
        em.getTransaction ().commit ();
        em.close ();
        return book;
    }
    
    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager ();
        em.getTransaction ().begin ();
        em.merge (book);
        em.flush ();
        em.clear ();
        Book savedBook = em.find (Book.class, book.getId ());
        em.getTransaction ().commit ();
        em.close ();
        return savedBook;
    }
    
    @Override
    public void deleteBookById(Long id) {
        
        EntityManager em = getEntityManager ();
        em.getTransaction ().begin ();
        Book book = em.find (Book.class, id);
        em.remove (book);
        em.getTransaction ().commit ();
        em.close ();
    
    }
    
    @Override
    public List<Book> findAll() {
        EntityManager em = getEntityManager ();
        try {
            TypedQuery<Book> query = em.createNamedQuery ("find_all_books", Book.class);
            return query.getResultList ();
        } finally {
            em.close ();
        }
    }
    
    private EntityManager getEntityManager() {
        
        return emf.createEntityManager ();
    }
}