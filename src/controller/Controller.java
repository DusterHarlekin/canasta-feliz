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
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import view.ComboItem;

public class Controller {

    private Screen vista;
    private Model modelo;
    
    public Controller(Screen vista, Model modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        this.vista.calcDecreaseProductSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object boxItem = vista.calcDecreaseProductSelect.getSelectedItem();
                if (((ComboItem) boxItem).getValue() != null) {
                    int idProducto = Integer.parseInt(((ComboItem) boxItem).getValue());
                    vista.calcDecreaseDateSelect.setEnabled(true);
                    try {
                        ResultSet rs = modelo.getInvProductDates(idProducto);

                        DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
                        boxModel.addElement(new ComboItem("Seleccione una fecha", null));
                        while (rs.next()) {
                            //newProductProviderSelect.a (new ComboItem(rs.getString("rif")+ " " + rs.getString("nombre"), rs.getInt("id_proveedor")));
                            //USEN ESTE MÉTODO
                            boxModel.addElement(new ComboItem(rs.getString("fecha_entrada"), rs.getString("fecha_entrada")));

                        }
                        vista.calcDecreaseDateSelect.setModel(boxModel);

                    } catch (SQLException ex) {
                        Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
                    boxModel.addElement(new ComboItem("", null));
                    vista.calcDecreaseDateSelect.setModel(boxModel);
                    vista.calcDecreaseDateSelect.setEnabled(false);
                }

            }
        });
        
        

        
        //GET
        
        this.vista.newRegistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.cLayout.show(vista.contentLayout, "new" + vista.currentView);
                switch (vista.currentView) {
                    case "Product":
                        vista.newProductTitle.setIcon(new ImageIcon(new ImageIcon("./src/imgs/iconProduct.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
                    case "Provider":
                        vista.newProviderTitle.setIcon(new ImageIcon(new ImageIcon("./src/imgs/iconProvider.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
                         
                            try {
                                ResultSet rs = modelo.getProviders();

                                DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
                                while (rs.next()) {
                                    //newProductProviderSelect.a (new ComboItem(rs.getString("rif")+ " " + rs.getString("nombre"), rs.getInt("id_proveedor")));
                                    //USEN ESTE MÉTODO

                                    boxModel.addElement(new ComboItem(rs.getString("rif") + " " + rs.getString("nombre"), rs.getString("id_proveedor")));

                                }
                                vista.newProductProviderSelect.setModel(boxModel);

                            } catch (SQLException ex) {
                                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                    case "Inventory":
                        vista.newInventoryTitle.setIcon(new ImageIcon(new ImageIcon("./src/imgs/iconInventory.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
                        
                            try {
                                ResultSet rs = modelo.getProducts();

                                DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
                                while (rs.next()) {
    
                                    boxModel.addElement(new ComboItem(rs.getString(2), rs.getString(1)));

                                }
                                vista.newInvProductSelect.setModel(boxModel);

                            } catch (SQLException ex) {
                                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                }
            }
        });
        
        this.vista.calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.cLayout.show(vista.contentLayout, "calcDecrease");
                vista.currentView = vista.calcButton.getName();
                vista.calcDecreaseTitle.setIcon(new ImageIcon(new ImageIcon("./src/imgs/iconCalc.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
                try {
                    ResultSet rsProducts = modelo.getProducts();
                    //ResultSet rsInventory = modelo.getInvProducts();
                    DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
                    boxModel.addElement(new ComboItem("Seleccione un producto", null));
                    while (rsProducts.next()) {
                        boxModel.addElement(new ComboItem(rsProducts.getString(2), rsProducts.getString(1)));
                    }
                    vista.calcDecreaseProductSelect.setModel(boxModel);

                } catch (SQLException ex) {
                    Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
        
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

                        dtm.addRow(new Object[]{rsProducts.getString(2),
                            rsProducts.getDouble(3),
                            rsProducts.getDouble(4),
                            rsProducts.getString(5) + " " + rsProducts.getString(6)
                        });
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.vista.inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dtm = (DefaultTableModel) vista.registerTable.getModel();
                dtm.setColumnCount(0);
                dtm.setRowCount(0);

                dtm.addColumn("Producto");
                dtm.addColumn("Peso Inicial");
                dtm.addColumn("Peso Actual");
                dtm.addColumn("Merma Total");
                dtm.addColumn("Fecha de entrada");

                try {
                    ResultSet rsInventory = modelo.getInvProducts();

                    while (rsInventory.next()) {

                        dtm.addRow(new Object[]{rsInventory.getString(2),
                            rsInventory.getDouble(3),
                            rsInventory.getDouble(4),
                            rsInventory.getDouble(5),
                            rsInventory.getString(6)
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
                        
                        vista.newProviderDocText.setText("");
                        vista.newProviderNameText.setText("");
                        vista.newProviderSurnameText.setText("");
                        vista.newProviderPhoneText.setText("");
                        vista.newProviderMailText.setText("");
                        vista.newProviderAddressText.setText("");
                        
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
                
                int id_proveedor =Integer.parseInt(((ComboItem)boxItem).getValue());
                        
                if (Validation.validarNombres(nombre, false)
                        && Validation.validarDouble(precio)
                        && Validation.validarDouble(merma_promedio)) {
                    
                    try {
                        Double precioParsed = Double.parseDouble(vista.newProductPriceText.getText());
                        Double merma_promedioParsed = Double.parseDouble(vista.newProductDecrAvText.getText());

                        modelo.postProduct(new Product(id_proveedor, nombre, precioParsed, merma_promedioParsed));
                        vista.productsButton.doClick();
                        
                        vista.newProductNameText.setText("");
                        vista.newProductPriceText.setText("");
                        vista.newProductDecrAvText.setText("");
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
        });
        
        this.vista.newInvProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Object boxItem = vista.newInvProductSelect.getSelectedItem();
                
                
                String pesoInicial = vista.newInvKgInitial.getText();
                int idProducto = Integer.parseInt(((ComboItem)boxItem).getValue());
                        
                if (Validation.validarDouble(pesoInicial)) {
                    
                    try {
                        Double pesoInicialParsed = Double.parseDouble(pesoInicial);
                        
                        modelo.postInvProduct(new Inventory(idProducto, pesoInicialParsed, pesoInicialParsed, 0));
                        vista.inventoryButton.doClick();
                        
                        vista.newInvKgInitial.setText("");
                        
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
