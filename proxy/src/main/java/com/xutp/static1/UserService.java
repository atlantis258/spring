package com.xutp.static1;

public interface UserService {

    void register(User user);

    boolean login(String name, String password);
}
