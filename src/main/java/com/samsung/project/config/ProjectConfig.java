//package com.samsung.project.config;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class ProjectConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Bean
//    public InitializingBean initializingBean() {
//        return () -> SecurityContextHolder.setStrategyName(
//                SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        String userByUserNameQuery = "select username, password, enabled from users where username = ?";
//        String authsByUserQuery = "select username, authority" +
//                " from authorities where username = ?";
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.setUsersByUsernameQuery(userByUserNameQuery);
//        userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);
//        return userDetailsManager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//                .mvcMatchers("/auth/**").permitAll()
//                .anyRequest()
//                .authenticated();
//    }
//
//
//    // ~~~~~~~~~~~~~~~~~~~~ Custom Authentication Provider ~~~~~~~~~~~~~~~
////    private final CustomAuthenticationProvider customAuthenticationProvider;
////
////    @Autowired
////    public ProjectConfig(CustomAuthenticationProvider customAuthenticationProvider) {
////        this.customAuthenticationProvider = customAuthenticationProvider;
////    }
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.authenticationProvider(customAuthenticationProvider);
////    }
//
//
////        @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
////
////        UserDetails user = User.withUsername("john")
////                .password("123")
////                .authorities("read")
////                .build();
////
////        userDetailsService.createUser(user);
////
////        auth.userDetailsService(userDetailsService)
////                .passwordEncoder(NoOpPasswordEncoder.getInstance());
////
////    }
//
//}
//
