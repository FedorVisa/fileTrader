package com.example.usersData;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    GUEST,USER,ADMIN;

    @Override
    public String getAuthority() {
        return null;
    }
}
