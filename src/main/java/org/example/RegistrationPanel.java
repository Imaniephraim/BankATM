package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistrationPanel  extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField  nameField;

    private ATMApp app;
    public RegistrationPanel(ATMApp app) {
        this.app = app;
        setLayout(new GridBagLayout());
        setBackground(new Color(52, 102, 255));

        GridBagConstraints  gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        //username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        //username text field
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        //password label
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        //password field
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        //name label
        JLabel nameLabel = new JLabel("Name:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(nameLabel, gbc);

        //name text field
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(nameField, gbc);

        //registration button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(registerButton, gbc);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public  void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
             String name = nameField.getText();

             if(username.isEmpty() || password.isEmpty() || name.isEmpty()) {
                 JOptionPane.showMessageDialog(RegistrationPanel.this, "Please Fill In All Fields", "Registration Error", JOptionPane.ERROR_MESSAGE);
                 return;
             }

             try{
                 DatabaseHelper.createUser(username, password, name);
                 User user = DatabaseHelper.getUserByUsername(username);

                 if (user != null) {
                     DatabaseHelper.createAccount(user.getUserid(), generateAccount());
                     app.setUser(user);
                     app.showPanel("Dashboard");
                     JOptionPane.showMessageDialog(RegistrationPanel.this, "Registration Successful", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                 }else {
                     JOptionPane.showMessageDialog(RegistrationPanel.this, "Registration Failed", "Registration Error", JOptionPane.ERROR_MESSAGE);
                 }
             }catch (SQLException ex){
                 ex.printStackTrace();
                 JOptionPane.showMessageDialog(RegistrationPanel.this, "Registration Failed!", "Registration Error", JOptionPane.ERROR_MESSAGE);
             }
        }
    }

    private String generateAccount(){
        //you can create your own unique number
        return String.valueOf(System.currentTimeMillis());
    }
}



















































