import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    JTextField userField;
    JPasswordField passField;

    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setLayout(new GridLayout(3,2));

        add(new JLabel("Username:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        JButton loginBtn = new JButton("Login");
        add(loginBtn);

        loginBtn.addActionListener(e -> login());

        setVisible(true);
    }

    void login() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, userField.getText());
            ps.setString(2, new String(passField.getPassword()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Success");
                new ReservationForm();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}