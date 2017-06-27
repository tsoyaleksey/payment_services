package com.epam.payments.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class is designed to encrypt {@link com.epam.payments.models.User}'s password.
 */
public class PasswordEncoder {
    private static final Logger log = Logger.getLogger(PasswordEncoder.class);

    public static String encode(String password) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] arr = md.digest();
            for (byte b : arr) {
                sb.append(Integer.toHexString(b & 0xff));
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("Password encode error ", e);
        }
        return sb.toString();
    }
}


