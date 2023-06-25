package com.samsung.project.config;

import com.samsung.project.filter.AuthenticationEntryPoint;
import com.samsung.project.filter.UnAuthorizeFilter;
import com.samsung.project.security.InitialAuthenticationFilter;
import com.samsung.project.security.JwtAuthenticationFilter;
import com.samsung.project.security.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final InitialAuthenticationFilter initialAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final UnAuthorizeFilter unAuthorizeFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    public SecurityConfiguration(InitialAuthenticationFilter initialAuthenticationFilter, JwtAuthenticationFilter jwtAuthenticationFilter, UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider, UnAuthorizeFilter unAuthorizeFilter, AuthenticationEntryPoint authenticationEntryPoint) {
        this.initialAuthenticationFilter = initialAuthenticationFilter;

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.unAuthorizeFilter = unAuthorizeFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .mvcMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(unAuthorizeFilter)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
        ;

    }

}
