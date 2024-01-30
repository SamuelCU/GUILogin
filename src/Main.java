import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Kaluka");
        frame.setContentPane(new form1().login);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.darkGray);

        frame.setSize(400,240);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}