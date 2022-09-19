package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.param.*;

import java.util.List;

/**
 * Client-to-Server Operations
 *
 * 4.1 Create
 *
 * 4.2 Create Key Pair
 *
 * 4.3 Register
 *
 * 4.4 Re-key
 *
 * 4.5 Re-key Key Pair
 *
 * 4.6 Derive Key
 *
 * 4.7 Certify
 *
 * 4.8 Re-certify
 *
 * 4.9 Locate
 *
 * 4.10 Check
 *
 * 4.11 Get
 *
 * 4.12 Get Attributes
 *
 * 4.13 Get Attribute List
 *
 * 4.14 Add Attribute
 *
 * 4.15 Modify Attribute
 *
 * 4.16 Delete Attribute
 *
 * 4.17 Obtain Lease
 *
 * 4.18 Get Usage Allocation
 *
 * 4.19 Activate
 *
 * 4.20 Revoke
 *
 * 4.21 Destroy
 *
 * 4.22 Archive
 *
 * 4.23 Recover
 *
 * 4.24 Validate
 *
 * 4.25 Query
 *
 * 4.26 Discover Versions
 *
 * 4.27 Cancel
 *
 * 4.28 Poll
 *
 * 4.29 Encrypt
 *
 * 4.30 Decrypt
 *
 * 4.31 Sign
 *
 * 4.32 Signature Verify
 *
 * 4.33 MAC
 *
 * 4.34 MAC Verify
 *
 * 4.35 RNG Retrieve
 *
 * 4.36 RNG Seed
 *
 * 4.37 Hash
 *
 * 4.38 Create Split Key
 *
 * 4.39 Join Split Key
 *
 * 4.40 Export
 *
 * 4.41 Import
 */
public interface KmipService {
    //创建密钥
    List<String> sendCreatRequest(SecKeyCreateParam createParam);
    //销毁
    List<String> sendDestoryRequest(SecKeyDestroyParam destroyParam);
    //归档
    List<String> sendArchiveRequest(SecKeyArchiveParam archiveParam);
    //激活
    List<String> sendActivateRequest(SecKeyActivateParam archiveParam);
    //revoke
    List<String> sendRevokeRequest(SecKeyRevokeParam revokeParam);

    List<String> sendRecoverRequest(SecKeyDestroyParam destroyParam);



//    public String sendDestroyRequest(BsTemplateDto bsTemplateDto);
//
//    public String sendRegisterRequest(BsTemplateDto bsTemplateDto) ;
}
