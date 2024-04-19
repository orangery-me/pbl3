package com.nhom10.pbl.repository;

import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Article;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    public Optional<Article> findByTitle(String title);

    @NonNull
    public List<Article> findAll();
}
