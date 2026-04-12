import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CancellationForm extends JFrame {
    JTextField pnrField;

    public CancellationForm() {
        setTitle("Cancel Ticket");
        setSize(300,200);
        setLayout(new GridLayout(2,2));

        add(new JLabel("Enter PNR:"));
        pnrField = new JTextField();
        add(pnrField);

        JButton cancel = new JButton("Cancel");
        add(cancel);

        cancel.addActionListener(e -> cancelTicket());

        setVisible(true);
    }

    void cancelTicket() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM reservations WHERE pnr=?"
            );
            ps.setInt(1, Integer.parseInt(pnrField.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cancelled Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
