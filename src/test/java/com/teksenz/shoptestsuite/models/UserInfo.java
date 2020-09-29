package com.teksenz.shoptestsuite.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private String username;
    private String password;
}
