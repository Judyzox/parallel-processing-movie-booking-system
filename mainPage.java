package com.mycompany.pp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPage {
    public static void display() {
        JFrame frame = new JFrame("Movie Ticket Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(2, 1, 10, 10));

        // Create labels and buttons
        JLabel clickToBookLabel = new JLabel("Click to Book Your Movie Seat", JLabel.CENTER);
        JButton bookButton = new JButton("Book");

        // Add components to the frame
        frame.add(clickToBookLabel);
        frame.add(bookButton);

        // Add action listener for the "Book" button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new thread to handle GUI
                gui Thread = new gui();
                Thread.start();
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
