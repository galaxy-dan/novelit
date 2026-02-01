package com.galaxy.novelit.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.novelit.annotation.MockCustomUser;
import com.galaxy.novelit.auth.dto.response.LoginResDTO;
import com.galaxy.novelit.auth.service.AuthService;
import com.galaxy.novelit.auth.util.JwtUtils;
import com.galaxy.novelit.author.domain.User;
import com.galaxy.novelit.author.repository.UserRepository;
import com.galaxy.novelit.comment.request.CommentAddRequest;
import com.galaxy.novelit.comment.service.CommentService;
import com.galaxy.novelit.notification.service.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

// junit5 test
@WebMvcTest(CommentController.class)
//@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CommentService commentService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    NotificationService notificationService;

    @MockBean
    AuthService authService;

    @MockBean
    JwtUtils jwtUtils;

    String accessToken;

    HttpHeaders headers;

    String spaceUUID = "space-UUID";
    String directoryUUID = "directory-UUID";
    String commentContent = "comment-Content";
    String commentNickname = "RyuHeonJin";
    String userUUID;

    String email;
    String nickname;

    @MockBean
    Authentication authentication;

    /*
    RestTemplate restTemplate;

    @Value("${KAKAO_CLIENT_ID}")
    String KAKAO_CLIENT_ID;

    @Value("${KAKAO_REDIRECT_URI}")
    String KAKAO_REDIRECT_URI;*/

    User user;

    @BeforeEach
    void setup(){
        /*restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add();
        URI uri = restTemplate.postForLocation(KAKAO_REDIRECT_URI, null);
        String[] split = uri.getQuery().split("=", 2);
        String code = split[1];

        LoginResDTO loginResDTO = authService.kakaoLogin(code);

        accessToken = loginResDTO.getAccessToken();*/

        email = "dd@kakao.com";

        user = new User(email, username);

        /*
        nickname = "mocknickname";

        User user = new User(email,nickname);

        authentication = UsernamePasswordAuthenticationToken.authenticated(user, "", List.of(() -> "USER"));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);*/
    }

    String username = "username";

    @Test
    //@MockCustomUser
    @DisplayName("댓글 작성 성공 : 알림까지")
    void addComment() throws Exception {
        CommentAddRequest request = new CommentAddRequest(spaceUUID, directoryUUID
                , commentContent, commentNickname);

        String content = objectMapper.writeValueAsString(request);

        Mockito.doNothing().when(commentService).addComment(request, "user-uuid");
        Mockito.doNothing().when(notificationService).notifyAllCommentReceiver(request, "user-uuid");

        /*HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + "mock accesstoken");*/

        mockMvc.perform(MockMvcRequestBuilders.post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login()
                                .attributes(attributes -> attributes.put("sub", "user-uuid"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private void setUserToContextByUsername() {
        authentication = UsernamePasswordAuthenticationToken.authenticated(user, "", List.of(() -> "USER"));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
    }

}
