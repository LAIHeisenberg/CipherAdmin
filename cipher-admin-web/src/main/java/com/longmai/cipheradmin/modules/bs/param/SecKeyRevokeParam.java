package com.longmai.cipheradmin.modules.bs.param;

import ch.ntb.inf.kmip.kmipenum.EnumRevocationReasonCode;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SecKeyRevokeParam {
    private List<String> uuidKeys;
    /**
     * @see EnumRevocationReasonCode
     */
    private String revocationReasonCode;
    private String compromiseOccurrenceDate;
    private String username;
    private String password;
}
