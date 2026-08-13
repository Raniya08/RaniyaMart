package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.model.User;

public interface UserDAO {

    boolean create(User user);

    User findByEmail(String email);
}
