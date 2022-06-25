package com.encore.byebuying.domain.user.dto;

import com.encore.byebuying.domain.user.Location;
import lombok.Getter;

import java.util.Collection;

@Getter
public class UserSaveDTO {
    private String username;
    private String password;
    private String email;
    private int defaultLocationIdx;
    private Collection<Location> locations;
}
