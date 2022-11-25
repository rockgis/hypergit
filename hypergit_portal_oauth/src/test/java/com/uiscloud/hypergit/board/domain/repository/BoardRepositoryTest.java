package com.uiscloud.hypergit.board.domain.repository;

import com.goodmit.hypergit.board.domain.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles="dev")
@Slf4j
class BoardRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testBoardCount() {
        assertDoesNotThrow(()->{
           long count =  boardRepository.count();
           log.info("{}",count);
        });
    }
}