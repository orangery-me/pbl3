package com.nhom10.pbl.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    private String title;
    private String content;
    private Long userId;
}
