package com.atlantis.dynamic.jdk;

import com.atlantis.static1.User;

public interface UserService {

    void register(User user);

    boolean login(String name, String password);
}
