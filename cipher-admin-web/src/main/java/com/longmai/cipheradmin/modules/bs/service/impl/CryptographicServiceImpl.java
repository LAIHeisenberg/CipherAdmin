package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.service.CryptographicService;
import com.longmai.cipheradmin.modules.bs.service.Kmip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptographicServiceImpl implements CryptographicService {
    @Autowired
    Kmip kmip;



}
