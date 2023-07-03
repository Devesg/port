package com.personal.parkwa.service;

import java.util.List;

import com.personal.parkwa.entity.Article;
import com.personal.parkwa.entity.Role;
import com.personal.parkwa.entity.User;
import com.personal.parkwa.exception.AppException;
import com.personal.parkwa.exception.ErrorCode;
import com.personal.parkwa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User join(String name, String email, String password) {
        
        // email 중복 검사
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, email + "는 이미 있습니다.");
        });

        // save
        User user = User.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .name(name)
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return user;
    }

    public User login(String name, String email, String password) {
        // 인증 과정

        // 등록 회원 없음
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOTFOUND, " 등록된 " + email + "이 없습니다."));

        // password 틀림
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 했습니다.");
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email"));

        return toUserDetail(user);
    }

    private UserDetails toUserDetail(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
    public User findbyUserName(String name)
    {
        User user = userRepository.findByName(name);
        return user;
    }

    public void doUpdate(Long userId) {
        User user = userRepository.findById(userId).get();

        userRepository.save(user);
    }

    /* Delete */
    public void doDelete(Long userId) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("userId"));

        // 회원과 관련된 Article 삭제
        List<Article> articles = articleService.findByUid(userId);
        for (Article article : articles) {
            articleService.doDelete(article.getArticleId());
        }
        
        userRepository.delete(user);
    }
}
