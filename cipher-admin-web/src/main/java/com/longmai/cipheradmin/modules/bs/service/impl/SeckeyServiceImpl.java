package com.longmai.cipheradmin.modules.bs.service.impl;

import ch.ntb.inf.kmip.kmipenum.EnumCryptographicAlgorithm;
import ch.ntb.inf.kmip.kmipenum.EnumObjectType;
import ch.ntb.inf.kmip.kmipenum.EnumOperation;
import com.longmai.cipheradmin.modules.bs.domain.KmsCryptographicObject;
import com.longmai.cipheradmin.modules.bs.param.SecKeyCreateParam;
import com.longmai.cipheradmin.modules.bs.param.SecKeyDestroyParam;
import com.longmai.cipheradmin.modules.bs.param.SecKeyParam;
import com.longmai.cipheradmin.modules.bs.param.SecKeyQueryParam;
import com.longmai.cipheradmin.modules.bs.repository.KmsCryptographicObjectRepository;
import com.longmai.cipheradmin.modules.bs.service.SeckeyService;
import com.longmai.cipheradmin.modules.bs.service.KmipService;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectQueryCriteria;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 密钥管理服务
 */
@Service
@Slf4j
public class SeckeyServiceImpl implements SeckeyService {

    @Resource
    private KmipService kmip;
    @Resource
    private KmsCryptographicObjectRepository kmsCryptographicObjectRepository;

    public static final Map<Integer, List<Integer>> OBJECT_TYPE_MAP = new HashMap<>();

    public static final Map<Integer, List<Integer>> OPERATION_MAP = new HashMap<>();

    static {
        OBJECT_TYPE_MAP.put(EnumObjectType.SymmetricKey, Arrays.asList(EnumCryptographicAlgorithm.AES, EnumCryptographicAlgorithm.DES,
                EnumCryptographicAlgorithm.DESede));
        OBJECT_TYPE_MAP.put(EnumObjectType.PublicKey, Arrays.asList(EnumCryptographicAlgorithm.RSA));
        OBJECT_TYPE_MAP.put(EnumObjectType.PrivateKey, Arrays.asList(EnumCryptographicAlgorithm.RSA));

        OPERATION_MAP.put(EnumOperation.Create, Arrays.asList(EnumCryptographicAlgorithm.AES, EnumCryptographicAlgorithm.DES,
                EnumCryptographicAlgorithm.DESede));
        OPERATION_MAP.put(EnumOperation.CreateKeyPair, Arrays.asList(EnumCryptographicAlgorithm.RSA));
    }


    @Override
    public List<String> createSecKeys(SecKeyCreateParam secKeyCreateParam) {
        if (Objects.isNull(secKeyCreateParam)) {
            return null;
        }
        List<String> uuidKeys = kmip.sendCreatRequest(secKeyCreateParam);
        return uuidKeys;
    }

    @Override
    public List<String> destroySecKeys(SecKeyDestroyParam secKeyDestroyParam) {
        if(Objects.isNull(secKeyDestroyParam)){
            return null;
        }
        List<String> uuidKeys = kmip.sendDestoryRequest(secKeyDestroyParam);
        return uuidKeys;
    }

    @Override
    public Map<String,Object> queryAll(SecKeyQueryParam queryParam, Pageable pageable) {
        Page<SecKeyParam> page = kmsCryptographicObjectRepository.findAllPage((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,queryParam,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

}
