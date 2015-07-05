package com.quuiko.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {
	
	
	public static String encode(String password){
		String encodedPassword=null;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}
	
	public static boolean matches(String rawPassword, String encodedPassword){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	public static void main(String[] x){
		String inputPassword="gyv712";
		String encPassword="$2a$10$wxlJ4X/8EdfjwJui7Kba9OaW4RmShMi5yDS.Ay9CeQvvjSQw0SKWi";
//		String crypt=encode(inputPassword);
//		System.out.println("Enc:"+crypt);
		boolean isCorrect=matches(inputPassword,encPassword);
		System.out.println("Pwd:"+inputPassword+"| Match:"+isCorrect);
	}
}
