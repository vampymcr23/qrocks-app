package com.quuiko.services;

public interface QRocksKGService {
	/**
	 * From me
	 * @param bN
	 * @return
	 */
	public String gtQRKGFromBusiness(String bN);
	
	/**
	 * To validate
	 * @param bN
	 * @param qrKG
	 * @return
	 */
	public boolean isValidQRKG(String bN,String qrKG);
}
