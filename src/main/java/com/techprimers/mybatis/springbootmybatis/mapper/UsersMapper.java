package com.techprimers.mybatis.springbootmybatis.mapper;

import com.techprimers.mybatis.springbootmybatis.model.Task;
import com.techprimers.mybatis.springbootmybatis.model.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {

    @Select("select  from sk_user")
    List<Users> findAll();

    @Select("SELECT * FROM sk_user WHERE id =#{id}")
    Users findById(Long id);

    @Insert("insert into sk_user(nickname) values(#{nickName})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Long.class)
    void insert(Users users);


    @Update("UPDATE sk_user set nickname =#{nickName} WHERE id=#{id} ")
    int update(Users users);


    @Delete("DELETE FROM sk_user WHERE id =#{id}")
    int delete(Long id);
}
