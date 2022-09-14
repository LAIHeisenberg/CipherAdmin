package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 加密操作枚举
 */
@Getter
@AllArgsConstructor
public enum CryptographicOperationEnum {
    Create(1,"Create"),
    CreateKeyPair(2,"CreateKeyPair"),
    Register(3,"Register"),
    ReKey(4,"ReKey"),
    DeriveKey(5,"DeriveKey"),
    Certify(6,"Certify"),
    ReCertify(7,"ReCertify"),
    Locate(8,"Locate"),
    Check(9,"Check"),
    Get(10,"Get"),
    GetAttributes(11,"GetAttributes"),
    GetAttributeList(12,"GetAttributeList"),
    AddAttribute(13,"AddAttribute"),
    ModifyAttribute(14,"ModifyAttribute"),
    DeleteAttribute(15,"DeleteAttribute"),
    ObtainLease(16,"ObtainLease"),
    GetUsageAllocation(17,"GetUsageAllocation"),
    Activate(18,"Activate"),
    Revoke(19,"Revoke"),
    Destroy(20,"Destroy"),
    Archive(21,"Archive"),
    Recover(22,"Recover"),
    Validate(23,"Validate"),
    Query(24,"Query"),
    Cancel(25,"Cancel"),
    Poll(26,"Poll"),
    Notify(27,"Notify"),
    Put(28,"Put");

    private final Integer code;
    private final String description;

    public static CryptographicOperationEnum find(Integer code) {
        for (CryptographicOperationEnum value : CryptographicOperationEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
