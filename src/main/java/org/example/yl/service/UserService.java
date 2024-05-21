package org.example.yl.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.yl.model.UserDto;
import org.example.yl.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    //로그인
    public UserDto getLoginUser(String userId, String pw) {
        UserDto user=null;
        UserDto dbuser=mapper.getLoginUser(userId);
        if(dbuser!=null){
            if(dbuser.getPw().equals(pw)){
                user=dbuser;
            }
        }
        return user;
    }

    public boolean join(UserDto user){
        if(mapper.getLoginUser(user.getUserId())==null){
            mapper.insertUser(user);
            return true;
        }
        return true;
    }

    public void modify(UserDto user){
        mapper.updateUser(user);
    }

    public void delete(String user_idx){
        mapper.deleteUser(user_idx);
    }

    public UserDto getUser(String user_idx){
        return mapper.getLoginUser(user_idx);
    }

    public List<UserDto> getUserList(){
        return mapper.getUserList();
    }

}
