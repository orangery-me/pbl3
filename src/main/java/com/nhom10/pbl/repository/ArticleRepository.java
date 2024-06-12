package com.nhom10.pbl.repository;

import org.springframework.stereotype.Repository;

import com.nhom10.pbl.models.Article;
import com.nhom10.pbl.models.Status;
import com.nhom10.pbl.models.UserModel;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    public @NonNull Optional<Article> findById(@NonNull Long id);

    public Optional<List<Article>> findByTitleContaining(String title);

    public Optional<List<Article>> findByContentContaining(String content);

    public Optional<List<Article>> findByAuthor(UserModel author);

    // @Query("Select a from Article a where a.createdAt <= : creationTime")
    // public Optional<List<Article>>
    // findAllWithCreatedAtBefore(@Param("creationTime") Date creationTime);

    public Optional<List<Article>> findByStatus(Status status);

    @NonNull
    public List<Article> findAll();

    // Thêm phương thức mới để lấy 3 bài viết mới nhất
    List<Article> findTop3ByOrderByCreatedAtDesc();
}
