package com.atlantis;

import com.atlantis.entity.User;

public interface UserService {

    void register(User user);

    boolean login(String name, String password);
}
