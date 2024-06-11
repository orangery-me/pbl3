package com.nhom10.pbl.payload.response;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nhom10.pbl.models.Article;
import com.nhom10.pbl.models.Status;
import com.nhom10.pbl.payload.request.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String firstImageUrl;
    private UserDTO author;
    private Date createdAt;
    private Date updatedAt;
    private Status status;

    public static ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .firstImageUrl(extractFirstImage(article.getContent()))
                .author(UserDTO.mapToUserDTO(article.getAuthor()))
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .status(article.getStatus())
                .build();
    }

    public static Article mapToArticle(ArticleResponse articleResponse) {
        return Article.builder()
                .id(articleResponse.getId())
                .title(articleResponse.getTitle())
                .content(articleResponse.getContent())
                .author(UserDTO.mapToUserModel(articleResponse.getAuthor()))
                .createdAt(articleResponse.getCreatedAt())
                .updatedAt(articleResponse.getUpdatedAt())
                .status(articleResponse.getStatus())
                .build();
    }

    private static String extractFirstImage(String content) {
        Pattern pattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group(1) : null;
    }
}
