package com.example;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class Api {

    public static void main(String[] args) throws Exception {
        String url = "https://genshin.jmp.blue/characters/all";
        String jsonString = getData(url,-1);
        JSONArray array = new JSONArray(jsonString);
        String[] names = new String[array.length()];
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.getJSONObject(i);
            String name = obj.getString("name");
            names[i]=name;
        }
        int idx= (int)(Math.random()*names.length);
        String charName = names[idx].toLowerCase();
        if (charName.equals("aratakiitto")) {
            charName = "arataki-itto";
        }
        String nospacescharName= charName.replaceAll("\\s", "");
        if (!nospacescharName.equals("kinich") && !nospacescharName.equals("kachina") ) {
             String portrait = "https://genshin.jmp.blue/characters/" + nospacescharName + "/portrait";
             try {      
             downloadUsingStream(portrait, "JavaAPIProject/src/images/"+ nospacescharName +".png");
             } catch (IOException e) {
             e.printStackTrace();
            }
        }else{
             String card = "https://genshin.jmp.blue/characters/" + nospacescharName + "/card";
             try {      
             downloadUsingStream(card, "JavaAPIProject/src/images/"+ nospacescharName +".png");
             } catch (IOException e) {
             e.printStackTrace(); 
        }
    }
       
        
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Who is this?");
        boolean guess= false;
        while (guess == false) {
            String userGuess = scanner.nextLine();
            if (!userGuess.equals(charName)) {
                System.out.println("Incorrect! Try Again?");
            }else{
                guess = true;
                System.out.println("Correct!");
            }
        }
        scanner.close();
    }

     private static void downloadUsingStream(String urlStr, String file) throws IOException{ 
        //https://www.digitalocean.com/community/tutorials/java-download-file-url
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    

     public static String getData(String endpoint, int state) throws Exception {
            /*endpoint is a url (string) that you get from an API website*/
            URL url = null;
            
            if(state == 0){
             url = new URL("https://genshin.jmp.blue/characters/wriothesley/portrait");
            }else if (state == -1){
                url = new URL(endpoint);
            }
            
           
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
             return  content.toString();
    }

   
    
}

