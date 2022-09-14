package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 加密算法枚举
 */
@Getter
@AllArgsConstructor
public enum CryptographicAlgorithmEnum {

    DES(1, "DES"),
    DESEDE(2, "DESede"),
    AES(3, "AES"),
    RSA(4, "RSA"),
    DSA(5, "DSA"),
    ECDSA(6, "ECDSA"),
    HMAC_SHA1(7, "HMAC_SHA1"),
    HMAC_SHA224(8, "HMAC_SHA224"),
    HMAC_SHA256(9, "HMAC_SHA256"),
    HMAC_SHA384(10, "HMAC_SHA384"),
    HMAC_SHA512(11, "HMAC_SHA512"),
    HMAC_MD5(12, "HMAC_MD5"),
    DH(13, "DH"),
    ECDH(14, "ECDH"),
    ECMQV(15, "ECMQV"),
    Blowfish(16, "Blowfish"),
    Camellia(17, "Camellia"),
    IDEA(18, "IDEA"),
    MARS(19, "MARS"),
    RC2(20, "RC2"),
    RC4(21, "RC4"),
    RC5(22, "RC5"),
    SKIPJACK(23, "SKIPJACK"),
    Twofish(24, "Twofish"),
    PGP(25, "PGP");


    private final Integer code;
    private final String description;

    public static CryptographicAlgorithmEnum find(Integer code) {
        for (CryptographicAlgorithmEnum value : CryptographicAlgorithmEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
