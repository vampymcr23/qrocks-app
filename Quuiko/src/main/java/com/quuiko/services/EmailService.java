package com.quuiko.services;

import com.quuiko.exception.QRocksException;
import com.quuiko.util.Email;


public interface EmailService {
	public void sendEmail(final Email email) throws QRocksException;
	
	public String getAccountEmail();
}