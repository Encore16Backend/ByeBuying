package com.encore.byebuying.domain.user.dto;

import com.encore.byebuying.domain.user.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor // for Test
public class CreateUserDTO {
    private String username;
    private String password;
    private String email;
    private int defaultLocationIdx;
    private Collection<Location> locations;
}
