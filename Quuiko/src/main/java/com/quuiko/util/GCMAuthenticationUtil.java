package com.quuiko.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Utileria para obtener el token de autenticacion de google
 * Ejemplo: http://www.vogella.com/tutorials/AndroidCloudToDeviceMessaging/article.html
 * @author victorherrera
 *
 */
public class GCMAuthenticationUtil {
  private GCMAuthenticationUtil() {
    // Util class cannot get instanziated
  }

  public static String getToken(String email, String password)
      throws IOException {
    // create the post data
    // Requires a field with the email and the password
    StringBuilder builder = new StringBuilder();
    builder.append("Email=").append(email);
    builder.append("&Passwd=").append(password);
    builder.append("&accountType=GOOGLE");
    builder.append("&source=MyLittleExample");
    builder.append("&service=ac2dm");

    // Setup the Http Post
    byte[] data = builder.toString().getBytes();
    URL url = new URL("https://www.google.com/accounts/ClientLogin");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setUseCaches(false);
    con.setDoOutput(true);
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type",
        "application/x-www-form-urlencoded");
    con.setRequestProperty("Content-Length", Integer.toString(data.length));

    // Issue the HTTP POST request
    OutputStream output = con.getOutputStream();
    output.write(data);
    output.close();

    // read the response
    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String line = null;
    String auth_key = null;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith("Auth=")) {
        auth_key = line.substring(5);
      }
    }

    // Finally get the authentication token
    // To something useful with it
    return auth_key;
  }
  
  public static void main(String[] args) throws IOException {
	    String token = GCMAuthenticationUtil.getToken("vampy.mcr.23@gmail.com","vampygina2323");
	    System.out.println(token);
  }
} 
