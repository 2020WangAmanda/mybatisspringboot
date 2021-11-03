package com.techprimers.mybatis.springbootmybatis.service;

import com.techprimers.mybatis.springbootmybatis.mapper.TaskMapper;
import com.techprimers.mybatis.springbootmybatis.mapper.UsersMapper;
import com.techprimers.mybatis.springbootmybatis.model.Task;
import com.techprimers.mybatis.springbootmybatis.model.Users;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/10/15
 */
@Service
public class UsersService {
    private UsersMapper usersMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    public UsersService() {

    }
    public UsersService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }


    public List<Task> getAllTaskWithUser(){

        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        TaskMapper mapper= sqlSession.getMapper(TaskMapper.class);
       return mapper.findAll();


    }
    public void testCachewithDifferentNamespace(){
        SqlSession sqlSession1=sqlSessionFactory.openSession(true);
        SqlSession sqlSession2=sqlSessionFactory.openSession(true);
        SqlSession sqlSession3=sqlSessionFactory.openSession(true);

        TaskMapper taskmapper1= sqlSession1.getMapper(TaskMapper.class);
        TaskMapper taskmapper2= sqlSession2.getMapper(TaskMapper.class);


        UsersMapper usersmapper1 =sqlSession3.getMapper(UsersMapper.class);


        System.out.println("****1 user read--------"+taskmapper1.findAll().get(0).getUsers().getNickName());
        sqlSession1.close();
        System.out.println("****2 user read--------"+taskmapper2.findAll().get(0).getUsers().getNickName());
        Users users=new Users();
        users.setId(1l);
        users.setNickName("wuyan");
        usersmapper1.update(users);
        sqlSession3.commit();
        System.out.println("****2 user read after update--------- "+taskmapper2.findAll().get(0).getUsers().getNickName());



    }

    public List<Users> getAll(){

        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        UsersMapper mapper= sqlSession.getMapper(UsersMapper.class);

        SqlSession sqlSession1=sqlSessionFactory.openSession(true);
        UsersMapper mapper1 =sqlSession1.getMapper(UsersMapper.class);
//        //同一个session会话 修改操作执行后 一级缓存失效
//        System.out.println(" read----- "+mapper.findAll().size());
//        System.out.println(" read------"+mapper.findAll().size());
//        System.out.println(" delete------"+mapper.delete((long) 8));
//        System.out.println(" read------"+mapper.findAll().size());

        //两个session会话不共享缓存
        System.out.println(" read----- "+mapper.findAll().size());
        System.out.println(" read------"+mapper.findAll().size());
        System.out.println(" delete------"+mapper1.delete((long) 3));
        sqlSession1.commit();

        System.out.println(" read------"+mapper.findAll().size());

        return mapper.findAll();
    }

    public List<Users> getAllwith2Cache(){

        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        UsersMapper mapper= sqlSession.getMapper(UsersMapper.class);

        SqlSession sqlSession1=sqlSessionFactory.openSession(true);
        UsersMapper mapper1 =sqlSession1.getMapper(UsersMapper.class);
        SqlSession sqlSession2=sqlSessionFactory.openSession(true);
        UsersMapper mapper2 =sqlSession1.getMapper(UsersMapper.class);

        //当提交事务时，sqlSession1查询完数据后，sqlSession2相同的查询是否会从缓存中获取数据
        System.out.println(" read0----- "+mapper.findAll().size());
        sqlSession.commit();
        System.out.println(" read1------"+mapper1.findAll().size());

        mapper2.delete((long) 6);

        sqlSession2.commit();
        System.out.println(" read1------"+mapper1.findAll().size());


        return mapper.findAll();
    }

    //批量插入
    @Transactional
    public void batchInsert(List<Users> userList){
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        UsersMapper mapper = session.getMapper(UsersMapper.class);

        for (int i = 0; i <userList.size() ; i++) {
            mapper.insert(userList.get(i));
            if(i%1000==999){
                session.commit();
                session.clearCache();
            }
        }
        session.commit();
        session.clearCache();
    }
}
