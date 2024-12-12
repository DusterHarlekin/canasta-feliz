package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;
import model.Model;
import view.Screen;

public class Controller implements ActionListener {

    private Screen vista;
    private Model modelo;

    public Controller(Screen vista, Model modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.providersButton.addActionListener(this);
        this.vista.productsButton.addActionListener(this);
        this.vista.cutsButton.addActionListener(this);
    }

    public void iniciarVista() {

        vista.setVisible(true);

        vista.setLocationRelativeTo(null);

        showMessageDialog(null, "This is even shorter");
    }

    public void actionPerformed(ActionEvent e) {

        DefaultTableModel dtm = (DefaultTableModel) vista.registerTable.getModel();
        dtm.setColumnCount(0);
        dtm.setRowCount(0);
        System.out.println("ESTOY AQUÍ");

        if (e.getSource().equals(vista.providersButton)) {

            dtm.addColumn("Rif");
            dtm.addColumn("Nombre");
            dtm.addColumn("Apellido");
            dtm.addColumn("Teléfono");
            dtm.addColumn("Correo");

            try {
                ResultSet rsProviders = modelo.getProviders();

                while (rsProviders.next()) {

                    dtm.addRow(new Object[]{rsProviders.getString("rif"),
                        rsProviders.getString("nombre"),
                        rsProviders.getString("apellido"),
                        rsProviders.getString("telefono"),
                        rsProviders.getString("correo")
                    });
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(vista.productsButton)) {
            
            dtm.addColumn("Nombre");
            dtm.addColumn("Precio");
            dtm.addColumn("Proveedor");

            try {
                ResultSet rsProducts = modelo.getProducts();

                while (rsProducts.next()) {

                    dtm.addRow(new Object[]{rsProducts.getString(1),
                        rsProducts.getDouble(2),
                        rsProducts.getString(3) + " " + rsProducts.getString(4)
                    });
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(vista.cutsButton)) {
            
            dtm.addColumn("Nombre");
            dtm.addColumn("Producto");
            dtm.addColumn("Merma Promedio");

            try {
                ResultSet rsCuts = modelo.getCuts();

                while (rsCuts.next()) {
                    System.out.println("AUSILIO");
                    dtm.addRow(new Object[]{rsCuts.getString(1),
                        rsCuts.getString(3),
                        rsCuts.getDouble(2),
                    });
                }

            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
