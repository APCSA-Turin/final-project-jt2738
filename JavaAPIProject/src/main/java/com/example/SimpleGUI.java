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
        JLabel titleLabel = new JLabel("Who is this?", SwingConstants.CENTER);
        JTextField inputField = new JTextField(); //input text 
        JButton fetchButton = new JButton("Enter Answer");//a button fetches data when pressed
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
        String userInput = inputField.getText().trim();

        if (correctAnswer.isEmpty()) {
            // No character loaded yet — load one
            try {
                CharacterInfo character = Api.getRandomCharacter();
                correctAnswer = character.displayName;
                ImageIcon icon = new ImageIcon(character.imagePath);
                imageLabel.setIcon(icon);
                outputArea.setText("Who's this?");
                inputField.setText(""); // clear input
            } catch (Exception ex) {
                ex.printStackTrace();
                outputArea.setText("Error fetching character.");
            }
        } else {
            // Character is loaded — check user guess
            if (userInput.equalsIgnoreCase(correctAnswer)) {
                outputArea.setText("Correct! It's " + correctAnswer + ".");
                correctAnswer = ""; // Reset for next character
            } else {
                outputArea.setText("Incorrect! Try again.");
            }
        }
    }
});

        frame.setVisible(true);
    }
}
