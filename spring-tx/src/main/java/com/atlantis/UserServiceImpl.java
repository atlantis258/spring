package com.atlantis;

import com.atlantis.dao.UserDAO;
import com.atlantis.entity.User;
import org.springframework.transaction.annotation.Transactional;

// 切入点
@Transactional
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void register(User user) {
        this.userDAO.save(user);
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("UserServiceImpl.login");
        return false;
    }
}
