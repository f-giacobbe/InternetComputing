package it.unical.dimes.reti.rest;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpRestClient {


    public final static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            getCustomers(httpclient);
            getCustomerWithID(httpclient, 10);

            String newCustomer = "<CUSTOMER><ID>2341</ID><FIRSTNAME>Prova Todida</FIRSTNAME>" +
                    "<LASTNAME>RetiDiCalcolatori</LASTNAME>" +
                    "<STREET>DIMES,UNICAL</STREET>" +
                    "<CITY>Cosenza</CITY>" +
                    "</CUSTOMER>";

            postCustomer(httpclient, newCustomer);
            deleteCustomerWithID(httpclient, 2341);

        } finally {
            httpclient.close();
        }
    }

    private static void getCustomers(CloseableHttpClient httpclient) throws IOException {
        /*HttpClient per eseguire una richiesta HTTP GET verso l'URL specificato http://www.thomas-bayer.com/sqlrest/CUSTOMER/
        e stampare alcune informazioni sulla risposta ricevuta.

        Qui viene istanziato un oggetto HttpGet che rappresenta
        una richiesta HTTP GET verso l'URL http://www.thomas-bayer.com/sqlrest/CUSTOMER/.

        Viene stampato un messaggio in console per indicare che la richiesta HTTP
        sta per essere eseguita. httpget.getRequestLine() restituisce una stringa
        che rappresenta la linea di richiesta HTTP (nel formato "METODO URL HTTP/Versione").

        viene eseguita effettivamente la richiesta HTTP utilizzando un oggetto CloseableHttpClient

        Viene ottenuta la linea di stato della risposta HTTP (contenente la versione del protocollo,
        il codice di stato e la frase di stato) e il corpo della risposta convertito in una stringa.


         */
        HttpGet httpget = new HttpGet("http://www.thomas-bayer.com/sqlrest/CUSTOMER/");
        System.out.println("Executing request " + httpget.getRequestLine());

        CloseableHttpResponse response = httpclient.execute(httpget);
        StatusLine statusLine = response.getStatusLine();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("----------------------------------------");
        System.out.println(
                statusLine.getProtocolVersion() + " " +
                        statusLine.getStatusCode() + " " +
                        statusLine.getReasonPhrase()
        );
        System.out.println(responseBody);
    }

    private static void getCustomerWithID(CloseableHttpClient httpclient, long id) throws IOException {
        HttpGet httpget = new HttpGet("http://www.thomas-bayer.com/sqlrest/CUSTOMER/" + id);
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httpget);
        StatusLine statusLine = response.getStatusLine();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("----------------------------------------");
        System.out.println(
                statusLine.getProtocolVersion() + " " +
                        statusLine.getStatusCode() + " " +
                        statusLine.getReasonPhrase()
        );
        System.out.println(responseBody);
    }

    private static void postCustomer(CloseableHttpClient httpclient, String customerXML) throws IOException {
        HttpPost httpost = new HttpPost("http://www.thomas-bayer.com/sqlrest/CUSTOMER/");
        httpost.setEntity(new StringEntity(customerXML, "utf-8"));
        System.out.println("Executing request " + httpost.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httpost);
        StatusLine statusLine = response.getStatusLine();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("----------------------------------------");
        System.out.println(
                statusLine.getProtocolVersion() + " " +
                        statusLine.getStatusCode() + " " +
                        statusLine.getReasonPhrase()
        );
        System.out.println(responseBody);
    }

    private static void deleteCustomerWithID(CloseableHttpClient httpclient, long id) throws IOException {
        HttpDelete httpdel = new HttpDelete("http://www.thomas-bayer.com/sqlrest/CUSTOMER/" + id);
        System.out.println("Executing request " + httpdel.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httpdel);
        StatusLine statusLine = response.getStatusLine();
        System.out.println("----------------------------------------");
        System.out.println(statusLine);
    }

    private static void putCustomer(CloseableHttpClient httpclient, String customerXML) throws IOException {
        HttpPut httpost = new HttpPut("http://www.thomas-bayer.com/sqlrest/CUSTOMER/");
        httpost.setEntity(new StringEntity(customerXML, "utf-8"));
        System.out.println("Executing request " + httpost.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httpost);
        StatusLine statusLine = response.getStatusLine();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("----------------------------------------");
        System.out.println(statusLine);
        System.out.println(responseBody);
    }
}
