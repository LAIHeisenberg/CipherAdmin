package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.param.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeckeyService {
    /**
     * 创建
     * @param secKeyCreateParam
     * @return
     */
    List<String> createSecKeys(SecKeyCreateParam secKeyCreateParam);

    /**
     * 销毁
     * @param secKeyDestroyParam
     * @return
     */
    List<String> destroySecKeys(SecKeyDestroyParam secKeyDestroyParam);

    /**
     * 归档
     * @param secKeyArchiveParam
     * @return
     */
    List<String> archiveSecKeys(SecKeyArchiveParam secKeyArchiveParam);

    /**
     * 激活
     * @param secKeyActivateParam
     * @return
     */
    List<String> activateSecKeys(SecKeyActivateParam secKeyActivateParam);

    /**
     * 注销
     * @return
     */
    List<String> revokeSecKeys(SecKeyRevokeParam secKeyRevokeParam);

    /**
     * 恢复
     * @return
     */
    List<String> recoverSecKeys(SecKeyDestroyParam secKeyDestroyParam);


    Object queryAll(SecKeyQueryParam queryParam, Pageable pageable);
}
