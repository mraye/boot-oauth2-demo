package com.github.vspro.framework.config.security;

import com.github.vspro.persistent.dao.UserDao;
import com.github.vspro.persistent.domain.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created  on 2018/9/8.
 */

@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDo userDo = userDao.selectByUserName(username);
        if (userDo == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        List<String> dbAuthorities = userDao.loadAuthoritiesByUserName(username);
        if (!dbAuthorities.isEmpty() && dbAuthorities.size() > 0){
            List<GrantedAuthority> list = dbAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            authorities.addAll(list);
        }
        return new User(userDo.getUserName(), userDo.getPassword(),authorities);
    }
}
