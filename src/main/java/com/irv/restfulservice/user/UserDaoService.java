package com.irv.restfulservice.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> userList = new ArrayList<>();
    private static int usersCount = 3;
    static {
        userList.add(new User(1,"Joel",new Date()));
        userList.add(new User(2,"Alex",new Date()));
        userList.add(new User(3,"Wen",new Date()));
    }
    public List<User> findAll(){
        return userList;
    }
    public User save(User user){
        if (user.getId() ==  null){
            user.setId(++usersCount);
        }
        userList.add(user);
        return user;
    }
    public User findOne(int id){
        for (User u: userList){
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }
    public User deleteUser(int id){
        Iterator iterator = userList.iterator();
        while (iterator.hasNext()){
            User user = (User) iterator.next();
            if (user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
