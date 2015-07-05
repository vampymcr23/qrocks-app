package com.quuiko.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class QRocksAESEn {
	private static final byte[] secret=new byte[]{'Q','R','o','c','k','s','K','o','a','l','i','t','a','7','1','2'};
	private static final String MODE="AES";
	public static String hide(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException{
		String enc="";
		Key k=keyGen();
		Cipher cipher=Cipher.getInstance(MODE);
		cipher.init(Cipher.ENCRYPT_MODE,k);
		byte[] rawInfo=data.getBytes("ISO-8859-1");
		byte[] encValue=cipher.doFinal(rawInfo);
		enc=Base64.encodeBase64String(encValue);
		return enc;
	}
	
	public static String hide(String data,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		String enc="";
		Key k=keyGen(key);
		Cipher cipher=Cipher.getInstance(MODE);
		cipher.init(Cipher.ENCRYPT_MODE,k);
		
		byte[] encValue=cipher.doFinal(data.getBytes());
		enc=Base64.encodeBase64String(encValue);
		return enc;
	}
	
	public static String show(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException{
		String dec="";
		Key k=keyGen();
		Cipher cipher=Cipher.getInstance(MODE);
		cipher.init(Cipher.DECRYPT_MODE,k);
		byte[] decodedValue=Base64.decodeBase64(data);
		byte[] decValue=cipher.doFinal(decodedValue);
		dec=new String(decValue,"ISO-8859-1");
		return dec;
	}
	
	public static String show(String data,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		String dec="";
		Key k=keyGen(key);
		Cipher cipher=Cipher.getInstance(MODE);
		cipher.init(Cipher.DECRYPT_MODE,k);
		byte[] decodedValue=Base64.decodeBase64(data);
		byte[] decValue=cipher.doFinal(decodedValue);
		dec=new String(decValue);
		return dec;
	}
	
	private static Key keyGen(){
		Key k=new SecretKeySpec(secret, MODE);
		return k;
	}
	
	private static Key keyGen(String key){
		byte[] mySecret=key.getBytes();
		Key k=new SecretKeySpec(mySecret, MODE);
		return k;
	}
	
	/**
	 * Regresa el serial 
	 * @param clave Clave que se usa para generar el serial
	 * @param llave Llave de encriptacion
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException 
	 */
	public static String getSerialKey(String clave,String llave) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		String serial="";
		if(llave!=null){
			serial=hide(clave,llave);
		}else{
			serial=hide(clave);
		}
		return serial;
	}
	
	/**
	 * Regresa el serial 
	 * @param clave Clave que se usa para generar el serial
	 * @param llave Llave de encriptacion
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException 
	 */
	public static String getSerialKey(String clave) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		String serial="";
		serial=hide(clave);
		return serial;
	}
	
	/**
	 * Valida si el serial es valido dado la clave original 
	 * @param serial
	 * @param claveOriginal
	 * @param llave
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean validSerial(String serial,String claveOriginal,String llave) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		String clave=null;
		if(llave!=null){
			clave=show(serial,llave);
		}else{
			clave=show(serial);
		}
		if(clave==null || clave.isEmpty()){
			return false;
		}
		return clave.equals(claveOriginal);
	}
	
	/**
	 * Valida si el serial es valido dado la clave original 
	 * @param serial
	 * @param claveOriginal
	 * @param llave
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean validSerial(String serial,String claveOriginal) {
		String clave=null;
		try {
			clave=show(serial);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(clave==null || clave.isEmpty()){
			return false;
		}
		return clave.equals(claveOriginal);
	}
	
	
	public static void main(String[] x) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
//		String[] arr=new String[]{"O26UWkomx6S4KsWrh8G/lg==","1287051","1287052"};
//		String key="vampyGinakoalit1";
//		for(String str:arr){
//			String enc=hide(str,key);
//			String dec=show(enc,key);
//			System.out.println("Cadena:"+str+"\nEnc:"+enc+"\nDesenc:"+dec);
//			System.out.println("===========================");
//		}
		String clave="zitla_012013";
		String serial=getSerialKey(clave);
		System.out.println("Clave:"+clave+"\tSerial:"+serial);
		String serialInput="hpcrOwd8XBDqeSj+lpQUiA==";
		boolean esValido=validSerial(serialInput, clave);
		System.out.println("El serial:"+serialInput+" es valido?"+esValido);
		
	}
}
