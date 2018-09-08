package com.github.vspro.persistent.dao;

import com.github.vspro.persistent.domain.UserDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserDo record);

    UserDo selectByPrimaryKey(Long id);

    List<UserDo> selectAll();

    int updateByPrimaryKey(UserDo record);

    UserDo selectByUserName(@Param("userName") String userName);

    List<String> loadAuthoritiesByUserName(@Param("userName") String userName);
}