package com.pearson.registrationassistant.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class invokes a webservice. This class is designed to be invoked
 * from command line
 *
 * Created by abhaykulkarni on 08/12/16.
 */
public class WSInvoker {

  public static void main(String[] args) {
    if (args.length != 2) {
//      System.out.println(Arrays.deepToString(args));
      System.out.println("Need correct # of arguments run the webservice invoker.");
      System.out.println("Usage: java -jar wsinvoker.jar com.pearson.registrationassistant.client.WSInvoker <company_identifier>");
      System.exit(1);
    }
    String url = "http://localhost:8080/RA/api/assistant/process/" + args[1];
    URL httpUrl = null;
    try {
      httpUrl = new URL(url);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      System.exit(1);
    }

    // make a connection
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) httpUrl.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    try {
      // set parameters on the connections
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept","application/json");

      // get result
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line = null;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }

      reader.close();
      connection.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
