package com.personal.parkwa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {

    @Id // 대표값
    @GeneratedValue(generator = "USER_SEQ_GENERATOR") // 자동생성 어노테이션
    @Column(name = "user_id")
    private Long userId;

    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;


}
