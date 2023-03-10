package com.example.hibernatedao;


import com.example.hibernatedao.dao.AuthorDao;
import com.example.hibernatedao.dao.AuthorDaoImpl;
import com.example.hibernatedao.dao.BookDao;
import com.example.hibernatedao.dao.BookDaoImpl;
import com.example.hibernatedao.domain.Author;
import com.example.hibernatedao.domain.Book;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = "com.example.hibernatedao.dao")
@Import({AuthorDaoImpl.class , BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {
    
    @Autowired
    AuthorDao authorDao;
    
    @Autowired
    BookDao bookDao;
    
    @Test
    void testFindAllAuthors(){
        
        List<Author> authors = authorDao.findAll();
        
        assertThat (authors).isNotNull ();
        assertThat (authors.size ()).isGreaterThan (0);
    }
    
    @Test
    void testFindAllBooks(){
        List<Book>  books = bookDao.findAll();
        
        assertThat (books).isNotNull();
        assertThat (books.size ()).isGreaterThan (0);
        
        
    }
    
    
    @Test
    void testFindBookByISBN() {
        Book book = new Book ();
        book.setIsbn ("1234" + RandomString.make ());
        book.setTitle ("Testing");
        
        Book saved = bookDao.savedNewBook (book);
        
        Book fetched = bookDao.findByISBN (book.getIsbn ());
        
        assertThat (fetched).isNotNull ();
        
    }
    @Test
    void testListAuthorByLastNameLike(){
        List<Author> authors = authorDao.listAuthorByLastNameLike ("walls");
        assertThat(authors).isNotNull ();
        assertThat (authors.size ()).isGreaterThan (0);
        
    }
    @Test
    void testDeleteBook() {
        Book book = new Book ();
        
        book.setIsbn ("1234");
        book.setPublisher ("Self");
        book.setTitle ("my book");
        Book saved = bookDao.savedNewBook (book);
        
        bookDao.deleteBookById (saved.getId ());
        Book deleted = bookDao.getById (saved.getId ());
        assertThat (deleted).isNull ();
        
    }
    
    @Test
    void updateBookTest(){
    
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
    
        Author author = new Author();
        author.setId(3L);
    
        book.setAuthorId(1L);
        Book saved = bookDao.savedNewBook (book);
    
        saved.setTitle("New Book");
        bookDao.updateBook(saved);
    
        Book fetched = bookDao.getById(saved.getId());
    
        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }
    
    @Test
    void findAllBooks(){
        List<Book>  books = bookDao.findAllBooks (PageRequest.of(0,10));
        assertThat (books).isNotNull ();
        assertThat (books.size()).isEqualTo (5);
        
    }
    
    @Test
    void findAllBooksSortByTitle(){
        List<Book> books = bookDao.findAllBooksByTitle (PageRequest.of (0,10, Sort.by (Sort.Order.desc ("title"))));
        
        assertThat (books).isNotNull();
        assertThat (books.size ()).isEqualTo (5);
        
    }
    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        
        Author author = new Author();
        author.setId(3L);
        
        book.setAuthorId(1L);
        Book saved = bookDao.savedNewBook (book);
        
        assertThat(saved).isNotNull();
    }
    
    @Test
    void testGetBookByName() {
        Book book = bookDao.findByTitle ("Clean Code");
        
        assertThat(book).isNotNull();
    }
    
    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);
        
        assertThat(book.getId()).isNotNull();
    }
    
    @Test
    void testGetAuthor(){
        Author author = authorDao.getById (1L);
        assertThat(author).isNotNull();
    }
    
    @Test
    void testAuthorByName(){
        
        Author author = authorDao.findAuthorByName ("Craig","Walls");
        assertThat (author).isNotNull ();
    }
    
    @Test
    void testSaveAuthor(){
        
        Author author = new Author();
        author.setFirstName ("John");
        author.setLastName ("Mahdi");
        Author saved = authorDao.saveNewAuthor (author);
        
        assertThat (saved).isNotNull();
        assertThat (saved.getId ()).isNotNull();
    }
    
    @Test
    void testUpdateAuthor(){
        Author author = new Author();
        author.setFirstName ("john");
        author.setLastName("t");
        
        Author saved = authorDao.saveNewAuthor (author);
        
        saved.setLastName ("Thompson");
        Author updated =  authorDao.updateAuthor (saved);
        
        assertThat (updated.getLastName ()).isEqualTo ("Thompson");
        
    }
    
    @Test
    void testDeleteAuthor() {
    
        Author author = new Author ();
        author.setFirstName ("john");
        author.setLastName ("t");
        Author saved = authorDao.saveNewAuthor (author);
        authorDao.deleteAuthorById (saved.getId ());
        Author deleted = authorDao.getById (saved.getId ());
        assertThat (deleted).isNull ();
    }
    
    @Test
    void testGetAuthorByNameCriteria(){
        Author author = authorDao.findAuthorByNameCriteria("Craig","Walls");
        
        assertThat (author).isNotNull ();
        
    }
}