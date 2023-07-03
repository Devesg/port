package com.personal.parkwa.controller;

import javax.validation.Valid;

import com.personal.parkwa.dto.ArticleForm;
import com.personal.parkwa.dto.PaginationRequest;
import com.personal.parkwa.entity.Article;
import com.personal.parkwa.service.ArticleService;
import com.personal.parkwa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/write")
    public String writeform() {
        return "/article/write";
    }

    @PostMapping(value = "/write")
    public String write(@Valid ArticleForm form, BindingResult bindingResult, Model model) {

        log.info("{}", form.toString());

        Article article = Article
                .builder()
                .user(userService.findbyUserName(form.getName()))
                .title(form.getTitle())
                .content(form.getContent())
                .server(form.getServer())
                .build();
        log.info("{}", article.toString());
        articleService.doInsert(article);
        return "redirect:/articles/list";
    }


    @GetMapping(value = "/modify/{id}")
    public String modifyform(@PathVariable("id") Long articleId, Model model) {
        model.addAttribute("article", articleService.doSelect(articleId));
        return "/article/modify";
    }

    @PostMapping(value = "/modify")
    public String modifyArticle(Long articleId, @Valid ArticleForm form) {
        log.info(">>>>>>>>>>>>>  : {} ", articleId);
        articleService.doUpdate(articleId, form);
        return "redirect:/articles/list";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long articleId) {
        log.info(">>>>>>>>>>>>> 여기  : {} ", articleId);
        articleService.doDelete(articleId);
        return "redirect:/articles/list";
    }

    @GetMapping(value = "/list")
    public String getArticleList(PaginationRequest pageRequest, Model model) {

        log.info(">>>>>>>>>>> pageRequest : " + pageRequest.toString());
        PageRequest pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

        Page<Article> articlePage;

        if (pageRequest.getServer() != null) {
            model.addAttribute("server", pageRequest.getServer());
            if (pageRequest.getSearchBy() != null && !pageRequest.getSearchBy().equals("")) {
                model.addAttribute("searchBy", pageRequest.getSearchBy());
                model.addAttribute("keyword", pageRequest.getKeyword());
                // 서버 , 검색자, 키워드 매개변수 받고 검색
                log.info("서버, 검색자, 키워드");
                articlePage = articleService.findByServerAndSearchBy(pageRequest.getServer(), pageRequest.getSearchBy(), pageRequest.getKeyword(), pageable);
            } else {
                // 서버 검색
                log.info("서버");
                articlePage = articleService.findByServer(pageRequest.getServer(), pageable);
            }
        } else {
            if (pageRequest.getSearchBy() != null && !pageRequest.getSearchBy().equals("")) {
                // 검색자, 키워드 매개변수 받고 검색
                log.info("검색자, 키워드 매개변수 받고 검색");
                model.addAttribute("searchBy", pageRequest.getSearchBy());
                model.addAttribute("keyword", pageRequest.getKeyword());
                articlePage = articleService.findByTitleContainingKeywordOrContentContainingKeyword(pageRequest.getSearchBy(), pageRequest.getKeyword(), pageable);
            } else {
                // 기본 검색
                log.info("기본 검색");
                articlePage = articleService.getAllArticles(pageable);
            }
        }
        log.info(">>>>>>>>>> {}", articlePage.getTotalPages());
        model.addAttribute("list", articlePage.getContent());
        model.addAttribute("currentPage", articlePage.getNumber());
        model.addAttribute("totalPages", articlePage.getTotalPages());
        return "/article/list";
    }

    @GetMapping(value = "/{id}")
    public String getArticle(@PathVariable("id") Long articleId, Model model) {
        log.info(">>>>>>>>>>{}", articleId);
        articleService.updateView(articleId);
        model.addAttribute("article", articleService.doSelect(articleId));
        return "/article/article";
    }
}
