package com.techprimers.mybatis.springbootmybatis.model;

public class Users {

    private String nickName;
    private Long id;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }


    public Long getId() {
        return id;
    }
}
