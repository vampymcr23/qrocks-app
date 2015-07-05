package com.quuiko.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.quuiko.dtos.JPlayerSong;
import com.quuiko.dtos.QRocksCancion;
import com.quuiko.exception.QRocksException;

public class Utileria {
	/**
	 * Enum para el manejo de expresion de la programacion, este enum es para la periodicidad
	 * @author victorherrera
	 *
	 */
	public enum TipoPeriodicidad{
		S("SEMANAL"),D("DIARIO"),W("WEEKLY"),Q("QUINCENAL"),FN("FORTNIGHT"),M("MENSUAL"),X("X");
		public String value;
		private TipoPeriodicidad(String value){
			this.value=value;
		}
	}
	
	public enum CondicionLogica{AND,OR}
	/**
	 * indica si un objeto es nulo
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o){
		return o==null;
	}
	/**
	 * Indica si el objeto no es nulo
	 * @param o
	 * @return
	 */
	public static boolean isNotNull(Object o){
		return !isNull(o);
	}
	/**
	 * Valida si una cadena no esta vacia o nula
	 * @param str
	 * @return
	 */
	public static boolean isValid(String str){
		return (str!=null && !str.trim().isEmpty());
	}
	/**
	 * indica si una coleccion es nula o vacia
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyCollection(Collection collection){
		return (collection==null || collection.isEmpty());
	}
	
	/**
	 * Indica si el mapa es nulo o vacio
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyMap(Map map){
		return (map==null || map.isEmpty());
	}
	/**
	 * Valida si un arreglo esta vacio
	 * @param arr
	 * @return
	 */
	public static boolean isEmptyArray(Object[] arr){
		return (arr==null || arr.length==0);
	}
	
	/**
	 * Envia una excepcion, donde el mensaje debe de venir de la lista de mensajes "Mensajes_es.properties" o "Mensajes_en.properties"
	 * @param mensaje	cadena que debe ser una llave de "Mensajes_*.properties"
	 * @throws QRocksException Se lanza si no se encuentra el mensaje en el properties.
	 */
	public static void onError(String mensaje)throws QRocksException{
		throw new QRocksException(mensaje);
	}
	/**
	 * Envia una excepcion, donde el mensaje debe de venir de la lista de mensajes "Mensajes_es.properties" o "Mensajes_en.properties"
	 * @param mensaje	cadena que debe ser una llave de "Mensajes_*.properties"
	 * @param parametros Variables a reemplazar de este mensaje
	 * @throws QRocksException
	 */
	public static void onError(String mensaje,String... parametros) throws QRocksException{
		throw new QRocksException(mensaje,parametros);
	}
	
	/**
	 * Valida si un objeto es numerico o no.
	 * @param obj
	 * @return
	 */
	public static boolean isNumber(Object obj){
		boolean isNumber=true;
		if(isNull(obj)){
			isNumber=false;
		}
		try{
			isNumber=NumberUtils.isNumber(obj.toString());
		}catch(Exception e){
			isNumber=false;
		}
		return isNumber;
	}
	/**
	 * Obtiene el mensaje del properties
	 * @param llave Llave o key del Mensajes properties del cual queremos recuperar el texto
	 * @param locale localidad del mensaje, espaniol o ingles, si es nulo, por default sera en espaniol.
	 * @param parametros Parametros para el mensaje 
	 * @return
	 */
	public static String obtenerMensajeProperties(String llave,Locale locale,String... parametros){
		String mensaje=null;
		locale=(isNull(locale))?new Locale("ES"):locale;
//		mensaje=ManejadorRecursosFactory.getManejadorRecursos().obtenerMensaje(llave, locale, parametros);
		mensaje=Labels.getLabel(llave,parametros);
		return mensaje;
	}
	
	/**
	 * Obtiene el mensaje de properties dependiendo del locale que este en la sesion
	 * @param llave
	 * @param parametros
	 * @return
	 */
	public static String obtenerMensajePropertiesLocaleDeSesion(String llave,String... parametros){
//		Locale locale=obtenerLocaleSesion();
		String mensaje=Labels.getLabel(llave,parametros);
		return mensaje;
	}
	
	/**
	 * Ayuda a armar un query para jpql, si tiene ya un where no lo agrega, y va agregando la condicion y un and u or al final de cada condicion
	 * @param str Stringbuilder que ya esta inicialido y tiene la sentencia de select entidades antes del where 
	 * @param condicion 
	 * @param condicionLogica
	 */
	public static void agregarCondicion(StringBuilder str,String condicion,CondicionLogica condicionLogica){
		if(isNotNull(str) && isValid(condicion)){
			if(str.indexOf("where")==-1){
				str.append(" where ");
			}
			str.append(condicion);
			switch(condicionLogica){
				case AND:
					str.append(" and ");	
				break;
				case OR:
					str.append(" or ");
				break;
			}
		}
	}
	/**
	 * Ayuda a obtener el query del str sin los And u Or al final
	 * @param str
	 * @return
	 */
	public static String obtenerQuery(StringBuilder str){
		String query=null;
		if(isNotNull(str)){
			query=str.toString();
			int posicionAnd=str.lastIndexOf("and");
			int posicionOr=str.lastIndexOf("or");
			int tamanioQuery=str.toString().trim().length();
			if((tamanioQuery-posicionAnd)<=("and".length())){
				query=str.replace(posicionAnd,tamanioQuery, "").toString();
			}
			if((tamanioQuery-posicionOr)<=("or".length())){
				query=str.replace(posicionOr,tamanioQuery, "").toString();
			}
		}
		return query;
	}
	/**
	 * Obtiene la extension del archivo
	 * @param nombreArchivo
	 * @return
	 */
	public static String obtenerExtensionArchivo(String nombreArchivo){
		String extension=null;
		if(isValid(nombreArchivo)){
			String[] partes=nombreArchivo.split("[.]");
			if(partes!=null && partes.length>1){
				extension=partes[partes.length-1];
			}
		}
		return extension;
	}
	
	/**
	 * Convierte un objeto a long, si es invalido te regresa nulo.
	 * @param o
	 * @return
	 */
	public static Long castToLong(Object o){
		Long value=null;
		if(isNotNull(o)){
			try{
				value=castToBigDecimal(o).longValue();
			}catch(Exception e){
				return null;
			}
		}
		return value;
	}
	
	/**
	 * Convierte un objeto a entero, si hay algun error, el objeto es nulo.
	 * @param o
	 * @return
	 */
	public static Integer castToInteger(Object o){
		Integer value=null;
		try{
			value=castToBigDecimal(o).intValue();
		}catch(Exception e){
			return null;
		}
		return value;
	}
	/**
	 * Convierte un objeto a decimal
	 * @param o
	 * @return
	 */
	public static Double castToDouble(Object o){
		try{
			return castToBigDecimal(o).doubleValue();
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * Convierte un objeto a bigDecimal
	 * @param o
	 * @return
	 */
	public static BigDecimal castToBigDecimal(Object o){
		try{
			return new BigDecimal(o.toString());
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * permite guardar un valor en la sesion
	 * @param name Nombre que representa el valor a guardar en sesion
	 * @param value Valor a almacenar en la sesion
	 */
	public static void asignarValorSesion(String name,Object value){
//		Session sesion=Sessions.getCurrent();
//		if(isNotNull(sesion)){
//			sesion.setAttribute(name, value);
//		}
	}
	/**
	 * Obtiene el valor en sesion de un parametro almacenado en la misma
	 * @param name Nombre que contiene el valor que ocupamos recuperar de la sesion
	 * @return
	 */
	public static Object obtenerValorSesion(String name){
		Object value=null;
//		Session sesion=Sessions.getCurrent();
//		if(isNotNull(sesion)){
//			value=sesion.getAttribute(name);
//		}
		return value;
	}
	/**
	 * Obtiene el valor de una peticion
	 * @param name
	 * @return
	 */
	public static Object obtenerValorRequest(String name){
//		return Executions.getCurrent().getParameter(name);
		return null;
	}
	
	/**
	 * Compara 2 fechas, trunca las horas y minutos y despues compara solamente fechas
	 * @param dia1 Fecha que nos importa comparar si es mayor, menor o igual al dia2
	 * @param dia2 Fecha que nos importa comparar
	 * @param truncarTiempo Boolean, si es verdadero, se ignora el tiempo, de lo contrario se considera
	 * @return diferencia Regresa 1, si dia1 es mayor que dia2, -1 si dia1 es menor que dia2, 0 si son iguales
	 */
	public static int compararFechas(Date dia1, Date dia2,boolean truncarTiempo){
		//Se define el formato de las fechas, aqui cuando se ejecute el metodo de format se le quita las horas,minutos y segundos.
		DateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
		//Si no se va a ignorar el tiempo, entonces se compara con todo y tiempo, por naturaleza, asi lo hace llava con el metood compareTo
		if(!truncarTiempo){
			return dia1.compareTo(dia2);
		}
		//Aqui se trunca el tiempo PERO te regresa un String
		String f1=formato.format(dia1);
		String f2=formato.format(dia2);
		try {
			//Volvemos a obtener el objeto DATE con el metodo parse YA QUE SI NO ES DATE no jalaria la comparacion de "compareTo"
			dia1=formato.parse(f1);
			dia2=formato.parse(f2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Ahora si, ya que tenemos los 2 objetos Date sin horas entonces ya podemos comparar las fechas a las 00 horas, 00 minutos y 00 segundos.
		return dia1.compareTo(dia2);
	}
	
	/**
	 * Metodo que permite validar la expression que el usuario proporciona para saber si se puede convertir en una expresion de quartz
	 * @param expresionValidar
	 * @return
	 */
	public static boolean validarExpresionProgramacion(String expresionValidar){
		String patronNumerico="(0?[1-9]|\\d{1,3})-";
		String patronFecha="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)";
		String patronHoy="(HOY|NOW)";
//		String patronPeriodicidad="(M|D|S|Q|W|FN)";
		//FIXME Remover en PROD este, ya que no debe de llevar X
		String patronPeriodicidad="(M|D|S|Q|W|FN|X)";
//		String patronTiempoHoraMinutos="(-((0?[0-9]|1[0-9]|2[0-3]):(00|15|30|45)))?";
		//FIXME Remover en PROD este, ya que no debe de permitir cualquier minuto, solo de 00-15-30-45
		String patronTiempoHoraMinutos="(-(([01]?[0-9]|2[0-3]):[0-5][0-9]))?";
		String patronValidar=patronNumerico+"[(]("+patronHoy+"|"+patronFecha+")[)]-"+patronPeriodicidad+patronTiempoHoraMinutos;
		Pattern patron=Pattern.compile(patronValidar);
		Matcher match=patron.matcher(expresionValidar);
		boolean esValido=match.matches();
		return esValido;
	}
	
	
	
	/**
	 * Obtiene la fecha del ultimo dia del mes de la fecha proporcionada
	 * @param d
	 * @return
	 */
	public static Date getLastDayOfMonth(Date d){
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}
	
	/**
	 * Obtiene la fecha del ultimo dia del mes de la fecha proporcionada
	 * @param d
	 * @return
	 */
	public static Calendar getCalendarOfLastDayOfMonth(Date d){
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.DATE, -1);
		return c;
	}
	/**
	 * Metodo que obtiene la expresion de quartz en base a los parametros enviados de un archivo.
	 * @param numPagos numero de pagos
	 * @param fecha Fecha de inicio
	 * @param periodicidad Periodicidad de la expresion puede ser D|S|Q|FN|M
	 * @param hora Hora en la que se desea prograar, esta puede ser opcional (vacia o nula) o con el formato de hh:mm
	 * @return Arreglo de expresiones de quartz
	 * @throws QRocksException
	 */
	public static String[] parseToQuartz(String numPagos,String fecha,String periodicidad,String hora) throws QRocksException{
		String[] expresionesQuartz=new String[2];
		if(!isValid(numPagos)||!isValid(fecha) || !isValid(periodicidad)){
			onError("err.validador.validadorExpresionProgramacion.mensajeError");
		}
		periodicidad=periodicidad.toUpperCase();
		String expresionProgramacion=numPagos+"-("+fecha+")-"+periodicidad;
		if(isValid(hora)){
			expresionProgramacion+="-"+hora;
		}
		boolean esValida=validarExpresionProgramacion(expresionProgramacion);
		if(!esValida){
			onError("err.validador.validadorExpresionProgramacion.mensajeError");
		}
		int horaDefault=3;//8 am para realizar las transacciones
		int minutoDefault=0;
		if(isValid(hora)){
			String[] tiempo=hora.split("[:]");
			if(!isEmptyArray(tiempo)){
				horaDefault=Integer.parseInt(tiempo[0]);
				minutoDefault=Integer.parseInt(tiempo[1]);
			}
		}
		TipoPeriodicidad tipoPeriodicidad=TipoPeriodicidad.valueOf(periodicidad.toUpperCase());
		DateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio=null;
		Integer diaSemana=null;
		Integer diaMes=null;
		Integer diaUltimoMes=null;
		Calendar calendario=null;
		try {
			fechaInicio=formato.parse(fecha);
			calendario=Calendar.getInstance();
			calendario.setTime(fechaInicio);
			diaSemana=calendario.get(Calendar.DAY_OF_WEEK);
			diaMes=calendario.get(Calendar.DAY_OF_MONTH);
			diaUltimoMes=getCalendarOfLastDayOfMonth(fechaInicio).get(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		switch(tipoPeriodicidad){
			case D:
				expresionesQuartz=new String[]{"0 "+minutoDefault+" "+horaDefault+" * * ?"};
			break;
			case S:
			case W:
				expresionesQuartz=new String[]{"0 "+minutoDefault+" "+horaDefault+" ? * "+diaSemana.intValue()};
			break;
			case Q:
			case FN:
				expresionesQuartz=new String[]{"0 "+minutoDefault+" "+horaDefault+" 15 * ?","0 "+minutoDefault+" "+horaDefault+" L * ?"};
			break;
			case M:
				if(diaMes==30){
					expresionesQuartz=new String[]{"0 "+minutoDefault+" "+horaDefault+" 30 * ?","0 "+minutoDefault+" "+horaDefault+" L 2 ?"};
				}else if(diaMes== diaUltimoMes){
					expresionesQuartz=new String[]{"0 "+minutoDefault+" "+horaDefault+" L * ?"};
				}else{
					expresionesQuartz=new String[]{"0 "+minutoDefault+" "+horaDefault+" "+diaMes+" * ?"};
				}
			break;
			default:
				//FIXME Remover el caso default ya que es para pruebas
				//El default hara que se ejecute acada minuto y diario para efectos de pruebas
				expresionesQuartz=new String[]{"0 "+minutoDefault+" "+horaDefault+" "+calendario.get(Calendar.DAY_OF_MONTH)+" "+(calendario.get(Calendar.MONTH)+1)+" ? "+calendario.get(Calendar.YEAR)};
			break;
		}
		return expresionesQuartz;
	}
	/**
	 * Permite convertir una fecha de String a Date
	 * @param fecha
	 * @return
	 */
	public static Date castToDate(String fecha){
		if(!isValid(fecha)){
			return null;
		}
		String formato="dd/MM/yyyy";
		return castToDate(fecha, formato);
	}
	/**
	 * Convierte de una fecha de String a Date
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static Date castToDate(String fecha,String formato){
		if(!isValid(fecha)){
			return null;
		}
		DateFormat format=new SimpleDateFormat(formato);
		try {
			return format.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Convierte una fecha al formato dd/MM/yyyy HH:mm:ss
	 * @param date Fecha a convertir
	 * @return
	 */
	public static String parseDateToString(Date date){
		String fecha=null;
		if(date!=null){
			DateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			fecha=format.format(date);
		}
		return fecha;
	}
	
	/**
	 * Convierte una fecha al formato dd/MM/yyyy HH:mm:ss
	 * @param date Fecha a convertir
	 * @param formato Formato en el que se convertira la fecha
	 * @return
	 */
	public static String parseDateToString(Date date,String formato){
		String fecha=null;
		if(date!=null){
			DateFormat format=new SimpleDateFormat(formato);
			fecha=format.format(date);
		}
		return fecha;
	}
	/**
	 * Obtiene la hora de una fecha
	 * @param fecha
	 * @return
	 */
	public static String obtenerHoraDeFecha(Date fecha){
		if(isNull(fecha)){
			return null;
		}
		String formato="HH:mm";
		DateFormat format=new SimpleDateFormat(formato);
		String hora=format.format(fecha);
		return hora;
	}
	
	/**
	 * Obtiene solo la fecha del sistema
	 * @param fecha
	 * @return
	 */
	public static String obtenerSoloFecha(Date fecha){
		if(isNull(fecha)){
			return null;
		}
		String formato="dd/MM/yyyy";
		DateFormat format=new SimpleDateFormat(formato);
		String soloFecha=format.format(fecha);
		return soloFecha;
	}
	
	/**
	 * Obtiene el Locale de la sesion
	 * @return
	 */
	public static Locale obtenerLocaleSesion(){
//		return (Locale)Sessions.getCurrent().getAttribute(Attributes.PREFERRED_LOCALE);
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map<String,Object>> arrayBeanToMap(List array){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(array!=null && !array.isEmpty()){
			Object b=array.get(0);
			Class beanClass=b.getClass();
			Field[] campos=beanClass.getDeclaredFields();
			Set<String> properties=new LinkedHashSet<String>();
			for(Field campo:campos){
				properties.add(campo.getName());
			}
			for(Object bean:array){
				Map<String,Object> mapa=new HashMap<String, Object>(); 
				for(String property:properties){
					PropertyDescriptor descriptor;
					try {
						descriptor = new PropertyDescriptor(property, beanClass);
						Method metodoGet=descriptor.getReadMethod();
						Object value=metodoGet.invoke(bean,new Object[]{});
						mapa.put(property, value);
					} catch (IntrospectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				list.add(mapa);
			}
		}
		return list;
	}
	/**
	 * Asigna una hora a una fecha dada y obtiene un objeto Date
	 * @param fecha
	 * @param hora
	 * @return
	 */
	public static Date asignarHora(Date fecha,String hora){
		String formato="dd/MM/yyyy HH:mm";
		DateFormat format=new SimpleDateFormat(formato);
		String fechaStr=obtenerSoloFecha(fecha);
		fechaStr+=" "+hora;
		Date fechaCorrecta=null;
		try {
			fechaCorrecta = format.parse(fechaStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fechaCorrecta;
	}

	
	/**
	 * Metodo que convierte una lista de beans, en una lista de mapas especificando unicamente las propiedades a leer del bean
	 * @param array
	 * @param beanProperties
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map<String,Object>> arrayBeanToMap(List array,String[] beanProperties){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(array!=null && !array.isEmpty()){
			Object b=array.get(0);
			Class beanClass=b.getClass();
			for(Object bean:array){
				Map<String,Object> mapa=new LinkedHashMap<String, Object>(); 
				for(String property:beanProperties){
					PropertyDescriptor descriptor;
					try {
						descriptor = new PropertyDescriptor(property, beanClass);
						Method metodoGet=descriptor.getReadMethod();
						Object value=metodoGet.invoke(bean,new Object[]{});
						String v=(value!=null)?value.toString():"";
						mapa.put(property, v);
					} catch (IntrospectionException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				list.add(mapa);
			}
		}
		return list;
	}
	
	/**
	 * Metodo para liberar memoria en la ejecucion de una tarea mediante Quartz
	 */
	public static void liberarMemoria(){	
		long memoriaTotal=Runtime.getRuntime().totalMemory();
		long memoriaLibre=Runtime.getRuntime().freeMemory();
		double t=memoriaTotal;
		double l=memoriaLibre;
		double porcentajeMemoriaLibre=(l/t)*(100d);
		//Si se esta ocupando mas del 75 de memoria, se corre el GC
		if(porcentajeMemoriaLibre<=25){
			System.out.println("=================================");
			System.out.println("Memoria total :"+memoriaTotal);
			System.out.println("Memoria libre :"+memoriaLibre);
			System.out.println("Porcentaje Uso:"+porcentajeMemoriaLibre);
			System.out.println("Liberando memoria...");
			System.out.println("=================================");
			System.gc();
		}
	}
	/**
	 * Metodo que obtiene solamente el nombre de un archivo sin la extension
	 * @param archivo
	 * @return
	 */
	public static String obtenerNombreArchivo(File archivo){
		String nombre=null;
		if(archivo!=null){
			String nombreCompleto=archivo.getName();
			String[] partes=nombreCompleto.split("[.]");
			nombre="";
			if(partes!=null && partes.length>1){
				for(int i=0;i<(partes.length-1);i++){
					if(i<(partes.length-2)){
						nombre+=partes[i]+".";
					}else{
						nombre+=partes[i];
					}
				}
			}else if(partes!=null && partes.length==1){
				nombre=partes[0];
			}
		}
		return nombre;
	}
	
	public static JPlayerSong parseSong(QRocksCancion cancion) throws QRocksException{
		if(isNull(cancion)){
			//FIXME Mensaje
			onError("");
		}
		JPlayerSong song=new JPlayerSong();
		File f=cancion.getArchivo();
		//FIXME TERMINARLO
		return song;
	}
	
	public static String formatoDecimal(double valor){
		DecimalFormat df = new DecimalFormat("0.00##");
		String result = df.format(valor);
		return result;
	}
	/**
	 * Convierte un numero de tipo String a un numero redondeado a 2 decimales 
	 * @param value
	 * @return
	 */
	public static double redondear(String value){
		Double decimal=new Double(value);
		return redondear(decimal,2);
	}
	
	/**
	 * Convierte un numero decimal a un  numero decimal redondeado
	 * @param value
	 * @param places
	 * @return
	 */
	public static double redondear(double value, int places) {
		 if (places < 0){
			 throw new IllegalArgumentException();
		 }

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	private static final Double  DEFAULT_WIDTH=250d;
	public static byte[] getResizeImage(File originalImageFile){
		byte[] data=null;
		BufferedImage originalImage=null;
		BufferedImage resizedImage=null;
		try {
			originalImage = ImageIO.read(originalImageFile);
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			int originalHeight=originalImage.getHeight();
			int originalWidth=originalImage.getWidth();
			double originalWidthD=new Double(originalWidth);
			Double scalePercent=(DEFAULT_WIDTH/ originalWidthD);
			Double newHeight=scalePercent*originalHeight;
			int resizedHeight=newHeight.intValue();
			resizedImage = new BufferedImage(DEFAULT_WIDTH.intValue(), resizedHeight, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, DEFAULT_WIDTH.intValue(), resizedHeight, null);	
			g.dispose();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( resizedImage, "jpg", baos );
			baos.flush();
			data=baos.toByteArray();
//			ImageIO.write(resizedImage, "jpg",originalImageFile);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				data=FileUtils.readFileToByteArray(originalImageFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return data;
    }
	
	public static void main(String... a) throws IOException{
//		File archivo=new File("/Users/victorherrera/Documents/GV/cabogrill1.jpg");
//		byte[] data=getResizeImage(archivo);
//		File archivoNuevo=new File("/Users/victorherrera/Documents/GV/cabogrill1_resized.jpg");
//		FileUtils.writeByteArrayToFile(archivoNuevo,data);
//		boolean hayError=validarMesa("ab2sasd1");
//		System.out.println("Hay error:"+hayError);
	}
}
