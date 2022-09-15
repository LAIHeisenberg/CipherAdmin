package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.param.SecKeyCreateParam;
import com.longmai.cipheradmin.modules.bs.service.CryptographicService;
import com.longmai.cipheradmin.modules.bs.service.KmipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CryptographicServiceImpl implements CryptographicService {
    @Autowired
    KmipService kmip;


    @Override
    public String createCryptographic(SecKeyCreateParam secKeyCreateParam) {
        if (Objects.isNull(secKeyCreateParam)) {
            return "";
        }
        return null;
    }
}
