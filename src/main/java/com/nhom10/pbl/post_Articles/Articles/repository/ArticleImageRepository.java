package com.nhom10.pbl.post_Articles.Articles.repository;

import com.nhom10.pbl.post_Articles.Articles.model.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
}

