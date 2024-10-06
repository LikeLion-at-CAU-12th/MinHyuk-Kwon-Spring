package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.request.ArticleCreateRequestDto;
import com.example.demo.dto.request.ArticleUpdateRequestDto;
import com.example.demo.dto.response.ArticleResponseDto;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private ArticleJpaReqpository articleReqpository;
    @Autowired
    private CategoryArticleJpaRepository categoryArticleRepository;
    @Autowired
    private ArticleLogJpaRepository articleLogRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Transactional
    public Long createArticle(ArticleCreateRequestDto requestDto) {
        // 멤버 찾기
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 회원이 존재하지 않습니다."));

        // 해당 멤버로 게시글 작성 및 저장
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .comments(new ArrayList<>())
                .build();
        articleReqpository.save(article);

        // 새롭게 작성한 게시글로 로그 생성
        ArticleLog articleLog = ArticleLog.builder()
                .article(article)
                .content(requestDto.getContent())
                .title(requestDto.getTitle())
                .build();
        articleLogRepository.save(articleLog);

        // 카테고리가 반영된 게시글 생성
        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                // 카테고리를 찾아온다
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 존재하지 않습니다."));

                // 찾아온 카테고리를 게시글과 연결해서 CategoryArticle을 생성한다
                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .article(article)
                        .category(category)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }
        return article.getId();
    }

    @Transactional
    public List<ArticleResponseDto> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleReqpository.findByMemeberId(memberId);
        // 우선 memberId에 해당하는 모든 article을 List형태로 가져온뒤에 ArticleResponseDto의 List형태로 반환함
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteArticle(Long articleId) {

        // 삭제할 article이 존재하는지 확인
        Article article = articleReqpository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 게시글이 없습니다."));

        // 조회한 article 삭제
        articleReqpository.deleteById(article.getId());
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, ArticleUpdateRequestDto requestDto) {
        // 수정할 article이 존재하는지 확인
        Article article = articleReqpository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 게시글이 없습니다."));

        //기존 article의 title 수정
        if (requestDto.getTitle() != null) {
            article.setTitle(requestDto.getTitle());
        }
        // 기존 article의 content 수정
        if (requestDto.getContent() != null) {
            article.setContent(requestDto.getContent());
        }

        articleReqpository.save(article);

        //기존 categoryArticle 삭제
        categoryArticleRepository.deleteCategoryArticleByArticle(article);

        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                // 카테고리를 찾아온다
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 존재하지 않습니다."));

                // 찾아온 카테고리를 게시글과 연결해서 CategoryArticle을 생성한다
                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .article(article)
                        .category(category)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }

        // article 수정 로그 생성
        ArticleLog articleLog = ArticleLog.builder()
                .article(article)
                .title(article.getTitle())
                .content(article.getContent())
                .build();
        articleLogRepository.save(articleLog);

        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent());
    }
}
