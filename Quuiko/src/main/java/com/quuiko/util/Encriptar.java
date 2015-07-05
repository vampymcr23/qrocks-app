package com.quuiko.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encriptar {
	public static String encryptionKey="+6BQHP8z6sg8/kwBBVaFLgwkbbXSSfC14MxKwHMf9oQ=";

	public static void main(String args[]) {
	    String encrypt = encrypt("mypassword");
	    String decrypted=decrypt(encryptionKey, encrypt);
	    System.out.println("decrypted value:" + decrypted);
	    String newDec="Qft++9bn+8V/0sSUIq2OLw==";
	    System.out.println("Decrypt value:"+decrypt(encryptionKey, newDec));
	}

	public static String encrypt(String value) {
	    try {
	        // Get the KeyGenerator
	        KeyGenerator kgen = KeyGenerator.getInstance("AES");
	        kgen.init(256);
	        // Generate the secret key specs.
	        SecretKey skey = kgen.generateKey();
	        byte[] raw = skey.getEncoded();
	        String key = new Base64().encodeAsString(raw);
//	        encryptionKey = key;
	        System.out.println("------------------Key------------------");
	        System.out.println(key);
	        System.out.println("--------------End of Key---------------");
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	        String encrypt = (new Base64()).encodeAsString(cipher.doFinal(value.getBytes()));
	        System.out.println("encrypted string:" + encrypt);
	        return encrypt;
	    } catch (NoSuchAlgorithmException ex) {
	        ex.printStackTrace();
	    } catch (IllegalBlockSizeException ex) {
	    	ex.printStackTrace();
	    } catch (BadPaddingException ex) {
	    	ex.printStackTrace();
	    } catch (InvalidKeyException ex) {
	    	ex.printStackTrace();
	    } catch (NoSuchPaddingException ex) {
	    	ex.printStackTrace();
	    }
	    return null;
	}

	public static String decrypt(String key, String encrypted) {
	    try {
	        Key k = new SecretKeySpec(new Base64().decode(key), "AES");
	        Cipher c = Cipher.getInstance("AES");
	        c.init(Cipher.DECRYPT_MODE, k);
	        byte[] decodedValue = new Base64().decode(encrypted);
	        byte[] decValue = c.doFinal(decodedValue);
	        String decryptedValue = new String(decValue);
	        return decryptedValue;
	    } catch (IllegalBlockSizeException ex) {
	    	ex.printStackTrace();
	    } catch (BadPaddingException ex) {
	    	ex.printStackTrace();
	    } catch (InvalidKeyException ex) {
	    	ex.printStackTrace();
	    } catch (NoSuchAlgorithmException ex) {
	    	ex.printStackTrace();
	    } catch (NoSuchPaddingException ex) {
	    	ex.printStackTrace();
	    }
	    return null;
	}
}
