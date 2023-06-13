package com.samsung.project.repo;

import com.samsung.project.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserRepo {
    @Select("SELECT * from users where username= #{username}")
    User findUserByUsername(String username);

    @Insert("insert into users(username,password,enabled) values(#{username},#{password},1)")
    int save(User user);
}
