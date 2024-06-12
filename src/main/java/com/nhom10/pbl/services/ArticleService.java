package com.nhom10.pbl.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhom10.pbl.models.Article;
import com.nhom10.pbl.models.Status;
import com.nhom10.pbl.models.UserModel;
import com.nhom10.pbl.payload.response.ArticleResponse;
import com.nhom10.pbl.payload.request.ArticleRequest;
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
            newArticle.setAuthor(userRepository.findById(article.getUserId())
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

    public List<ArticleResponse> getArticleById(Long id) {
        List<ArticleResponse> responses = new ArrayList<ArticleResponse>();
        articleRepository.findById(id).ifPresent(article -> {
            responses.add(ArticleResponse.mapToArticleResponse(article));
        });
        return responses;
    }

    public List<ArticleResponse> getArticleByTitle(String title) {
        List<ArticleResponse> responses = new ArrayList<ArticleResponse>();
        articleRepository.findByTitleContaining(title).ifPresent(articles -> {
            responses.addAll(articles.stream().map(ArticleResponse::mapToArticleResponse).collect(Collectors.toList()));
        });
        return responses;
    }

    public List<ArticleResponse> getArticleByContent(String content) {
        List<ArticleResponse> responses = new ArrayList<ArticleResponse>();
        articleRepository.findByContentContaining(content).ifPresent(articles -> {
            responses.addAll(articles.stream().map(ArticleResponse::mapToArticleResponse).collect(Collectors.toList()));
        });
        return responses;
    }

    public List<ArticleResponse> getArticleByAuthor(String authorName) {
        List<UserModel> authors = userRepository.findByFullnameContaining(authorName)
                .orElseThrow(() -> new NullPointerException("Author not found"));
        List<ArticleResponse> responses = new ArrayList<ArticleResponse>();
        authors.forEach(author -> articleRepository.findByAuthor(author).ifPresent(articles -> {
            responses.addAll(articles.stream().map(ArticleResponse::mapToArticleResponse).collect(Collectors.toList()));
        }));
        return responses;
    }

    // public List<ArticleResponse> getArticleWithCreationTimeBefore(Date date) {
    // List<ArticleResponse> responses = new ArrayList<ArticleResponse>();
    // articleRepository.findAllWithCreatedAtBefore(date).ifPresent(articles -> {
    // responses.addAll(articles.stream().map(ArticleResponse::mapToArticleResponse).collect(Collectors.toList()));
    // });
    // return responses;
    // }

    public List<ArticleResponse> getArticleByStatus(Status status) {
        List<ArticleResponse> responses = new ArrayList<ArticleResponse>();
        articleRepository.findByStatus(status).ifPresent(articles -> {
            responses.addAll(articles.stream().map(ArticleResponse::mapToArticleResponse).collect(Collectors.toList()));
        });
        return responses;
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream().map(ArticleResponse::mapToArticleResponse)
                .collect(Collectors.toList());
    }

    public ArticleResponse findArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return ArticleResponse.mapToArticleResponse(article);
    }

    public List<ArticleResponse> getLatestArticles() {
        return articleRepository.findTop3ByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToArticleResponseWithImage)
                .collect(Collectors.toList());

    }

    private ArticleResponse mapToArticleResponseWithImage(Article article) { // để trích xuất URL của hình ảnh đầu tiên
                                                                             // từ nội dung bài viết
        // và cập nhật content của ArticleResponse thành URL của hình ảnh đầu tiên để
        // hiển thị trước khi người dùng click vào bài viết để xem chi tiết
        String firstImage = extractFirstImage(article.getContent());
        ArticleResponse response = ArticleResponse.mapToArticleResponse(article);
        response.setContent(firstImage);
        return response;
    }

    private String extractFirstImage(String content) { // tìm tất cả các thẻ hình ảnh <img> trong nội dung bài viết.
        // Lấy ra URL của hình ảnh đầu tiên từ kết quả tìm kiếm.
        Pattern pattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group(1) : null;
    }

    public ArticleResponse updateStatus(Long id, Status status) throws Exception {
        Article article = articleRepository.findById(id).orElseThrow(Exception::new);
        article.setStatus(status);
        articleRepository.save(article);
        return ArticleResponse.mapToArticleResponse(articleRepository.save(article));
    }
}
