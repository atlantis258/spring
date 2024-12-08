package com.xutp.dao;

import com.xutp.entity.User;

import java.util.List;

public interface UserDAO {

    public void save(User user);

    public List<User> queryAllUsers();
}
