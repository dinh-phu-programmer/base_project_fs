package com.samsung.project.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.samsung.project.exception.ErrorHttpResponse;
import com.samsung.project.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Value(("${jwt.signing.key}"))
    private String signingKey;

    @Autowired
    public InitialAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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

        if (user.getPassword() == null) {
            ErrorHttpResponse errorHttpResponse = new ErrorHttpResponse();
            errorHttpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            errorHttpResponse.setMessage("password can't null");
            errorHttpResponse.setDateTime(LocalDateTime.now());
            errorHttpResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());

            response.resetBuffer();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            response.getOutputStream().print(mapper.writeValueAsString(errorHttpResponse));
            response.flushBuffer(); // marks response as committed -- if we don't do this the request will go through normally!
//            filterChain.doFilter(request,response);
        } else {
            Authentication authentication = new UsernamePasswordAuthentication(user.getUsername(), user.getPassword());

            authenticationManager.authenticate(authentication);

            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
            Map<String, String> claimsMaps = new HashMap<>();
            claimsMaps.put("username", user.getUsername());
            String jwt = Jwts.builder()
                    .setClaims(claimsMaps)
                    .signWith(key)
                    .compact();

            response.setHeader("Authorization", jwt);
            response.addCookie(new Cookie("token", jwt));
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login") || !request.getMethod().equalsIgnoreCase("POST");
    }
}
