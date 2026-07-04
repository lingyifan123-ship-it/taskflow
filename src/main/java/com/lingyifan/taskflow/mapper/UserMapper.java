package com.lingyifan.taskflow.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingyifan.taskflow.domain.User;
import org.apache.ibatis.annotations.Select;
public interface UserMapper extends BaseMapper<User> { @Select("select * from app_user where username=#{username}") User selectByUsername(String username); }
