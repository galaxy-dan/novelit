package com.galaxy.novelit.annotation;

import com.galaxy.novelit.author.domain.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MockCustomUserSecurityContextFactory implements WithSecurityContextFactory<MockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(MockCustomUser annotation) {
        String username = StringUtils.hasLength(annotation.username()) ? annotation.username() : annotation.value();
        Assert.notNull(username, annotation + " cannnot have null username on both username and value properties");

        // 직접 securitycontext에 득록해서 사용해라..이건가.
        String email = "dd@kakao.com";
        String nickname = "mocknickname";

        User user = new User(email,nickname);

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, "", List.of(() -> "USER"));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }

    private List<GrantedAuthority> settingRole(MockCustomUser annotation) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (String role : annotation.roles()) {
            Assert.isTrue(!role.startsWith("ROLE_"), "roles cannot start with Role_ Got " + role);
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return grantedAuthorities;
    }


}
