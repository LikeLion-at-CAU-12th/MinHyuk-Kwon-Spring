package com.example.demo.repository;

import com.example.demo.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleJpaReqpository extends JpaRepository<Article, Long> {
    List<Article> findByMemeberId(Long memberId);
    void deleteById(Long articleId);
}
