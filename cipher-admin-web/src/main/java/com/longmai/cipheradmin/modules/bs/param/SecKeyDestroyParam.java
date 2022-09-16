package com.longmai.cipheradmin.modules.bs.param;

import lombok.Data;

import java.util.List;

@Data
public class SecKeyDestroyParam {
    private List<String> uuidKeys;
    private String username;
    private String password;

}
