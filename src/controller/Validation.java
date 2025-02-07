
package controller;

import java.util.regex.Pattern;
import static javax.swing.JOptionPane.showMessageDialog;

public class Validation {
    
    public static boolean validarNombres(String text) {
        Pattern NAME_PATTERN = Pattern.compile("([a-zA-Z0-9_\\s]+)");
        
        if(!NAME_PATTERN.matcher(text).matches()){
            showMessageDialog(null, "El campo no debe estar vacío");
        }
        return NAME_PATTERN.matcher(text).matches();
    }
    
    public static boolean validarTelf(String text) { 
        
        Pattern TELF_PATTERN = Pattern.compile("\\d{10}");
        
        if(!TELF_PATTERN.matcher(text).matches()){
            showMessageDialog(null, "Debe ingresar un teléfono válido");
        }
        return TELF_PATTERN.matcher(text).matches();
       
    }
    
    public static boolean validarInt(String text){
        int v;
        try {
          v=Integer.parseInt(text);
          return true;
        } catch (NumberFormatException e) {
           showMessageDialog(null, "El campo ingresado debe ser un número sin decimales");
           return false;
        }
    }
    
    public static boolean validarDouble(String text){
        double v;
        try {
          v=Double.parseDouble(text);
          return true;
        } catch (NumberFormatException e) {
             showMessageDialog(null, "El campo ingresado debe ser un número, puede tener decimales");
           return false;
        }
    }
    
    public static boolean validarDocumento(String text){
        
        Pattern DOC_PATTERN = Pattern.compile("^[V|E|J|P][0-9]{5,9}$");
        if(!DOC_PATTERN.matcher(text).matches()){
            showMessageDialog(null, "El campo ingresado debe coincidir con el formato de un documento de identificación venezolano");
        }
        return DOC_PATTERN.matcher(text).matches();
    }
    
    public static boolean validarDireccion(String text){
        
        
        if(text.trim().equals("")){
            showMessageDialog(null, "Debe ingresar una dirección");
            return false;
        }
        return true;
    }
    
    public static boolean validarCorreo(String text){
        
        Pattern CORREO_PATTERN = Pattern.compile("^[^@]+@[^@]+.[a-zA-Z]{2,}$");
        
        if(!CORREO_PATTERN.matcher(text).matches()){
            showMessageDialog(null, "Debe ingresar un correo electrónico");
        }
        return CORREO_PATTERN.matcher(text).matches();
    }
}
