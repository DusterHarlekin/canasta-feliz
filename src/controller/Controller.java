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
import java.awt.Component;
import java.awt.Image;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import view.ComboItem;
import view.Login;

public class Controller {

    private final Login login;
    private final Screen vista;
    private final Model modelo;
    private int currentInvProductId;
    private double currentInvProductWeight;
    private double newInvProductWeight;
    private double productDecreaseAverage;
    private double realDecreasePerc;
    private double realWeight;
    private double weightDiff;
    private User currentUser;
    
    public Controller(Screen vista, Model modelo, Login login) {
        this.vista = vista;
        this.modelo = modelo;
        this.login = login;
        
        //PAGE DE MERMA (SELECT DEL PRODUCTO)
        this.vista.calcDecreaseProductSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // SELECT DEL PRODUCTO
                Object boxItem = vista.calcDecreaseProductSelect.getSelectedItem();
                Object firstItem = vista.calcDecreaseProductSelect.getItemAt(0);
                if (((ComboItem) boxItem).getValue() != null) {
                    
                    int idProducto = Integer.parseInt(((ComboItem) boxItem).getValue());
                    vista.calcDecreaseDateSelect.setEnabled(true);
                    try {
                        ResultSet rs = modelo.getInvProductDates(idProducto);
                        ResultSet rsProduct = modelo.getProduct(idProducto);
                        
                        //Producto
                         if (rsProduct.next()) {
                            //newProductProviderSelect.a (new ComboItem(rs.getString("rif")+ " " + rs.getString("nombre"), rs.getInt("id_proveedor")));
                            //USEN ESTE MÉTODO
                            productDecreaseAverage = rsProduct.getDouble("merma_promedio");
                            vista.calcDecreaseAverage.setText(productDecreaseAverage+"%");
                        }
                        
                        //Fechas
                        DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
                        boxModel.addElement(new ComboItem("Seleccione una fecha", null));
                        while (rs.next()) {
                            //newProductProviderSelect.a (new ComboItem(rs.getString("rif")+ " " + rs.getString("nombre"), rs.getInt("id_proveedor")));
                            //USEN ESTE MÉTODO
                            boxModel.addElement(new ComboItem(rs.getString("fecha_entrada"), rs.getString("id_inventario")));

                        }
                      
                        vista.calcDecreaseDateSelect.setModel(boxModel);
                        if (((ComboItem) firstItem).getValue() == null) {
                            vista.calcDecreaseProductSelect.removeItemAt(0);
                        }
                       
                        

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
        
        //PAGE DE MERMA (SELECT DE LA FECHA)
        this.vista.calcDecreaseDateSelect.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
         
                Object boxItem = vista.calcDecreaseDateSelect.getSelectedItem();
                if (((ComboItem) boxItem).getValue() != null) {
                    int idInventario = Integer.parseInt(((ComboItem) boxItem).getValue());
              
                    try {
                        ResultSet rs = modelo.getInvProduct(idInventario);
                        
                        if (rs.next()) {
                            vista.calcDecreaseUseKg.setEnabled(true);
                            vista.calcDecreaseCurrentInv.setText(rs.getDouble("peso_actual") + " Kg.");
                            currentInvProductId = rs.getInt("id_inventario");
                            currentInvProductWeight = rs.getDouble("peso_actual");
                        }
                       

                    } catch (SQLException ex) {
                        Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    vista.calcDecreaseUseKg.setText("");
                    vista.calcDecreaseUseKg.setEnabled(false);
                }

            }
        });
        
        //PAGE DE MERMA (INPUT CANTIDAD REAL)
        // Listen for changes in the text
        this.vista.calcDecreaseCurrentKg.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                update();
            }

            public void update() {

                String decreaseCurrentKg = ("".equals(vista.calcDecreaseCurrentKg.getText().trim()) ? "0" : vista.calcDecreaseCurrentKg.getText());
                Double decreaseUseKg = "".equals(vista.calcDecreaseCurrentKg.getText().trim()) ? 0 : Double.parseDouble(vista.calcDecreaseUseKg.getText());
                if (!Validation.validarDouble(decreaseCurrentKg) || "0".equals(decreaseCurrentKg)) {
                
                    vista.calcDecreaseDiff.setText("0 Kg.");
                    vista.calcDecreaseAverageWeight.setText("0 Kg.");
                   
                } else {
                  
                    realWeight = Double.parseDouble(decreaseCurrentKg);
                    
                    //CALCULO DE MERMA
                    Double averageWeight = decreaseUseKg - decreaseUseKg * (productDecreaseAverage/100);
                    //Double averageDecreaseWeight = decreaseUseKg * (productDecreaseAverage/100);
                    System.out.print(averageWeight);
                    weightDiff = realWeight - averageWeight;
                    realDecreasePerc = 100 - (realWeight / decreaseUseKg) * 100;
                    
                    //MOSTRAMOS EN PANTALLA
                    vista.calcDecreaseDiff.setText(weightDiff + " Kg.");
                    vista.calcDecreaseAverageWeight.setText(averageWeight + " Kg.");
                    vista.calcDecreaseReal.setText(realDecreasePerc + "%");
                }
            }

        });
        
        //PAGE DE MERMA (INPUT CANTIDAD)
        // Listen for changes in the text
        this.vista.calcDecreaseUseKg.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                update();
            }

            public void update() {

                String decreaseUseKg = ("".equals(vista.calcDecreaseUseKg.getText().trim()) ? "0" : vista.calcDecreaseUseKg.getText());
                if (!Validation.validarDouble(decreaseUseKg) || Double.parseDouble(decreaseUseKg) > currentInvProductWeight || Double.parseDouble(decreaseUseKg) == 0) {
                    
                    vista.calcDecreaseNewInv.setText("0 Kg.");
                    vista.calcDecreaseCurrentKg.setText("");
                    vista.calcDecreaseCurrentKg.setEnabled(false);
                } else {
                    newInvProductWeight = currentInvProductWeight - Double.parseDouble(decreaseUseKg);
                    vista.calcDecreaseNewInv.setText(newInvProductWeight + " Kg.");
                     vista.calcDecreaseCurrentKg.setEnabled(true);
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
                dtm.addColumn("Teléfono");
                dtm.addColumn("Correo");

                try {
                    ResultSet rsProviders = modelo.getProviders();

                    while (rsProviders.next()) {

                        dtm.addRow(new Object[]{rsProviders.getString("rif"),
                            rsProviders.getString("nombre"),
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
                            rsProducts.getString(5)
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
                dtm.addColumn("Fecha de entrada");

                try {
                    ResultSet rsInventory = modelo.getInvProducts();

                    while (rsInventory.next()) {

                        dtm.addRow(new Object[]{rsInventory.getString(2),
                            rsInventory.getDouble(3),
                            rsInventory.getDouble(4),
                            rsInventory.getString(5)
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
                String telefono = vista.newProviderPhoneText.getText();
                String correo = vista.newProviderMailText.getText();
                String direccion = vista.newProviderAddressText.getText();
                        
                if (Validation.validarDocumento(rif)
                        && Validation.validarNombres(nombre)
                        && Validation.validarTelf(telefono)
                        && Validation.validarCorreo(correo)
                        && Validation.validarDireccion(direccion)) {

                    try {
                        modelo.postProvider(new Provider(rif, nombre, telefono, correo, direccion));
                        vista.providersButton.doClick();
                        
                        vista.newProviderDocText.setText("");
                        vista.newProviderNameText.setText("");
                        vista.newProviderPhoneText.setText("");
                        vista.newProviderMailText.setText("");
                        vista.newProviderAddressText.setText("");
                        
                    } catch (Exception ex) {
                        System.out.print("ERROR. " + ex.getMessage());
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
                        
                if (Validation.validarNombres(nombre)
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
                        
                        modelo.postInvProduct(new Inventory(idProducto, pesoInicialParsed, pesoInicialParsed));
                        vista.inventoryButton.doClick();
                        
                        vista.newInvKgInitial.setText("");
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
        });
        
        this.vista.calcDecreaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modelo.updateInvProduct(newInvProductWeight, currentInvProductId);
                    
                    vista.homeButton.doClick();
                    Screen.clearForm(vista.calcDecrease);

                } catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
        //LOGIN
        
        this.login.loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!"".equals(login.userText.getText().trim()) && !"".equals(login.passwordText.getText())){
                    try {
                        ResultSet rs = modelo.login(login.userText.getText().trim(), login.passwordText.getText());

                        if(rs.next()){
                            currentUser = new User("", "", rs.getString("nombre"), rs.getString("apellido"), rs.getString("cedula"), rs.getInt("rol"));
                            vista.helloText.setText("¡Hola " + currentUser.getNombre() + " " + currentUser.getApellido() + "!");
                            vista.roleText.setText(currentUser.getRolText());
                            login.dispose();
                            iniciarVista();
                        }else{
                            showMessageDialog(null, "Usuario o contraseña incorrectos. Verifique los datos.");
                        }



                    } catch (Exception ex) {
                        System.out.print(ex.getMessage());
                    }
                }else{
                    showMessageDialog(null, "No debe dejar campos vacíos.");
                }
                

            }
        });
        
        this.vista.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              vista.dispose();
              iniciarVistaLogin();
                

            }
        });
        
    }
    

    public void iniciarVista() {

        vista.setVisible(true);
        vista.cLayout.show(vista.contentLayout, "dashboardView");

        vista.setLocationRelativeTo(null);

        vista.homeButton.putClientProperty("btnLevel", 3);
        vista.providersButton.putClientProperty("btnLevel", 2);
        vista.productsButton.putClientProperty("btnLevel", 2);
        vista.inventoryButton.putClientProperty("btnLevel", 2);
        vista.calcButton.putClientProperty("btnLevel", 3);
        vista.kitchenReportButton.putClientProperty("btnLevel", 2);
        vista.supervisorReportButton.putClientProperty("btnLevel", 2);
        vista.inventoryReportButton.putClientProperty("btnLevel", 2);
        vista.productLoseReportButton.putClientProperty("btnLevel", 2);
        
        vista.configButton.putClientProperty("btnLevel", 1);
        vista.helpButton.putClientProperty("btnLevel", 3);
        vista.exitButton.putClientProperty("btnLevel", 3);
        
         for(Component control : vista.sideBar.getComponents())
        {
            if(control instanceof JButton)
            {
                if ((int)((JButton)control).getClientProperty("btnLevel") < (int)currentUser.getRol()){
                    control.setVisible(false);
                }else{
                    control.setVisible(true);
                }
            }
           
          
        }
        //showMessageDialog(null, "This is even shorter");
    }
    
    public void iniciarVistaLogin() {
        login.userText.setText("");
        login.passwordText.setText("");
        login.setVisible(true);

        login.setLocationRelativeTo(null);

         login.userLogoLabel.setIcon(new ImageIcon(new ImageIcon("./src/imgs/user.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
         login.logoLabel.setIcon(new ImageIcon(new ImageIcon("./src/imgs/logo.png").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT)));
        //showMessageDialog(null, "This is even shorter");
    }
    
    
    
    
    
    
    
    
    
    

}
