package com.mycompany.pp;  

import javax.swing.*;  
import java.awt.*;  
import java.beans.PropertyChangeEvent;  
import java.beans.PropertyChangeListener;  
import java.util.ArrayList;  
import java.util.List;  

public class gui extends Thread implements PropertyChangeListener {  
    private JButton[][] seatButtons;  
    private final String userName;  
    private final List<String> reservedSeats;  
    
    public gui(String userName) {  
        this.userName = userName;  
        this.reservedSeats = new ArrayList<>();  
    }  

    @Override  
    public void run() {  
        JFrame frame = new JFrame("Movie Ticket Booking - " + userName);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        frame.setSize(600, 400);  
        frame.setLayout(new BorderLayout());  
 
        JPanel headerPanel = new JPanel();  
        headerPanel.setBackground(Color.BLACK);  
        JLabel headerLabel = new JLabel("Welcome to Cinema Booking - " + userName);  
        headerLabel.setForeground(Color.YELLOW);  
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));  
        headerPanel.add(headerLabel);  
        
        JPanel seatPanel = new JPanel(new GridLayout(Ticket.ROWS, Ticket.COLUMNS, 5, 5));  
        seatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  
        
        seatButtons = new JButton[Ticket.ROWS][Ticket.COLUMNS];  
        for (int i = 0; i < Ticket.ROWS; i++) {  
            for (int j = 0; j < Ticket.COLUMNS; j++) {  
                JButton seatButton = new JButton((i * Ticket.COLUMNS + j + 1) + "");  
                seatButton.setBackground(Color.GREEN);   
                seatButton.setFont(new Font("SansSerif", Font.BOLD, 14));  
                final int row = i;  
                final int column = j;  

                seatButton.addActionListener(e -> handleSeatClick(seatButton, row, column));  

                seatButtons[i][j] = seatButton;  
                seatPanel.add(seatButton);  
            }  
        }  

        JPanel footerPanel = new JPanel();  
        footerPanel.setBackground(Color.DARK_GRAY);  
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 

        JButton finishButton = new JButton("Finish");  
        finishButton.setBackground(Color.GREEN);  
        finishButton.setForeground(Color.WHITE);  
        finishButton.setFont(new Font("SansSerif", Font.BOLD, 16));  
        finishButton.setFocusPainted(false);  
        
        finishButton.addActionListener(e -> {  
            if (reservedSeats.isEmpty()) {  
                JOptionPane.showMessageDialog(frame, "You have not reserved any seats.", "No Reservations", JOptionPane.INFORMATION_MESSAGE);  
            } else {  
                StringBuilder message = new StringBuilder("You have reserved the following seats:\n");  
                for (String seat : reservedSeats) {  
                    message.append(seat).append("\n");  
                }  
                JOptionPane.showMessageDialog(frame, message.toString(), "Reservation Summary", JOptionPane.INFORMATION_MESSAGE);  
            }  
            frame.dispose(); 
        });  

        footerPanel.add(finishButton);  

   
        frame.add(headerPanel, BorderLayout.NORTH);  
        frame.add(seatPanel, BorderLayout.CENTER);  
        frame.add(footerPanel, BorderLayout.SOUTH);   
        frame.setVisible(true);  
  
        Ticket.addPropertyChangeListener(this);  
 
        frame.addWindowListener(new java.awt.event.WindowAdapter() {  
            @Override  
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {  
                Ticket.removePropertyChangeListener(gui.this);  
            }  
        });  
    }  

    private void handleSeatClick(JButton seatButton, int row, int column) {  
        String reservedBy = Ticket.isSeatBooked(row, column);  
        if (reservedBy != null) {  
            // Notify the user that the seat is already booked  
            JOptionPane.showMessageDialog(null, "Sorry, seat " + seatButton.getText() + " is already booked by " + reservedBy + "!", "Seat Already Booked", JOptionPane.ERROR_MESSAGE);  
        } else {
            boolean isBooked = Ticket.bookSeat(row, column, userName);  
            if (isBooked) {  
                seatButton.setBackground(Color.RED);
                reservedSeats.add("Seat " + seatButton.getText());
                JOptionPane.showMessageDialog(null, "Seat " + seatButton.getText() + " booked successfully by user " + userName + "!", "Booking Successful", JOptionPane.INFORMATION_MESSAGE);  
            } else {  
                JOptionPane.showMessageDialog(null, "Failed to book seat! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);  
            }  
        }  
    }  

    @Override  
    public void propertyChange(PropertyChangeEvent evt) {  
        if ("Tickets".equals(evt.getPropertyName())) {  
            boolean[][] bookedSeats = (boolean[][]) evt.getNewValue();  
            for (int i = 0; i < Ticket.ROWS; i++) {  
                for (int j = 0; j < Ticket.COLUMNS; j++) {  
                    if (bookedSeats[i][j]) {  
                        seatButtons[i][j].setBackground(Color.RED);
                    }  
                }  
            }  
        }  
    }  
}
