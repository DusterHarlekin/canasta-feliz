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
import controller.Validation;
import view.ComboItem;

public class Controller {

    private Screen vista;
    private Model modelo;

    public Controller(Screen vista, Model modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.providersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dtm = (DefaultTableModel) vista.registerTable.getModel();
                dtm.setColumnCount(0);
                dtm.setRowCount(0);

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
            }
        });
        this.vista.productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dtm = (DefaultTableModel) vista.registerTable.getModel();
                dtm.setColumnCount(0);
                dtm.setRowCount(0);

                dtm.addColumn("Nombre");
                dtm.addColumn("Precio");
                dtm.addColumn("Merma Promedio (%)");
                dtm.addColumn("Proveedor");

                try {
                    ResultSet rsProducts = modelo.getProducts();

                    while (rsProducts.next()) {

                        dtm.addRow(new Object[]{rsProducts.getString(1),
                            rsProducts.getDouble(2),
                            rsProducts.getDouble(3),
                            rsProducts.getString(4) + " " + rsProducts.getString(5)
                        });
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //POST
        this.vista.newProviderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
                String rif = vista.newProviderDocText.getText();
                String nombre = vista.newProviderNameText.getText();
                String apellido = vista.newProviderSurnameText.getText();
                String telefono = vista.newProviderPhoneText.getText();
                String correo = vista.newProviderMailText.getText();
                String direccion = vista.newProviderAddressText.getText();
                        
                if (Validation.validarDocumento(rif)
                        && Validation.validarNombres(nombre, false)
                        && Validation.validarNombres(apellido, true)
                        && Validation.validarTelf(telefono)
                        && Validation.validarCorreo(correo)
                        && Validation.validarDireccion(direccion)) {

                    try {
                        modelo.postProvider(new Provider(rif, nombre, apellido, telefono, correo, direccion));
                        vista.providersButton.doClick();
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
        });
        
        this.vista.newProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Object boxItem = vista.newProductProviderSelect.getSelectedItem();
                
                String nombre = vista.newProductNameText.getText();
                String precio = vista.newProductPriceText.getText();
                String merma_promedio = vista.newProductDecrAvText.getText();
                System.out.println(((ComboItem)boxItem).getValue());
                int id_proveedor =Integer.parseInt(((ComboItem)boxItem).getValue());
                        
                if (Validation.validarNombres(nombre, false)
                        && Validation.validarDouble(precio)
                        && Validation.validarDouble(merma_promedio)) {
                    
                    try {
                        Double precioParsed = Double.parseDouble(vista.newProductPriceText.getText());
                        Double merma_promedioParsed = Double.parseDouble(vista.newProductDecrAvText.getText());

                        modelo.postProduct(new Product(id_proveedor, nombre, precioParsed, merma_promedioParsed));
                        vista.productsButton.doClick();
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
        });
    }

    public void iniciarVista() {

        vista.setVisible(true);

        vista.setLocationRelativeTo(null);

        showMessageDialog(null, "This is even shorter");
    }

}
