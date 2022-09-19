package com.longmai.cipheradmin.modules.bs.param;

import lombok.Data;

import java.util.List;

@Data
public class SecKeyActivateParam {
    private List<String> uuidKeys;
    private String username;
    private String password;
}
