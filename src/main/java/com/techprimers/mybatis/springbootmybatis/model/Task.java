package com.techprimers.mybatis.springbootmybatis.model;

/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/11/03
 */
public class Task {

    private String name;
    private Long id;
    private Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String nname) {
        this.name = nname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
