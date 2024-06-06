package com.nhom10.pbl.payload.response;

import java.util.Date;

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
    private UserDTO author;
    private Date createAt;
    private Date updatedAt;
    private Status status;

    public static ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(UserDTO.mapToUserDTO(article.getAuthor()))
                .createAt(article.getCreatedAt())
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
                .createdAt(articleResponse.getCreateAt())
                .updatedAt(articleResponse.getUpdatedAt())
                .status(articleResponse.getStatus())
                .build();
    }
}
