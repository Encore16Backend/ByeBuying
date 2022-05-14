package com.encore.byebuying.requestDto;

import com.encore.byebuying.domain.Location;
import lombok.Getter;

import java.util.Collection;

@Getter
public class UserFormRequest {
    private String username;
    private String password;
    private String email;
    private int defaultLocationIdx;
    private Collection<Location> locations;
}
