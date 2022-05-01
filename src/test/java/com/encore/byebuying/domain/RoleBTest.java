package com.encore.byebuying.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleBTest {

    @Test
    public void Enum_Test() {
        String role = RoleB.ADMIN.getAuthority();

        assertThat(role).isEqualTo("ROLE_ADMIN");
    }

}