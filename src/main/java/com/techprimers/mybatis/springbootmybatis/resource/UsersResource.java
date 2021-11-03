package com.techprimers.mybatis.springbootmybatis.resource;

import com.techprimers.mybatis.springbootmybatis.mapper.UsersMapper;
import com.techprimers.mybatis.springbootmybatis.model.Task;
import com.techprimers.mybatis.springbootmybatis.model.Users;
import com.techprimers.mybatis.springbootmybatis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {

    private UsersMapper usersMapper;
    @Autowired
    private UsersService usersService;

    public UsersResource(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }


    @GetMapping("/all2")
    public List<Users> getAll() {
        return usersService.getAll();
    }
    @GetMapping("/all")
    public void getAllTask() {
         usersService.testCachewithDifferentNamespace();
    }


    @PostMapping("/update")
    public int  updateUser(@RequestBody Users users) {
       return usersMapper.update(users);
    }

    @PostMapping("/del")
    public int  updateUser(@RequestParam("id") long id) {
        return usersMapper.delete(id);
    }

    @PostMapping("/batchInsert")
    private List<Users> update() {
        List<Users> userList=new ArrayList<Users>();


        for (int i = 0; i < 10; i++) {
            Users users = new Users();
            users.setNickName("yuwang"+i);
            userList.add(users);
        }

        usersService.batchInsert(userList);

        return usersMapper.findAll();
    }
}
