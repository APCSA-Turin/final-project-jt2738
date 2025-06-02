package com.example;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
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

    public static CharacterInfo getRandomCharacter ()throws Exception {
        String url = "https://genshin.jmp.blue/characters/all"; //url fetching all characters' info
        String jsonString = getDataNames(url,-1); //this string gets the data from the url and stores it in a string
        JSONArray array = new JSONArray(jsonString); //convert the string to a JSON array
        String[] names = new String[array.length()]; //string array is made to hold the names of the characters
        for(int i=0;i<array.length();i++){ //iterates through json array to get ONLY the names of the characters
            JSONObject obj = array.getJSONObject(i);
            names[i] = obj.getString("name");
            
        }
        int idx= (int)(Math.random()*names.length); //generates a random idx to get a character
        String displayName = names[idx]; 
        String charName = displayName.toLowerCase(); //makes name lowercase so it works with the url
        if (charName.equals("aratakiitto")) { //checks for itto's name, since it has a dash
            charName = "arataki-itto";
        }
        String nospacescharName= charName.replaceAll("\\s", ""); 
        String imageUrl; 
        if (!nospacescharName.equals("kinich") && !nospacescharName.equals("kachina") ) { //checks for instance of kinich or kachina
             imageUrl = "https://genshin.jmp.blue/characters/" + nospacescharName + "/portrait"; //if not either, then image URL is portrait
            
        }else{
             imageUrl = "https://genshin.jmp.blue/characters/" + nospacescharName + "/card"; //else get card of character
    }
       String filePath = "JavaAPIProject/src/images/" + nospacescharName + ".png"; //creates file path for image so it's accessible
       downloadUsingStream(imageUrl, filePath); //dounloads image from given URL, then saves to file path


        return new CharacterInfo(displayName, filePath); //returns the character's info
    }

    public static String fetchImage(String name){ //method gets image from the directory
        File directory = new File("JavaAPIProject/src/images"); //stores the place where images are stored
        File[] list = directory.listFiles();
        for(File f : list){ 
            if(f.getName().equals(name)){ 
                return name; //iterates through files from API and returns name of file if matches, then returns the name so it can return the image
            }
        }
        return "null";
    }



     public static void downloadUsingStream(String urlStr, String file) throws IOException{ //image is downloaded and then saved to file path
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

    

     public static String getDataNames(String endpoint, int state) throws Exception { //gets data from API, returns a string
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

     public static String parseData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseData'");
     }

   
    
}

