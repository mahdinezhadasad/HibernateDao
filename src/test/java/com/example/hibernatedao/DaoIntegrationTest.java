package com.example.hibernatedao;


import com.example.hibernatedao.dao.AuthorDao;
import com.example.hibernatedao.dao.AuthorDaoImpl;
import com.example.hibernatedao.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = "com.example.hibernatedao.dao")
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {
    
    @Autowired
    AuthorDao authorDao;
    
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
}