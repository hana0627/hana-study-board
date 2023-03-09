package com.example.hanastudyboard.repository;

import com.example.hanastudyboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}