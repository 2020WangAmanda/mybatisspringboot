package com.techprimers.mybatis.springbootmybatis.mapper;

import com.techprimers.mybatis.springbootmybatis.model.Task;
import com.techprimers.mybatis.springbootmybatis.model.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/11/03
 */
@Mapper
public interface TaskMapper {

    @Select("select * from task where id =#{id}")
    Task findByd(Long id);

    @Select("SELECT * from task ")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "name", property = "name"),
        @Result(property = "users",
            column="id",
            javaType = Users.class,
            one=@One(select ="com.techprimers.mybatis.springbootmybatis.mapper.UsersMapper.findById")
        )
    })
    List<Task> findAll();

    @Update("update task set name =#{name} WHERE id=#{id}")
    int updateTask(Task task);

}

