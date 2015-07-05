package com.quuiko.services;

import java.util.List;

import com.quuiko.dtos.NotificationMessage;
import com.quuiko.exception.QRocksException;

public interface GCMNotificationService {
	/**
	 * Envia notificaciones a varios dispositivos android
	 * @param message
	 * @throws QRocksException
	 */
	public void sendNotifications(List<String> gcmIds,NotificationMessage message) throws QRocksException;
	
	/**
	 * Envia notificaciones a varios dispositivos iOS
	 * @param gcmIds
	 * @param message
	 * @throws QRocksException
	 */
	public void sendNotificationsIOS(List<String> gcmIds,NotificationMessage message) throws QRocksException;
	
	public int getNumNotificacionesPorBloque();
	
}
