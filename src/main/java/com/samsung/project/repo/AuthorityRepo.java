package com.samsung.project.repo;

import com.samsung.project.model.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AuthorityRepo {

    @Select("SELECT * from authorities where username= #{username}")
    List<Authority> getAuthoritiesByUsername(String username);
}
