package com.samsung.project.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samsung.project.exception.ErrorHttpResponse;
import com.samsung.project.model.User;
import com.samsung.project.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.samsung.project.constant.SecurityConstant.*;


@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Value(("${jwt.signing.key}"))
    private String signingKey;

    @Autowired
    public InitialAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        User user = mapper.readValue(sb.toString(), User.class);

        if (user.getUsername() == null || user.getPassword() == null) {
            ErrorHttpResponse errorHttpResponse = ErrorHttpResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("username or password is not correct")
                    .dateTime(LocalDateTime.now())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();

            response.resetBuffer();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            response.getOutputStream().print(mapper.writeValueAsString(errorHttpResponse));
            response.flushBuffer();
        } else {
            Authentication authentication = new UsernamePasswordAuthentication(user.getUsername(), user.getPassword());

            authenticationManager.authenticate(authentication);

            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

            Map<String, Object> claimsUsername = new HashMap<>();
            claimsUsername.put(USERNAME, user.getUsername());

            Map<String, Object> claimsAuthorities = new HashMap<>();
            claimsAuthorities.put(AUTHORITIES, this.getUserAuthorities());

            String jwt = Jwts.builder()
                    .setClaims(claimsUsername)
                    .addClaims(claimsAuthorities)
                    .signWith(key)
                    .compact();

            response.setHeader(AUTHORIZATION, jwt);
            response.addCookie(new Cookie(TOKEN, jwt));
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login") || !request.getMethod().equalsIgnoreCase("POST");
    }

    private List<String> getUserAuthorities() {
        return Arrays.asList("admin", "manager");
    }
}
