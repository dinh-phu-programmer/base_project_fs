package com.samsung.project.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/hello")
    public String hello() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Thread.currentThread().getName()+" - "+ securityContext.getAuthentication().getName();
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @GetMapping("/hello-authority")
    public String hello2() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Thread.currentThread().getName()+" - "+ securityContext.getAuthentication().getName();
    }

}
