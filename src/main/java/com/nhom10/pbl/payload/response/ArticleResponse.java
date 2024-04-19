package com.nhom10.pbl.payload.response;

import java.sql.Date;

import com.nhom10.pbl.models.Article;
import com.nhom10.pbl.models.Status;
import com.nhom10.pbl.payload.resquest.UserDTO;

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
    private UserDTO user;
    private Date createAt;
    private Date updatedAt;
    private Status status;

    public static ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .user(UserDTO.mapToUserDTO(article.getUser()))
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
                .user(UserDTO.mapToUserModel(articleResponse.getUser()))
                .createdAt(articleResponse.getCreateAt())
                .updatedAt(articleResponse.getUpdatedAt())
                .status(articleResponse.getStatus())
                .build();
    }
}
