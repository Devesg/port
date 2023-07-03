package com.personal.parkwa.service;

import java.util.List;

import javax.transaction.Transactional;

import com.personal.parkwa.dto.ArticleForm;
import com.personal.parkwa.entity.Article;
import com.personal.parkwa.entity.Server;
import com.personal.parkwa.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Transactional
    public int updateView(Long id) {
        return articleRepository.updateView(id);
    }

    // 서버검색
    public Page<Article> findByServer(Server server, Pageable pageable) {
        return articleRepository.findByServer(server, pageable);
    }

    // 서버+제목+내용 검색
    public Page<Article> findByServerAndContainingKeyword(Server server, String keyword, Pageable pageable) {
        return articleRepository.findByServerAndContainingKeyword(server, keyword, pageable);
    }

    // 서버+제목 or 서버+내용 검색
    public Page<Article> findByServerAndSearchBy(Server server, String searchBy, String keyword, Pageable pageable) {
        return articleRepository.findByServerAndSearchBy(server, searchBy, keyword, pageable);
    }

    // 제목+내용 검색
    public Page<Article> findByContainingKeyword(String keyword, Pageable pageable) {
        return articleRepository.findByContainingKeyword(keyword, pageable);
    }

    // 제목 or 내용 검색
    public Page<Article> findByTitleContainingKeywordOrContentContainingKeyword(String searchBy, String keyword,
                                                                                Pageable pageable) {
        return articleRepository.findByTitleContainingKeywordOrContentContainingKeyword(searchBy, keyword, pageable);
    }

    /* Select */
    public Article doSelect(Long articleId) {
        return articleRepository.findById(articleId).get();
    }

    /* Insert */
    public void doInsert(Article article) {
        articleRepository.save(article);
    }

    /* Update */
    public void doUpdate(Long articleId, ArticleForm form) {
        Article saveArticle = articleRepository.findById(articleId).get();
        saveArticle.setTitle(form.getTitle());
        saveArticle.setServer(form.getServer());
        saveArticle.setContent(form.getContent());
        ;
        articleRepository.save(saveArticle);
    }

    /* Delete */
    public void doDelete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public List<Article> findByUid(Long userId) {
        return articleRepository.findByUserUserId(userId);
    }
}
