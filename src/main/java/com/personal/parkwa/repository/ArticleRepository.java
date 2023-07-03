package com.personal.parkwa.repository;

import java.util.ArrayList;

import com.personal.parkwa.entity.Article;
import com.personal.parkwa.entity.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.server = :server")
    Page<Article> findByServer(Server server, Pageable pageable);

    // 서버 받고 제목이나 내용이 keyword ( 서버 항목에서 keyword )
    @Query("SELECT a FROM Article a WHERE a.server = :server AND (a.title LIKE %:keyword% AND a.content LIKE %:keyword%)")
    Page<Article> findByServerAndContainingKeyword(Server server, String keyword, Pageable pageable);

    // 서버 받고 searchBy = keyword

    @Query("SELECT a FROM Article a WHERE a.server = :server AND (:searchBy = 'title' AND a.title LIKE %:keyword%) OR (:searchBy = 'content' AND a.content LIKE %:keyword%)")
    Page<Article> findByServerAndSearchBy(Server server, String searchBy, String keyword,
                                          Pageable pageable);

    // 제목이나 내용이 keyword
    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% AND a.content LIKE %:keyword%")
    Page<Article> findByContainingKeyword(String keyword, Pageable pageable);

    // searchBy = keyword
    @Query("SELECT a FROM Article a WHERE (:searchBy = 'title' AND a.title LIKE %:keyword%) OR (:searchBy = 'content' AND a.content LIKE %:keyword%)")
    Page<Article> findByTitleContainingKeywordOrContentContainingKeyword(String searchBy, String keyword,
                                                                         Pageable pageable);

    ArrayList<Article> findByUserUserId(Long userId);

    @Modifying
    @Query("update Article p set p.view = p.view + 1 where p.id = :id")
    int updateView(Long id);
}
