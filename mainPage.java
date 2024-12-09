package com.mycompany.pp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPage {
    public static void display() {
        // Main frame
        JFrame frame = new JFrame(" Welcome to movie Booking ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        JLabel headerLabel = new JLabel(" movie Booking System ", JLabel.CENTER);
        headerLabel.setForeground(Color.YELLOW);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerPanel.add(headerLabel);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.DARK_GRAY);

        JLabel clickToBookLabel = new JLabel("Click to Book Your Movie Seat", JLabel.CENTER);
        clickToBookLabel.setForeground(Color.WHITE);
        clickToBookLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton bookButton = new JButton(" Book ticket Now");
        bookButton.setBackground(Color.YELLOW);
        bookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bookButton.setFocusPainted(false);

        contentPanel.add(clickToBookLabel);
        contentPanel.add(bookButton);

        // Footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.BLACK);
        JLabel footerLabel = new JLabel("Enjoy Your Movie!");
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footerPanel.add(footerLabel);

        // Add panels to frame
        frame.add(headerPanel);
        frame.add(contentPanel);
        frame.add(footerPanel);

        // Action listener for the "Book" button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new thread to handle GUI
                gui bookingThread = new gui();
                bookingThread.start();
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
