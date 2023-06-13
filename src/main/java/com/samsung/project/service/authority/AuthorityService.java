package com.samsung.project.service.authority;

import com.samsung.project.model.Authority;
import com.samsung.project.repo.AuthorityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    private final AuthorityRepo authorityRepo;

    @Autowired
    public AuthorityService(AuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
    }
    
    public List<Authority> getAuthoritiesByUsername(String username) {
        return this.authorityRepo.getAuthoritiesByUsername(username);
    }
}
