package com.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SimpleGUI{
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Data Viewer");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel titleLabel = new JLabel("Who is this?", SwingConstants.CENTER);
        JTextField inputField = new JTextField(); //input text 
        JButton fetchButton = new JButton("Enter Answer");//a button fetches data when pressed
        JTextArea outputArea = new JTextArea();//where the fetched data will output 
        outputArea.setEditable(false);
        JLabel imageLabel= new JLabel();;

        // Layout setup
        JPanel panel = new JPanel(new GridLayout(4, 1)); //create the JPanel object
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
                String name = inputField.getText().trim(); //city is in reference to my example
                String fileName = "";

                if (!name.isEmpty()) {
                    // Placeholder for data — replace with real API call 
                    try {
                        fileName += Api.fetchImage(Api.parseData());
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    ImageIcon image1 = new ImageIcon("JavaAPIProject/src/images"+fileName);
                    imageLabel.setIcon(image1);
                    
                } else {
                    outputArea.setText("Please enter a name:");
                }
            }
        });

        frame.setVisible(true);
    }
}
