package com.example.democlient;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;

public class HttpClient {

    public static void callServlet(String target) {
        Transaction transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("CallServlet");

            URL url = new URL(target);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
	    /*
	     * elastic-apm-traceparent: 00-0af7651916cd43dd8448eb211c80319c-b7ad6b7169203331-01
             * (_____________________)  () (______________________________) (______________) ()
             *            v             v                 v                        v         v
             *       Header name     Version           Trace-Id                Span-Id     Flags
	     */
	    http.setRequestProperty ("traceparent", "00-"+
			    transaction.getTraceId()+"-"+
			    transaction.getId()+"-01");

            System.out.println("GET [" + target + "]\n");

            //Connect:
            http.connect();

            //Wait for answer.
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inLine = null;

            while ((inLine = in.readLine()) != null) {
                System.out.println(inLine + "\n");
            }

            //Disconnect:
            http.disconnect();

        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            transaction.end();
        }
    }

    public static void main(String[] args) {

        String target = "http://127.0.0.1:8080/";
        int sleepTimeMs = 1000;

        while (true) {
            callServlet(target);
            try {
                Thread.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                System.out.println("Exiting...\n");
            }
        }
    }
}



