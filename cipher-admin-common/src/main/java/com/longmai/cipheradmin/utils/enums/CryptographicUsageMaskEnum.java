package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CryptographicUsageMaskEnum {
    SIGN(1, "sign"),
    VERIFY(2, "verify"),
    ENCRYPT(4, "encrypt"),
    DECRYPT(8, "decrypt"),
    WRAP_KEY(10, "wrap key"),
    UNWRAP_KEY(20, "unwrap key"),
    EXPORT(40, "export"),
    MAC_GENERATE(80, "mac_generate"),
    MAC_VERIFY(100, "mac_verify"),
    DERIVE_KEY(200, "derive_key"),
    CONTENT_COMMITMENT(400, "content commitment"),
    KEY_AGREEMENT(800, "key agreement"),
    CERTIFICATE_SIGN(1000, "certificate sign"),
    CRL_SIGN(2000, "crl sign"),
    GENERATE_CRYPTOGRAM(4000, "generate cryptogram"),
    VALIDATE_CRYPTOGRAM(8000, "validate cryptogram"),
    TRANSLATE_ENCRYPT(10000, "translate encrypt"),
    TRANSLATE_DECRYPT(20000, "translate decrypt"),
    TRANSLATE_WRAP(40000, "translate wrap"),
    TRANSLATE_UNWRAP(80000, "translate unwrap");


    private final Integer code;
    private final String description;

    public static CryptographicUsageMaskEnum find(Integer code) {
        for (CryptographicUsageMaskEnum value : CryptographicUsageMaskEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
