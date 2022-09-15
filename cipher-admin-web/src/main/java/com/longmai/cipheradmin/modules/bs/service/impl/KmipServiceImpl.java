package com.longmai.cipheradmin.modules.bs.service.impl;

import ch.ntb.inf.kmip.attributes.*;
import ch.ntb.inf.kmip.container.KMIPBatch;
import ch.ntb.inf.kmip.container.KMIPContainer;
import ch.ntb.inf.kmip.kmipenum.*;
import ch.ntb.inf.kmip.objects.Authentication;
import ch.ntb.inf.kmip.objects.CredentialValue;
import ch.ntb.inf.kmip.objects.base.*;
import ch.ntb.inf.kmip.stub.KMIPStubInterface;
import ch.ntb.inf.kmip.types.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.longmai.cipheradmin.modules.bs.param.*;
import com.longmai.cipheradmin.modules.bs.service.KmipService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateDto;
import com.longmai.cipheradmin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import java.util.*;
import java.util.stream.Collectors;

/**
 * kmip服务
 * <p>
 * 加密算法：
 * 对称加密：DES\DESede\AES\
 * <p>
 * 非对称加密：RSA\DSA\ECDSA\DH\ECDH
 * <p>
 * 哈希加密：HMAC_SHA1\HMAC_SHA224\HMAC_SHA256\HMAC_SHA384\HMAC_SHA512\HMAC_MD5
 */
@Slf4j
@Service
public class KmipServiceImpl implements KmipService {

    @Autowired
    private KMIPStubInterface stub;

    public static final Map<Integer, List<Integer>> OBJECT_TYPE_MAP = new HashMap<>();

    public static final Map<Integer, List<Integer>> OPERATION_MAP = new HashMap<>();

    static {
        OBJECT_TYPE_MAP.put(EnumObjectType.SymmetricKey, Arrays.asList(EnumCryptographicAlgorithm.AES, EnumCryptographicAlgorithm.DES, EnumCryptographicAlgorithm.DESede));
        OBJECT_TYPE_MAP.put(EnumObjectType.PublicKey, Arrays.asList(EnumCryptographicAlgorithm.RSA));
        OBJECT_TYPE_MAP.put(EnumObjectType.PrivateKey, Arrays.asList(EnumCryptographicAlgorithm.RSA));

        OPERATION_MAP.put(EnumOperation.Create, Arrays.asList(EnumCryptographicAlgorithm.AES, EnumCryptographicAlgorithm.DES, EnumCryptographicAlgorithm.DESede));
        OPERATION_MAP.put(EnumOperation.CreateKeyPair, Arrays.asList(EnumCryptographicAlgorithm.RSA));
    }


    /**
     * 创建密钥
     *
     * @return 密钥IDS
     * <p>
     * 对称密钥
     * Test Case: Create / Destroy
     * Create (symmetric key)
     * In: objectType=’00000002’ (Symmetric Key), attributes={ CryptographicAlgorithm=’00000003’ (AES), CryptographicLength=’128’, CryptographicUsageMask=‘0000000C’ }
     * Out: objectType=’00000002’, uuidKey
     * <p>
     * Test Case: Register / Create / Get attributes / Destroy
     * Create (symmetric key using template)
     * In: objectType=’00000002’, template={ NameValue=’Template1’, NameType=’00000001’ }, attributes={ CryptographicAlgorithm=’AES’, CryptographicLength=’128’, CryptographicUsageMask=‘0000000C’ }
     * Out: objectType=’00000002’, uuidKey
     * <p>
     * Test Case: Create / Locate / Get / Destroy
     * Create (symmetric key)
     * In: objectType = ‘00000002’, attributes={ Name={ NameValue=‘Key1’, NameType=’00000001’ }, CryptographicAlgorithm=’3DES’, CryptographicLength=’168’, CryptographicUsageMask=‘0000000C’, ContactInformation=’Joe’  }
     * Out: objectType = ‘00000002’, uuidKey
     * <p>
     * 非对称密钥
     * In: commonAttributes={ CryptographicAlgorithm=’RSA’, CryptographicLength=’1024’ }, privateKeyAttributes={ Name={ NameValue=‘PrivateKey1’, NameType=’00000001’ }, CryptographicUsageMask=’00000001’ }, publicKeyAttributes={ NameValue=‘PublicKey1’, NameType=’00000001’ }, CryptographicUsageMask=’00000002’ }
     * Out: uuidPrivateKey, uuidPublicKey
     * <p>
     * Key Roll-over
     * Create (symmetric key)
     * In: objectType=’00000002’, attributes={ CryptographicAlgorithm=’AES’, CryptographicLength=’128’, CryptographicUsageMask=‘0000000C’, Name={ NameValue=‘rekeyKey’, NameType=’00000001’ } }
     * Out: objectType=’00000002’, uuidKey
     * <p>
     * Create (symmetric key)
     * In: objectType=’00000002’, attributes={ CryptographicAlgorithm=’AES’, CryptographicLength=’128’, CryptographicUsageMask=‘0000000C’, Name={ NameValue=‘rekeyKey’, NameType=’00000001’ } }
     * Out: objectType=’00000002’, uuidKey
     * <p>
     * Archival
     * Create (symmetric key)
     * In: objectType=’00000002’, attributes={ CryptographicAlgorithm=’AES’, CryptographicLength=’128’, CryptographicUsageMask=‘0000000C’, Name={ NameValue=‘archiveKey’, NameType=’00000001’ } }
     * Out: objectType=’00000002’, uuidKey
     */
    @Override
    public List<String> sendCreatRequest(SecKeyCreateParam createParam) {
        Integer objectType = createParam.getObjectType();
        KMIPContainer container = null;
        if (EnumObjectType.SymmetricKey == objectType) {//对称加密
            container = createParamToKMIPContainer(createParam);
        } else if (EnumObjectType.PrivateKey == objectType || EnumObjectType.PublicKey == objectType) { //非对称密钥
            container = createKeyPairParamToKMIPContainer(createParam);
        } else if (EnumObjectType.Template == objectType) {
            container = createTemplateParamToKMIPContainer(createParam);
        }
        KMIPContainer request = createParamToKMIPContainer(createParam);
        KMIPContainer response = stub.processRequest(request);
        log.info("\n-------\n" + response + "\n-----------");
        if (Objects.isNull(response) || CollectionUtil.isEmpty(response.getBatches())) {
            return null;
        }
        List<KMIPBatch> kmipBatches = response.getBatches();
        kmipBatches = kmipBatches.stream().filter(kmipBatch -> ObjectUtil.isNotNull(kmipBatch.getResultStatus()) && kmipBatch.getResultStatus().getValue() == EnumResultStatus.Success).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(kmipBatches)) {
            return null;
        }
        List<String> uuidKeys = new ArrayList<>();

        for (KMIPBatch kmipParam : kmipBatches) {
            ArrayList<Attribute> attributes = kmipParam.getAttributes();
            if (CollectionUtil.isEmpty(attributes)) {
                break;
            }
            attributes.forEach(attribute -> {
                if (attribute instanceof UniqueIdentifier) {
                    KMIPAttributeValue[] ar = attribute.getValues();
                    if (ArrayUtils.isNotEmpty(ar)) {
                        uuidKeys.add(ar[0].getValueString());
                    }
                }
            });
        }
        return uuidKeys;
    }

    /**
     * 销毁密钥
     * @param destroyParam
     * @return
     */
    @Override
    public List<String> sendDestoryRequest(SecKeyDestroyParam destroyParam) {
        List<String> uuidKeys = destroyParam.getUuidKeys();
        KMIPContainer container = new KMIPContainer();
        if (StringUtils.isNotBlank(destroyParam.getUsername()) && StringUtils.isNotBlank(destroyParam.getPassword())) {
            CredentialValue credentialValue = new CredentialValue(destroyParam.getUsername(), destroyParam.getPassword());
            Credential credential = new Credential(new EnumCredentialType(EnumCredentialType.UsernameAndPassword), credentialValue);
            container.setAuthentication(new Authentication(credential));
        }
        KMIPBatch batch = new KMIPBatch();
        container.addBatch(batch);
        container.calculateBatchCount();

        batch.setOperation(EnumOperation.Destroy);
        for (String uuidKey : uuidKeys) {
            UniqueIdentifier uniqueIdentifier = new UniqueIdentifier();
            uniqueIdentifier.setValue(uuidKey,null);
            batch.addAttribute(uniqueIdentifier);
        }

        KMIPContainer response = stub.processRequest(container);
        log.info("\n-------\n" + response + "\n-----------");
        if (Objects.isNull(response) || CollectionUtil.isEmpty(response.getBatches())) {
            return null;
        }
        List<KMIPBatch> kmipBatches = response.getBatches();
        kmipBatches = kmipBatches.stream().filter(kmipBatch -> ObjectUtil.isNotNull(kmipBatch.getResultStatus()) && kmipBatch.getResultStatus().getValue() == EnumResultStatus.Success).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(kmipBatches)) {
            return null;
        }
        List<String> res = new ArrayList<>();

        for (KMIPBatch kmipParam : kmipBatches) {
            ArrayList<Attribute> attributes = kmipParam.getAttributes();
            if (CollectionUtil.isEmpty(attributes)) {
                break;
            }
            attributes.forEach(attribute -> {
                if (attribute instanceof UniqueIdentifier) {
                    KMIPAttributeValue[] ar = attribute.getValues();
                    if (ArrayUtils.isNotEmpty(ar)) {
                        res.add(ar[0].getValueString());
                    }
                }
            });
        }
        return res;
    }



    /**
     * 对称加密
     *
     * @param secKeyCreateParam
     * @return
     */
    private KMIPContainer createParamToKMIPContainer(SecKeyCreateParam secKeyCreateParam) {
        if (Objects.isNull(secKeyCreateParam)) {
            return null;
        }
        KMIPContainer container = new KMIPContainer();

        if (StringUtils.isNotBlank(secKeyCreateParam.getUsername()) && StringUtils.isNotBlank(secKeyCreateParam.getPassword())) {
            CredentialValue credentialValue = new CredentialValue(secKeyCreateParam.getUsername(), secKeyCreateParam.getPassword());
            Credential credential = new Credential(new EnumCredentialType(EnumCredentialType.UsernameAndPassword), credentialValue);
            container.setAuthentication(new Authentication(credential));
        }

        KMIPBatch batch = new KMIPBatch();
        container.addBatch(batch);
        container.calculateBatchCount();

        // Set Operation and Attribute
        batch.setOperation(EnumOperation.Create);
        batch.addAttribute(new ObjectType(secKeyCreateParam.getObjectType()));

        // Set Template Attribute
        ArrayList<Attribute> templateAttributes = new ArrayList<Attribute>();
        templateAttributes.add(new CryptographicAlgorithm(secKeyCreateParam.getCryptAlgorithm()));
        templateAttributes.add(new CryptographicLength(secKeyCreateParam.getSeckeyLength()));
        templateAttributes.add(new CryptographicUsageMask(secKeyCreateParam.getCryptMask()));
        TemplateAttributeStructure tas = new TemplateAttribute();
        tas.setAttributes(templateAttributes);
        batch.addTemplateAttributeStructure(tas);

        return container;
    }

    /**
     * 非对称加密
     *
     * @param secKeyCreateParam
     * @return
     */
    private KMIPContainer createKeyPairParamToKMIPContainer(SecKeyCreateParam secKeyCreateParam) {
        if (Objects.isNull(secKeyCreateParam)) {
            return null;
        }
        KMIPContainer container = new KMIPContainer();

        if (StringUtils.isNotBlank(secKeyCreateParam.getUsername()) && StringUtils.isNotBlank(secKeyCreateParam.getPassword())) {
            CredentialValue credentialValue = new CredentialValue(secKeyCreateParam.getUsername(), secKeyCreateParam.getPassword());
            Credential credential = new Credential(new EnumCredentialType(EnumCredentialType.UsernameAndPassword), credentialValue);
            container.setAuthentication(new Authentication(credential));
        }

        KMIPBatch batch = new KMIPBatch();
        container.addBatch(batch);
        container.calculateBatchCount();

        // Set Operation and Attribute
        batch.setOperation(EnumOperation.CreateKeyPair);
        batch.addAttribute(new ObjectType(EnumObjectType.PublicKey));
        batch.addAttribute(new ObjectType(EnumObjectType.PrivateKey));

        // Set Template Attribute
        ArrayList<Attribute> commonTemplateAttributes = new ArrayList<>();
        commonTemplateAttributes.add(new CryptographicAlgorithm(secKeyCreateParam.getCryptAlgorithm()));
        commonTemplateAttributes.add(new CryptographicLength(secKeyCreateParam.getSeckeyLength()));
        CommonTemplateAttribute tas = new CommonTemplateAttribute();
        tas.setAttributes(commonTemplateAttributes);
        batch.addTemplateAttributeStructure(tas);

        ArrayList<Attribute> privateKeyTemplateAttributes = new ArrayList<>();
        privateKeyTemplateAttributes.add(new CryptographicUsageMask(secKeyCreateParam.getCryptMask()));
        if (CollectionUtil.isNotEmpty(secKeyCreateParam.getNames())) {
            for (KmipName kmipName : secKeyCreateParam.getNames()) {
                Name name = new Name();
                name.setValue("NameValue", kmipName.getNameValue());
                name.setValue("NameType", kmipName.getNameType());
                privateKeyTemplateAttributes.add(name);
            }
        }
        PrivateKeyTemplateAttribute prs = new PrivateKeyTemplateAttribute();
        prs.setAttributes(privateKeyTemplateAttributes);
        batch.addTemplateAttributeStructure(prs);

        ArrayList<Attribute> publicKeyTemplateAttributes = new ArrayList<>();
        publicKeyTemplateAttributes.add(new CryptographicUsageMask(secKeyCreateParam.getCryptMask()));
        if (CollectionUtil.isNotEmpty(secKeyCreateParam.getNames())) {
            for (KmipName kmipName : secKeyCreateParam.getNames()) {
                Name name = new Name();
                name.setValue("NameValue", kmipName.getNameValue());
                name.setValue("NameType", kmipName.getNameType());
                publicKeyTemplateAttributes.add(name);
            }
        }
        PublicKeyTemplateAttribute pus = new PublicKeyTemplateAttribute();
        pus.setAttributes(publicKeyTemplateAttributes);
        batch.addTemplateAttributeStructure(pus);

        return container;
    }

    /**
     * 模板加密
     *
     * @param secKeyCreateParam
     * @return
     */
    private KMIPContainer createTemplateParamToKMIPContainer(SecKeyCreateParam secKeyCreateParam) {
        if (Objects.isNull(secKeyCreateParam)) {
            return null;
        }
        KMIPContainer container = new KMIPContainer();

        if (StringUtils.isNotBlank(secKeyCreateParam.getUsername()) && StringUtils.isNotBlank(secKeyCreateParam.getPassword())) {
            CredentialValue credentialValue = new CredentialValue(secKeyCreateParam.getUsername(), secKeyCreateParam.getPassword());
            Credential credential = new Credential(new EnumCredentialType(EnumCredentialType.UsernameAndPassword), credentialValue);
            container.setAuthentication(new Authentication(credential));
        }

        KMIPBatch batch = new KMIPBatch();
        container.addBatch(batch);
        container.calculateBatchCount();

        // Set Operation and Attribute
        batch.setOperation(EnumOperation.Create);
        batch.addAttribute(new ObjectType(secKeyCreateParam.getObjectType()));

        // Set Template Attribute
        ArrayList<Attribute> templateAttributes = new ArrayList<Attribute>();
        templateAttributes.add(new CryptographicAlgorithm(secKeyCreateParam.getCryptAlgorithm()));
        templateAttributes.add(new CryptographicLength(secKeyCreateParam.getSeckeyLength()));
        templateAttributes.add(new CryptographicUsageMask(secKeyCreateParam.getCryptMask()));
        TemplateAttributeStructure tas = new TemplateAttribute();
        tas.setAttributes(templateAttributes);
        batch.addTemplateAttributeStructure(tas);

        ArrayList<Attribute> templates = new ArrayList<Attribute>();
        if (CollectionUtil.isNotEmpty(secKeyCreateParam.getNames())) {
            for (KmipName kmipName : secKeyCreateParam.getNames()) {
                Name name = new Name();
                name.setValue("NameValue", kmipName.getNameValue());
                name.setValue("NameType", kmipName.getNameType());
                templates.add(name);
            }
        }
        TemplateAttribute templateAttribute = new TemplateAttribute();
        templateAttribute.setAttributes(templateAttributes);
        batch.addTemplateAttributeStructure(templateAttribute);

        return container;
    }


}
