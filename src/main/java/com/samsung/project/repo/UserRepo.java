package com.samsung.project.repo;

import com.samsung.project.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepo {
    @Select("SELECT * from users where username= #{username}")
    User findUserByUsername(String username);

    @Insert("insert into users(username,password,email,created_at,updated_at) values(#{username},#{password},#{email},#{createdAt},#{updatedAt})")
    int save(User user);
}
