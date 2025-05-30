package com.example;

public class CharacterInfo {
    public String displayName;
    public String imagePath;

    public CharacterInfo(String displayName, String imagePath) { // method to more easily create a character object and access its properties from the API
        this.displayName = displayName;
        this.imagePath = imagePath;
    }
}

