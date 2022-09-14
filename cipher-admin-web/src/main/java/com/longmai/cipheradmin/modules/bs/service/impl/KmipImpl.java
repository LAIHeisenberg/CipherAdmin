package com.longmai.cipheradmin.modules.bs.service.impl;

import ch.ntb.inf.kmip.attributes.*;
import ch.ntb.inf.kmip.container.KMIPBatch;
import ch.ntb.inf.kmip.container.KMIPContainer;
import ch.ntb.inf.kmip.kmipenum.*;
import ch.ntb.inf.kmip.objects.Authentication;
import ch.ntb.inf.kmip.objects.CredentialValue;
import ch.ntb.inf.kmip.objects.base.Attribute;
import ch.ntb.inf.kmip.objects.base.Credential;
import ch.ntb.inf.kmip.objects.base.TemplateAttribute;
import ch.ntb.inf.kmip.objects.base.TemplateAttributeStructure;
import ch.ntb.inf.kmip.stub.KMIPStubInterface;
import ch.ntb.inf.kmip.types.*;
import cn.hutool.core.collection.CollectionUtil;
import com.longmai.cipheradmin.modules.bs.service.Kmip;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class KmipImpl implements Kmip {

    @Autowired
    private KMIPStubInterface stub;

    /**
     * 创建秘钥
     * @return
     */
    @Override
    public String sendCreatRequest(BsTemplateDto bsTemplateDto) {
        AtomicReference<String> identifier = new AtomicReference<>("");
        KMIPContainer request = createKMIPRequest(bsTemplateDto);
        KMIPContainer response = stub.processRequest(request);
        if(response!=null && CollectionUtil.isNotEmpty(response.getBatches())){
            ArrayList<KMIPBatch> kmipBatches = response.getBatches();
            KMIPBatch result = kmipBatches.get(0);
            if(result!=null){
                ArrayList<Attribute> attributes = result.getAttributes();
                attributes.forEach(attribute->{
                    if("Unique Identifier".equals(attribute.getAttributeName())){
                        KMIPAttributeValue[] ar = attribute.getValues();
                        identifier.set(ar[0].getValueString());
                    }
                });
            }
        }
        return identifier.get();
    }


    @Override
    public String sendDestroyRequest(BsTemplateDto bsTemplateDto) {
        KMIPContainer request = destoryKMIPRequest(bsTemplateDto);
        KMIPContainer response = stub.processRequest(request);
        return response.toString();
    }

    @Override
    public String sendRegisterRequest(BsTemplateDto bsTemplateDto) {
        KMIPContainer request = registerKMIPRequest(bsTemplateDto);
        KMIPContainer response = stub.processRequest(request);
        return response.toString();
    }


    private static KMIPContainer registerKMIPRequest(BsTemplateDto bsTemplateDto) {
        if(Objects.isNull(bsTemplateDto)){
            return null;
        }
        KMIPContainer container = new KMIPContainer();

        if(bsTemplateDto.getUserId() != null && bsTemplateDto.getPwd() != null){
            CredentialValue credentialValue = new CredentialValue(bsTemplateDto.getUserId(), bsTemplateDto.getPwd());
            Credential credential = new Credential(new EnumCredentialType(EnumCredentialType.UsernameAndPassword), credentialValue);
            container.setAuthentication(new Authentication(credential));
        }

        KMIPBatch batch = new KMIPBatch();
        container.addBatch(batch);
        container.calculateBatchCount();

        // Set Operation and Attribute
        batch.setOperation(EnumOperation.Register);
        batch.addAttribute(new ObjectType(EnumObjectType.SymmetricKey));

        // Set Template Attribute
        ArrayList<Attribute> templateAttributes = new ArrayList<Attribute>();
//        EnumCryptographicAlgorithm.AES
        templateAttributes.add(new CryptographicAlgorithm(Integer.valueOf(bsTemplateDto.getCryptographicAlgorithm())));
        templateAttributes.add(new CryptographicLength(Integer.valueOf(bsTemplateDto.getCryptographicLength())));
        templateAttributes.add(new CryptographicUsageMask(Integer.valueOf(bsTemplateDto.getCryptographicUsageMask())));
        TemplateAttributeStructure tas = new TemplateAttribute();
        tas.setAttributes(templateAttributes);
        batch.addTemplateAttributeStructure(tas);

        return container;
    }


    private static KMIPContainer destoryKMIPRequest(BsTemplateDto bsTemplateDto) {
        if(Objects.isNull(bsTemplateDto)){
            return null;
        }
        KMIPContainer container = new KMIPContainer();

        if(bsTemplateDto.getUserId() != null && bsTemplateDto.getPwd() != null){
            CredentialValue credentialValue = new CredentialValue(bsTemplateDto.getUserId(), bsTemplateDto.getPwd());
            Credential credential = new Credential(new EnumCredentialType(EnumCredentialType.UsernameAndPassword), credentialValue);
            container.setAuthentication(new Authentication(credential));
        }

        KMIPBatch batch = new KMIPBatch();
        container.addBatch(batch);
        container.calculateBatchCount();

        // Set Operation and Attribute
        batch.setOperation(EnumOperation.Destroy);
        KMIPType kmipType = new KMIPByteString(bsTemplateDto.getUuidKey());
        batch.addAttribute(new UniqueIdentifier(kmipType));

        // Set Template Attribute
        ArrayList<Attribute> templateAttributes = new ArrayList<Attribute>();
//        EnumCryptographicAlgorithm.AES
        templateAttributes.add(new CryptographicAlgorithm(Integer.valueOf(bsTemplateDto.getCryptographicAlgorithm())));
        templateAttributes.add(new CryptographicLength(Integer.valueOf(bsTemplateDto.getCryptographicLength())));
        templateAttributes.add(new CryptographicUsageMask(Integer.valueOf(bsTemplateDto.getCryptographicUsageMask())));
        TemplateAttributeStructure tas = new TemplateAttribute();
        tas.setAttributes(templateAttributes);
        batch.addTemplateAttributeStructure(tas);

        return container;
    }


    private static KMIPContainer createKMIPRequest(BsTemplateDto bsTemplateDto) {
        if(Objects.isNull(bsTemplateDto)){
            return null;
        }
        KMIPContainer container = new KMIPContainer();

        if(bsTemplateDto.getUserId() != null && bsTemplateDto.getPwd() != null){
            CredentialValue credentialValue = new CredentialValue(bsTemplateDto.getUserId(), bsTemplateDto.getPwd());
            Credential credential = new Credential(new EnumCredentialType(EnumCredentialType.UsernameAndPassword), credentialValue);
            container.setAuthentication(new Authentication(credential));
        }

        KMIPBatch batch = new KMIPBatch();
        container.addBatch(batch);
        container.calculateBatchCount();

        // Set Operation and Attribute
        batch.setOperation(EnumOperation.Create);
        batch.addAttribute(new ObjectType(EnumObjectType.SymmetricKey));

        // Set Template Attribute
        ArrayList<Attribute> templateAttributes = new ArrayList<Attribute>();
//        EnumCryptographicAlgorithm.AES
        templateAttributes.add(new CryptographicAlgorithm(Integer.valueOf(bsTemplateDto.getCryptographicAlgorithm())));
        templateAttributes.add(new CryptographicLength(Integer.valueOf(bsTemplateDto.getCryptographicLength())));
        templateAttributes.add(new CryptographicUsageMask(Integer.valueOf(bsTemplateDto.getCryptographicUsageMask())));
        TemplateAttributeStructure tas = new TemplateAttribute();
        tas.setAttributes(templateAttributes);
        batch.addTemplateAttributeStructure(tas);

        return container;
    }



}
