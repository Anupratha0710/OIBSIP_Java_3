import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReservationForm extends JFrame {
    JTextField name, train, from, to, date, cls;

    public ReservationForm() {
        setTitle("Reservation");
        setSize(400, 400);
        setLayout(new GridLayout(7,2));

        name = new JTextField();
        train = new JTextField();
        cls = new JTextField();
        date = new JTextField();
        from = new JTextField();
        to = new JTextField();

        add(new JLabel("Name")); add(name);
        add(new JLabel("Train No")); add(train);
        add(new JLabel("Class")); add(cls);
        add(new JLabel("Date")); add(date);
        add(new JLabel("From")); add(from);
        add(new JLabel("To")); add(to);

        JButton book = new JButton("Book");
        add(book);

        book.addActionListener(e -> bookTicket());

        setVisible(true);
    }

   void bookTicket() {
    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO reservations(name, train_number, class_type, date_of_journey, from_location, to_location) VALUES (?,?,?,?,?,?)"
        );

        ps.setString(1, name.getText());
        ps.setString(2, train.getText());
        ps.setString(3, cls.getText());
        ps.setDate(4, java.sql.Date.valueOf(date.getText())); // FIXED
        ps.setString(5, from.getText());
        ps.setString(6, to.getText());

        int rows = ps.executeUpdate();

        if (rows > 0) {
            JOptionPane.showMessageDialog(this, "Ticket Booked!");
        }

    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    }

    
}
