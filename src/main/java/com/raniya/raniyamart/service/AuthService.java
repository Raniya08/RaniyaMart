package com.raniya.raniyamart.service;

import org.mindrot.jbcrypt.BCrypt;

import com.raniya.raniyamart.dao.UserDAO;
import com.raniya.raniyamart.dao.UserDAOImpl;
import com.raniya.raniyamart.model.User;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAOImpl();
    }

    public boolean register(String name,
                            String email,
                            String password,
                            String role) {

        if (name == null || name.isBlank()) {
            return false;
        }

        if (email == null || email.isBlank()) {
            return false;
        }

        if (password == null || password.length() < 6) {
            return false;
        }

        if (!role.equals("BUYER") && !role.equals("SELLER")) {
            return false;
        }

        if (userDAO.findByEmail(email) != null) {
            return false;
        }

        String passwordHash =
                BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPasswordHash(passwordHash);
        user.setRole(role);

        return userDAO.create(user);
    }

    public User login(String email, String password) {

        if (email == null || password == null) {
            return null;
        }

        User user =
                userDAO.findByEmail(email.trim().toLowerCase());

        if (user == null) {
            return null;
        }

        if (!BCrypt.checkpw(
                password,
                user.getPasswordHash())) {

            return null;
        }

        return user;
    }
}
