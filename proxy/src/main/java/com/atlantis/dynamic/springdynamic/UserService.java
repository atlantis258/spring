package com.atlantis.dynamic.springdynamic;

import com.atlantis.static1.User;

public interface UserService {

    void register(User user);

    boolean login(String name, String password);
}
