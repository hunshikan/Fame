package com.zbw.fame.service.impl;

import com.zbw.fame.exception.TipException;
import com.zbw.fame.mapper.UsersMapper;
import com.zbw.fame.model.Users;
import com.zbw.fame.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User Service 层实现类
 *
 * @auther zbw
 * @create 2017/7/12 21:24
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Users login(String username, String password) {
        Users temp = new Users();
        temp.setUsername(username);
        temp.setPassword_md5(password);
        Users user = usersMapper.selectOne(temp);
        if (user == null) {
            throw new TipException("用户名或者密码错误");
        }
        return user;
    }
}
