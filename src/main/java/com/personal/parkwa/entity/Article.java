package com.personal.parkwa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Article extends BaseEntity {

    @Id // 대표값
    @GeneratedValue(generator = "ARTICLE_SEQ_GENERATOR") // 자동생성 어노테이션
    @Column(name = "article_id")
    private Long articleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", referencedColumnName = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Server server;

    @Column
    private String title;

    @Column
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

}
