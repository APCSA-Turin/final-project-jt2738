package com.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SimpleGUI{
    private static String correctAnswer = "";
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Data Viewer");
        frame.setSize(400, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel titleLabel = new JLabel("Character Guessing Game", SwingConstants.CENTER);
        JTextField inputField = new JTextField(); //input text 
        JButton fetchButton = new JButton("Enter");//a button fetches data when pressed
        JTextArea outputArea = new JTextArea();//where the fetched data will output 
        outputArea.setEditable(false);
        JLabel imageLabel= new JLabel();;

        // Layout setup
        JPanel panel = new JPanel(new GridLayout(5, 1)); //create the JPanel object
        //Jpanel is like a tray that you put things on and then you put the whole tray into your window
        //this panel holds a title, input field, button, and output area
        panel.add(titleLabel);
        panel.add(inputField);
        panel.add(fetchButton);
        panel.add(outputArea);
        panel.add(imageLabel);

        //We have added components to our panel, then we add the PANEL to our FRAME
        frame.add(panel); 

        // Button behavior
        fetchButton.addActionListener(new ActionListener() { 
    @Override
    public void actionPerformed(ActionEvent e) { 
        CharacterInfo character = null; // when button is pressed, character object is made
        try {
            character = Api.getRandomCharacter(); //character is initialized using API
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        correctAnswer = character.displayName; //program stores correct answer of the name
        String userInput = inputField.getText(); // gathers user input from text field

        if (correctAnswer.isEmpty()) { // If no character is fetched, display an error
            try {
                ImageIcon icon = new ImageIcon(character.imagePath);
                imageLabel.setIcon(icon); // sets image in the JLabel
                outputArea.setText("Who's this?"); //asks user to guess the character
                inputField.setText(""); // clear input
            } catch (Exception ex) {
                ex.printStackTrace();
                outputArea.setText("Error fetching character. Please input a real name!"); //if name is not found in data bases, displays error and propmts user to try again
            }
        } else {
            if (userInput.equalsIgnoreCase(correctAnswer)) {
                outputArea.setText("Correct! It's " + correctAnswer + ".");
                correctAnswer = ""; // Reset for next character. Player presses enter again to get a new character
            } else {
                outputArea.setText("Incorrect! Try again."); //if incorrect player is prompted to try again
            }
        }
    }
});

        frame.setVisible(true); // Make the frame visible
    }
}
