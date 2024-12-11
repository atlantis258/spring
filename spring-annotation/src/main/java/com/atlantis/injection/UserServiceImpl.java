package com.atlantis.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
//    @Autowired
    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Autowired
    @Qualifier("userDAOImpl")
//    @Qualifier("userDAO")
    public void setUserDAO(UserDAO userDAO) {
        System.out.println("UserServiceImpl.setUserDAO");
        this.userDAO = userDAO;
    }

    @Override
    public void register() {
        userDAO.save();
    }
}
