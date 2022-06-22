package com.encore.byebuying.domain;

import com.encore.byebuying.domain.code.RoleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTypeBTest {

    @Test
    public void Enum_Test() {
        String role = RoleType.ADMIN.getCode();

        assertThat(role).isEqualTo("ROLE_ADMIN");
    }

}