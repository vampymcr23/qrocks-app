package com.quuiko.youtube.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

public class YoutubeLocalCallbackServer implements VerificationCodeReceiver{
	volatile String code;
    private final int LOCAL_SERVER_PORT = 80;
    private static Logger log=Logger.getLogger(YoutubeLocalCallbackServer.class.getCanonicalName());

    public synchronized String waitForCode() {

        try {
            this.wait();
        } catch (Exception ex) {
        }
        log.log(Level.INFO,"Returning code is:"+ code);
        System.out.println("returning code is -> " + code);
        return code;

    }

    public String getRedirectUri() {

        new Thread(new MyThread()).start();
        return "http://localhost:"+LOCAL_SERVER_PORT;
    }

    public void stop() {
    }

    class MyThread implements Runnable {

        public void run() {
            try {
                //    return GoogleOAuthConstants.OOB_REDIRECT_URI;
                ServerSocket ss = new ServerSocket(LOCAL_SERVER_PORT);
                System.out.println("server is ready...");
                log.log(Level.INFO,"Server is ready...");
                Socket socket = ss.accept();
                System.out.println("new request....");
                log.log(Level.INFO,"New request...");
                InputStream is = socket.getInputStream();
                StringWriter writer = new StringWriter();
                String firstLine = null;

                InputStreamReader isr = new InputStreamReader(is);
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(isr);
                String read = br.readLine();
                firstLine = read;
                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(os, true);
                
                StringTokenizer st = new StringTokenizer(firstLine, " ");
                st.nextToken();
                String codeLine = st.nextToken();
                st = new StringTokenizer(codeLine, "=");
                st.nextToken();
                code = st.nextToken();
                
                out.write("RETURNED CODE IS "+code+"");
                out.flush();
//                is.close();
                
                socket.close();

                log.log(Level.INFO,"Extracted code is..."+code);
                System.out.println("Extracted coded is " + code);

                synchronized (YoutubeLocalCallbackServer.this) {
                	YoutubeLocalCallbackServer.this.notify();
                }
                System.out.println("return is " + sb.toString());
            } catch (IOException ex) {
            	log.log(Level.INFO, "Errror al intentar establecer conexion en el puerto:"+LOCAL_SERVER_PORT);
                log.log(Level.SEVERE, null, ex);
            }
        }
    }
}
