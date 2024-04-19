package com.nhom10.pbl.security.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.Article;
import com.nhom10.pbl.models.Status;
import com.nhom10.pbl.payload.response.ArticleResponse;
import com.nhom10.pbl.payload.resquest.ArticleRequest;
import com.nhom10.pbl.repository.ArticleRepository;
import com.nhom10.pbl.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleResponse createArticle(ArticleRequest article) {
        Article newArticle = new Article();
        try {
            newArticle.setTitle(article.getTitle());
            newArticle.setContent(article.getContent());
            newArticle.setUser(userRepository.getUserById(article.getUserId())
                    .orElseThrow(() -> new NullPointerException("User not found")));
            newArticle.setCreatedAt(new Date(System.currentTimeMillis()));
            newArticle.setUpdatedAt(new Date(System.currentTimeMillis()));
            newArticle.setStatus(Status.PENDING);
            articleRepository.save(newArticle);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
        return ArticleResponse.mapToArticleResponse(newArticle);
    }

    public Article findByTitle(String title) {
        return articleRepository.findByTitle(title).orElseThrow(() -> new NullPointerException("Article not found"));
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream().map(ArticleResponse::mapToArticleResponse)
                .collect(Collectors.toList());
    }
}
