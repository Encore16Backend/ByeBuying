package com.encore.byebuying.domain.user.dto;

import com.encore.byebuying.domain.user.Location;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
