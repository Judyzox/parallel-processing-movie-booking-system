package com.mycompany.pp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPage {
    public static void appear() {
       // Main frame
        JFrame frame = new JFrame("Welcome to Movie Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel headerLabel = new JLabel("Movie Booking System", JLabel.CENTER);
        headerLabel.setForeground(Color.YELLOW);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerPanel.add(headerLabel);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.DARK_GRAY);

        // "Click to Book Your Movie Seat" label
        JLabel clickToBookLabel = new JLabel("Click to Book Your Movie Seat", JLabel.CENTER);
        clickToBookLabel.setForeground(Color.WHITE);
        clickToBookLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        clickToBookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(Color.DARK_GRAY);

        JLabel userBookingLabel = new JLabel("Enter number of booking users to book:");
        userBookingLabel.setForeground(Color.WHITE);
        userBookingLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JTextField userInputField = new JTextField(5);

        inputPanel.add(userBookingLabel);
        inputPanel.add(userInputField);

        // "Book Ticket Now" button
        JButton bookButton = new JButton("Book Ticket Now");
        bookButton.setBackground(Color.YELLOW);
        bookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bookButton.setFocusPainted(false);
        bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to content panel
        contentPanel.add(clickToBookLabel);
        contentPanel.add(Box.createVerticalStrut(10)); // Adds vertical spacing
        contentPanel.add(inputPanel);
        contentPanel.add(Box.createVerticalStrut(10)); // Adds vertical spacing
        contentPanel.add(bookButton);

        // Footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.BLACK);
        JLabel footerLabel = new JLabel("Enjoy Your Movie!");
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footerPanel.add(footerLabel);

        // Add panels to frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);

        // Add action listener for the "Book" button
        bookButton.addActionListener(e -> {  
            String inputText = userInputField.getText();  
            try {  
                int numberOfThreads = Integer.parseInt(inputText);  

                if (numberOfThreads <= 0) {  
                    JOptionPane.showMessageDialog(frame, "Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);  
                    return;  
                }  

                for (int i = 1; i <= numberOfThreads; i++) {  
                    gui guiThread = new gui("User " + i);  
                    guiThread.start();  
                }  
             
            } catch (NumberFormatException ex) {  
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);  
            }  
        });

        // Display the frame
        frame.setVisible(true);
    }
}
