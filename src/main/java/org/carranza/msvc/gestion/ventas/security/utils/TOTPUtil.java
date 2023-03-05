package org.carranza.msvc.gestion.ventas.security.utils;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import de.taimos.totp.TOTP;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TOTPUtil {

    public String generateCode() {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(Constants.TOTP_KEY);
        String hexKey = Hex.encodeHexString(bytes);
        String code = TOTP.getOTP(hexKey);
        return code;
    }

    public Boolean verifyCode(String code) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(Constants.TOTP_KEY);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.validate(hexKey, code);
    }
}