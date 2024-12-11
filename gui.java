package com.mycompany.pp;  

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.beans.PropertyChangeEvent;  
import java.beans.PropertyChangeListener;  

public class gui extends Thread implements PropertyChangeListener {  
    private JTextField availableSeatsField;  

    @Override  
    public void run() {  
        // Create the frame  
        JFrame frame = new JFrame("Movie Ticket Booking");  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        frame.setSize(300, 200);  
        frame.setLayout(new GridLayout(4, 2, 10, 10));  

        // Create labels, text fields, and button  
        JLabel movieSeatsLabel = new JLabel("Movie seats: 5");  
        JLabel availableSeatsLabel = new JLabel("Available seats:");  
        availableSeatsField = new JTextField(String.valueOf(Ticket.getTickets()));  
        availableSeatsField.setEditable(false);  

        JLabel bookSeatsLabel = new JLabel("Book seats:");  
        JTextField bookSeatsField = new JTextField();  

        JButton finishButton = new JButton("Finish");  

        // Add components to the frame  
        frame.add(movieSeatsLabel);  
        frame.add(new JLabel()); // Empty label for alignment  
        frame.add(availableSeatsLabel);  
        frame.add(availableSeatsField);  
        frame.add(bookSeatsLabel);  
        frame.add(bookSeatsField);  
        frame.add(finishButton);  

        // Add action listener for the "Finish" button  
        finishButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                try {  
                    int bookedSeats = Integer.parseInt(bookSeatsField.getText());  

                    // Check if the input is valid  
                    if (bookedSeats <= 0 || bookedSeats > Ticket.getTickets()) {  
                        JOptionPane.showMessageDialog(frame, "Invalid number of seats!", "Error", JOptionPane.ERROR_MESSAGE);  
                    } else {  
                        // Book the tickets  
                        Ticket.BookTicket(bookedSeats);  

                        JOptionPane.showMessageDialog(frame, "Seats booked successfully:)!");  
                    }  
                } catch (NumberFormatException ex) {  
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);  
                }  
            }  
        });  

        // Register this GUI as a listener for ticket updates  
        Ticket.addPropertyChangeListener(this);  

        // Display the frame  
        frame.setVisible(true);  

        // Cleanup listener when the window is closed  
        frame.addWindowListener(new java.awt.event.WindowAdapter() {  
            @Override  
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {  
                Ticket.removePropertyChangeListener(gui.this);  
            }  
        });  
    }  

    @Override  
    public void propertyChange(PropertyChangeEvent evt) {  
        if ("Tickets".equals(evt.getPropertyName())) {  
            // Update the available seats field when Tickets changes  
            availableSeatsField.setText(String.valueOf(evt.getNewValue()));  
        }  
    }  
}