package com.atlantis.dao;

import com.atlantis.entity.User;

import java.util.List;

public interface UserDAO {

    public void save(User user);

    public List<User> queryAllUsers();
}
