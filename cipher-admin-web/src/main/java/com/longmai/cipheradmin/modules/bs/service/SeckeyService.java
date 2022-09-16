package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.param.SecKeyCreateParam;
import com.longmai.cipheradmin.modules.bs.param.SecKeyDestroyParam;
import com.longmai.cipheradmin.modules.bs.param.SecKeyQueryParam;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeckeyService {
    List<String> createSecKeys(SecKeyCreateParam secKeyCreateParam);
    List<String> destroySecKeys(SecKeyDestroyParam secKeyDestroyParam);

    Object queryAll(SecKeyQueryParam queryParam, Pageable pageable);
}
