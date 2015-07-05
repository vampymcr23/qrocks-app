package com.quuiko.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quuiko.beans.Negocio;
/**
 * Utileria para procesar un jSON o parsear
 * @author victorherrera
 *
 */
public class JSONUtil {
//	private static Gson gson=new Gson();
//	private static Gson gson=new GsonBuilder().setDateFormat(DateFormat.FULL).create();
	private static Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
	public void test(){
//		Negocio negocio=new Negocio();
//		negocio.setFecha(new Date());
//		negocio.setNombre("test");
//		Gson gson=new Gson();
//		String json=gson.toJson(negocio);
		String json="{nombre:'Test',fecha:'2014-06-04T18:19:56'}";
		System.out.println("JSON:"+json);
		Negocio negocio=gson.fromJson(json, Negocio.class);
		System.out.println("Negocio:"+negocio);
	}
	
	/**
	 * Convierte un objeto en jSON
	 * @param bean
	 * @return
	 */
	public static String getJSONfromObject(Object bean){
		String json=gson.toJson(bean);
		return json;
	}
	
	/**
	 * Recupera el objeto mediante un objeto json
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T getObjectFromJSON(String json,Class<T> type){
		
		T o=gson.fromJson(json, type);
		return o;
	}
	
	/**
	 * Recupera el objeto mediante un objeto json
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> List<T> getListObjectFromJSON(String json,Class<T> type){
		List<T> o=(List<T>)gson.fromJson(json, type);
		return o;
	}
	
	public static void main(String... x){
		JSONUtil test=new JSONUtil();
		test.test();
	}
}
