package com.quuiko.youtube.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.quuiko.exception.QRocksException;

/**
 * Shared class used by every sample. Contains methods for authorizing a user and caching credentials.
 * Al dar de alta la llave, debe de provenir de API como aplicacion de escritorio, y no web.
 */
public class Auth {
	private static Logger log=Logger.getLogger(Auth.class.getCanonicalName());
	//FIXME PRODUCCION (client_secrets_prod.json)- DEV(client_secrets.json)
//	public static String clientJSONCredencialForYoutube="client_secrets_prod.json";
	public static String clientJSONCredencialForYoutube="client_secrets.json";

    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * This is the directory that will be used under the user's home directory where OAuth tokens will be stored.
     */
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    /**
     * Authorizes the installed application to access user's protected data.
     *
     * @param scopes              list of scopes needed to run youtube upload.
     * @param credentialDatastore name of the credential datastore to cache OAuth tokens
     * @throws QRocksException 
     */
    public static Credential authorize(List<String> scopes, String credentialDatastore) throws IOException, QRocksException {

        // Load client secrets.
//        Reader clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/client_secrets.json"));
    	log.log(Level.INFO,"Tratando de leer el archivo:"+clientJSONCredencialForYoutube);
    	Reader clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/"+clientJSONCredencialForYoutube));
    	log.log(Level.INFO,"Credenciales leidas...");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
        // Checks that the defaults have been replaced (Default = "Enter X here").
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/?api=youtube"+ "into src/main/resources/"+clientJSONCredencialForYoutube);
            System.exit(1);
        }

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
                .build();

        // Build the local server and bind it to port 8080
        //FIXME Antes estaba asi
        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
        log.log(Level.INFO,"Intentando establecer LocalServerReceiver...");
//        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(9090).build();
//        YoutubeLocalCallbackServer localReceiver= new YoutubeLocalCallbackServer();
        log.log(Level.INFO,"LocalServerReceiver exitoso!...");
//        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setHost("quuiko.com").setPort(80).build();
        // Authorize.
        Credential credential=null;
        try{
        	log.log(Level.INFO,"Intentando obtener las credenciales de LocalServerReceiver Host Localhost y puerto:"+localReceiver.getPort());
        	credential=new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
        	String token=credential.getAccessToken();
        	System.out.println("Token de google:"+token);
        }catch(Exception e){
        	log.log(Level.SEVERE,"Ocurrio un error al obtener las credenciales de google api, causado por:"+e.getMessage());
        	throw new QRocksException(e.getMessage(),true);
        }
        return credential;
    }
    
    public static void main(String... a){
    	List<String> scopes = com.google.common.collect.Lists.newArrayList("https://www.googleapis.com/auth/youtube");
    	// Authorize the request.
        try {
			Credential credential = Auth.authorize(scopes, "playlistupdates");
			System.out.println("Credential:"+credential);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QRocksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public static void setClientJSONCredencialForYoutube(
			String clientJSONCredencialForYoutube) {
		Auth.clientJSONCredencialForYoutube = clientJSONCredencialForYoutube;
	}
}
