package com.longmai.cipheradmin.modules.bs.service;

import ch.ntb.inf.kmip.container.KMIPContainer;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateDto;
import com.longmai.cipheradmin.modules.system.param.SecKeyCreateParam;

public interface Kmip {

    String sendCreatRequest(BsTemplateDto bsTemplateDto);

    String sendCreatRequest(SecKeyCreateParam bsTemplateDto);

    public String sendDestroyRequest(BsTemplateDto bsTemplateDto);

    public String sendRegisterRequest(BsTemplateDto bsTemplateDto) ;
}
