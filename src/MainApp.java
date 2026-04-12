import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public MainApp() {
        // Frame setup
        setTitle("Online Exam Login");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Panel with grid layout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Username field
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // Password field
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Empty cell + Login button
        panel.add(new JLabel(""));
        loginButton = new JButton("Login");
        panel.add(loginButton);

        add(panel);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Call your existing Login.java method
                int userId = Login.loginUser(username, password);

                if (userId != -1) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    // ✅ Launch OnlineExam window here
                    SwingUtilities.invokeLater(() -> new OnlineExam(userId, 101));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}