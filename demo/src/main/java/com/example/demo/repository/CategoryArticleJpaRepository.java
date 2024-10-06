package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryArticleJpaRepository extends JpaRepository<CategoryArticle, Long> {
    List<CategoryArticle> findByArticle(Article article);
    void deleteCategoryArticleByArticle(Article article);
}
