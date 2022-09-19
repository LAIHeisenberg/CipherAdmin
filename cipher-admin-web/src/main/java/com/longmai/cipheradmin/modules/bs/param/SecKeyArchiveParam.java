package com.longmai.cipheradmin.modules.bs.param;

import lombok.Data;

import java.util.List;

/**
 * 密钥归档请求
 */
@Data
public class SecKeyArchiveParam {
    private List<String> uuidKeys;
    private String username;
    private String password;
}
