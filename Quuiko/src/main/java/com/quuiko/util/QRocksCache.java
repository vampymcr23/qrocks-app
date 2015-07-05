package com.quuiko.util;
/**
 * Clase para el manejo del tiempo del cache de una entidad
 * @author victorherrera
 *
 */
public class QRocksCache {
	public static final int ALWAYS=0;
	public static final int MIDDLE_MINUTE=((1000*30));
	public static final int ONE_MINUTE=((1000*60));
	public static final int QUARTER_HOUR=(15*(1000*60));
	public static final int MIDDLE_HOUR=(30*((1000*60)));
	public static final int PER_HOUR=(60*(1000*60));
	public static final int PER_DAY=( 24*60*(1000*60));
}
