package org.example.yl.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.yl.model.UserDto;

import java.util.List;

public interface UserMapper {

    public UserDto getLoginUser(@Param("userId") String userId);

    public String insertUser(UserDto user);

    public String updateUser(UserDto user);

    public String deleteUser(@Param("user_idx") String user_idx);

    public List<UserDto> getUserList();
}
