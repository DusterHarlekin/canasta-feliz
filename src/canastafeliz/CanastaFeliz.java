
package canastafeliz;
import controller.Controller;
import static javax.swing.JOptionPane.showMessageDialog;
import view.Screen;
import model.Model;

public class CanastaFeliz {

    public static void main(String[] args) {
        Model modelo = new Model();
        Screen vista = new Screen();        
        Controller controlador = new Controller( vista , modelo );
        
        controlador.iniciarVista();
       
    }
    
}
