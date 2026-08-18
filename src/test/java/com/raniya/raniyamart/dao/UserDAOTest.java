package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.dao.impl.UserDAOImpl;
import com.raniya.raniyamart.model.User;
import com.raniya.raniyamart.util.DBUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private final UserDAO userDAO = new UserDAOImpl();

    @BeforeAll
    static void initDb() {
        DBUtil.initializeDataSource();
    }

    @Test
    void testCreateAndFindByEmail() {
        User user = new User();
        user.setFullName("DAO Test User");
        String uniqueEmail = "daotest_" + System.currentTimeMillis() + "@example.com";
        user.setEmail(uniqueEmail);
        user.setPasswordHash("hashed_secret");
        user.setRole("BUYER");

        User created = userDAO.create(user);
        assertNotNull(created.getId());

        Optional<User> found = userDAO.findByEmail(uniqueEmail);
        assertTrue(found.isPresent());
        assertEquals("DAO Test User", found.get().getFullName());
    }
}
