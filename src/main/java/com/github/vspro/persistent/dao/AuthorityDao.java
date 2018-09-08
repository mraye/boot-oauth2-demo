package com.github.vspro.persistent.dao;

import com.github.vspro.persistent.domain.AuthorityDo;
import java.util.List;

public interface AuthorityDao {
    int deleteByPrimaryKey(Long id);

    int insert(AuthorityDo record);

    AuthorityDo selectByPrimaryKey(Long id);

    List<AuthorityDo> selectAll();

    int updateByPrimaryKey(AuthorityDo record);
}