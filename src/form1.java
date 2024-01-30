// Clase form1
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class form1 {
    protected JPanel login;
    private JLabel label1;
    public JTextField email;
    private JButton ingresar;
    private JPasswordField password;
    private JLabel label2;
    private JLabel label3;
    private JLabel advertencia;

    public form1() {
        ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dbUrl = "jdbc:mysql://localhost:3306/guilogin";
                String dbUser = "root";
                String dbpassword = "root";

                try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpassword)) {
                    String user = email.getText();
                    String query = "SELECT contraseña FROM usuarios WHERE email = ?";
                    String query2 = "SELECT biografia FROM usuarios WHERE email = ?";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, user);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                String passwordUser = resultSet.getString("contraseña");

                                try (PreparedStatement pS = connection.prepareStatement(query2)) {
                                    pS.setString(1, user);
                                    try (ResultSet rst = pS.executeQuery()) {
                                        if (rst.next()) {
                                            String biography = rst.getString("biografia");
                                            System.out.println(biography);

                                            // Comparar contraseñas
                                            char[] enteredPassword = password.getPassword();
                                            String enteredPasswordString = new String(enteredPassword);

                                            if (passwordUser.equals(enteredPasswordString)) {
                                                // Cerrar el frame actual
                                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ingresar);
                                                frame.dispose();

                                                // Crear y mostrar el panel con la biografía
                                                JPanel panel = new JPanel();
                                                JLabel labelBiografia = new JLabel("Biografía:");
                                                JTextArea textAreaBiografia = new JTextArea(10, 30);
                                                textAreaBiografia.setLineWrap(true);
                                                textAreaBiografia.setWrapStyleWord(true);
                                                textAreaBiografia.setText(biography);
                                                textAreaBiografia.setEditable(false);

                                                panel.add(labelBiografia);
                                                panel.add(new JScrollPane(textAreaBiografia));

                                                // Crear un nuevo frame para la biografía
                                                JFrame frameBiografia = new JFrame("Biografía");
                                                frameBiografia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                frameBiografia.setSize(400, 240);
                                                frameBiografia.setLocationRelativeTo(null);
                                                frameBiografia.setContentPane(panel);
                                                frameBiografia.setVisible(true);

                                            } else {
                                                advertencia.setText("Usuario y/o contraseña incorrectos");
                                                email.setText("");
                                                password.setText("");
                                            }
                                        }
                                    }
                                }
                            } else {
                                advertencia.setText("Usuario no encontrado");
                                email.setText("");
                                password.setText("");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
