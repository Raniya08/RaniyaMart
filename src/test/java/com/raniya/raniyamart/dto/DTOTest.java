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
        UserResponseDTO dto = new UserResponseDTO(5L, "Jane Buyer", "buyer@example.com", "BUYER");
        assertEquals(5L, dto.getId());
        assertEquals("Jane Buyer", dto.getFullName());
        assertEquals("buyer@example.com", dto.getEmail());
        assertEquals("BUYER", dto.getRole());
    }
}
