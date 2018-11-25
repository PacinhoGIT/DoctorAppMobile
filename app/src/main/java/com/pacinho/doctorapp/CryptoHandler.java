package com.pacinho.doctorapp;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by patryk on 2018-11-18.
 */

public class CryptoHandler {

    private static final String CRYPTO_ALGORITHM = "SHA-256";

    public static String hashToSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(CRYPTO_ALGORITHM);
        md.update( input.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        return  String.format( "%064x", new BigInteger( 1, digest ) );
    }
}
