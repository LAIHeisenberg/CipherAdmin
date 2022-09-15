package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.param.SecKeyCreateParam;
import com.longmai.cipheradmin.modules.bs.service.impl.SecKeyDestroyParam;

import java.util.List;

public interface KmipService {

    List<String> sendCreatRequest(SecKeyCreateParam createParam);

    List<String> sendDestoryRequest(SecKeyDestroyParam destroyParam);

//    public String sendDestroyRequest(BsTemplateDto bsTemplateDto);
//
//    public String sendRegisterRequest(BsTemplateDto bsTemplateDto) ;
}
