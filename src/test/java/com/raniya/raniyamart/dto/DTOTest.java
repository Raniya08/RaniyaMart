package com.raniya.raniyamart.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DTOTest {

    @Test
    void testUserResponseDTOSettersAndGetters() {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(10L);
        dto.setFullName("Raniya Admin");
        dto.setEmail("admin@raniyamart.com");
        dto.setRole("ADMIN");

        assertEquals(10L, dto.getId());
        assertEquals("Raniya Admin", dto.getFullName());
        assertEquals("admin@raniyamart.com", dto.getEmail());
        assertEquals("ADMIN", dto.getRole());
    }

    @Test
    void testUserResponseDTOConstructor() {
        com.raniya.raniyamart.model.User user = new com.raniya.raniyamart.model.User();
        user.setId(5L);
        user.setFullName("Jane Buyer");
        user.setEmail("buyer@example.com");
        user.setRole("BUYER");

        UserResponseDTO dto = new UserResponseDTO(user);
        assertEquals(5L, dto.getId());
        assertEquals("Jane Buyer", dto.getFullName());
        assertEquals("buyer@example.com", dto.getEmail());
        assertEquals("BUYER", dto.getRole());
    }
}
