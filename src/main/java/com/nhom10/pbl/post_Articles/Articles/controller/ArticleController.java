package com.nhom10.pbl.post_Articles.Articles.controller;

import com.nhom10.pbl.post_Articles.Articles.model.Article;
import com.nhom10.pbl.post_Articles.Articles.model.ArticleImage;
import com.nhom10.pbl.post_Articles.Articles.repository.ArticleRepository;
import com.nhom10.pbl.post_Articles.Articles.repository.ArticleImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleImageRepository articleImageRepository;

    @PostMapping("/newArticle")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleRequest request) {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());

        Article savedArticle = articleRepository.save(article);

        for (String imageUrl : request.getImageUrls()) {
            ArticleImage articleImage = new ArticleImage();
            articleImage.setImageUrl(imageUrl);
            articleImage.setArticle(savedArticle);
            articleImageRepository.save(articleImage);
        }

//        return ResponseEntity.ok("Article created successfully");
        return ResponseEntity.ok(savedArticle);
//        Benh dau mua dang b....
//        <img src:="...."></img>
    }
}
