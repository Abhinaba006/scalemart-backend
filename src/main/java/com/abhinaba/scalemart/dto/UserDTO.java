package com.abhinaba.scalemart.dto;

import com.abhinaba.scalemart.model.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private Role role;
}