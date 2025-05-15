package com.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;


public class Api {

    public static void main(String[] args) throws Exception {
        //String url = "xivapi.com/asset?icon=064000/064001_hr1.png";
        String url = "https://genshin.jmp.blue/characters/all";
        String jsonString = getData(url);
        JSONArray array = new JSONArray(jsonString);
        String[] names = new String[array.length()];
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.getJSONObject(i);
            String name = obj.getString("name");
            names[i]=name;
            System.out.println(name);
        }


  
    }
    public static String getData(String endpoint) throws Exception {
            /*endpoint is a url (string) that you get from an API website*/
            URL url = new URL(endpoint);
            /*connect to the URL*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            /*creates a GET request to the API.. Asking the server to retrieve information for our program*/
            connection.setRequestMethod("GET");
            /* When you read data from the server, it wil be in bytes, the InputStreamReader will convert it to text. 
            The BufferedReader wraps the text in a buffer so we can read it line by line*/
            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;//variable to store text, line by line
            /*A string builder is similar to a string object but faster for larger strings, 
            you can concatenate to it and build a larger string. Loop through the buffer 
            (read line by line). Add it to the stringbuilder */
            StringBuilder content = new StringBuilder();
            while ((inputLine = buff.readLine()) != null) {
                content.append(inputLine);
            }
            buff.close(); //close the bufferreader
            connection.disconnect(); //disconnect from server 
            return content.toString(); //return the content as a string
     


    }

}

