package com.example.hibernatedao.dao;

import com.example.hibernatedao.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuthorDaoImpl implements AuthorDao {
    
    private  final EntityManagerFactory emf;
    
    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();
        
        try {
            Query query = em.createQuery("SELECT a from Author a where a.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
            List<Author> authors = query.getResultList();
            
            return authors;
        } finally {
            em.close();
        }
    }
    
    @Override
    public Author getById(Long id) {
        return getEntityManager ().find (Author.class,id);
    }
    
    @Override
    public Author findAuthorByName(String firstName, String lastName) {
         EntityManager em = getEntityManager ();
      //  TypedQuery<Author> query = getEntityManager().createQuery("SELECT a FROM Author a " +
        //
        //
        //"WHERE a.firstName = :first_name and a.lastName = :last_name", Author.class);
        TypedQuery<Author>  query = em.createNamedQuery("find_by_name",Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }
    
    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager ();
        em.getTransaction ().begin ();
        em.persist (author);
        em.flush ();
        em.getTransaction ().commit();
        
        return author;
    }
    
    @Override
    public Author updateAuthor(Author author) {
        
        
        EntityManager em = getEntityManager ();
        em.joinTransaction ();
        em.merge (author);
        em.flush ();
        em.close ();
        return em.find (Author.class, author.getId ());
        
    }
    
    @Override
    public void deleteAuthorById(Long id) {
        
        EntityManager em = getEntityManager ();
        em.getTransaction ().begin ();
        Author author = em.find (Author.class, id);
        em.remove (author);
        em.flush ();
        em.getTransaction ().commit ();
        
    }
    
    @Override
    public List<Author> findAll() {
        
        EntityManager em = getEntityManager ();
        try {
        
                TypedQuery<Author> typedQuery = em.createNamedQuery ("author_find_all", Author.class);
                return typedQuery.getResultList();
        
        }
        finally {
            em.close();
        }
        
    }
    
    private EntityManager getEntityManager(){
        
        return emf.createEntityManager ();
    }
}