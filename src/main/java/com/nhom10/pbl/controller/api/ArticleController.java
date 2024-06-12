package com.nhom10.pbl.controller.api;

import com.nhom10.pbl.models.Status;
import org.springframework.http.ResponseEntity;

import com.nhom10.pbl.payload.request.ArticleRequest;
import com.nhom10.pbl.payload.response.ArticleResponse;
import com.nhom10.pbl.services.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleRequest articleRequest) {
        try {
            return ResponseEntity.ok(articleService.createArticle(articleRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public List<ArticleResponse> getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @GetMapping("/title/{title}")
    public List<ArticleResponse> getArticleByTitle(@PathVariable String title) {
        return articleService.getArticleByTitle(title);
    }

    @GetMapping("/content/{content}")
    public List<ArticleResponse> getArticleByContent(@PathVariable String content) {
        return articleService.getArticleByContent(content);
    }

    @GetMapping("/author/{authorName}")
    public List<ArticleResponse> getArticleByAuthor(@PathVariable String authorName) {
        return articleService.getArticleByAuthor(authorName);
    }

    @GetMapping("/status/{status}")
    public List<ArticleResponse> getArticleByStatus(@PathVariable String status) {
        Status articleStatus = Status.valueOf(status.toUpperCase()); // Chuyển đổi String sang Status
        return articleService.getArticleByStatus(articleStatus);
    }

    @GetMapping("/all")
    public List<ArticleResponse> getAllArticles() {
        return articleService.getAllArticles();
    }

    // Thêm endpoint mới để lấy 3 bài viết mới nhất
    @GetMapping("/top3")
    public List<ArticleResponse> getTop3Articles() {
        return articleService.getTop3Articles();
    }

}
