package com.example.hanastudyboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest //sliceTest 컨트롤러 외에 다른 객체를 호출하지 않음
@Disabled("Spring Data REST 통합 테스트는 불필요함으로 제외시킴")
@DisplayName("Data REST - api테스트")
@SpringBootTest
@Transactional // test에서 @Transactional어노테이션이 있다면 모두 roll back
@AutoConfigureMockMvc
public class DataRestTest {
    private final MockMvc mvc;


    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }


    @Test
    @DisplayName("[api] 게시글 리스트 조회")
    void ArticleListTest() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) // DateRest가 기본적으로 제공하는 hal+json은 MediaType에 없음
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(MockMvcResultHandlers.print());
    }



    @Test
    @DisplayName("[api] 게시글 단건 조회")
    void Article() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
    void ArticleCommentListFromArticle() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[api] 댓글 리스트 조회")
    void ArticleCommentList() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[api] 댓글 단건 조회")
    void ArticleComment() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("[api] 회원 관련 API 는 일체 제공하지 않는다.")
    @Test
    void givenNothing_whenRequestingUserAccounts_thenThrowsException() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(post("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(put("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(patch("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(delete("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(head("/api/userAccounts")).andExpect(status().isNotFound());
    }
}
